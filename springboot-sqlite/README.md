## SpringBoot集成Sqlite  

## 依赖
```xml
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.45.1.0</version>
</dependency>
```

## 配置
```yaml
spring:
  datasource:
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: org.sqlite.JDBC
    url: jdbc:sqlite:/data/sqlite/test.db
```

## sqlite数据类型
https://www.runoob.com/sqlite/sqlite-tutorial.html
