package com.app.common.mutidatesource;

/**
 * 多数据源的枚举
 */
public interface DSEnum {

    /**
     * 核心数据源
     */
    String MASTER_DATA_SOURCE = "masterDataSource";
    /**
     * 其他业务的数据源
     */
    String SLAVE_DATA_SOURCE = "slaveDataSource";
}
