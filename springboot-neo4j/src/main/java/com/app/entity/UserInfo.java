package com.app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity(label = "userInfo")
public class UserInfo extends BaseNode {

    private String userId;

    /**
     * 如果名称与数据库一致那么就不需要这个注解
     */
    @Property(name = "name")
    private String name;

    private Integer age;

    private String sex;

}