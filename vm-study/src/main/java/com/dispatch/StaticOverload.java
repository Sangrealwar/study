package com.dispatch;

import java.io.Serializable;

/**
 * 名称：静态分派练习，静态分派是编译器决定采用哪种重载方式的一种做法
 * 功能：方法的调用是先经过调用，到解析的过程
 *          调用是确定调用的方法版本，传递的是符号引用
 *          然后在类加载的解析阶段，将符号引用转化为直接引用
 *      在方法调用中，用于方法解析的必须有前提，即：实际执行前必须有一个确定的版本
 *      目前有静态方法和私有方法，这两种方法不可能通过继承或其他方式重载版本，可用于解析
 * 条件：
 * Created by wq on 2018/1/6.
 */
public class StaticOverload {
    public static void sayHello(Object arg){
        System.out.println("hello Object");
    }

    public static void sayHello(int arg){
        System.out.println("hello int");
    }

    public static void sayHello(long arg){
        System.out.println("hello long");
    }

    public static void sayHello(Character arg){
        System.out.println("hello Character");
    }

    public static void sayHello(char arg){
        System.out.println("hello char");
    }

    public static void sayHello(char... arg){
        System.out.println("hello char...");
    }

    public static void sayHello(Serializable arg){
        System.out.println("hello Serializable");
    }

    public static void main(String[] args) {
        //首先匹配到char，注意，必须是单引号，双引号就是字符串了，字符串实现了Serializable接口
        //详情请看编译器的提示。。。
        sayHello('a');
    }
}
