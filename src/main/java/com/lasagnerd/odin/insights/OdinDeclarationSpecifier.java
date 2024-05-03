package com.lasagnerd.odin.insights;

import com.lasagnerd.odin.lang.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OdinDeclarationSpecifier extends OdinVisitor {

    List<OdinDeclarationSpec> declarationSpecs = new ArrayList<>();

    private OdinDeclarationSpecifier() {

    }

    public static List<OdinDeclarationSpec> getDeclarationSpecs(OdinDeclaration odinDeclaration) {
        OdinDeclarationSpecifier odinDeclarationSpecifier = new OdinDeclarationSpecifier();
        odinDeclaration.accept(odinDeclarationSpecifier);
        return odinDeclarationSpecifier.declarationSpecs;
    }

    @Override
    public void visitParameterInitialization(@NotNull OdinParameterInitialization o) {
        boolean using = o.getParameter().getUsing() != null;
        OdinTypeDefinitionExpression typeDefinitionExpression = null;
        OdinExpression valueExpression;

        if (o.getTypeDefinitionContainer() != null) {
            typeDefinitionExpression = o.getTypeDefinitionContainer().getTypeDefinitionExpression();
        }

        valueExpression = o.getExpression();

        for (var declaredIdentifier : o.getDeclaredIdentifiers()) {
            OdinDeclarationSpec odinDeclarationSpec = new OdinDeclarationSpec();
            odinDeclarationSpec.setDeclaredIdentifier(declaredIdentifier);
            odinDeclarationSpec.setValueExpression(valueExpression);
            odinDeclarationSpec.setTypeDefinitionExpression(typeDefinitionExpression);
            odinDeclarationSpec.setHasUsing(using);

            declarationSpecs.add(odinDeclarationSpec);
        }
    }

    @Override
    public void visitParameterDecl(@NotNull OdinParameterDecl o) {
        OdinTypeDefinitionExpression typeDefinition = o.getTypeDefinition();
        for (OdinParameter odinParameter : o.getParameterList()) {
            OdinDeclarationSpec spec = new OdinDeclarationSpec();
            OdinDeclaredIdentifier declaredIdentifier = odinParameter.getDeclaredIdentifier();
            boolean hasUsing = odinParameter.getUsing() != null;
            spec.setDeclaredIdentifier(declaredIdentifier);
            spec.setHasUsing(hasUsing);
            spec.setTypeDefinitionExpression(typeDefinition);
            declarationSpecs.add(spec);
        }
    }

    @Override
    public void visitVariadicParameterDeclaration(@NotNull OdinVariadicParameterDeclaration o) {
        OdinDeclarationSpec odinDeclarationSpec = new OdinDeclarationSpec();
        odinDeclarationSpec.setDeclaredIdentifier(o.getParameter().getDeclaredIdentifier());
        odinDeclarationSpec.setTypeDefinitionExpression(o.getTypeDefinitionContainer().getTypeDefinitionExpression());
        odinDeclarationSpec.setVariadic(true);
        odinDeclarationSpec.setHasUsing(false);

        declarationSpecs.add(odinDeclarationSpec);
    }

    @Override
    public void visitVariableDeclarationStatement(@NotNull OdinVariableDeclarationStatement o) {
        boolean hasUsing = o.getUsing() != null;

        for (var declaredIdentifier : o.getDeclaredIdentifiers()) {
            OdinDeclarationSpec odinDeclarationSpec = new OdinDeclarationSpec();
            odinDeclarationSpec.setTypeDefinitionExpression(o.getTypeDefinitionExpression());
            odinDeclarationSpec.setHasUsing(hasUsing);
            odinDeclarationSpec.setDeclaredIdentifier(declaredIdentifier);
            declarationSpecs.add(odinDeclarationSpec);
        }
    }

    @Override
    public void visitVariableInitializationStatement(@NotNull OdinVariableInitializationStatement o) {
        boolean hasUsing = o.getUsing() != null;
        OdinTypeDefinitionExpression typeDefinition = o.getTypeDefinition();
        for (int i = 0; i < o.getDeclaredIdentifiers().size(); i++) {
            OdinDeclarationSpec odinDeclarationSpec = new OdinDeclarationSpec();
            OdinExpressionsList expressionsList = o.getExpressionsList();
            if (expressionsList.getExpressionList().size() > i) {
                OdinExpression odinExpression = expressionsList.getExpressionList().get(i);
                odinDeclarationSpec.setValueExpression(odinExpression);
            }
            odinDeclarationSpec.setDeclaredIdentifier(o.getDeclaredIdentifiers().get(i));
            odinDeclarationSpec.setHasUsing(hasUsing);
            odinDeclarationSpec.setTypeDefinitionExpression(typeDefinition);

            declarationSpecs.add(odinDeclarationSpec);
        }
    }
}