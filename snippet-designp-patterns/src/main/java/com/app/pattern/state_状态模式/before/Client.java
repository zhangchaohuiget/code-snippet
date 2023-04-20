package com.app.pattern.state_状态模式.before;

/**
 * @version v1.0
 * @ClassName: Client
 * @Description: TODO(一句话描述该类的功能)
 * @Author: 
 */
public class Client {
    public static void main(String[] args) {
        //创建电梯对象
        Lift lift = new Lift();

        //设置当前电梯的状态
        lift.setState(ILift.RUNNING_STATE);

        //打开
        lift.open();
        lift.close();
        lift.run();
        lift.stop();
    }
}
