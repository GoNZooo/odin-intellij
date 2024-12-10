package com.lasagnerd.odin.codeInsight;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.lasagnerd.odin.codeInsight.dataflow.OdinSymbolValueStore;
import com.lasagnerd.odin.codeInsight.evaluation.EvOdinValue;
import com.lasagnerd.odin.codeInsight.symbols.OdinSymbol;
import com.lasagnerd.odin.codeInsight.symbols.OdinVisibility;
import com.lasagnerd.odin.codeInsight.typeSystem.TsOdinType;
import com.lasagnerd.odin.lang.psi.OdinDeclaration;
import com.lasagnerd.odin.lang.psi.OdinDeclaredIdentifier;
import com.lasagnerd.odin.lang.psi.OdinScopeBlock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.With;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;

@AllArgsConstructor
@Getter
public class OdinContext {

    @Setter
    OdinScopeBlock scopeBlock;

    @Setter
    Map<OdinDeclaration, List<OdinSymbol>> declarationSymbols = new HashMap<>();

    @Setter
    OdinContext parentContext;


    public List<OdinSymbol> getDeclarationSymbols(@NotNull OdinDeclaration declaration) {
        return declarationSymbols.getOrDefault(declaration, Collections.emptyList());
    }

    @Setter
    private String packagePath;
    public static final OdinContext EMPTY = new OdinContext();

    Map<String, List<OdinSymbol>> symbolTable = new HashMap<>();

    // Used for specialization
    Map<String, EvOdinValue> polyParaValueStorage = new HashMap<>();

    @With
    OdinSymbolValueStore symbolValueStore = new OdinSymbolValueStore();

    public OdinContext(String packagePath) {
        this.packagePath = packagePath;
    }


    /**
     * Used substituting polymorphic types. The key
     * is the name of the polymorphic type
     */
    Map<String, TsOdinType> typeTable = new HashMap<>();

    /**
     * Acts as a cache for already defined types. Prevents stackoverflow in circular references
     */
    Map<OdinDeclaredIdentifier, TsOdinType> knownTypes = new HashMap<>();

    Map<TsOdinType, Map<List<? extends PsiElement>, TsOdinType>> specializedTypes = new HashMap<>();

    public OdinContext() {

    }

    public TsOdinType getSpecializedType(TsOdinType genericType, List<PsiElement> arguments) {
        Map<List<? extends PsiElement>, TsOdinType> argumentsMap = this.specializedTypes.get(genericType);
        if (argumentsMap != null) {
            return argumentsMap.get(arguments);
        }
        return null;
    }

    public void addSpecializedType(TsOdinType genericType, TsOdinType specializedType, List<PsiElement> arguments) {
        specializedTypes.computeIfAbsent(genericType, t -> new HashMap<>()).put(arguments, specializedType);
    }

    @Nullable
    public OdinSymbol getSymbol(String name) {
        List<OdinSymbol> symbols = symbolTable.get(name);
        if (symbols != null && !symbols.isEmpty())
            return symbols.getFirst();

        return parentContext != null ? parentContext.getSymbol(name) : null;
    }

    @NotNull
    public List<OdinSymbol> getSymbols(String name) {
        List<OdinSymbol> symbols = symbolTable.get(name);
        if (symbols == null) {
            return parentContext != null ? parentContext.getSymbols(name) : Collections.emptyList();
        }
        return symbols;
    }

    public EvOdinValue getValue(String name) {
        EvOdinValue value = polyParaValueStorage.get(name);
        if (value != null)
            return value;

        return parentContext != null ? parentContext.getValue(name) : null;
    }

    public TsOdinType getType(String polymorphicParameter) {
        TsOdinType tsOdinType = typeTable.get(polymorphicParameter);
        if (tsOdinType == null) {
            return parentContext != null ? parentContext.getType(polymorphicParameter) : null;
        }
        return tsOdinType;
    }

    public void addType(String typeName, TsOdinType type) {
        typeTable.put(typeName, type);
    }

    public void addTypes(OdinContext context) {
        typeTable.putAll(context.typeTable);
    }

    public Collection<PsiNamedElement> getNamedElements() {
        return symbolTable.values().stream()
                .filter(c -> !c.isEmpty())
                .map(List::getFirst)
                .map(OdinSymbol::getDeclaredIdentifier)
                .toList();
    }

    public Collection<OdinSymbol> getFilteredSymbols(Predicate<OdinSymbol> filter) {
        return symbolTable.values()
                .stream()
                .flatMap(List::stream)
                .filter(filter)
                .toList();
    }

    public Collection<OdinSymbol> getSymbols() {
        return symbolTable.values().stream().flatMap(Collection::stream).toList();
    }

    public Collection<OdinSymbol> getSymbols(OdinVisibility minVisibility) {
        return getFilteredSymbols(symbol -> symbol.getVisibility().compareTo(minVisibility) >= 0);
    }


    public void addAll(Collection<? extends OdinSymbol> symbols) {
        addAll(symbols, true);
    }

    public void addAll(Collection<? extends OdinSymbol> symbols, boolean override) {
        for (OdinSymbol symbol : symbols) {
            if (!symbolTable.containsKey(symbol.getName()) || override) {
                symbolTable.computeIfAbsent(symbol.getName(), s -> new ArrayList<>()).add(symbol);
            }
        }
    }

    public void putAll(OdinContext context) {
        this.symbolTable.putAll(context.symbolTable);
        typeTable.putAll(context.typeTable);
        knownTypes.putAll(context.knownTypes);
        specializedTypes.putAll(context.specializedTypes);
        if (context.getParentContext() != null) {
            if (parentContext == null) {
                parentContext = new OdinContext();
            }
            parentContext.putAll(context.getParentContext());
        }
    }

    public void addKnownType(OdinDeclaredIdentifier declaredIdentifier, TsOdinType type) {
        knownTypes.put(declaredIdentifier, type);
    }

    public void add(OdinSymbol odinSymbol) {
        add(odinSymbol, true);
    }

    public void add(PsiNamedElement namedElement) {
        add(new OdinSymbol(namedElement));
    }

    public void add(OdinSymbol symbol, boolean override) {

        symbolTable.computeIfAbsent(symbol.getName(), s -> new ArrayList<>()).add(symbol);


    }

    public static OdinContext from(Collection<OdinSymbol> symbols) {
        if (symbols.isEmpty())
            return OdinContext.EMPTY;

        OdinContext newContext = new OdinContext();
        for (var symbol : symbols) {
            newContext.symbolTable
                    .computeIfAbsent(symbol.getName(), s -> new ArrayList<>())
                    .add(symbol);
        }

        return newContext;
    }


    public static OdinContext from(List<OdinSymbol> identifiers, String packagePath) {
        OdinContext newContext = from(identifiers);
        newContext.packagePath = packagePath;

        return newContext;
    }

    public OdinContext with(List<OdinSymbol> identifiers) {
        OdinContext newContext = from(identifiers);
        newContext.packagePath = this.packagePath;

        return newContext;
    }

    public OdinContext with(String packagePath) {
        OdinContext newContext = new OdinContext();
        newContext.symbolTable = this.symbolTable;
        newContext.packagePath = packagePath;
        newContext.parentContext = parentContext;
        return newContext;
    }

    public OdinContext flatten() {
        OdinContext odinContext = new OdinContext();
        odinContext.setPackagePath(packagePath);

        OdinContext curr = this;
        do {
            odinContext.addAll(curr.getSymbols(), false);
            curr = curr.getParentContext();
        } while (curr != null);

        return odinContext;
    }

    public void setRoot(OdinContext context) {
        if (parentContext != null) {
            parentContext.setRoot(context);
        } else {
            if (context != this) {
                parentContext = context;
            }
        }
    }

    public void addAll(Map<String, EvOdinValue> builtInValues) {
        polyParaValueStorage.putAll(builtInValues);
    }

    public boolean isShadowing(String name) {
        return this.symbolTable.get(name) != null &&
                (parentContext != null && parentContext.getSymbol(name) != null);
    }


}
