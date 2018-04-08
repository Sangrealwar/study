package com.third;

/**
 * 名称：被动使用类字段演示一
 * 功能：
 * 条件：
 * Created by wq on 2018/1/1.
 */
public class SuperClass {
    static {
        System.out.println("父类初始化");
    }

    public static int value = 123;
}
