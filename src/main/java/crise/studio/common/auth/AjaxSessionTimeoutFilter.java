package crise.studio.common.auth;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxSessionTimeoutFilter  implements Filter {

    public static final String AJAX_REQUEST_HEADER_KEY = "X-Requested-With";
    public static final String AJAX_REQUEST_HEADER_VALUE = "XMLHttpRequest";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nothing to be done here.
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if(isAjax(req)) {
            try {
                chain.doFilter(req, res);
            } catch (AccessDeniedException e) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
            } catch (AuthenticationException e) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {
        // Nothing to be done here.
    }

    public static boolean isAjax(HttpServletRequest request) {
        return AJAX_REQUEST_HEADER_VALUE.equals(request.getHeader(AJAX_REQUEST_HEADER_KEY));
    }


}
