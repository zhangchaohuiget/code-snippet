package com.app.service;

import com.app.entity.User;
import com.app.mapper.UserMapper;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public Page<User> queryUser1() {
        return userMapper.selectPage(new Page<>(), null);
    }

    @DS("slave")
    public Page<User> queryUser2() {
        return userMapper.selectPage(PageDTO.of(1, 10), null);
    }
}
