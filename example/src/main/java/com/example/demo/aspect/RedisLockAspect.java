package com.example.demo.aspect;

import com.example.demo.annotation.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Pecker
 * @Description 对标记了@RedisLock 注解的类进行切面处理,实现分布式锁
 * @since 2020-09-03
 */
@Slf4j
//@Aspect
//@Component
public class RedisLockAspect {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("@annotation(com.example.demo.annotation.RedisLock)")
    public void proxyAspect() {
    }

    @Around("@annotation(redisLock)")
    public Object doAround(ProceedingJoinPoint joinPoint, RedisLock redisLock) throws Throwable {
        //保证执行finally 释放锁是释放的自己的锁
        String redisValue = UUID.randomUUID().toString();
        try {
            //原子操作
            Boolean result = redisTemplate.opsForValue()
                    .setIfAbsent(redisLock.key(), redisValue, redisLock.expire(), redisLock.timeUnit());
            if (!result){
                //加锁失败,已有任务成功加锁
                return null;
            }
            Object o = joinPoint.proceed();
            return o;
        } finally {
            //解锁
            if (redisValue.equals(redisTemplate.opsForValue().get(redisValue))) {
                redisTemplate.delete(redisLock.key());
            }
        }
    }
}
