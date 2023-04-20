package com.app.model;

import com.app.config.annotation.Document;
import com.app.config.annotation.Field;
import com.app.config.annotation.FieldType;
import com.app.config.annotation.Id;
import lombok.Data;

import java.util.Date;


@Data
@Document(indexName = "shop")
public class Shop {
    @Id
    @Field(type = FieldType.Long)
    private Long id;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(type = FieldType.Date)
    private Date createTime;
}
