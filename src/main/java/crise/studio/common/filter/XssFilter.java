package crise.studio.common.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@SuppressWarnings("unused")
public class XssFilter implements Filter {

    private FilterConfig fc;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String SWAGGER_UI = "/swagger-ui.htm";
    private static final String WEB_JARS = "/webjars";
    private static final String RESOURCES = "/resources";

    @Override
    public void destroy() {
        this.fc = null;
    }

    /**
     * 필터 사용시 스웨거 UI가 에러발생함.
     * 스웨거 UI 사용시 XSS필터 사용 안함
     * @param req
     * @param res
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
            FilterChain chain) throws IOException, ServletException {

        try {
            String servletPath = "";
            if (req instanceof HttpServletRequest) {
                servletPath = ((HttpServletRequest)req).getServletPath();
            }

            if (StringUtils.startsWithIgnoreCase(servletPath, WEB_JARS)
                    || StringUtils.startsWithIgnoreCase(servletPath, SWAGGER_UI)
                    || StringUtils.startsWithIgnoreCase(servletPath, RESOURCES)) {
                chain.doFilter(req, res);
            } else {
                chain.doFilter(new XSSRequestWrapper((HttpServletRequest) req), res);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
        this.fc = fc;
    }
}
