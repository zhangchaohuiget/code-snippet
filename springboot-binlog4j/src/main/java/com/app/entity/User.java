package com.app.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户表
 *
 * @author ch
 * @date 2023/9/4 17:58
 */
@Data
public class User {
    private Long id;
    private String name;
    private Integer sex;
    private Date createTime;
}
