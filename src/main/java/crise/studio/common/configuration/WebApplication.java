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

public class WebApplication implements WebApplicationInitializer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onStartup(ServletContext container) throws ServletException {

        logger.info("[SHE] {} :: WebApplication Start", LocalDateTime.now());

        //AnnotationConfigWebApplicationContext dispatcherContext = getDispatcherContext();
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
    //    container.addListener(new IIOProviderContextListener());
                
        CharacterEncodingFilter charEncodingFilter = new CharacterEncodingFilter();
        charEncodingFilter.setEncoding("UTF-8");
        charEncodingFilter.setForceEncoding(true);

        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        FilterRegistration.Dynamic charEncoding = container.addFilter("characterEncoding", charEncodingFilter);
        charEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");

        XssFilter xssFilter = new XssFilter();
        FilterRegistration.Dynamic xssFiltering = container.addFilter("xssFilter", xssFilter);
       
        DelegatingFilterProxy springSecurityFilterChain = new DelegatingFilterProxy();
        FilterRegistration.Dynamic springSecurityFiltering = container.addFilter("springSecurityFilterChain", springSecurityFilterChain);
        springSecurityFiltering.addMappingForUrlPatterns(dispatcherTypes, true, "/*");
        
    }

    private AnnotationConfigWebApplicationContext getDispatcherContext() {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(DispatcherConfig.class);
        context.register(HibernateConfig.class);
        return context;
    }
}
