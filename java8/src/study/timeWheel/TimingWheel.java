package study.timeWheel;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 名称：时间轮算法简要
 *       有三个约定：1.新加入的对象总是保存在当前指针转动的上一个位置
 *                  2.相同的对象仅存在一个slot中
 *                  3.指针转动到当前位置对应的slot中保存的对象，就意味着timeout了
 *
 *       正确性有待考究
 * 功能：
 * 条件：
 * Created by wq on 2018/6/6.
 */
public class TimingWheel<E> {
    //timeUnit是时间单位，类比时钟的秒
    //每一个tick代表的时长，类比时钟的1
    private final long tickDuration;
    //每一轮的tick数，类比时钟的12
    private final int ticksPerWheel;
    private volatile int currentTickIndex = 0;

    private final CopyOnWriteArrayList<ExpirationListener<E>> expirationListeners = new CopyOnWriteArrayList<ExpirationListener<E>>();
    private final ArrayList<Slot<E>> wheel;
    private final Map<E, Slot<E>> indicator = new ConcurrentHashMap<E, Slot<E>>();

    private final AtomicBoolean shutdown = new AtomicBoolean(false);
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private Thread workerThread;

    public TimingWheel(int tickDuration, int ticksPerWheel, TimeUnit timeUnit) {
        if (timeUnit == null) {
            throw new NullPointerException("unit");
        }
        if (tickDuration <= 0) {
            throw new IllegalArgumentException("tickDuration must be greater than 0: " + tickDuration);
        }
        if (ticksPerWheel <= 0) {
            throw new IllegalArgumentException("ticksPerWheel must be greater than 0: " + ticksPerWheel);
        }

        this.wheel = new ArrayList<Slot<E>>();
        this.tickDuration = TimeUnit.MILLISECONDS.convert(tickDuration, timeUnit);
        this.ticksPerWheel = ticksPerWheel + 1;

        for (int i = 0; i < this.ticksPerWheel; i++) {
            wheel.add(new Slot<E>(i));
        }
        wheel.trimToSize();

        workerThread = new Thread(new TickWorker(), "Timing-Wheel");
    }

    public void start() {
        if (shutdown.get()) {
            throw new IllegalStateException("Cannot be started once stopped");
        }

        if (!workerThread.isAlive()) {
            workerThread.start();
        }
    }

    public boolean stop() {
        if (!shutdown.compareAndSet(false, true)) {
            return false;
        }

        boolean interrupted = false;
        while (workerThread.isAlive()) {
            workerThread.interrupt();
            try {
                workerThread.join(100);
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
        if (interrupted) {
            Thread.currentThread().interrupt();
        }

        return true;
    }

    public void addExpirationListener(ExpirationListener<E> listener) {
        expirationListeners.add(listener);
    }

    public void removeExpirationListener(ExpirationListener<E> listener) {
        expirationListeners.remove(listener);
    }

    /***
     * Start_Timer
     * @param e
     * @return
     */
    public long add(E e) {
        synchronized (e) {
            //首先检查同样的元素是否已经添加到TimingWheel中，确保约定2
            checkAdd(e);

            //返回上一个位置，根据记录的当前位置判断，可能当前是0点，那就返回11点
            int previousTickIndex = getPreviousTickIndex();
            //把当前对象加入到这个槽的数组中
            Slot<E> slot = wheel.get(previousTickIndex);
            slot.add(e);
            indicator.put(e, slot);

            //返回这个对象的过期时间，因为当前已经进行到了0点，加入到了11点，那总共需要走(12-1)*60个秒
            return (ticksPerWheel - 1) * tickDuration;
        }
    }

    private void checkAdd(E e) {
        Slot<E> slot = indicator.get(e);
        if (slot != null) {
            slot.remove(e);
        }
    }

    private int getPreviousTickIndex() {
        lock.readLock().lock();
        try {
            int cti = currentTickIndex;
            if (cti == 0) {
                return ticksPerWheel - 1;
            }

            return cti - 1;
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * Stop_Timer
     * @param e
     * @return
     */
    public boolean remove(E e) {
        synchronized (e) {
            //获取所在的位置
            Slot<E> slot = indicator.get(e);
            if (slot == null) {
                return false;
            }
            //指示槽的玩意也删了
            indicator.remove(e);
            //从槽中删除
            return slot.remove(e) != null;
        }
    }

    private void notifyExpired(int idx) {
        Slot<E> slot = wheel.get(idx);
        Set<E> elements = slot.elements();
        for (E e : elements) {
            //过期了，就从这个槽中删除
            slot.remove(e);
            synchronized (e) {
                Slot<E> latestSlot = indicator.get(e);
                if (latestSlot.equals(slot)) {
                    //指示槽也删了
                    indicator.remove(e);
                }
            }
            //通知一下，过期了
            for (ExpirationListener<E> listener : expirationListeners) {
                listener.expired(e);
            }
        }
    }


    private class TickWorker implements Runnable {

        private long startTime;
        private long tick;

        @Override
        public void run() {
            startTime = System.currentTimeMillis();
            tick = 1;

            //获取当前的tick
            for (int i = 0; !shutdown.get(); i++) {
                if (i == wheel.size()) {
                    i = 0;
                }
                lock.writeLock().lock();
                try {
                    currentTickIndex = i;
                } finally {
                    lock.writeLock().unlock();
                }
                //通知这个tick已经过期了
                notifyExpired(currentTickIndex);
                waitForNextTick();
            }
        }

        private void waitForNextTick() {
            for (; ; ) {
                long currentTime = System.currentTimeMillis();
                long sleepTime = tickDuration * tick - (currentTime - startTime);

                if (sleepTime <= 0) {
                    break;
                }

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    return;
                }
            }

            tick++;
        }
    }

    private static class Slot<E> {

        private int id;
        private Map<E, E> elements = new ConcurrentHashMap<E, E>();

        public Slot(int id) {
            this.id = id;
        }

        public void add(E e) {
            elements.put(e, e);
        }

        public E remove(E e) {
            return elements.remove(e);
        }

        public Set<E> elements() {
            return elements.keySet();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + id;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Slot other = (Slot) obj;
            if (id != other.id)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Slot [id=" + id + ", elements=" + elements + "]";
        }

    }

}
