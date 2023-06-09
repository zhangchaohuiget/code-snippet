package com.app.controller;

import com.app.dao.entity.User;
import com.app.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/errorUpdate")
    public Object first() {
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("admin");
        userService.updateUserError(user);
        return "first controller";
    }

    @RequestMapping("/errorUpdate2")
    public Object second() {
        User user = new User();
        user.setId(1);
        user.setUsername("admin");
        user.setPassword("admin");
        userService.updateUserError2(user);
        return "second controller";
    }


}
