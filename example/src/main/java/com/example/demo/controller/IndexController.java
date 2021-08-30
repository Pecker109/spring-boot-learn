package com.example.demo.controller;


import com.example.demo.annotation.DoneTime;
import com.example.demo.annotation.ResponseResult;
import com.example.demo.model.request.Index3Request;
import com.example.demo.request.ParamRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


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
        return "!!! hello !!!";
    }

    @PostMapping("/index2")
    public String index2(@RequestBody @Valid ParamRequest request) {
        return request.getPwdSwitch();
    }

    @DoneTime
    @PostMapping("/index3")
    public String index3(@RequestBody Index3Request request) {

        return request.getArrStr();
    }

}
