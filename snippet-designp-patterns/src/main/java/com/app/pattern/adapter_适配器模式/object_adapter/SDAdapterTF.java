package com.app.pattern.adapter_适配器模式.object_adapter;

/**
 * @version v1.0
 * @ClassName: SDAdapterTF
 * @Description: 适配器类
 * @Author:
 */
public class SDAdapterTF implements SDCard {

    //声明适配者类
    private TFCard tfCard;

    public SDAdapterTF(TFCard tfCard) {
        this.tfCard = tfCard;
    }

    public String readSD() {
        System.out.println("adapter read tf card");
        return tfCard.readTF();
    }

    public void writeSD(String msg) {
        System.out.println("adapter write tf card");
        tfCard.writeTF(msg);
    }
}
