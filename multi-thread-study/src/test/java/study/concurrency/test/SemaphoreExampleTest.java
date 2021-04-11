package study.concurrency.test;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2018/4/15.
 */
public class SemaphoreExampleTest extends TestCase {

    @Test
    public void testIsEmptyWhenConstracted() {
        SemaphoreExample<Integer> bb = new SemaphoreExample(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    @Test
    public void testIsFullAfterPuts() throws InterruptedException {
        SemaphoreExample<Integer> bb = new SemaphoreExample(10);
        for (int i = 0; i < 10; i++) {
            bb.put(i);
        }
        assertTrue(bb.isFull());
        assertFalse(bb.isEmpty());
    }

    @Test
    public void testTakeBlockWhenEmpty(){
        final SemaphoreExample<Integer> bb = new SemaphoreExample(10);
        Thread taker = new Thread() {
            public void run() {
                try {
                    int unused = bb.take();
                    //如果执行到这里，说明出现一个错误
                    fail();
                } catch (InterruptedException success) {
                    System.out.println("阻塞成功");
                }
            }
        };
        try {
            taker.start();
            Thread.sleep(1000);
            taker.interrupt();
            taker.join(1000);
            System.out.println(taker.isAlive());
        }catch (Exception e){
            fail();
        }
    }
}
