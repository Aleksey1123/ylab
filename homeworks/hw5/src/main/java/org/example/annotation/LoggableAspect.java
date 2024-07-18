package org.example.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class LoggableAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggableAspect.class);

    @Pointcut("@within(org.example.annotation.Loggable) || @annotation(org.example.annotation.Loggable)")
    public void annotatedByLoggable() {
    }

    @Around("annotatedByLoggable()")
    public Object logging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("Calling method: {}", proceedingJoinPoint.getSignature());
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis() - start;
        logger.info("Execution of method {} finished. Execution time is {} ms.", proceedingJoinPoint.getSignature(), end);
        return result;
    }
}
