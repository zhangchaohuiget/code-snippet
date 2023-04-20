package com.app.dao.repository;

import com.app.common.dao.repository.ManagerMapper;
import com.app.dao.entity.ManagerInfo;

/**
 * Description  :
 */
public interface ManagerInfoDao extends ManagerMapper {
    ManagerInfo findByUsername(String username);
}
