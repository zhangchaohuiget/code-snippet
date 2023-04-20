package com.app.model;

import cn.easyes.annotation.IndexId;
import cn.easyes.annotation.IndexName;
import lombok.Data;

import java.util.Date;

@Data
@IndexName("shop")
public class Shop {
    @IndexId
    private Long id;
    private String name;
    private Date createTime;
}
