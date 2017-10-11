package crise.studio.common.configuration;

import java.time.LocalDateTime;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import crise.studio.common.filter.XssFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * web.xml을 java config로 변경함(WebApplicationInitializer)
 * springServletContainerInitializer 감지하여 작동함
 * @author wclee
 */

public class WebApplication implements WebApplicationInitializer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onStartup(ServletContext container) throws ServletException {

        logger.info("[SHEM] {} :: WebApplication Start", LocalDateTime.now());

        WebApplicationContext dispatcherContext = getDispatcherContext();
        DispatcherServlet dispatcherServlet = new DispatcherServlet(dispatcherContext);
        dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", dispatcherServlet);

        //dispatcherContext.setServletContext(container);

        dispatcher.setLoadOnStartup(1);
        dispatcher.setAsyncSupported(true);
        dispatcher.addMapping("/");
        dispatcher.setMultipartConfig(new MultipartConfigElement(null,20480000,40960000,0));
        
        container.addListener(new ContextLoaderListener(dispatcherContext));    
                
        CharacterEncodingFilter charEncodingFilter = new CharacterEncodingFilter();
        charEncodingFilter.setEncoding("UTF-8");
        charEncodingFilter.setForceEncoding(true);

        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        FilterRegistration.Dynamic charEncoding = container.addFilter("characterEncoding", charEncodingFilter);
        charEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");

        XssFilter xssFilter = new XssFilter();
        FilterRegistration.Dynamic xssFiltering = container.addFilter("xssFilter", xssFilter);
       // DelegatingFilterproxy : a servlet filter that allows spring security to wrap all application requests so that all requests are secured
       // It is used in conjunction with the servlet filter life cycle to bind Spring bean dependencies to the servlet filter.
        DelegatingFilterProxy springSecurityFilterChain = new DelegatingFilterProxy();
        FilterRegistration.Dynamic springSecurityFiltering = container.addFilter("springSecurityFilterChain", springSecurityFilterChain);
        springSecurityFiltering.addMappingForUrlPatterns(dispatcherTypes, true, "/*");        
    }

    private AnnotationConfigWebApplicationContext getDispatcherContext() {
        // AnnotationConfigWebApplictaionContext를 통해 @Configuration 이용 방식으로 spring application context 생성
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        // dispatcher register & hibernate register (config)
        context.register(DispatcherConfig.class);
        context.register(HibernateConfig.class);
        return context;
    }
}
