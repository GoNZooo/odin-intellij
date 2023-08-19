// Generated by JFlex 1.9.1 http://jflex.de/  (tweaked for IntelliJ platform)
// source: Odin.flex

package com.lasagnerd.odin.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.lasagnerd.odin.lang.psi.OdinTypes.*;


public class OdinLexer implements FlexLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int DQ_STRING_STATE = 2;
  public static final int SQ_STRING_STATE = 4;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0,  0,  1,  1,  2, 2
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\u10ff\u0100";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\1\0\1\1\1\3\22\0\1\1"+
    "\1\4\1\5\1\6\1\0\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22"+
    "\1\23\6\24\2\25\1\26\1\27\1\30\1\31\1\32"+
    "\1\33\1\34\4\35\1\36\1\35\16\37\1\40\5\37"+
    "\1\41\1\42\1\43\1\44\1\45\1\0\1\46\1\47"+
    "\1\50\1\51\1\52\1\53\1\54\1\55\1\56\1\57"+
    "\1\60\1\61\1\62\1\63\1\64\1\65\1\37\1\66"+
    "\1\67\1\70\1\71\1\72\1\73\1\74\1\75\1\37"+
    "\1\76\1\77\1\100\1\101\u0181\0";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[512];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\3\0\1\1\2\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\17"+
    "\1\20\2\21\1\22\1\23\1\24\1\25\1\26\1\27"+
    "\1\30\1\31\1\32\1\33\1\34\20\31\1\35\1\36"+
    "\1\37\1\40\1\41\1\42\2\41\1\43\1\41\2\2"+
    "\1\44\1\45\1\46\1\47\1\50\1\51\1\52\1\53"+
    "\1\0\1\54\1\55\1\56\1\0\1\57\1\60\3\0"+
    "\1\61\2\0\1\62\1\63\1\64\1\65\1\66\7\31"+
    "\1\67\5\31\1\70\1\31\1\71\15\31\1\72\1\73"+
    "\1\74\1\41\4\0\1\75\1\76\1\77\1\100\1\101"+
    "\1\0\1\102\1\0\1\102\1\103\1\104\1\105\1\106"+
    "\1\107\13\31\1\110\1\31\1\111\1\31\1\112\14\31"+
    "\1\113\3\0\1\114\1\115\3\31\1\116\1\117\4\31"+
    "\1\120\1\121\11\31\1\122\4\31\1\123\2\31\1\124"+
    "\1\0\2\31\1\125\1\31\1\126\3\31\1\127\13\31"+
    "\1\130\1\131\1\0\7\31\1\132\1\133\1\134\3\31"+
    "\1\135\1\136\1\137\2\31\1\140\2\31\1\141\1\31"+
    "\1\142\1\143\1\31\1\144\2\31\1\145\1\146\3\31"+
    "\1\147\1\31\1\150\1\151\1\31\1\152";

  private static int [] zzUnpackAction() {
    int [] result = new int[265];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\102\0\204\0\306\0\u0108\0\u014a\0\u018c\0\306"+
    "\0\306\0\u01ce\0\u0210\0\306\0\306\0\306\0\u0252\0\u0294"+
    "\0\306\0\u02d6\0\u0318\0\u035a\0\u039c\0\u03de\0\306\0\306"+
    "\0\u0420\0\u0462\0\u04a4\0\306\0\306\0\u04e6\0\306\0\306"+
    "\0\306\0\u0528\0\u056a\0\u05ac\0\u05ee\0\u0630\0\u0672\0\u06b4"+
    "\0\u06f6\0\u0738\0\u077a\0\u07bc\0\u07fe\0\u0840\0\u0882\0\u08c4"+
    "\0\u0906\0\306\0\u0948\0\306\0\u098a\0\u09cc\0\306\0\u0a0e"+
    "\0\u0a50\0\306\0\u0a92\0\306\0\u0ad4\0\306\0\u0b16\0\306"+
    "\0\u0b58\0\306\0\306\0\306\0\306\0\u0b9a\0\306\0\306"+
    "\0\u0bdc\0\u0c1e\0\u0c60\0\306\0\u0ca2\0\u0ce4\0\u0d26\0\306"+
    "\0\u0d68\0\u0daa\0\u0dec\0\306\0\306\0\306\0\u0e2e\0\u0e70"+
    "\0\u0eb2\0\u0ef4\0\u0f36\0\u0f78\0\u0fba\0\u0ffc\0\u04e6\0\u103e"+
    "\0\u1080\0\u10c2\0\u1104\0\u1146\0\u04e6\0\u1188\0\u04e6\0\u11ca"+
    "\0\u120c\0\u124e\0\u1290\0\u12d2\0\u1314\0\u1356\0\u1398\0\u13da"+
    "\0\u141c\0\u145e\0\u14a0\0\u14e2\0\306\0\u1524\0\306\0\306"+
    "\0\u1566\0\u15a8\0\u15ea\0\u162c\0\306\0\306\0\306\0\306"+
    "\0\306\0\u166e\0\u16b0\0\u16f2\0\u1734\0\u1776\0\u17b8\0\u17fa"+
    "\0\306\0\306\0\u183c\0\u187e\0\u18c0\0\u1902\0\u1944\0\u1986"+
    "\0\u19c8\0\u1a0a\0\u1a4c\0\u1a8e\0\u1ad0\0\u1b12\0\u1b54\0\u04e6"+
    "\0\u1b96\0\u04e6\0\u1bd8\0\u1c1a\0\u1c5c\0\u1c9e\0\u1ce0\0\u1d22"+
    "\0\u1d64\0\u1da6\0\u1de8\0\u1e2a\0\u1e6c\0\u1eae\0\306\0\u1ef0"+
    "\0\u1f32\0\u1f74\0\306\0\306\0\u1fb6\0\u1ff8\0\u203a\0\u04e6"+
    "\0\u04e6\0\u207c\0\u20be\0\u2100\0\u2142\0\u04e6\0\u04e6\0\u2184"+
    "\0\u21c6\0\u2208\0\u224a\0\u228c\0\u22ce\0\u2310\0\u2352\0\u2394"+
    "\0\u04e6\0\u23d6\0\u2418\0\u245a\0\u249c\0\u04e6\0\u24de\0\u2520"+
    "\0\u04e6\0\u2562\0\u25a4\0\u25e6\0\u04e6\0\u2628\0\u04e6\0\u266a"+
    "\0\u26ac\0\u26ee\0\u04e6\0\u2730\0\u2772\0\u27b4\0\u27f6\0\u2838"+
    "\0\u287a\0\u28bc\0\u28fe\0\u2940\0\u2982\0\u29c4\0\u04e6\0\u04e6"+
    "\0\u2a06\0\u2a48\0\u2a8a\0\u2acc\0\u2b0e\0\u2b50\0\u2b92\0\u2bd4"+
    "\0\u04e6\0\u04e6\0\u04e6\0\u2c16\0\u2c58\0\u2c9a\0\u04e6\0\u04e6"+
    "\0\u04e6\0\u2cdc\0\u2d1e\0\u04e6\0\u2d60\0\u2da2\0\u04e6\0\u2de4"+
    "\0\u04e6\0\u04e6\0\u2e26\0\u04e6\0\u2e68\0\u2eaa\0\u04e6\0\u04e6"+
    "\0\u2eec\0\u2f2e\0\u2f70\0\u04e6\0\u2fb2\0\u04e6\0\u04e6\0\u2ff4"+
    "\0\u04e6";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[265];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length() - 1;
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpacktrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\4\1\5\2\6\1\7\1\10\1\11\1\12\1\13"+
    "\1\14\1\15\1\16\1\17\1\20\1\21\1\22\1\23"+
    "\1\24\1\25\3\26\1\27\1\30\1\31\1\32\1\33"+
    "\1\34\1\35\4\36\1\37\1\4\1\40\1\41\1\36"+
    "\1\42\1\43\1\44\1\45\1\46\1\47\2\36\1\50"+
    "\3\36\1\51\1\52\1\53\1\54\1\55\1\56\1\57"+
    "\1\60\1\36\1\61\2\36\1\62\1\63\1\64\1\65"+
    "\2\66\2\4\1\66\1\67\34\66\1\70\37\66\2\71"+
    "\2\4\5\71\1\72\30\71\1\73\37\71\103\0\1\5"+
    "\1\74\1\75\100\0\2\6\127\0\1\76\57\0\1\77"+
    "\21\0\1\100\60\0\1\101\20\0\1\102\47\0\1\103"+
    "\31\0\1\104\101\0\1\105\67\0\1\106\11\0\1\107"+
    "\1\110\67\0\1\111\75\0\1\112\4\0\1\113\7\0"+
    "\1\114\70\0\1\115\1\0\4\26\10\0\1\116\6\0"+
    "\1\26\1\0\1\117\2\0\1\116\3\0\3\120\3\0"+
    "\1\121\7\0\1\122\25\0\1\115\1\0\4\26\10\0"+
    "\1\116\6\0\1\26\4\0\1\116\3\0\3\120\51\0"+
    "\1\123\1\124\101\0\1\125\101\0\1\126\1\127\71\0"+
    "\4\36\7\0\4\36\4\0\31\36\26\0\4\36\7\0"+
    "\4\36\4\0\24\36\1\130\4\36\26\0\4\36\7\0"+
    "\4\36\4\0\11\36\1\131\7\36\1\132\7\36\26\0"+
    "\4\36\7\0\4\36\4\0\1\36\1\133\15\36\1\134"+
    "\11\36\26\0\4\36\7\0\4\36\4\0\5\36\1\135"+
    "\3\36\1\136\5\36\1\137\10\36\1\140\26\0\4\36"+
    "\7\0\4\36\4\0\14\36\1\141\1\36\1\142\12\36"+
    "\26\0\4\36\7\0\4\36\4\0\1\36\1\143\15\36"+
    "\1\144\11\36\26\0\4\36\7\0\4\36\4\0\6\36"+
    "\1\145\6\36\1\146\1\147\12\36\26\0\4\36\7\0"+
    "\4\36\4\0\1\36\1\150\27\36\26\0\4\36\7\0"+
    "\4\36\4\0\11\36\1\151\5\36\1\152\11\36\26\0"+
    "\4\36\7\0\4\36\4\0\21\36\1\153\7\36\26\0"+
    "\4\36\7\0\4\36\4\0\1\36\1\154\17\36\1\155"+
    "\7\36\26\0\4\36\7\0\4\36\4\0\5\36\1\156"+
    "\23\36\26\0\4\36\7\0\4\36\4\0\23\36\1\157"+
    "\2\36\1\160\2\36\26\0\4\36\7\0\4\36\4\0"+
    "\21\36\1\161\7\36\26\0\4\36\7\0\4\36\4\0"+
    "\16\36\1\162\3\36\1\163\6\36\26\0\4\36\7\0"+
    "\4\36\4\0\10\36\1\164\20\36\35\0\1\165\45\0"+
    "\1\166\33\0\1\167\50\0\2\66\2\0\1\66\1\0"+
    "\34\66\1\0\37\66\5\0\1\170\14\0\3\171\13\0"+
    "\1\172\5\0\2\170\2\0\2\170\7\0\1\170\2\0"+
    "\1\170\1\0\1\170\1\173\1\170\1\0\1\174\5\0"+
    "\2\71\2\0\5\71\1\0\30\71\1\0\37\71\11\0"+
    "\1\170\72\0\1\74\130\0\1\175\101\0\1\176\67\0"+
    "\1\177\112\0\1\200\1\201\50\0\14\112\1\202\65\112"+
    "\2\113\2\0\76\113\22\0\4\203\71\0\1\204\1\0"+
    "\1\204\2\0\4\205\76\0\2\206\100\0\3\207\77\0"+
    "\4\210\7\0\2\210\7\0\6\210\57\0\1\211\101\0"+
    "\1\212\72\0\4\36\7\0\4\36\4\0\23\36\1\213"+
    "\5\36\26\0\4\36\7\0\4\36\4\0\23\36\1\214"+
    "\5\36\26\0\4\36\7\0\4\36\4\0\5\36\1\215"+
    "\23\36\26\0\4\36\7\0\4\36\4\0\22\36\1\216"+
    "\6\36\26\0\4\36\7\0\4\36\4\0\16\36\1\217"+
    "\12\36\26\0\4\36\7\0\4\36\4\0\6\36\1\220"+
    "\22\36\26\0\4\36\7\0\4\36\4\0\22\36\1\221"+
    "\6\36\26\0\4\36\7\0\4\36\4\0\16\36\1\222"+
    "\12\36\26\0\4\36\7\0\4\36\4\0\22\36\1\223"+
    "\6\36\26\0\4\36\7\0\4\36\4\0\24\36\1\224"+
    "\4\36\26\0\4\36\7\0\4\36\4\0\14\36\1\225"+
    "\14\36\26\0\4\36\7\0\4\36\4\0\21\36\1\226"+
    "\7\36\26\0\4\36\7\0\4\36\4\0\20\36\1\227"+
    "\10\36\26\0\4\36\7\0\4\36\4\0\20\36\1\230"+
    "\2\36\1\231\5\36\26\0\4\36\7\0\4\36\4\0"+
    "\14\36\1\232\14\36\26\0\4\36\7\0\4\36\4\0"+
    "\23\36\1\233\5\36\26\0\4\36\7\0\4\36\4\0"+
    "\1\234\30\36\26\0\4\36\7\0\4\36\4\0\3\36"+
    "\1\235\25\36\26\0\4\36\7\0\4\36\4\0\17\36"+
    "\1\236\11\36\26\0\4\36\7\0\4\36\4\0\23\36"+
    "\1\237\5\36\26\0\4\36\7\0\4\36\4\0\21\36"+
    "\1\240\7\36\26\0\4\36\7\0\4\36\4\0\11\36"+
    "\1\241\17\36\26\0\4\36\7\0\4\36\4\0\1\36"+
    "\1\242\22\36\1\243\4\36\26\0\4\36\7\0\4\36"+
    "\4\0\11\36\1\244\17\36\26\0\4\36\7\0\4\36"+
    "\4\0\11\36\1\245\17\36\26\0\4\36\7\0\4\36"+
    "\4\0\5\36\1\246\23\36\35\0\1\247\72\0\3\170"+
    "\77\0\4\250\7\0\2\250\7\0\6\250\50\0\4\251"+
    "\7\0\2\251\7\0\6\251\50\0\4\252\7\0\2\252"+
    "\7\0\6\252\26\0\21\112\1\253\60\112\22\0\4\203"+
    "\10\0\1\116\6\0\1\203\4\0\1\116\3\0\3\254"+
    "\43\0\4\205\76\0\4\205\17\0\1\205\10\0\3\254"+
    "\43\0\2\206\21\0\1\206\56\0\3\207\20\0\1\207"+
    "\56\0\4\210\7\0\2\210\6\0\7\210\50\0\4\36"+
    "\7\0\4\36\4\0\17\36\1\255\11\36\26\0\4\36"+
    "\7\0\4\36\4\0\1\256\30\36\26\0\4\36\7\0"+
    "\4\36\4\0\1\36\1\257\27\36\26\0\4\36\7\0"+
    "\4\36\4\0\5\36\1\260\15\36\1\261\5\36\26\0"+
    "\4\36\7\0\4\36\4\0\23\36\1\262\5\36\26\0"+
    "\4\36\7\0\4\36\4\0\5\36\1\263\23\36\26\0"+
    "\4\36\7\0\4\36\4\0\23\36\1\264\5\36\26\0"+
    "\4\36\7\0\4\36\4\0\1\36\1\265\27\36\26\0"+
    "\4\36\7\0\4\36\4\0\5\36\1\266\23\36\26\0"+
    "\4\36\7\0\4\36\4\0\15\36\1\267\13\36\26\0"+
    "\4\36\7\0\4\36\4\0\14\36\1\270\5\36\1\271"+
    "\6\36\26\0\4\36\7\0\4\36\4\0\5\36\1\272"+
    "\23\36\26\0\4\36\7\0\4\36\4\0\17\36\1\273"+
    "\11\36\26\0\4\36\7\0\4\36\4\0\21\36\1\274"+
    "\7\36\26\0\4\36\7\0\4\36\4\0\1\275\30\36"+
    "\26\0\4\36\7\0\4\36\4\0\5\36\1\276\13\36"+
    "\1\277\7\36\26\0\4\36\7\0\4\36\4\0\13\36"+
    "\1\300\15\36\26\0\4\36\7\0\4\36\4\0\3\36"+
    "\1\301\25\36\26\0\4\36\7\0\4\36\4\0\24\36"+
    "\1\302\4\36\26\0\4\36\7\0\4\36\4\0\24\36"+
    "\1\303\4\36\26\0\4\36\7\0\4\36\4\0\23\36"+
    "\1\304\5\36\26\0\4\36\7\0\4\36\4\0\16\36"+
    "\1\305\12\36\26\0\4\36\7\0\4\36\4\0\5\36"+
    "\1\306\23\36\26\0\4\36\7\0\4\36\4\0\17\36"+
    "\1\307\11\36\26\0\4\36\7\0\4\36\4\0\16\36"+
    "\1\310\12\36\26\0\4\36\7\0\4\36\4\0\16\36"+
    "\1\311\12\36\26\0\4\312\7\0\2\312\7\0\6\312"+
    "\50\0\4\174\7\0\2\174\7\0\6\174\50\0\4\170"+
    "\7\0\2\170\7\0\6\170\50\0\4\36\7\0\4\36"+
    "\4\0\1\313\30\36\26\0\4\36\7\0\4\36\4\0"+
    "\22\36\1\314\6\36\26\0\4\36\7\0\4\36\4\0"+
    "\13\36\1\315\15\36\26\0\4\36\7\0\4\36\4\0"+
    "\11\36\1\316\17\36\26\0\4\36\7\0\4\36\4\0"+
    "\21\36\1\317\7\36\26\0\4\36\7\0\4\36\4\0"+
    "\11\36\1\320\17\36\26\0\4\36\7\0\4\36\4\0"+
    "\15\36\1\321\13\36\26\0\4\36\7\0\4\36\4\0"+
    "\23\36\1\322\5\36\26\0\4\36\7\0\4\36\4\0"+
    "\5\36\1\323\23\36\26\0\4\36\7\0\4\36\4\0"+
    "\11\36\1\324\17\36\26\0\4\36\7\0\4\36\4\0"+
    "\21\36\1\325\7\36\26\0\4\36\7\0\4\36\4\0"+
    "\11\36\1\326\17\36\26\0\4\36\7\0\4\36\4\0"+
    "\11\36\1\327\17\36\26\0\4\36\7\0\4\36\4\0"+
    "\14\36\1\330\14\36\26\0\4\36\7\0\4\36\4\0"+
    "\5\36\1\331\23\36\26\0\4\36\7\0\4\36\4\0"+
    "\1\36\1\332\27\36\26\0\4\36\7\0\4\36\4\0"+
    "\21\36\1\333\7\36\26\0\4\36\7\0\4\36\4\0"+
    "\3\36\1\334\25\36\26\0\4\36\7\0\4\36\4\0"+
    "\3\36\1\335\25\36\26\0\4\36\7\0\4\36\4\0"+
    "\22\36\1\336\6\36\26\0\4\36\7\0\4\36\4\0"+
    "\16\36\1\337\12\36\26\0\4\36\7\0\4\36\4\0"+
    "\7\36\1\340\21\36\26\0\4\341\7\0\2\341\7\0"+
    "\6\341\50\0\4\36\7\0\4\36\4\0\3\36\1\342"+
    "\25\36\26\0\4\36\7\0\4\36\4\0\5\36\1\343"+
    "\23\36\26\0\4\36\7\0\4\36\4\0\16\36\1\344"+
    "\12\36\26\0\4\36\7\0\4\36\4\0\16\36\1\345"+
    "\12\36\26\0\4\36\7\0\4\36\4\0\11\36\1\346"+
    "\17\36\26\0\4\36\7\0\4\36\4\0\10\36\1\347"+
    "\20\36\26\0\4\36\7\0\4\36\4\0\7\36\1\350"+
    "\21\36\26\0\4\36\7\0\4\36\4\0\23\36\1\351"+
    "\5\36\26\0\4\36\7\0\4\36\4\0\27\36\1\352"+
    "\1\36\26\0\4\36\7\0\4\36\4\0\16\36\1\353"+
    "\12\36\26\0\4\36\7\0\4\36\4\0\22\36\1\354"+
    "\6\36\26\0\4\36\7\0\4\36\4\0\23\36\1\355"+
    "\5\36\26\0\4\36\7\0\4\36\4\0\7\36\1\356"+
    "\21\36\26\0\4\36\7\0\4\36\4\0\16\36\1\357"+
    "\12\36\26\0\4\36\7\0\4\36\4\0\23\36\1\360"+
    "\5\36\26\0\4\36\7\0\4\36\4\0\10\36\1\361"+
    "\20\36\26\0\4\36\7\0\4\36\4\0\15\36\1\362"+
    "\13\36\26\0\4\173\7\0\2\173\7\0\6\173\50\0"+
    "\4\36\7\0\4\36\4\0\1\36\1\363\27\36\26\0"+
    "\4\36\7\0\4\36\4\0\23\36\1\364\5\36\26\0"+
    "\4\36\7\0\4\36\4\0\24\36\1\365\4\36\26\0"+
    "\4\36\7\0\4\36\4\0\3\36\1\366\25\36\26\0"+
    "\4\36\7\0\4\36\4\0\3\36\1\367\25\36\26\0"+
    "\4\36\7\0\4\36\4\0\21\36\1\370\7\36\26\0"+
    "\4\36\7\0\4\36\4\0\16\36\1\371\12\36\26\0"+
    "\4\36\7\0\4\36\4\0\5\36\1\372\23\36\26\0"+
    "\4\36\7\0\4\36\4\0\24\36\1\373\4\36\26\0"+
    "\4\36\7\0\4\36\4\0\5\36\1\374\23\36\26\0"+
    "\4\36\7\0\4\36\4\0\24\36\1\375\4\36\26\0"+
    "\4\36\7\0\4\36\4\0\22\36\1\376\6\36\26\0"+
    "\4\36\7\0\4\36\4\0\5\36\1\377\23\36\26\0"+
    "\4\36\7\0\4\36\4\0\23\36\1\u0100\5\36\26\0"+
    "\4\36\7\0\4\36\4\0\17\36\1\u0101\11\36\26\0"+
    "\4\36\7\0\4\36\4\0\21\36\1\u0102\7\36\26\0"+
    "\4\36\7\0\4\36\4\0\23\36\1\u0103\5\36\26\0"+
    "\4\36\7\0\4\36\4\0\23\36\1\u0104\5\36\26\0"+
    "\4\36\7\0\4\36\4\0\24\36\1\u0105\4\36\26\0"+
    "\4\36\7\0\4\36\4\0\16\36\1\u0106\12\36\26\0"+
    "\4\36\7\0\4\36\4\0\5\36\1\u0107\23\36\26\0"+
    "\4\36\7\0\4\36\4\0\7\36\1\u0108\21\36\26\0"+
    "\4\36\7\0\4\36\4\0\10\36\1\u0109\20\36\4\0";

  private static int [] zzUnpacktrans() {
    int [] result = new int[12342];
    int offset = 0;
    offset = zzUnpacktrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpacktrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String[] ZZ_ERROR_MSG = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\3\0\1\11\3\1\2\11\2\1\3\11\2\1\1\11"+
    "\5\1\2\11\3\1\2\11\1\1\3\11\20\1\1\11"+
    "\1\1\1\11\2\1\1\11\2\1\1\11\1\1\1\11"+
    "\1\1\1\11\1\1\1\11\1\1\4\11\1\0\2\11"+
    "\1\1\1\0\1\1\1\11\3\0\1\11\2\0\1\1"+
    "\3\11\36\1\1\11\1\1\2\11\4\0\5\11\1\0"+
    "\1\1\1\0\4\1\2\11\34\1\1\11\3\0\2\11"+
    "\35\1\1\0\26\1\1\0\50\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[265];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** Number of newlines encountered up to the start of the matched text. */
  @SuppressWarnings("unused")
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  @SuppressWarnings("unused")
  protected int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  @SuppressWarnings("unused")
  private boolean zzEOFDone;

  /* user code: */
 StringBuffer string = new StringBuffer();

  public OdinLexer() {
    this((java.io.Reader)null);
  }


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public OdinLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** Returns the maximum size of the scanner buffer, which limits the size of tokens. */
  private int zzMaxBufferLen() {
    return Integer.MAX_VALUE;
  }

  /**  Whether the scanner buffer can grow to accommodate a larger token. */
  private boolean zzCanGrow() {
    return true;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }

  public final int getTokenStart() {
    return zzStartRead;
  }

  public final int getTokenEnd() {
    return getTokenStart() + yylength();
  }

  public void reset(CharSequence buffer, int start, int end, int initialState) {
    zzBuffer = buffer;
    zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
  }

  /**
   * Refills the input buffer.
   *
   * @return      {@code false}, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position {@code pos} from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType advance() throws java.io.IOException
  {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
        return null;
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { return BAD_CHARACTER;
            }
          // fall through
          case 107: break;
          case 2:
            { return WHITE_SPACE;
            }
          // fall through
          case 108: break;
          case 3:
            { return NOT;
            }
          // fall through
          case 109: break;
          case 4:
            { yybegin(DQ_STRING_STATE); string.setLength(0);
            }
          // fall through
          case 110: break;
          case 5:
            { return HASH;
            }
          // fall through
          case 111: break;
          case 6:
            { return MOD;
            }
          // fall through
          case 112: break;
          case 7:
            { return AND;
            }
          // fall through
          case 113: break;
          case 8:
            { yybegin(SQ_STRING_STATE);
            }
          // fall through
          case 114: break;
          case 9:
            { return LPAREN;
            }
          // fall through
          case 115: break;
          case 10:
            { return RPAREN;
            }
          // fall through
          case 116: break;
          case 11:
            { return STAR;
            }
          // fall through
          case 117: break;
          case 12:
            { return PLUS;
            }
          // fall through
          case 118: break;
          case 13:
            { return COMMA;
            }
          // fall through
          case 119: break;
          case 14:
            { return MINUS;
            }
          // fall through
          case 120: break;
          case 15:
            { return DOT;
            }
          // fall through
          case 121: break;
          case 16:
            { return DIV;
            }
          // fall through
          case 122: break;
          case 17:
            { return INTEGER_DEC_LITERAL;
            }
          // fall through
          case 123: break;
          case 18:
            { return COLON;
            }
          // fall through
          case 124: break;
          case 19:
            { return SEMICOLON;
            }
          // fall through
          case 125: break;
          case 20:
            { return LT;
            }
          // fall through
          case 126: break;
          case 21:
            { return EQ;
            }
          // fall through
          case 127: break;
          case 22:
            { return GT;
            }
          // fall through
          case 128: break;
          case 23:
            { return QUESTION;
            }
          // fall through
          case 129: break;
          case 24:
            { return AT;
            }
          // fall through
          case 130: break;
          case 25:
            { return IDENTIFIER;
            }
          // fall through
          case 131: break;
          case 26:
            { return LBRACKET;
            }
          // fall through
          case 132: break;
          case 27:
            { return RBRACKET;
            }
          // fall through
          case 133: break;
          case 28:
            { return CARET;
            }
          // fall through
          case 134: break;
          case 29:
            { return LBRACE;
            }
          // fall through
          case 135: break;
          case 30:
            { return PIPE;
            }
          // fall through
          case 136: break;
          case 31:
            { return RBRACE;
            }
          // fall through
          case 137: break;
          case 32:
            { return TILDE;
            }
          // fall through
          case 138: break;
          case 33:
            { 
            }
          // fall through
          case 139: break;
          case 34:
            { yybegin(YYINITIAL); return DQ_STRING_LITERAL;
            }
          // fall through
          case 140: break;
          case 35:
            { yybegin(YYINITIAL); return SQ_STRING_LITERAL;
            }
          // fall through
          case 141: break;
          case 36:
            { return NEQ;
            }
          // fall through
          case 142: break;
          case 37:
            { return REMAINDER;
            }
          // fall through
          case 143: break;
          case 38:
            { return MOD_EQ;
            }
          // fall through
          case 144: break;
          case 39:
            { return ANDAND;
            }
          // fall through
          case 145: break;
          case 40:
            { return AND_EQ;
            }
          // fall through
          case 146: break;
          case 41:
            { return ANDNOT;
            }
          // fall through
          case 147: break;
          case 42:
            { return STAR_EQ;
            }
          // fall through
          case 148: break;
          case 43:
            { return PLUS_EQ;
            }
          // fall through
          case 149: break;
          case 44:
            { return MINUS_EQ;
            }
          // fall through
          case 150: break;
          case 45:
            { return ARROW;
            }
          // fall through
          case 151: break;
          case 46:
            { return RANGE;
            }
          // fall through
          case 152: break;
          case 47:
            { return LINE_COMMENT;
            }
          // fall through
          case 153: break;
          case 48:
            { return DIV_EQ;
            }
          // fall through
          case 154: break;
          case 49:
            { return COMPLEX_INTEGER_DEC_LITERAL;
            }
          // fall through
          case 155: break;
          case 50:
            { return LSHIFT;
            }
          // fall through
          case 156: break;
          case 51:
            { return LTE;
            }
          // fall through
          case 157: break;
          case 52:
            { return EQEQ;
            }
          // fall through
          case 158: break;
          case 53:
            { return GTE;
            }
          // fall through
          case 159: break;
          case 54:
            { return RSHIFT;
            }
          // fall through
          case 160: break;
          case 55:
            { return DO;
            }
          // fall through
          case 161: break;
          case 56:
            { return IF;
            }
          // fall through
          case 162: break;
          case 57:
            { return IN;
            }
          // fall through
          case 163: break;
          case 58:
            { return OR_EQ;
            }
          // fall through
          case 164: break;
          case 59:
            { return OROR;
            }
          // fall through
          case 165: break;
          case 60:
            { return XOR_EQ;
            }
          // fall through
          case 166: break;
          case 61:
            { return REMAINDER_EQ;
            }
          // fall through
          case 167: break;
          case 62:
            { return ANDAND_EQ;
            }
          // fall through
          case 168: break;
          case 63:
            { return TRIPLE_DASH;
            }
          // fall through
          case 169: break;
          case 64:
            { return RANGE_EXCLUSIVE;
            }
          // fall through
          case 170: break;
          case 65:
            { return RANGE_INCLUSIVE;
            }
          // fall through
          case 171: break;
          case 66:
            { return FLOAT_DEC_LITERAL;
            }
          // fall through
          case 172: break;
          case 67:
            { return INTEGER_BIN_LITERAL;
            }
          // fall through
          case 173: break;
          case 68:
            { return INTEGER_OCT_LITERAL;
            }
          // fall through
          case 174: break;
          case 69:
            { return INTEGER_HEX_LITERAL;
            }
          // fall through
          case 175: break;
          case 70:
            { return LSHIFT_EQ;
            }
          // fall through
          case 176: break;
          case 71:
            { return RSHIFT_EQ;
            }
          // fall through
          case 177: break;
          case 72:
            { return FOR;
            }
          // fall through
          case 178: break;
          case 73:
            { return MAP;
            }
          // fall through
          case 179: break;
          case 74:
            { return NIL;
            }
          // fall through
          case 180: break;
          case 75:
            { return OROR_EQ;
            }
          // fall through
          case 181: break;
          case 76:
            { return BLOCK_COMMENT;
            }
          // fall through
          case 182: break;
          case 77:
            { return COMPLEX_FLOAT_LITERAL;
            }
          // fall through
          case 183: break;
          case 78:
            { return CASE;
            }
          // fall through
          case 184: break;
          case 79:
            { return CAST;
            }
          // fall through
          case 185: break;
          case 80:
            { return ELSE;
            }
          // fall through
          case 186: break;
          case 81:
            { return ENUM;
            }
          // fall through
          case 187: break;
          case 82:
            { return PROC;
            }
          // fall through
          case 188: break;
          case 83:
            { return TRUE;
            }
          // fall through
          case 189: break;
          case 84:
            { return WHEN;
            }
          // fall through
          case 190: break;
          case 85:
            { return BREAK;
            }
          // fall through
          case 191: break;
          case 86:
            { return DEFER;
            }
          // fall through
          case 192: break;
          case 87:
            { return FALSE;
            }
          // fall through
          case 193: break;
          case 88:
            { return UNION;
            }
          // fall through
          case 194: break;
          case 89:
            { return USING;
            }
          // fall through
          case 195: break;
          case 90:
            { return IMPORT;
            }
          // fall through
          case 196: break;
          case 91:
            { return MATRIX;
            }
          // fall through
          case 197: break;
          case 92:
            { return NOT_IN;
            }
          // fall through
          case 198: break;
          case 93:
            { return RETURN;
            }
          // fall through
          case 199: break;
          case 94:
            { return STRUCT;
            }
          // fall through
          case 200: break;
          case 95:
            { return SWITCH;
            }
          // fall through
          case 201: break;
          case 96:
            { return BIT_SET;
            }
          // fall through
          case 202: break;
          case 97:
            { return DYNAMIC;
            }
          // fall through
          case 203: break;
          case 98:
            { return FOREIGN;
            }
          // fall through
          case 204: break;
          case 99:
            { return OR_ELSE;
            }
          // fall through
          case 205: break;
          case 100:
            { return PACKAGE;
            }
          // fall through
          case 206: break;
          case 101:
            { return CONTINUE;
            }
          // fall through
          case 207: break;
          case 102:
            { return DISTINCT;
            }
          // fall through
          case 208: break;
          case 103:
            { return AUTO_CAST;
            }
          // fall through
          case 209: break;
          case 104:
            { return OR_RETURN;
            }
          // fall through
          case 210: break;
          case 105:
            { return TRANSMUTE;
            }
          // fall through
          case 211: break;
          case 106:
            { return FALLTHROUGH;
            }
          // fall through
          case 212: break;
          default:
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
