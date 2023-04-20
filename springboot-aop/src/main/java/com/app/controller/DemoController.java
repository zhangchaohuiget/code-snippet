package com.app.controller;

import com.app.aspect.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Log
    @GetMapping("/hello")
    public String hello() {
        return "hi";
    }
}
