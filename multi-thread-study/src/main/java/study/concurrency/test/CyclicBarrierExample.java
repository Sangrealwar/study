package study.concurrency.test;



import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 名称：
 * 功能：利用CyclicBarrier等待所有的生产者线程和消费者线程同时开始，然后根据伪随机技术，判断两个消费者是否按顺序执行
 * 在并行迭代算法中十分有用，通常这种算法将一个问题拆分为一系列的独立问题
 *
 *
 * 和闭锁一样，
 * 条件：
 * Created by wq on 2018/4/15.
 */
public class CyclicBarrierExample {
    private static final ExecutorService pool = Executors.newCachedThreadPool();

    private final AtomicInteger putSum = new AtomicInteger(0);
    private final AtomicInteger takeSum = new AtomicInteger(0);
    private final CyclicBarrier barrier;
    private final SemaphoreExample<Integer> bb;
    private final int nTrials, nPairs;

    public static void main(String[] args) {
        new CyclicBarrierExample(10, 10, 100).test();
        pool.shutdown();
    }

    CyclicBarrierExample(int capacity, int npairs, int ntrials) {
        this.bb = new SemaphoreExample<>(capacity);
        this.nTrials = ntrials;
        this.nPairs = npairs;
        this.barrier = new CyclicBarrier(capacity * 2 + 1);
    }

    void test() {
        try {
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
            }
            //等待所有线程就绪
            barrier.await();
            //等待所有线程执行完
            barrier.await();
            if(putSum.get() == takeSum.get()){
                System.out.println("相等");
            }
            System.out.println("程序执行了");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }

    }

    class Producer implements Runnable {
        @Override
        public void run() {
            try {
                int seed = (this.hashCode() ^ (int) System.nanoTime());
                int sum = 0;
                System.out.println("生产者"+Thread.currentThread().getName()+"等待开始计算");
                barrier.await();
                //每个线程内部将缓存的数字合并起来
                for (int i = nTrials; i > 0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = xorShift(seed);
                }
                putSum.getAndAdd(sum);
                System.out.println("生产者"+Thread.currentThread().getName()+"等待结束计算");
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                int sum = 0;
                System.out.println("消费者"+Thread.currentThread().getName()+"等待开始计算");
                barrier.await();
                for (int i = nTrials; i > 0; --i) {
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                System.out.println("消费者"+Thread.currentThread().getName()+"等待结束计算");
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }
}
