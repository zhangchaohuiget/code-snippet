package com.app.pattern.state_状态模式.after;

/**
 * @version v1.0
 * @ClassName: Client
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 
 */
public class Client {
    public static void main(String[] args) {
        //创建环境角色对象
        Context context = new Context();
        //设置当前电梯装填
        context.setLiftState(new ClosingState());

        context.open();
        context.run();
        context.close();
        context.stop();
    }
}
