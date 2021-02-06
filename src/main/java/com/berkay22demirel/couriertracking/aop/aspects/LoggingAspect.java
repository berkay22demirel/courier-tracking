package com.berkay22demirel.couriertracking.aop.aspects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private Logger logger = LogManager.getLogger(LoggingAspect.class);

    @AfterThrowing(value = "com.berkay22demirel.couriertracking.aop.pointcuts.SystemPointCuts.loggable()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
        try {
            logger.error(joinPoint.getSignature().toString(), exception);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

}


