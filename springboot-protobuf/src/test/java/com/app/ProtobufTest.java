package com.app;

import com.app.model.bo.UserInfoFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class ProtobufTest {

    @Test
    public void codecTest() throws IOException {
        UserInfoFactory.UserInfo.Builder builder = UserInfoFactory.UserInfo.newBuilder();
        UserInfoFactory.UserInfo userInfo = builder.setId(1L).setName("zs").setSex(UserInfoFactory.EnumSex.SEX_MAN).build();
        // userInfo.writeTo(Files.newOutputStream(Paths.get("userInfo.bin")));
        // 序列化
        byte[] bytes = userInfo.toByteArray();
        // 反序列化
        UserInfoFactory.UserInfo parseFrom = UserInfoFactory.UserInfo.parseFrom(bytes);
        Assert.assertEquals(userInfo, parseFrom);
    }
}