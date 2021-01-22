package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.annotation.DoneTime;
import com.example.demo.annotation.ResponseResult;
import com.example.demo.request.ParamRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * @author Pecker
 */
@RestController
public class IndexController {

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

    @PostMapping("/index2")
    public String index2(@RequestBody @Valid ParamRequest request) {
        return request.getPwdSwitch();
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

}
