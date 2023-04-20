package com.app;

import com.app.entity.User;
import com.app.service.UserService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DemoTest {

    @Autowired
    UserService userService;

    /**
     * 测试多数据源是否配置成功，顺便测试分页配置
     */
    @Test
    public void test() {
        Page<User> userPage1 = userService.queryUser1();
        System.out.println(userPage1);
        Page<User> userPage2 = userService.queryUser2();
        System.out.println(userPage2);
    }

}
