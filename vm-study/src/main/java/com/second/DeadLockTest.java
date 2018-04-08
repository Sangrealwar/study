package com.second;

import java.io.IOException;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2017/12/31.
 */
public class DeadLockTest {

    public static void createBusyThread() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                    ;
            }
        }, "testBusyThread");
        thread.start();
    }

    public static void createLockThread(final Object lock) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "testLockThread");
        thread.start();
    }

    static class SyncAddRunnable implements Runnable {
        int a, b;

        public SyncAddRunnable(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            synchronized (Integer.valueOf(a)) {
                synchronized (Integer.valueOf(b)) {
                    System.out.printf(""+a + b);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        br.readLine();
//        createBusyThread();
//        br.readLine();
//        Object obj = new Object();
//        createLockThread(obj);

        //相互等待的死锁，因为Integer.valueOf会在-127到127之间建立缓存，循环多次提高在两个线程中等待死锁的概率
        for (int i = 0; i < 100; i++) {
            new Thread(new SyncAddRunnable(1, 2)).start();
            new Thread(new SyncAddRunnable(2, 1)).start();
        }
    }
}
