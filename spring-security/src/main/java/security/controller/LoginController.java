package security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pecker
 * @Description 类信息
 * @since 2022-03-08
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/test1")
    public String test1() {
        return "test1";
    }


}
