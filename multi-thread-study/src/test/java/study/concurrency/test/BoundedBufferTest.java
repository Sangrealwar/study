package study.concurrency.test;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2018/4/15.
 */
public class BoundedBufferTest extends TestCase {

    @Test
    void testIsEmptyWhenConstracted() {
        BoundedBuffer<Integer> bb = new BoundedBuffer(10);
        assertTrue(bb.isEmpty());
        assertFalse(bb.isFull());
    }

    @Test
    void testIsFullAfterPuts() throws InterruptedException {
        BoundedBuffer<Integer> bb = new BoundedBuffer(10);
        for (int i = 0; i < 10; i++) {
            bb.put(i);
        }
        assertTrue(bb.isFull());
        assertFalse(bb.isEmpty());
    }
}
