package com.third;

import com.third.ConstClass;

/**
 * 名称：非主动使用类变量演示
 * 功能：
 * 条件：
 * Created by wq on 2018/1/1.
 */
public class NoInitialization {

    //演示一：通过子类引用父类的静态字段，不会导致父类初始化
//    public static void main(String[] args) {
//        System.out.println(SubClass.value);
//    }
    //演示二：通过数组定义来引用类，不会触发此类的初始化
//    public static void main(String[] args) {
//        SuperClass[] sca = new SuperClass[10];
//    }

    //演示三：常量在编译阶段会存入调用类的常量池中
    //本质上并没有直接引用到定义常量的类，因此不会触发定义常量类的初始化，HELLOWORD常量已经存到NoInitialization
    public static void main(String[] args) {
        System.out.println(ConstClass.HELLOWORLD);
    }
}
