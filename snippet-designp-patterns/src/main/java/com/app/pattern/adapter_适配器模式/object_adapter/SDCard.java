package com.app.pattern.adapter_适配器模式.object_adapter;

/**
 * @version v1.0
 * @ClassName: SDCard
 * @Description: 目标接口
 * @Author:
 */
public interface SDCard {

    //从SD卡中读取数据
    String readSD();
    //往SD卡中写数据
    void writeSD(String msg);
}
