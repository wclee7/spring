package crise.studio.configuration.interceptor;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class BndInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(BndInterceptor.class);

    @Value("${config.bndView}")
    private String  bnd_view;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException{
        logger.debug("##### start preHandle #####");
        logger.debug("#### bnd_view=>{}", bnd_view);
        if (bnd_view.equals("true")) {
            return true;
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }
    }
}
