package study.concurrency.test.ThisEscape;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author; weiqian
 * @Date: 2021/4/1 5:58 下午
 * @Version:
 */
public class UnSafeEscape {

    //当this引用逸出后，在构造函数未完成前，可能被其他对象获取到this的引用，从而获取到字段的零值（如int的0）
    private int a =2;

    public UnSafeEscape(EventSource es){
        es.registerListener(new EventListener() {
            @Override
            public long run() {
                return UnSafeEscape.this.a;
            }
        });
    }

    final static EventSource eventSource = new EventSource();

    public static void main(String[] args) {
        AtomicInteger count = new AtomicInteger();

        //让所有线程先暂停
        final CountDownLatch main = new CountDownLatch(1);

        //不停尝试打印
        Runnable a = new Runnable() {
            @Override
            public void run() {
                try {
                    main.await();
                    int i = 0;
                    while (i < 100) {
                        Thread.sleep(Math.round(500));
                        long run = eventSource.run();
                        if (run != 2) {
                            System.out.println("不正常的值");
                        }else{
                            System.out.println("正常值");
                        }
                        i++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(a);
        thread.start();

        Runnable b = new Runnable() {
            @Override
            public void run() {
                try {
                    main.await();
                    int i = 0;
                    while (i < 100) {
                        UnSafeEscape a = new UnSafeEscape(eventSource);
                        Thread.sleep(Math.round(500));
                        eventSource.clear();
                        count.incrementAndGet();
                        i++;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread threadb = new Thread(b);
        threadb.start();

        //释放闭锁，让所有线程开始执行
        main.countDown();
    }
}
