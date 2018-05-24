package study.concurrency.test;

import java.util.concurrent.Semaphore;

/**
 * 名称：
 * 功能：用信号量控制put和take，实际中，可能会用到ArrayBlockingQueue等有界缓存，原理相同
 * 条件：
 * Created by wq on 2018/4/15.
 */
public class BoundedBuffer<E> {

    //表示客户端获得的许可（初始为0）
    private final Semaphore availableItems;
    //表示数组原有的许可（为数组长度）
    private final Semaphore availableSpaces;

    private final E[] items;
    private int putPosition = 0, takePosition = 0;

    public BoundedBuffer(int capacity) {
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull() {
        return availableSpaces.availablePermits() == 0;
    }

    public void put(E x) throws InterruptedException {
        availableSpaces.acquire();
        doInsert(x);
        availableItems.release();
    }

    public E take() throws InterruptedException {
        availableItems.acquire();
        E item = doExtract();
        availableSpaces.release();
        return item;
    }

    private synchronized void doInsert(E x) {
        int i = putPosition;
        items[i] = x;
        putPosition = (++i == items.length) ? 0 : i;
    }

    private synchronized E doExtract() {
        int i = takePosition;
        E item = items[i];
        items[i] = null;
        takePosition = (++i == items.length) ? 0 : i;
        return item;
    }


}
