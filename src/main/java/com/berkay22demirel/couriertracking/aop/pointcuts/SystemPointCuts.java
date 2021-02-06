package com.berkay22demirel.couriertracking.aop.pointcuts;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class SystemPointCuts {

    @Pointcut("@annotation(com.berkay22demirel.couriertracking.aop.annotation.Loggable)")
    public void loggable() {
    }

}
