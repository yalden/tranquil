package com.ycourlee.tranquil.autoconfiguration.redisson;

import com.ycourlee.tranquil.redisson.annotation.Cacheable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yooonn
 * @date 2022.04.17
 */
@Aspect
public class CacheableAspect {

    private static final Logger log = LoggerFactory.getLogger(CacheableAspect.class);

    @Around(value = "@annotation(cacheableAnno)", argNames = "joinPoint,cacheableAnno")
    public Object lockableInterceptor(ProceedingJoinPoint joinPoint, Cacheable cacheableAnno) throws Throwable {


        return null;
    }
}