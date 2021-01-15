package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.annotation.DoneTime;
import com.example.demo.annotation.ResponseResult;
import com.example.demo.chain.AbstractChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author Pecker
 */
@RestController
public class IndexController {

    /**
     * 用了这么久的 @Autowired ,都是通过字段方式注入的,作为成员变量注入,
     * 原来 Spring 更推荐构造方法进行依赖注入
     */
//    @Autowired
//    private List<AbstractChain> abstractChainList;

    private final List<AbstractChain> abstractChainList;

    @Autowired
    public IndexController(List<AbstractChain> abstractChainList) {
        this.abstractChainList = abstractChainList;
    }

    @GetMapping("/index1")
    @ResponseResult
    @DoneTime(param = "IndexController")
    public String index(@RequestParam(value = "url", required = false) String url,
                        @ModelAttribute("modelUser") String modelUser,
                        @RequestAttribute("requestUser") String requestUser) {
        System.out.println("从 RequestAttribute 获取参数" + requestUser);
        System.out.println("从 ModelAttribute 获取参数" + modelUser);
        System.out.println("从 RequestParam 获取参数" + url);
        System.out.println("方法执行");
        return "hello !!!";
    }

    @GetMapping("/index2")
    public String index2() {
        return "hello hello";
    }

    @PostMapping("/index3")
    public String index3(@RequestParam String arrStr) {
        System.out.println("解析字符串 TO 数组 :");
        List<String> parseArray = JSONObject.parseArray(arrStr, String.class);
        System.out.println(parseArray);

        System.out.println("解析数组 TO 字符串 :");
        String jsonStr = JSONObject.toJSONString(parseArray);
        System.out.println(jsonStr);

        return jsonStr;
    }

    @GetMapping("/index4")
    public void index4() {
        abstractChainList.forEach(chain ->{
            chain.process();
            System.out.println(chain.getNext());
        });
    }

}
