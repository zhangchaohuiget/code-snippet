package com.app.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Data
@Document(indexName = "shop", type = "_doc")
public class Shop {
    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private String name;
    @Field(name = "create_time", type = FieldType.Date)
    private Date createTime;
}
