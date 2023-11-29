package com.app;

/**
 * my strategy
 *
 * @author ch
 * @date 2023/11/21 14:29
 */
public class MyStrategy extends AbstractPluginsStrategy {

    @Override
    public void downloadInfo() {
        System.out.println("two download info");
    }

    @Override
    public void updateInfo() {
        System.out.println("two update info");
    }
}
