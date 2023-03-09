package com.example.demo.aspect;


import com.alibaba.fastjson.JSON;
import com.example.demo.annotation.DoneTime;
import org.apache.commons.lang3.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @Description 所有标记@DoneTime 注解的类都会执行该切面.@Order 值越小,优先级越高.
 * @since 2020-04-23
 */
@Aspect
@Component
public class DoneTimeAspect {


    @Around(value = "@annotation(doneTime)")
    public Object around(ProceedingJoinPoint joinPoint, DoneTime doneTime) throws Throwable {
        StopWatch stopWatch = StopWatch.createStarted();
        try {
            System.out.println("doneTime.param()" + doneTime.param());
            System.out.println("执行" + joinPoint.getSignature().toShortString() + "方法,参数 request = " + JSON.toJSONString(joinPoint.getArgs()));
            Object o = joinPoint.proceed();
            return o;
        } finally {
            stopWatch.stop();
            System.out.println(joinPoint.getSignature().toShortString() + "执行耗时:" + stopWatch.getTime() + "毫秒");
        }


    }

   /* @Pointcut("@annotation(com.example.demo.annotation.DoneTime)")
    public void proxyAspect(){}

    //@Around("@annotation(doneTime)")
    @Around("proxyAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        DoneTime doneTime = method.getAnnotation(DoneTime.class);
        System.out.println("doneTime.param()"+doneTime.param());
        System.out.println("method ()" + method.getName());
        System.out.println("方法开始时间是:" + new Date());
        Object o = joinPoint.proceed();
        System.out.println("方法结束时间是:" + new Date());
        return o;
    }*/
}
