package study.concurrency.test;

import java.util.concurrent.CountDownLatch;

/**
 * 名称： CountDownLatch的例子，不要求每个线程在计数器到达之前等待，该工具简单的防止那些调用了await方法在所有线程都通过时执行
 *        countDown方法调用后，并不会阻塞当前线程
 * 功能：
 * 条件：
 * Created by wq on 2018/7/15.
 */
public class TestHarness {

    public static void main(String[] args) throws InterruptedException {
        TestHarness harness = new TestHarness();

        harness.timeTask(4, new Runnable() {
            @Override
            public void run() {
                System.out.println("我就随便跑一下");
            }
        });
    }

    public void timeTask(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            int b= i+1;
            String threadName = "线程" + b;
            Thread t = new Thread(threadName) {
                @Override
                public void run() {
                    try {
                        System.out.println(getName() + "在等待");
                        startGate.await();        //每个线程在等待开始的信号
                        System.out.println(getName() + "在等待开始的闭锁打开");
                        try {
                            task.run();
                        } finally {
                            System.out.println("执行完了");
                            endGate.countDown();
                            System.out.println("打开一个结束的闭锁");
                        }
                    } catch (InterruptedException ignored) {
                    }
                }
            };
            t.start();
        }

        long start = System.nanoTime();
        Thread.sleep(1000);             //主线程等待一下，可以直观的看出，所有线程在await阻塞了
        System.out.println("主线程：每个子任务的闭锁先关闭，等待每一个子任务自行开启闭锁");
        startGate.countDown();
        System.out.println("主线程：准备等待结束的闭锁打开");
        endGate.await();
        long end = System.nanoTime();
        System.out.println("耗时：" + (end - start) / 1000 + "ms");
    }
}
