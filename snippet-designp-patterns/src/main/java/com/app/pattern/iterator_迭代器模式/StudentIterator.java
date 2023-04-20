package com.app.pattern.iterator_迭代器模式;

/**
 * @version v1.0
 * @ClassName: StudentIterator
 * @Description: 抽象迭代器角色接口
 * @Author: 
 */
public interface StudentIterator {

    //判断是否还有元素
    boolean hasNext();

    //获取下一个元素
    Student next();
}
