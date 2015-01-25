package com.yaqa.util;

public class ParamUtils {

    public static void assertNotNull(Object param, String paramName) {
        if (param == null) {
            throw new IllegalArgumentException(paramName + " is null");
        }
    }

    public static void assertNotEmpty(String param, String paramName) {
        if (param == null || param.isEmpty()) {
            throw new IllegalArgumentException(paramName + " is empty");
        }
    }


    public static void assertPositive(Number param, String paramName) {
        if (param == null || param.doubleValue() <= 0.0d) {
            throw new IllegalArgumentException(paramName + " is not positive");
        }
    }
}
