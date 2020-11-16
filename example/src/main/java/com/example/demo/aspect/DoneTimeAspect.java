package com.example.demo.aspect;


import com.example.demo.annotation.DoneTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author Pecker
 * @Description 所有标记@DoneTime 注解的类都会执行该切面
 * @since 2020-04-23
 */
@Aspect
@Component
public class DoneTimeAspect {


    @Around(value = "@annotation(doneTime)")
    public Object around(ProceedingJoinPoint joinPoint, DoneTime doneTime) throws Throwable {

        System.out.println("doneTime.param()"+doneTime.param());
        System.out.println("方法开始时间是:" + new Date());
        Object o = joinPoint.proceed();
        System.out.println("方法结束时间是:" + new Date());
        return o;
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
