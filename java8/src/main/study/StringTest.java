package study;

/**
 * @Description: jdk8版本下的String测试
 *
 * 学习：
 * https://tech.meituan.com/2014/03/06/in-depth-understanding-string-intern.html
 * https://www.cnblogs.com/tiancai/p/9321338.html
 * https://www.oracle.com/java/technologies/javase/jdk7-relnotes.html#jdk7changes
 *
 * @Author; weiqian
 * @Date: 2020/8/31 8:58 上午
 * @Version:
 */
public class StringTest {
    public static void main(String[] args) {
        //有待测试，jdk6之前，所有打印均为false，因为字符串常量池不在堆上，在永久区，所以不管是否从常量池引用，new出来的都在堆上，直接""的字符串都在常量池里，地址是肯定不一样的
        //在jdk7之后
        //创建了2个对象，s是引用字符串的对象，分配在堆上，"1"是字符串，在1.7之后，存放在堆的常量池里，地址不同，和是否调用intern没关系
        String s = new String("1");
        s.intern();
        String s2 = "1";
        System.out.println(s == s2);

        String s3 = new String("1") + new String("1");
        s3.intern();
        String s4 = "11";
        System.out.println(s3 == s4);
    }
}
