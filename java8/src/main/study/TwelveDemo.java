package study;

/**
 * @Description: 理解volatile的happen before，表示的是，
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
