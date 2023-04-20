package com.app;

import com.app.service.SenderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestSender {

    @Autowired
    private SenderService senderService;

    @Test
    public void send() {
        senderService.direct("点对点");
        senderService.broadcast("广播");
        senderService.deadLetter("死信队列");
    }

}
