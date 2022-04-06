package security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2022-03-08
 */
@RestController
@RequestMapping("/ss/demo1/")
public class Demo1Controller {

    @RequestMapping("/test1")
    public String test1() {
        return "test1";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "admin";
    }

    @RequestMapping("/user")
    public String user() {
        return "user";
    }

}
