# SpringBoot 整合Neo4j图数据库
## 引入依赖
```xml
<!-- neo4j data依赖 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-neo4j</artifactId>
</dependency>
```
## 修改配置
```yaml
# 配置neo4j连接
spring:
  data:
    neo4j:
      uri: bolt://localhost:7687
      username: neo4j
      password: password
```
## 启用配置
```java
@EnableNeo4jRepositories
```

## 编辑实体类
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@NodeEntity(label = "classInfo")
public class ClassInfo extends BaseNode {
    private String name;
    private Integer num;
}
```

## 编辑数据访问层
```java
@Repository
public interface ClassInfoRepository extends Neo4jRepository<ClassInfo, Long> {
}
```

