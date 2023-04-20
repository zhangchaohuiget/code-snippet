package com.app;

import com.app.service.CalculateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculateTest {

    @Autowired
    private CalculateService calculateService;

    @Test
    public void testAdd() {
        calculateService.add(100, 100);
    }

}
