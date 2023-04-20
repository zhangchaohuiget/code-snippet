package com.app.pattern.adapter_适配器模式.class_adapter;

/**
 * @version v1.0
 * @ClassName: Computer
 * @Description: 计算机类
 * @Author:
 */
public class Computer {

    //从SD卡中读取数据
    public String readSD(SDCard sdCard) {
        if(sdCard == null) {
            throw  new NullPointerException("sd card is not null");
        }
        return sdCard.readSD();
    }
}
