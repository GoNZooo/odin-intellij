package com.lasagnerd.odin.lang.psi;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.LineColumn;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import com.lasagnerd.odin.codeInsight.OdinInsightUtils;
import com.lasagnerd.odin.codeInsight.symbols.OdinSymbol;
import com.lasagnerd.odin.codeInsight.symbols.OdinSymbolTable;
import com.lasagnerd.odin.codeInsight.symbols.OdinSymbolTableResolver;
import com.lasagnerd.odin.codeInsight.symbols.OdinSymbolType;
import com.lasagnerd.odin.codeInsight.typeInference.OdinInferenceEngine;
import com.lasagnerd.odin.codeInsight.typeSystem.TsOdinParameter;
import com.lasagnerd.odin.codeInsight.typeSystem.TsOdinParameterOwner;
import com.lasagnerd.odin.codeInsight.typeSystem.TsOdinType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OdinReference extends PsiReferenceBase<OdinIdentifier> {
    public static Logger LOG = Logger.getInstance(OdinReference.class);

    public OdinReference(@NotNull OdinIdentifier element) {
        super(element);
    }

    @Override
    public @NotNull TextRange getAbsoluteRange() {
        return getRangeInElement().shiftRight(getElement().getIdentifierToken().getTextRange().getStartOffset());
    }

    @Override
    public @Nullable PsiElement resolve() {

        if (getElement().getParent() instanceof OdinImplicitSelectorExpression implicitSelectorExpression) {
            TsOdinType tsOdinType = OdinInferenceEngine.doInferType(implicitSelectorExpression);
            OdinSymbolTable typeElements = OdinInsightUtils.getTypeElements(getElement().getProject(), tsOdinType);
            OdinSymbol symbol = typeElements.getSymbol(getElement().getText());
            if (symbol != null) {
                return symbol.getDeclaredIdentifier();
            }
            return null;
        }

        if (getElement().getParent() instanceof OdinNamedArgument namedArgument) {
            OdinSymbolTable symbolTable = OdinSymbolTableResolver.computeSymbolTable(getElement());
            OdinInsightUtils.OdinCallInfo callInfo = OdinInsightUtils.getCallInfo(symbolTable, namedArgument);
            if (callInfo.callingType() instanceof TsOdinParameterOwner parameterOwner) {
                List<TsOdinParameter> parameters = parameterOwner.getParameters();
                TsOdinParameter tsOdinParameter = parameters.stream()
                        .filter(p -> p.getName().equals(namedArgument.getIdentifier().getText()))
                        .findFirst().orElse(null);

                if (tsOdinParameter != null) {
                    return tsOdinParameter.getIdentifier();
                }
            }
        }

        try {
            OdinSymbol symbol = OdinSymbolTableResolver.findSymbol(getElement());
            if (symbol != null) {
                if (!OdinInsightUtils.isVisible(getElement(), symbol) && symbol.getSymbolType() == OdinSymbolType.PACKAGE_REFERENCE) {
                    return null;
                }
                PsiNamedElement declaredIdentifier = symbol.getDeclaredIdentifier();
                if (declaredIdentifier instanceof OdinImportDeclarationStatement importDeclarationStatement) {
                    return OdinPackageReference.resolvePackagePathDirectory(importDeclarationStatement.getImportPath());
                }
                return declaredIdentifier;

            }
            return null;
        } catch (StackOverflowError e) {
            logStackOverFlowError(getElement(), LOG);
            return null;
        }
    }

    public static void logStackOverFlowError(@NotNull OdinIdentifier element, Logger log) {
        String text = element.getText();
        int textOffset = element.getTextOffset();
        PsiFile containingFile = element.getContainingFile();
        String fileName = "UNKNOWN";
        if (containingFile != null) {
            VirtualFile virtualFile = containingFile.getVirtualFile();
            if (virtualFile != null) {
                fileName = virtualFile.getCanonicalPath();
            }
            LineColumn lineColumn = StringUtil.offsetToLineColumn(containingFile.getText(), textOffset);
            log.error("Stack overflow caused by element with text '%s' in %s:%d:%d".formatted(text,
                    fileName,
                    lineColumn.line + 1,
                    lineColumn.column + 1));
        } else {
            log.error("Stack overflow caused by element with text '%s'".formatted(text));
        }
    }

    @Override
    public Object @NotNull [] getVariants() {
        return new Object[0];
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        if (element instanceof PsiDirectory) {
            return element;
        }
        return super.bindToElement(element);
    }
}
