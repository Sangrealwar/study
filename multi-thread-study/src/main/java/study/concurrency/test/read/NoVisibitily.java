package study.concurrency.test.read;

/**
 * @Description: 构造函数中发生this逸出
 * @Author; weiqian
 * @Date: 2021/3/28 5:08 下午
 * @Version:
 */
public class NoVisibitily {

    private  static  boolean ready;

    private static  int num;

    private static class ReaderThread extends  Thread{
        @Override
        public void run() {
            while (!ready){
                Thread.yield();
            }
            System.out.println(num);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        num = 42;
        ready=true;
    }
}
