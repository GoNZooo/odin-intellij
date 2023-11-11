package com.lasagnerd.odin.insights;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.icons.ExpUiIcons;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.lasagnerd.odin.OdinIcons;
import com.lasagnerd.odin.lang.psi.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.*;

import static com.intellij.patterns.PlatformPatterns.psiElement;

public class OdinCompletionContributor extends CompletionContributor {

    public static final PsiElementPattern.@NotNull Capture<PsiElement> REFERENCE = psiElement().withElementType(OdinTypes.IDENTIFIER_TOKEN).afterLeaf(".");

    public static final @NotNull ElementPattern<PsiElement> AT_IDENTIFIER = psiElement().withElementType(OdinTypes.IDENTIFIER_TOKEN).andNot(REFERENCE);

    public OdinCompletionContributor() {

        extend(CompletionType.BASIC,
                REFERENCE,
                new CompletionProvider<>() {

                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters,
                                                  @NotNull ProcessingContext context,
                                                  @NotNull CompletionResultSet result) {
                        Project project = parameters.getPosition().getProject();
                        PsiElement position = parameters.getPosition().getParent();

                        OdinFile odinFile = (OdinFile) position.getContainingFile();
                        OdinFile odinOriginalFile = (OdinFile) parameters.getOriginalFile();
                        OdinFileScope fileScope = odinFile.getFileScope();

                        // This constitutes our scope
                        OdinRefExpression reference = (OdinRefExpression) PsiTreeUtil.findSiblingBackward(position, OdinTypes.REF_EXPRESSION, false, null);
                        if (reference != null) {

                            OdinDeclaredIdentifier identifierReference = (OdinDeclaredIdentifier) Objects.requireNonNull(reference.getIdentifier().getReference())
                                    .resolve();

                            if (identifierReference != null) {
                                OdinVariableInitializationStatement initialization = OdinInsightUtils.findFirstParentOfType(identifierReference,
                                        true,
                                        OdinVariableInitializationStatement.class);

                                OdinExpression odinExpression = initialization.getExpressionsList().getExpressionList().get(0);
                                OdinCompoundLiteral compoundLiteral = PsiTreeUtil.findChildOfType(odinExpression, OdinCompoundLiteral.class);

                                findCompletionsForStruct(result, compoundLiteral);
                            }

                            // Check if reference is an import
                            String importName = reference.getIdentifier().getText();

                            List<OdinDeclaredIdentifier> fileScopeDeclarations = OdinInsightUtils.findDeclarationsInImports(odinOriginalFile.getVirtualFile().getPath(),
                                    fileScope,
                                    importName,
                                    project);

                            addLookUpElements(result, fileScopeDeclarations);
                        }
                    }
                }
        );

        extend(CompletionType.BASIC, AT_IDENTIFIER,
                new CompletionProvider<>() {
                    @Override
                    protected void addCompletions(@NotNull CompletionParameters parameters,
                                                  @NotNull ProcessingContext context,
                                                  @NotNull CompletionResultSet result) {
                        PsiElement position = parameters.getPosition();


                        PsiElement parent = OdinInsightUtils.findFirstParentOfType(
                                position,
                                true,
                                OdinRefExpression.class);

                        if (parent != null) {
                            // Struct construction
                            OdinCompoundLiteral compoundLiteral
                                    = OdinInsightUtils.findFirstParentOfType(parent, true, OdinCompoundLiteral.class);

                            findCompletionsForStruct(result, compoundLiteral);
                        }

                        List<OdinDeclaredIdentifier> declarations = OdinInsightUtils
                                .findDeclarations(position, e -> true);
                        addLookUpElements(result, declarations);


                        OdinFile originalFile = (OdinFile) parameters.getOriginalFile();
                        OdinFileScope fileScope = originalFile.getFileScope();
                        if (fileScope == null) {
                            return;
                        }
                        Map<String, ImportInfo> importInfo = OdinInsightUtils.collectImportStatements(fileScope);
                        for (Map.Entry<String, ImportInfo> entry : importInfo.entrySet()) {
                            String name = entry.getKey();
                            ImportInfo info = entry.getValue();

                            LookupElementBuilder element = LookupElementBuilder.create(name)
                                    .withIcon(ExpUiIcons.Nodes.Package)
                                    .withTypeText(info.path())
                                    .withTailText(" -> " + info.library());

                            result.addElement(PrioritizedLookupElement.withPriority(element, 100));

                        }
                    }
                }
        );

    }

    private static void addLookUpElements(@NotNull CompletionResultSet result, List<OdinDeclaredIdentifier> declarations) {
        for (PsiElement declaration : declarations) {
            if (declaration instanceof PsiNameIdentifierOwner declaredIdentifier) {
                OdinInsightUtils.OdinTypeType typeType = OdinInsightUtils.classify((OdinDeclaredIdentifier) declaredIdentifier);
                Icon icon = switch (typeType) {
                    case STRUCT -> OdinIcons.Types.Struct;
                    case ENUM -> ExpUiIcons.Nodes.Enum;
                    case UNION -> OdinIcons.Types.Union;
                    case PROCEDURE, PROCEUDRE_OVERLOAD -> ExpUiIcons.Nodes.Function;
                    case VARIABLE -> ExpUiIcons.Nodes.Variable;
                    case CONSTANT -> ExpUiIcons.Nodes.Constant;
                    case UNKNOWN -> ExpUiIcons.FileTypes.Unknown;
                };


                if (typeType == OdinInsightUtils.OdinTypeType.PROCEDURE) {
                    LookupElementBuilder element = LookupElementBuilder.create(declaredIdentifier.getText()).withIcon(icon);
                    OdinProcedureDeclarationStatement firstParentOfType = OdinInsightUtils.findFirstParentOfType(declaredIdentifier, true, OdinProcedureDeclarationStatement.class);
                    element = procedureLookupElement(element, firstParentOfType).withInsertHandler(procedureInsertHandler());
                    result.addElement(PrioritizedLookupElement.withPriority(element, 0));
                } else if (typeType == OdinInsightUtils.OdinTypeType.PROCEUDRE_OVERLOAD) {
                    OdinProcedureOverloadStatement procedureOverloadStatement = OdinInsightUtils.findFirstParentOfType(declaredIdentifier, true, OdinProcedureOverloadStatement.class);
                    for (OdinIdentifier odinIdentifier : procedureOverloadStatement.getIdentifierList()) {
                        var resolvedReference = odinIdentifier.getReference();

                        if (resolvedReference != null) {
                            PsiElement resolved = resolvedReference.resolve();
                            if (resolved instanceof OdinDeclaredIdentifier) {
                                OdinProcedureDeclarationStatement declaringProcedure = OdinInsightUtils.getDeclaringProcedure((OdinDeclaredIdentifier) resolved);
                                if (declaringProcedure != null) {
                                    LookupElementBuilder element = LookupElementBuilder.create(resolved, declaredIdentifier.getText())
                                            .withItemTextItalic(true)
                                            .withIcon(icon)
                                            .withInsertHandler(procedureInsertHandler());
                                    element = procedureLookupElement(element, declaringProcedure);
                                    result.addElement(PrioritizedLookupElement.withPriority(element, 0));
                                }
                            }
                        }
                    }
                } else {
                    LookupElementBuilder element = LookupElementBuilder.create(declaredIdentifier).withIcon(icon);
                    result.addElement(PrioritizedLookupElement.withPriority(element, 0));
                }
            }
        }
    }

    @NotNull
    private static InsertHandler<LookupElement> procedureInsertHandler() {
        return (insertionContext, lookupElement) -> {
            insertionContext.getDocument().insertString(insertionContext.getTailOffset(), "(");
            insertionContext.getDocument().insertString(insertionContext.getTailOffset(), ")");
            insertionContext.getEditor().getCaretModel().moveToOffset(insertionContext.getTailOffset() - 1);
        };
    }

    @NotNull
    private static LookupElementBuilder procedureLookupElement(LookupElementBuilder element, OdinProcedureDeclarationStatement declaringProcedure) {
        var params = declaringProcedure.getProcedureType().getParamEntries();
        String tailText = "(";
        if (params != null) {
            tailText += params.getText();
        }
        tailText += ")";
        element = element.withTailText(tailText);

        OdinReturnType returnType = declaringProcedure.getProcedureType().getReturnType();
        if (returnType != null) {
            element = element.withTypeText(returnType
                    .getText());
        }
        return element;
    }

    private static void findCompletionsForStruct(@NotNull CompletionResultSet result, OdinCompoundLiteral compoundLiteral) {
        if (compoundLiteral == null || !(compoundLiteral.getType() instanceof OdinConcreteType concreteType)) {
            return;
        }

        var identifierExpressionList = concreteType.getIdentifierList();
        var identifier = identifierExpressionList.get(0);
        PsiElement reference = Objects.requireNonNull(identifier.getReference()).resolve();

        if (reference == null || !(reference.getParent() instanceof OdinStructDeclarationStatement structDeclarationStatement)) {
            return;
        }

        String structName = structDeclarationStatement.getDeclaredIdentifier().getText();
        OdinStructBody structBody = structDeclarationStatement.getStructType().getStructBlock().getStructBody();
        if (structBody == null) {
            return;
        }

        List<OdinFieldDeclarationStatement> fieldDeclarationStatementList = structBody.getFieldDeclarationStatementList();

        for (OdinFieldDeclarationStatement fieldDeclaration : fieldDeclarationStatementList) {
            String typeOfField = fieldDeclaration.getTypeDefinition().getText();
            for (OdinDeclaredIdentifier declaredIdentifier : fieldDeclaration.getDeclaredIdentifierList()) {
                LookupElementBuilder element = LookupElementBuilder.create((PsiNameIdentifierOwner) declaredIdentifier)
                        .withIcon(ExpUiIcons.Nodes.Property)
                        .withBoldness(true)
                        .withTypeText(typeOfField)
                        .withTailText(" -> " + structName);

                result.addElement(PrioritizedLookupElement.withPriority(element, 100));
            }
        }
    }
}
