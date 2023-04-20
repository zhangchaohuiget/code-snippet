package com.app;

import com.app.entity.User;
import com.app.service.UserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Test {

    @Autowired
    UserService userService;

    @org.junit.Test
    public void test() {
        User user = userService.queryUser1();
        System.out.println(user);
        User user2 = userService.queryUser2();
        System.out.println(user2);
    }

}
