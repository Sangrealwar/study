package study.concurrency.test.PuzzleTest;

import java.util.concurrent.CountDownLatch;

/**
 * 名称：带结果的闭锁，用来解决并行解题器的无限循环问题，如果有一个线程解答了题目，则闭锁开启
 * 功能：
 * 条件：
 * Created by wq on 2018/7/18.
 */
public class ValueLatch<T> {
    private T value = null;
    private final CountDownLatch done = new CountDownLatch(1);

    public boolean isSet() {
        return (done.getCount() == 0);
    }

    public synchronized void setValue(T newValue) {
        if (!isSet()) {
            value = newValue;
            done.countDown();
        }
    }

    public T getValue() throws InterruptedException {
        done.await();
        synchronized (this) {
            return value;
        }
    }
}
