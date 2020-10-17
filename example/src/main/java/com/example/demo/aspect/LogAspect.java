package com.example.demo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author Pecker
 * @Description 切面类
 * <p>
 * 1.@Aspect : 标记该类为切面类
 * 2.@Pointcut : 注解指定切点表达式
 * 3.@Before : 前置通知,在执行目标方法之前执行
 * 4.@After : 后置/最终通知,在执行目标方法之后执行  【无论是否出现异常最终都会执行】
 * 5.@AfterReturning : 返回后通知,在调用目标方法结束后执行 【出现异常不执行】
 * 6.@AfterThrowing : 异常通知,当目标方法执行异常时候执行此关注点代码
 * 7.@Around : 环绕通知,环绕目标方式执行
 * </p>
 * @since 2020-04-23
 */
@Aspect
@Component
public class LogAspect {
    @Pointcut("execution(public * com.example.demo.controller.*.*(..))")
    public void LogAspect() {
    }

    @Before("LogAspect()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("doBefore");
    }

    @After("LogAspect()")
    public void doAfter(JoinPoint joinPoint) {
        System.out.println("doAfter");
    }

    @AfterReturning("LogAspect()")
    public void doAfterReturning(JoinPoint joinPoint) {
        System.out.println("doAfterReturning");
    }

    @AfterThrowing("LogAspect()")
    public void deAfterThrowing(JoinPoint joinPoint) {
        System.out.println("deAfterThrowing");
    }

    @Around("LogAspect()")
    public Object deAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("deAround");
        //joinPoint.proceed() 为执行目标方法语句
        return joinPoint.proceed();
    }

}
