package com.app.service;

import com.app.common.annotion.DataSource;
import com.app.common.mutidatesource.DSEnum;
import com.app.entity.User;
import com.app.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User queryUser1() {
        return userMapper.selectById(1);
    }

    @DataSource(name = DSEnum.SLAVE_DATA_SOURCE)
    public User queryUser2() {
        return userMapper.selectById(1);
    }
}
