package com.app.entity;

import com.app.config.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * user
 *
 * @author ch
 * @date 2023/4/17 00:57
 */
@Data
@TableName("user_info")
public class User extends BaseEntity {
    @TableId
    private Long userId;

    private String userName;

}
