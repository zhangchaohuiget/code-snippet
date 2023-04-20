package com.app.pattern.bridge_桥接模式;

/**
 * @version v1.0
 * @ClassName: VideoFile
 * @Description: 视频文件(实现化角色)
 * @Author:
 */
public interface VideoFile {

    //解码功能
    void decode(String fileName);
}
