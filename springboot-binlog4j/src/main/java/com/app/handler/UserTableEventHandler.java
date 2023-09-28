package com.app.handler;

import com.app.constant.Constants;
import com.app.entity.User;
import com.gitee.Jmysy.binlog4j.core.BinlogEvent;
import com.gitee.Jmysy.binlog4j.core.IBinlogEventHandler;
import com.gitee.Jmysy.binlog4j.springboot.starter.annotation.BinlogSubscriber;
import lombok.extern.slf4j.Slf4j;

/**
 * 单表监听
 *
 * @author zhangch
 * @date 2023/9/5 09:21
 */
@Slf4j
@BinlogSubscriber(clientName = "master")
public class UserTableEventHandler implements IBinlogEventHandler<User> {

    @Override
    public void onInsert(BinlogEvent<User> event) {
        log.info("插入数据：{}", event.getData());
    }

    @Override
    public void onUpdate(BinlogEvent<User> event) {
        log.info("修改数据：{}", event.getData());
    }

    @Override
    public void onDelete(BinlogEvent<User> event) {
        log.info("删除数据：{}", event.getData());
    }

    @Override
    public boolean isHandle(String database, String table) {
        return Constants.DATABASE_NAME.equals(database) && Constants.USER_TABLE_NAME.equals(table);
    }

}