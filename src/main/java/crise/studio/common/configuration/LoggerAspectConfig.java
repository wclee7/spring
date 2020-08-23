package crise.studio.common.configuration;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggerAspectConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("execution(public * crise.studio.controller..*(..))")
    public void apiControllerLogPrint(JoinPoint joinPoint) {
        logger.info(this.makeLogString(joinPoint));
    }

    @Before("execution(public * crise.studio.facade..*(..)) || execution(public * crise.studio.service..*(..))")
    public void commonApiLogPrint(JoinPoint joinPoint) {
        logger.debug(this.makeLogString(joinPoint));
    }

    private String makeLogString(JoinPoint joinPoint) {

        String packageName = joinPoint.getSignature().getDeclaringTypeName();
        String className = joinPoint.getSignature().getName();
        StringBuilder builder = new StringBuilder("#### [STRT] ");
        builder.append(packageName);
        builder.append(" -> ");
        builder.append(className);

        if (joinPoint.getArgs().length > 0) {
            builder.append(" :: ");
        }

        for (Object o : joinPoint.getArgs()) {
            builder.append(o);
            builder.append("\t");
        }

        return builder.toString();
    }

}
