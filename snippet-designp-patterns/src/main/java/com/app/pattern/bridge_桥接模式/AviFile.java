package com.app.pattern.bridge_桥接模式;

/**
 * @version v1.0
 * @ClassName: AviFile
 * @Description: avi视频文件（具体的实现化角色）
 * @Author:
 */
public class AviFile implements VideoFile {

    public void decode(String fileName) {
        System.out.println("avi视频文件 ：" + fileName);
    }
}
