package crise.studio.common.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.hibernate.SessionFactory;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
//import org.jasypt.properties.EncryptableProperties;

@Configuration
//@EnableTransactionManagement
public class HibernateConfig {

    private Logger logger = LoggerFactory.getLogger(HibernateConfig.class);

    @Value("${jdbc.driver}")
    private String jdbcDriver;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUserName;

    @Value("${jdbc.password}")
    private String jdbcPwd;

    @Value("${jdbc.initSize}")
    private int jdbcInitSize;

    @Value("${jdbc.maxActive}")
    private int jdbcMaxActive;

    @Value("${jdbc.maxIdle}")
    private int jdbcMaxIdle;

    @Value("${jdbc.minIdle}")
    private int jdbcMinIdle;
    
    private final String DECRYPT_KEY = "##she_template_db##";
   
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        logger.info("#### sessionFactory ####");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[]{"crise.studio.model.entity", "crise.studio.configuration.converter"});
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {

        logger.info("### jdbcDriver : " + jdbcDriver);
        logger.info("### jdbcUrl : " + jdbcUrl);
        logger.info("### jdbcUserName : " + jdbcUserName);
        logger.info("### jdbcInitSize : " + jdbcInitSize);  // 초기 커넥션 개수
        logger.info("### jdbcMaxActive : " + jdbcMaxActive); // 커넥션 풀이 제공할 최대 커넥션 개수
        logger.info("### jdbcMaxIdle : " + jdbcMaxIdle); // 최대로 유지 될 수 있는 커넥션 개수, 음수
        logger.info("### jdbcMinIdle : " + jdbcMinIdle); // 최소한으로 유지할 커넥션 개수

       DataSource dataSource = new DataSource();
        dataSource.setDriverClassName(jdbcDriver);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUserName);
        //dataSource.setPassword(jdbcPwd);
        dataSource.setPassword(this.getDectyptDatabasePwd());
        dataSource.setInitialSize(jdbcInitSize);
        dataSource.setMaxActive(jdbcMaxActive);
        dataSource.setMaxIdle(jdbcMaxIdle);
        dataSource.setMinIdle(jdbcMinIdle);
        //dataSource.setRemoveAbandoned(true);
        //dataSource.setRemoveAbandonedTimeout(360);
        //dataSource.setLogAbandoned(true);
        dataSource.setValidationQuery("select 1");
        dataSource.setValidationInterval(3600000);
        dataSource.setTestWhileIdle(true);
        return dataSource;
        // dataSource.setPassword(this.getDectyptDatabasePwd()); 
    }

    private Properties hibernateProperties() {
        return new Properties() {
            {
                setProperty("hibernate.hbm2ddl.auto", "update");
                setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
                setProperty("hibernate.show_sql", "true");
                setProperty("hibernate.format_sql", "true");
                setProperty("hibernate.connection.useUnicode", "true");
                setProperty("hibernate.connection.characterEncoding", "UTF-8");
                setProperty("hibernate.connection.charSet", "UTF-8");
            }
        };
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }
    
    /**
     *  @Repository 클래스들에 대해 자동으로 예외를 Spring의 DataAccessException으로 일괄 변환해준다.
     * @return
     */
     @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
    
    // DB password 암호화    
     public String getDectyptDatabasePwd() {
        logger.info("#### getDectyptDatabasePwd");

        StandardPBEStringEncryptor pbeStringEncryptor = new StandardPBEStringEncryptor();
        pbeStringEncryptor.setPassword(DECRYPT_KEY);
        pbeStringEncryptor.setAlgorithm("PBEWithMD5AndDES");

        Properties props = new EncryptableProperties(pbeStringEncryptor);

        InputStream is = getClass().getResourceAsStream("/data.properties");
        try {
            props.load(is);
        } catch (IOException e) {
            logger.error("#[ERR] file not found exception {}", e.getMessage());
            return "";
        }
        logger.debug("## props getProp password [{}]",props.getProperty("jdbc.password"));
        return props.getProperty("jdbc.password");
    }
}
