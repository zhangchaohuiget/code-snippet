package com.app.pattern.responsibility_责任链模式;

/**
 * @version v1.0
 * @ClassName: LeaveRequest
 * @Description: 请假条类
 * @Author: 
 */
public class LeaveRequest {
    //姓名
    private String name;

    //请假天数
    private int num;

    //请假内容
    private String content;

    public LeaveRequest(String name, int num, String content) {
        this.name = name;
        this.num = num;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public String getContent() {
        return content;
    }
}
