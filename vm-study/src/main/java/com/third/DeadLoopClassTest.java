package com.third;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2018/1/1.
 */
public class DeadLoopClassTest {
    //虚拟机会保证初始化在多线程中正确的加锁同步，如果一个线程执行了cinit方法，其他线程会等待
    //当然，如果该线程初始化之后，其他线程就不会进入cinit方法，同一个类加载器中，一个类型只会被初始化一次
    static class DeadLoopClass {
        static {
            if (true) {
                System.out.println(Thread.currentThread() + "初始化DeadLoopClass");
                while (true) {
                }
            }
        }
    }

    public static void main(String[] args) {
        Runnable script = new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread()+"开始");
                DeadLoopClass dlc = new DeadLoopClass();
                System.out.println(Thread.currentThread()+"运行结束");
            }
        };

        Thread thread1 = new Thread(script);
        Thread thread2 = new Thread(script);
        thread1.start();
        thread2.start();
    }
}
