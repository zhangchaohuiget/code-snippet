package com.app.handler;

import com.gitee.Jmysy.binlog4j.core.BinlogEvent;
import com.gitee.Jmysy.binlog4j.core.IBinlogEventHandler;
import com.gitee.Jmysy.binlog4j.springboot.starter.annotation.BinlogSubscriber;
import lombok.extern.slf4j.Slf4j;

/**
 * 监听某个库的所有表
 *
 * @author ch
 * @date 2023/9/5 09:22
 */
@Slf4j
@BinlogSubscriber(clientName = "master", database = "hzwq-test")
public class TableEventHandler implements IBinlogEventHandler {

    @Override
    public void onInsert(BinlogEvent event) {
        log.info("{}表插入数据：{}", event.getTable(), event.getData());
    }

    @Override
    public void onUpdate(BinlogEvent event) {
        log.info("{}表修改数据:{}", event.getTable(), event.getData());
    }

    @Override
    public void onDelete(BinlogEvent event) {
        log.info("{}表删除数据:{}", event.getTable(), event.getData());
    }
}
