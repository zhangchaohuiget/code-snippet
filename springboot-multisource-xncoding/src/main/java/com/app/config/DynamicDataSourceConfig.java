package com.app.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.app.common.mutidatesource.DSEnum;
import com.app.common.mutidatesource.DynamicDataSource;
import com.app.config.properties.MasterProperties;
import com.app.config.properties.SlaveProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.SQLException;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement(order = 2)
public class DynamicDataSourceConfig {

    @Autowired
    MasterProperties masterProperties;

    @Autowired
    SlaveProperties slaveProperties;

    /**
     * 主数据源
     */
    private DruidDataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        masterProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 从数据源
     */
    private DruidDataSource slaveDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        slaveProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 单数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "app", name = "muti-datasource-open", havingValue = "false")
    public DruidDataSource singleDatasource() {
        return masterDataSource();
    }

    /**
     * 多数据源连接池配置
     */
    @Bean
    @ConditionalOnProperty(prefix = "app", name = "muti-datasource-open", havingValue = "true")
    public DynamicDataSource mutiDataSource() {

        DruidDataSource masterDataSource = masterDataSource();
        DruidDataSource slaveDataSource = slaveDataSource();

        try {
            masterDataSource.init();
            slaveDataSource.init();
        } catch (SQLException sql) {
            sql.printStackTrace();
        }

        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        HashMap<Object, Object> hashMap = new HashMap<>();
        hashMap.put(DSEnum.MASTER_DATA_SOURCE, masterDataSource);
        hashMap.put(DSEnum.SLAVE_DATA_SOURCE, slaveDataSource);
        dynamicDataSource.setTargetDataSources(hashMap);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

}
