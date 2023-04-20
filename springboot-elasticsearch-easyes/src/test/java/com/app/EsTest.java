package com.app;

import com.app.mapper.ShopMapper;
import com.app.model.Shop;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * test
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class EsTest {

    @Autowired
    private ShopMapper shopMapper;

    @Test
    public void test() {
        Shop shop = new Shop();
        shop.setId(2L);
        shop.setName("ls");
        shop.setCreateTime(new Date());
        shopMapper.insert(shop);

        Shop shop1 = shopMapper.selectById(2L);
        System.out.println(shop1);
    }
}
