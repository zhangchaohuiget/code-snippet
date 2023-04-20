package com.app.controller;

import com.app.api.UserServiceApi;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @DubboReference
    private UserServiceApi userServiceApi;

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable("name") String name) {
        return userServiceApi.sayHello(name);
    }

}
