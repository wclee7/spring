package crise.studio.common.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import crise.studio.configuration.interceptor.BndInterceptor;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"crise.studio.*"})
public class DispatcherConfig extends WebMvcConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("/index");
        registry.addViewController("/bnd").setViewName("welcome");
        registry.addViewController("/bnd/welcome").setViewName("welcome");
        registry.addViewController("/bnd/notAllow").setViewName("notAllow");
        registry.addViewController("/bnd/login").setViewName("login");
        registry.addViewController("/bnd/error").setViewName("error");
        registry.addViewController("/").setViewName("index");
       // registry.addRedirectViewController("/", "/bnd");
    }
    
    @Bean
    public BndInterceptor bndInterceptor() {
        return new BndInterceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(bndInterceptor()).addPathPatterns("/bnd/**");
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/api/**")
                .maxAge(360L)
                .allowedOrigins("*")
                .allowedMethods("GET", "POST")
                .allowedHeaders("x-requested-with")
                .allowedOrigins("*");
        
        registry.addMapping("/resources/**")
		        .maxAge(360L)
		        .allowedOrigins("*")
		        .allowedMethods("GET", "POST")
		        .allowedHeaders("x-requested-with")
		        .allowedOrigins("*");
    }
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
        objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        jsonConverter.setObjectMapper(objectMapper);
        converters.add(jsonConverter);
        
        MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter();
         XmlMapper xmlMapper = new XmlMapper();
        //Jacksonxml
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        xmlMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        xmlMapper.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true);
        xmlMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true);
        xmlMapper.configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, true);
        //xmlMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, true);
        //xmlMapper.configure(SerializationFeature.);
        xmlConverter.setObjectMapper(xmlMapper);
        converters.add(xmlConverter);
        
        super.configureMessageConverters(converters);
        
    }
    
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
    
    @Bean
    public BeanNameViewResolver beanNameViewResolver() {
        BeanNameViewResolver beanNameViewResolver = new BeanNameViewResolver();
        beanNameViewResolver.setOrder(0);
        return beanNameViewResolver;
    }
    
    @Bean
    public MappingJackson2JsonView jacksonJsonView() {
        return new MappingJackson2JsonView();
    }
    
    @Bean
    public UrlBasedViewResolver tilesViewResolver() {
        UrlBasedViewResolver urlBasedViewReolver = new UrlBasedViewResolver();
        urlBasedViewReolver.setViewClass(TilesView.class);
        urlBasedViewReolver.setOrder(1);
        return urlBasedViewReolver;               
    }
    
    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer tilesConfigurer= new TilesConfigurer();
        tilesConfigurer.setDefinitions("classpath:/tiles.xml");
        tilesConfigurer.setCheckRefresh(true);
        return tilesConfigurer;
    }
    
    
//    @Bean
//    public ViewResolver viewResolver() {
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setViewClass(JstlView.class);
//        viewResolver.setPrefix("/WEB-INF/views/");
//        viewResolver.setSuffix(".jsp");
//
//        return viewResolver;
//    }

    // equivalents for <mvc:resources/> tags
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
    }

    @Bean 
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocation(new PathMatchingResourcePatternResolver().getResource("/properties.xml"));
        return ppc;
    }

    public MappingJackson2HttpMessageConverter jacksonMessageConverter() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = new ObjectMapper();

        mapper.registerModule(new Hibernate4Module());

        messageConverter.setObjectMapper(mapper);
        return messageConverter;

    }
    
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }


}
