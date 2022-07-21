package com.ycourlee.tranquil.redisson.aop;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;

import javax.annotation.Nonnull;

/**
 * @author yongjiang
 * @date 2022.06.22
 */
public class LockableAnnotationAdvisor extends AbstractPointcutAdvisor {

    private static final long serialVersionUID = -7011908877621636112L;

    private Advice   advice;
    private Pointcut pointcut;

    public LockableAnnotationAdvisor(Advice advice, Pointcut pointcut) {
        this.advice = advice;
        this.pointcut = pointcut;
        setOrder(LOWEST_PRECEDENCE);
    }

    public LockableAnnotationAdvisor(Advice advice, Pointcut pointcut, int order) {
        this.advice = advice;
        this.pointcut = pointcut;
        setOrder(order);
    }

    @Nonnull
    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

    @Nonnull
    @Override
    public Advice getAdvice() {
        return advice;
    }
}
