package com.app.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置
 */
@Configuration
@MapperScan(basePackages = {"com.app.mapper"})
public class MybatisPlusConfig {

}
