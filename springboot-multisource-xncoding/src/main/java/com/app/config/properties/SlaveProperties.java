package com.app.config.properties;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 主库
 */
@Data
@Component
@ConfigurationProperties(prefix = "spring.datasource.slave")
public class SlaveProperties {
    private String driverClassName;
    private String url;
    private String userName;
    private String password;

    public void config(DruidDataSource dataSource) {
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
    }
}
