package com.app.pattern.adapter_适配器模式.class_adapter;

/**
 * @version v1.0
 * @ClassName: SDCardImpl
 * @Description: 具体的SD卡
 * @Author:
 */
public class SDCardImpl implements SDCard {

    public String readSD() {
        String msg = "SDCard read msg ： hello word SD";
        return msg;
    }

    public void writeSD(String msg) {
        System.out.println("SDCard write msg ：" + msg);
    }
}
