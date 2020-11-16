package com.example.demo.controller;


import com.example.demo.annotation.DoneTime;
import com.example.demo.annotation.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author dalaoyang
 * @Description
 * @project springboot_learn
 * @package com.dalaoyang.controller
 * @email yangyang@dalaoyang.cn
 * @date 2018/9/9
 */
@RestController
public class IndexController {

    @GetMapping("/index1")
    @ResponseResult
    @DoneTime(param = "IndexController")
    public String index(String url) {
        System.out.println(url);
        System.out.println("方法执行");
        return "hello !!!";
    }

    @GetMapping("/index2")
    public String index2() {
        System.out.println("方法2执行");
        return "hello hello";
    }

}
