package com.app.pattern.memento_备忘录模式.black_box;


/**
 * @version v1.0
 * @ClassName: RoleStateCaretaker
 * @Description: 备忘录对象管理对象
 * @Author:
 */
public class RoleStateCaretaker {

    //声明RoleStateMemento类型的变量
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
