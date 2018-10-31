package com.alpha.controller;

import com.alpha.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author whz
 * @date
 */
@RestController
public class UserController {
    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        User user = new User();
        user.setId("1");
        user.setUserName("小明");
        System.out.println(user);
        return user.toString();
    }

}