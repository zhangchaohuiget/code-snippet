package com.app.service;

import com.app.entity.User;
import com.app.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 后台用户管理
 */

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 通过ID查找用户
     *
     * @param id
     * @return
     */
    public User findById(Long id) {
        return userMapper.selectById(id);
    }

    /**
     * 新增用户
     *
     * @param user
     */
    public void insertUser(User user) {
        userMapper.insert(user);
    }

    /**
     * 修改用户
     *
     * @param user
     */
    public void updateUser(User user) {
        userMapper.updateById(user);
    }

    /**
     * 删除用户
     *
     * @param id
     */
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }

}
