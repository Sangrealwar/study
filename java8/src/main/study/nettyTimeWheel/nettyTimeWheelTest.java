package study.nettyTimeWheel;

//import io.netty.util.HashedWheelTimer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2018/6/8.
 */
public class nettyTimeWheelTest {

//    public static void main(String[] args) throws Exception {
//        test1();
//
//
////        test2();
//    }
//
//    public static void test1() throws InterruptedException {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(100, TimeUnit.MILLISECONDS);
//        System.out.println("start:" + LocalDateTime.now().format(formatter));
//        hashedWheelTimer.newTimeout(timeout -> {
//            System.out.println("task :" + LocalDateTime.now().format(formatter));
//        }, 3, TimeUnit.SECONDS);
//        Thread.sleep(5000);
//
//    }
//
//    public static void test2() throws Exception {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        HashedWheelTimer hashedWheelTimer = new HashedWheelTimer(100, TimeUnit.MILLISECONDS);
//        System.out.println("start:" + LocalDateTime.now().format(formatter));
//        hashedWheelTimer.newTimeout(timeout -> {
//            Thread.sleep(3000);
//            System.out.println("task1:" + LocalDateTime.now().format(formatter));
//        }, 3, TimeUnit.SECONDS);
//        hashedWheelTimer.newTimeout(timeout -> System.out.println("task2:" + LocalDateTime.now().format(
//                formatter)), 4, TimeUnit.SECONDS);
//        Thread.sleep(10000);
//    }

}
