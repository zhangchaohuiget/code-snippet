package com.app.pattern.adapter_适配器模式.object_adapter;

/**
 * @version v1.0
 * @ClassName: TFCard
 * @Description: 适配者类的接口
 * @Author:
 */
public interface TFCard {

    //从TF卡中读取数据
    String readTF();
    //往TF卡中写数据
    void writeTF(String msg);
}
