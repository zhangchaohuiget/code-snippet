package com.app.controller;

import com.app.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    KafkaProducer kafkaProducer;

    @GetMapping("/send")
    public String sendMessage(String msg) {
        kafkaProducer.send(msg);
        return "调用成功";
    }
}
