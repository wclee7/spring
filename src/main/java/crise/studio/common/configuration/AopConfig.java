package crise.studio.common.configuration;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.dao.DataAccessException;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Created by JinPark on 2016-12-21.
 */
@Configuration
@EnableAspectJAutoProxy
public class AopConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String POINTCUT_EXPRESSION = "execution(public * crise.studio.service..*(..))";

    private static final String[] METHOD_NAMES = new String[]{"saveOf*", "removeOf*", "get*"};

    private static final String[] METHOD_PREFIXES_READONLY = new String[]{"get"};

    private static final String[] METHOD_PREFIXES_ROLLBACK_RULE = new String[]{"saveOf", "removeOf"};

    /**
     * transaction aop config
     * @param txManager
     * @return
     */
    @Bean
    public Advisor transactionAdvisor(PlatformTransactionManager txManager) {
        logger.info("#### transactionAdvisor ####");

        AspectJExpressionPointcut aspectJExpressionPointcut = new AspectJExpressionPointcut();
        aspectJExpressionPointcut.setExpression(POINTCUT_EXPRESSION);
        final NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        Arrays.stream(METHOD_NAMES).filter(StringUtils::isNotBlank).forEach(name -> {
            RuleBasedTransactionAttribute ruleBasedTransactionAttribute = new RuleBasedTransactionAttribute();
            ruleBasedTransactionAttribute.setName(name);
            ruleBasedTransactionAttribute.setReadOnly(getReadOnlyAttribute(name));
            ruleBasedTransactionAttribute.setRollbackRules(getRollbackRulesAttribute(name));
            source.addTransactionalMethod(name, ruleBasedTransactionAttribute);
        });

        return new DefaultPointcutAdvisor(aspectJExpressionPointcut, new TransactionInterceptor(txManager, source));
    }

    /**
     * logger AOP config
     * @return
     */
    @Bean
    public LoggerAspectConfig loggerAspectConfig() {
        return new LoggerAspectConfig();
    }

    private boolean getReadOnlyAttribute(String name) {
        return containsPrefixes(name, METHOD_PREFIXES_READONLY);
    }

    private List<RollbackRuleAttribute> getRollbackRulesAttribute(String name) {
        return containsPrefixes(name, METHOD_PREFIXES_ROLLBACK_RULE) ? Collections.singletonList(new RollbackRuleAttribute(DataAccessException.class)) : null;
    }

    private boolean containsPrefixes(String name, String... prefixes) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("name argument not blank");
        }

        boolean isContains = false;

        for (String prefix : prefixes) {
            if (Objects.nonNull(prefix) && name.startsWith(prefix)) {
                isContains = true;
                break;
            }
        }

        return isContains;
    }


     /**
     * Scheduler Exception Handler
     * @return
     */
//    @Bean
//    public SchedulerAspectConfig schedulerAspectConfig() {
//        return new SchedulerAspectConfig();
//    }
}
