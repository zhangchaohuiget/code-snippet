package com.app.service;

import org.springframework.stereotype.Service;

@Service
public class CalculateService {

    /**
     * 加
     */
    public int add(int i, int j) {
        System.out.println("我是加法");
        return i + j;
    }

    /**
     * 减
     */
    public int sub(int i, int j) {
        System.out.println("我是减法");
        return i - j;
    }

    /**
     * 乘
     */
    public int mul(int i, int j) {
        System.out.println("我是乘法");
        return i * j;
    }

    /**
     * 除
     */
    public int div(int i, int j) {
        System.out.println("我是除法");
        return i / j;
    }
}