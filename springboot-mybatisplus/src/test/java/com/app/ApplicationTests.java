package com.app;

import com.app.entity.User;
import com.app.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * 测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Resource
    private UserService userService;

    /**
     * 测试增删改查
     */
    @Test
    public void test() {
        User user = new User();
        user.setUserName("zs");
        userService.insertUser(user);

        User user1 = userService.findById(user.getUserId());
        user1.setUserName("ls");
        userService.updateUser(user1);
        User user2 = userService.findById(user.getUserId());

        userService.deleteUser(user2.getUserId());

        User user3 = userService.findById(user2.getUserId());
        System.out.println("end");
    }
}
