package com.example.demo.controller;


import com.example.demo.annotation.DoneTime;
import com.example.demo.annotation.ResponseResult;
import org.springframework.web.bind.annotation.*;

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
    public String index(@RequestParam(value = "url",required = false) String url,
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
        System.out.println("方法2执行");
        return "hello hello";
    }

}
