/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.learn.sentinel.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.learn.sentinel.service.DemoService;
import com.learn.sentinel.service.ExceptionUtil;
import org.springframework.stereotype.Service;

/**
 * @author Eric Zhao
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    @SentinelResource(value = "test", blockHandler = "handleException", blockHandlerClass = {ExceptionUtil.class})
    public void test() {
        // 当触发流控规则时,会执行异常处理方法 handleException
        System.out.println("Test");
    }

    @Override
    @SentinelResource(value = "service-hello", fallback = "helloFallback")
    public String hello(long s) {
        // 触发流控规则会调用回调方法
        if (s < 0) {
            // 发生异常同样也会执行回调方法
            throw new IllegalArgumentException("invalid arg");
        }
        return String.format("Hello at %d", s);
    }

    @Override
    @SentinelResource(value = "service-helloAnother", defaultFallback = "defaultFallback",
        exceptionsToIgnore = {IllegalStateException.class})
    public String helloAnother(String name) {
        if (name == null || "bad".equals(name)) {
            // 发生异常会走回调方法
            throw new IllegalArgumentException("oops");
        }
        if ("foo".equals(name)) {
            // 当没有触发流控规则时,exceptionsToIgnore 会生效,也就是说会抛异常
            // 当触发流控规则时,会调用回调方法,会返回 default_fallback,代码都走不到这里,直接执行的回调方法
            // 这算不算 bug 呢 ?
            System.out.println("要抛异常了...");
            throw new IllegalStateException("oops");
        }
        return "Hello, " + name;
    }

    public String helloFallback(long s, Throwable ex) {
        // Do some log here.
        ex.printStackTrace();
        return "Oops, error occurred at " + s;
    }

    public String defaultFallback() {
        System.out.println("Go to default fallback");
        return "default_fallback";
    }
}
