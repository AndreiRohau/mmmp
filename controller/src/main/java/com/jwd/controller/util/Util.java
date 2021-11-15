package com.jwd.controller.util;

import static com.jwd.controller.util.Constant.JSP;
import static com.jwd.controller.util.Constant.JSP_FOLDER;
import static java.util.Objects.isNull;

public class Util {
    public static boolean isNullOrEmpty(final String s) {
        return isNull(s) || s.isEmpty();
    }

    public static boolean isNullOrEmpty(final char[] s) {
        return isNull(s) || s.length == 0;
    }

    public static String pathToJsp(final String jspName) {
        return JSP_FOLDER + jspName + JSP;
    }
}
