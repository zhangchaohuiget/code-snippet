package com.app.metadata;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("ROW")
public class Line {

    private String 名称;

    private String 类型;

    private String 可为空;

    private String 默认;

    private String 存储;

    private String 注释;

    public String get注释() {
        return 注释;
    }

    public void set注释(String 注释) {
        this.注释 = 注释;
    }

    public String get名称() {
        return 名称;
    }

    public void set名称(String 名称) {
        this.名称 = 名称;
    }

    public String get类型() {
        return 类型;
    }

    public void set类型(String 类型) {
        this.类型 = 类型;
    }

    public String get可为空() {
        return 可为空;
    }

    public void set可为空(String 可为空) {
        this.可为空 = 可为空;
    }

    public String get默认() {
        return 默认;
    }

    public void set默认(String 默认) {
        this.默认 = 默认;
    }

    public String get存储() {
        return 存储;
    }

    public void set存储(String 存储) {
        this.存储 = 存储;
    }

}
