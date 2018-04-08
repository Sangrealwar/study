package com.dispatch;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * 名称：invoke dynamic指令 MethodHandle，这个方法的使用和反射差别不大，单MethodHandle是模拟字节码层次的调用，没有Reflection中信息多
 *      且，MethodHandle是面向虚拟机的，Reflection是面向java语言的
 * 功能：
 * 条件：
 * Created by wq on 2018/1/6.
 */
public class MethodHandleTest {
    static class ClassA {
        public void println(String s) {
            System.out.println(s);
        }
    }

    public static void main(String[] args) throws Throwable {
        Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new ClassA();

        getPrintlnMH(obj).invokeExact(obj.getClass() +"：输出了个球");
    }

    private static MethodHandle getPrintlnMH(Object receiver) throws Throwable {
        /**
         * MethodType：代表方法类型，包含了返回值（第一个参数），参数（第二个及后续参数）
         */
        MethodType mt = MethodType.methodType(void.class, String.class);

        /**
         * lookup方法是MethodHandles的静态方法，这句话是在指定类中查找给定方法名（第二个参数），方法类型（methodType），并且符合调用权限的句柄（看作是引用）
         * 因为调用的是虚方法，方法第一个参数是隐式的，表示自己，这里可以通过bindTo来完成。
         */
        return lookup().findVirtual(receiver.getClass(), "println", mt).bindTo(receiver);
    }

}
