package com.jwd.controller.command.impl;

import javax.servlet.http.HttpServletRequest;

import static com.jwd.controller.util.Constant.*;
import static com.jwd.controller.util.Util.isNullOrEmpty;

public class AbstractPageProcessor {
    protected int prepareCurrentPage(final HttpServletRequest request) {
        String currentPageParam = request.getParameter(CURRENT_PAGE);
        if (isNullOrEmpty(currentPageParam)) {
            currentPageParam = PAGE_1;
        }
        return Integer.parseInt(currentPageParam);
    }

    protected int prepareCurrentLimit(final HttpServletRequest request) {
        String currentLimitParam = request.getParameter(PAGE_LIMIT);
        if (isNullOrEmpty(currentLimitParam)) {
            currentLimitParam = ELEMENTS_AT_PAGE;
        }
        return Integer.parseInt(currentLimitParam);
    }
}
