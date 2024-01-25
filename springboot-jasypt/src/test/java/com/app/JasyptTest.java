package com.app;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * jasypt test
 *
 * @author ch
 * @date 2024/1/25 11:45
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class JasyptTest {

    @Autowired
    private StringEncryptor stringEncryptor;

    /**
     * 测试加解密
     */
    @Test
    public void encrypt() {
        String usernameEnc = stringEncryptor.encrypt("admin");
        String passwordEnc = stringEncryptor.encrypt("admin123");
        log.info("test username encrypt is {}", usernameEnc);
        log.info("test password encrypt is {}", passwordEnc);

        log.info("test username is {}", stringEncryptor.decrypt(usernameEnc));
        log.info("test password is {}", stringEncryptor.decrypt(passwordEnc));
    }

}
