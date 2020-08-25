package study;

/**
 * @Description: 理解volatile的happen before，表示的是，volatile的
 * 就是如果一个线程先去写一个volatile变量，然后一个线程去读这个变量，那么这个写操作的结果一定对读的这个线程可见。
 * jsr133规范：http://www.cs.umd.edu/~pugh/java/memoryModel/jsr133.pdf
 * volatile的happen before定义
 * A write to a volatile variable v synchronizes-with all subsequent reads of v by any thread
 * (where subsequent is defined according to the synchronization order).
 *
 * 简单翻译为：对volatile变量的写会同步于所有线程对volatile的读。同步于表示写变量先行发生于读
 *
 * 内存语义
 * 当写一个volatile变量时，JMM会将线程对应的本地内存的共享变量刷新到主存中，当读取volatile变量时，线程会从主存中读取共享变量
 *
 *
 * 找一个对比，lock的
 * An unlock action on monitor m synchronizes-with all subsequent lock actions on m (where
 * subsequent is defined according to the synchronization order).
 *
 * 简单翻译为：在监视器下的解锁操作会同步于所有加锁操作，同步于表示解锁操作先行发生于加锁操作
 *
 *
 * 内存语义
 * 当线程释放锁时，JMM会把线程对应的本地内存的共享变量刷新到主存中，当线程获取锁时，JMM会将线程对应的本地内存置为无效
 * 从而使被监视器保护的临界代码必须从主内存去读取变量
 *
 * @Author; weiqian
 * @Date: 2020/8/18 9:07 上午
 * @Version:
 */
public class TwelveDemo {
    int a=0;
    int sum=0;
    volatile boolean flag=false;

    public void writer(){
        a=1;
        flag=true;
    }

    public void reader(){
        if(flag){
            sum=a;
        }
    }

    public static void main(String[] args)throws Exception {
        while (true){
            TwelveDemo twelveDemo=new TwelveDemo();
            Thread thread1=new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("1:"+System.currentTimeMillis());
                    twelveDemo.writer();
                }
            });

            Thread thread2=new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("2:"+System.currentTimeMillis());
                    twelveDemo.reader();
                }
            });

            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
            System.out.println("sum="+twelveDemo.sum);
            if(twelveDemo.sum==0){
                break;
            }
        }
    }
}
