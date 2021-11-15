package com.jwd.dao.util;

import static java.util.Objects.isNull;

public class Util {
    public static String convertNullToEmpty(String s) {
        if (isNull(s)) {
            s = "";
        }
        return s;
    }
}
