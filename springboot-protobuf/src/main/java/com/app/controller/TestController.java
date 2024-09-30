package com.app.controller;

import com.app.model.bo.UserInfoFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class TestController {

    /**
     * 测试基于protobuf的接口
     */
    @PostMapping(value = "/user", produces = "application/x-protobuf")
    public String testApi(@RequestBody UserInfoFactory.UserInfo userInfo) {
        return userInfo.toString();
    }

    @GetMapping("/test")
    public String test() {
        return "123";
    }
}