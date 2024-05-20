package com.lasagnerd.odin.insights;

import com.intellij.psi.PsiNamedElement;
import com.lasagnerd.odin.lang.psi.*;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
public class OdinSymbol {
    // Can't be an OdinDeclared identifier because here we might have
    @NotNull
    private final PsiNamedElement declaredIdentifier;
    OdinExpression valueExpression;
    OdinType type;
    OdinSymbolType symbolType;
    List<OdinAttributeStatement> attributeStatements;

    private final OdinVisibility visibility;

    public OdinSymbol(@NotNull PsiNamedElement declaredIdentifier, OdinVisibility visibility) {
        this.declaredIdentifier = declaredIdentifier;
        this.visibility = visibility;
    }

    public OdinSymbol(@NotNull PsiNamedElement declaredIdentifier) {
        this.declaredIdentifier = declaredIdentifier;
        this.visibility = OdinVisibility.PUBLIC;
    }

    public boolean isTyped() {
        return type != null;
    }

    public String getName() {
        return declaredIdentifier.getName();
    }

    boolean hasUsing;

    public enum OdinSymbolType {
        PARAMETER,
        FIELD,
        PROCEDURE,
        STRUCT,
        UNION,
        CONSTANT,
        VARIABLE,
        PACKAGE,
    }

    public enum OdinVisibility {
        PACKAGE_PRIVATE,
        FILE_PRIVATE,
        PUBLIC
    }

}