package com.lasagnerd.odin.lang;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.parser.GeneratedParserUtilBase;
import com.intellij.openapi.util.Key;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.lasagnerd.odin.lang.psi.OdinTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.coverage.gnu.trove.TObjectIntHashMap;

import java.util.Stack;

@SuppressWarnings("unused")
public class OdinParserUtil extends GeneratedParserUtilBase {

    private static final Key<TObjectIntHashMap<String>> MODES_KEY = Key.create("MODES_KEY");
    private static final Key<Stack<TObjectIntHashMap<String>>> MODES_STACK_KEY = Key.create("MODES_STACK_KEY");

    public static boolean afterClosingBrace(PsiBuilder builder, int level) {
        IElementType tokenType = lookBehindUntilNoWhitespace(builder);

        return tokenType == OdinTypes.RBRACE;
    }

    public static boolean multilineBlockComment(PsiBuilder builder, int level) {
        IElementType tokenType = lookBehindUntilNoWhitespace(builder);

        return tokenType == OdinTypes.MULTILINE_BLOCK_COMMENT;
    }

    public static boolean atClosingBrace(PsiBuilder builder, int level) {
        IElementType tokenType = builder.getTokenType();

        return tokenType == OdinTypes.RBRACE;
    }

    @Nullable
    private static IElementType lookBehindUntilNoWhitespace(PsiBuilder builder) {

        int i = 0;
        IElementType tokenType;
        int currentOffset = builder.getCurrentOffset();
        do {
            i--;
            tokenType = builder.rawLookup(i);
        } while ((tokenType == TokenType.WHITE_SPACE || tokenType == OdinTypes.NEW_LINE) && currentOffset + i > 0);
        return tokenType;
    }

    public static boolean enterNoBlockMode(PsiBuilder builder, int level) {
        // Save all current flags on a stack
        TObjectIntHashMap<String> flags = getParsingModes(builder);
        TObjectIntHashMap<String> flagsCopy = new TObjectIntHashMap<>(flags);
        Stack<TObjectIntHashMap<String>> stack = builder.getUserData(MODES_STACK_KEY);

        if (stack == null) {
            stack = new Stack<>();
            builder.putUserData(MODES_STACK_KEY, stack);
        }

        stack.push(flagsCopy);
        // Clear all flags
        flags.clear();

        flags.put("NO_BLOCK", flags.get("NO_BLOCK") + 1);
        return true;

    }

    public static boolean isModeOff(PsiBuilder builder, int level, String mode) {
        TObjectIntHashMap<String> flags = getParsingModes(builder);
        return flags.get(mode) <= 0;
    }

    public static boolean exitNoBlockMode(PsiBuilder builder, int level) {
        // Restore all flags from the stack
        TObjectIntHashMap<String> flags = getParsingModes(builder);
        Stack<TObjectIntHashMap<String>> stack = builder.getUserData(MODES_STACK_KEY);
        if(stack != null && !stack.isEmpty()) {
            TObjectIntHashMap<String> flagsCopy = stack.pop();
            flags.clear();
            flagsCopy.forEachEntry((key, value) -> {
                flags.put(key, value);
                return true;
            });
        }
        return true;
    }

    public static boolean enterMode(PsiBuilder builder, int level, String mode) {
        TObjectIntHashMap<String> flags = getParsingModes(builder);
        flags.put(mode, flags.get(mode) + 1);
        return true;
    }

    public static boolean exitMode(PsiBuilder builder, int level, String mode) {
        TObjectIntHashMap<String> flags = getParsingModes(builder);

        flags.put(mode, flags.get(mode) - 1);

        if (flags.get(mode) <= 0) {
            flags.remove(mode);
        }
        return true;
    }

    public static boolean isModeOn(PsiBuilder builder, int level, String mode) {
        TObjectIntHashMap<String> flags = getParsingModes(builder);

        return flags.get(mode) > 0;
    }


    @NotNull
    private static TObjectIntHashMap<String> getParsingModes(@NotNull PsiBuilder builder_) {
        TObjectIntHashMap<String> flags = builder_.getUserData(MODES_KEY);
        if (flags == null) builder_.putUserData(MODES_KEY, flags = new TObjectIntHashMap<>());
        return flags;
    }

    public static boolean beforeOperator(PsiBuilder builder, int level) {
        return OPERATORS.contains(builder.lookAhead(0));
    }

    public static boolean beforeComma(PsiBuilder builder, int level) {
        return builder.getTokenType() == OdinTypes.COMMA;
    }

    public static final TokenSet OPERATORS = TokenSet.create(
            OdinTypes.STAR,
            OdinTypes.DIV,
            OdinTypes.MOD,
            OdinTypes.REMAINDER,
            OdinTypes.PLUS,
            OdinTypes.MINUS,
            OdinTypes.AND,
            OdinTypes.PIPE,
            OdinTypes.TILDE,
            OdinTypes.ANDNOT,
            OdinTypes.ANDAND,
            OdinTypes.OROR,
            OdinTypes.IN,
            OdinTypes.NOT_IN,
            OdinTypes.LT,
            OdinTypes.GT,
            OdinTypes.LTE,
            OdinTypes.GTE,
            OdinTypes.EQEQ,
            OdinTypes.NEQ,
            OdinTypes.LSHIFT,
            OdinTypes.RSHIFT,
            OdinTypes.RANGE_INCLUSIVE,
            OdinTypes.RANGE_EXCLUSIVE,
            OdinTypes.NOT,
            OdinTypes.RANGE,
            OdinTypes.DOT,
            OdinTypes.OR_ELSE,
            OdinTypes.OR_BREAK,
            OdinTypes.OR_CONTINUE,
            OdinTypes.OR_RETURN,
            OdinTypes.QUESTION,
            OdinTypes.IF,
            OdinTypes.WHEN,
            OdinTypes.COMMA
    );
}
