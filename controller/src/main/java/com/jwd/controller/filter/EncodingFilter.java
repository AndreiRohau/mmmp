package com.jwd.controller.filter;

import com.jwd.controller.FrontController;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;

public class EncodingFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(FrontController.class.getName());
    private static final String
            FILTERABLE_CONTENT_TYPE = "application/x-www-form-urlencoded",
            ENCODING_DEFAULT = "UTF-8",
            ENCODING_INIT_PARAM_NAME = "encoding";

    private String encoding;

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        LOGGER.info("EncodingFilter#doFilter");
        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith(FILTERABLE_CONTENT_TYPE)) {
            request.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter(ENCODING_INIT_PARAM_NAME);
        if (encoding == null) {
            encoding = ENCODING_DEFAULT;
        }
    }
}
