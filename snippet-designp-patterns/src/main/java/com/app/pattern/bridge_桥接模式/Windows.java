package com.app.pattern.bridge_桥接模式;

/**
 * @version v1.0
 * @ClassName: Windows
 * @Description: 扩展抽象化角色(windows操作系统)
 * @Author:
 */
public class Windows extends OpratingSystem {

    public Windows(VideoFile videoFile) {
        super(videoFile);
    }

    public void play(String fileName) {
        videoFile.decode(fileName);
    }
}
