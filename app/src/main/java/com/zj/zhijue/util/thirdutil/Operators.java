package com.zj.zhijue.util.thirdutil;



import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Operators {
    public static final String AND = "&&";
    public static final String AND_NOT = "!";
    public static final char ARRAY_END = ']';
    public static final String ARRAY_END_STR = "]";
    public static final char ARRAY_SEPRATOR = ',';
    public static final String ARRAY_SEPRATOR_STR = ",";
    public static final char ARRAY_START = '[';
    public static final String ARRAY_START_STR = "[";
    public static final char BLOCK_END = '}';
    public static final String BLOCK_END_STR = "}";
    public static final char BLOCK_START = '{';
    public static final String BLOCK_START_STR = "{";
    public static final char BRACKET_END = ')';
    public static final String BRACKET_END_STR = ")";
    public static final char BRACKET_START = '(';
    public static final String BRACKET_START_STR = "(";
    public static final char CONDITION_IF = '?';
    public static final char CONDITION_IF_MIDDLE = ':';
    public static final String CONDITION_IF_STRING = "?";
    public static final String DIV = "/";
    public static final char DOLLAR = '$';
    public static final String DOLLAR_STR = "$";
    public static final char DOT = '.';
    public static final String DOT_STR = ".";
    public static final String EQUAL = "===";
    public static final String EQUAL2 = "==";
    public static final String G = ">";
    public static final String GE = ">=";
    public static final Map<String, Object> KEYWORDS = new HashMap();
    public static final String L = "<";
    public static final String LE = "<=";
    public static final String MOD = "%";
    public static final String MUL = "*";
    public static final String NOT_EQUAL = "!==";
    public static final String NOT_EQUAL2 = "!=";
    public static Map<String, Integer> OPERATORS_PRIORITY = new HashMap();
    public static final String OR = "||";
    public static final String PLUS = "+";
    public static final char QUOTE = '\"';
    public static final char SINGLE_QUOTE = '\'';
    public static final char SPACE = ' ';
    public static final String SPACE_STR = " ";
    public static final String SUB = "-";


    public static Object specialKey(Object leftValue, String key) {
        if ("length".equals(key)) {
            if (leftValue instanceof CharSequence) {
                return Integer.valueOf(((CharSequence) leftValue).length());
            }
            if (leftValue instanceof Map) {
                return Integer.valueOf(((Map) leftValue).size());
            }
            if (leftValue instanceof Map) {
                return Integer.valueOf(((Map) leftValue).size());
            }
            if (leftValue instanceof List) {
                return Integer.valueOf(((List) leftValue).size());
            }
            if (leftValue.getClass().isArray()) {
                return Integer.valueOf(Array.getLength(leftValue));
            }
        }
        return null;
    }



    public static boolean isEmpty(String value) {
        if (value == null) {
            return true;
        }
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    public static double getNumber(Object value) {
        double d = 0.0d;
        if (value == null) {
            return d;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        try {
            return Double.parseDouble(value.toString());
        } catch (Exception e) {
            return d;
        }
    }

    public static boolean isOpEnd(String op) {
        return isOpEnd(op.charAt(0));
    }

    public static boolean isOpEnd(char op) {
        if (op == BRACKET_END || op == ARRAY_END || op == ' ' || op == ARRAY_SEPRATOR) {
            return true;
        }
        return false;
    }

    public static boolean isDot(String opStr) {
        char op = opStr.charAt(0);
        if (op == DOT || op == ARRAY_START) {
            return true;
        }
        return false;
    }

    static {
        OPERATORS_PRIORITY.put("}", Integer.valueOf(0));
        OPERATORS_PRIORITY.put(")", Integer.valueOf(0));
        OPERATORS_PRIORITY.put(" ", Integer.valueOf(0));
        OPERATORS_PRIORITY.put(",", Integer.valueOf(0));
        OPERATORS_PRIORITY.put(ARRAY_END_STR, Integer.valueOf(0));
        OPERATORS_PRIORITY.put(OR, Integer.valueOf(1));
        OPERATORS_PRIORITY.put(AND, Integer.valueOf(1));
        OPERATORS_PRIORITY.put(EQUAL, Integer.valueOf(2));
        OPERATORS_PRIORITY.put(EQUAL2, Integer.valueOf(2));
        OPERATORS_PRIORITY.put(NOT_EQUAL, Integer.valueOf(2));
        OPERATORS_PRIORITY.put(NOT_EQUAL2, Integer.valueOf(2));
        OPERATORS_PRIORITY.put(G, Integer.valueOf(7));
        OPERATORS_PRIORITY.put(GE, Integer.valueOf(7));
        OPERATORS_PRIORITY.put(L, Integer.valueOf(7));
        OPERATORS_PRIORITY.put(LE, Integer.valueOf(8));
        OPERATORS_PRIORITY.put(PLUS, Integer.valueOf(9));
        OPERATORS_PRIORITY.put("-", Integer.valueOf(9));
        OPERATORS_PRIORITY.put(MUL, Integer.valueOf(10));
        OPERATORS_PRIORITY.put("/", Integer.valueOf(10));
        OPERATORS_PRIORITY.put(MOD, Integer.valueOf(10));
        OPERATORS_PRIORITY.put(AND_NOT, Integer.valueOf(11));
        OPERATORS_PRIORITY.put(".", Integer.valueOf(15));
        OPERATORS_PRIORITY.put(ARRAY_START_STR, Integer.valueOf(16));
        OPERATORS_PRIORITY.put("(", Integer.valueOf(17));
        OPERATORS_PRIORITY.put(BLOCK_START_STR, Integer.valueOf(17));
       // KEYWORDS.put(BuildConfig.buildJavascriptFrameworkVersion, null);
        KEYWORDS.put("true", Boolean.TRUE);
        KEYWORDS.put("false", Boolean.FALSE);
        //KEYWORDS.put(Name.UNDEFINED, null);
    }
}
