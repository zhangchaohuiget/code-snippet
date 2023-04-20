package com.app.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ceshi
 *
 * @author ch
 * @date 2023/4/17 00:01
 */
@Api(value = "测试控制器", tags = "测试控制器")
@RestController
public class DemoController {

    @ApiOperation(value = "获取用户列表", notes = "获取用户列表")
    @GetMapping("/hello")
    public String hello(String name) {
        return "hi, " + name;
    }

}
