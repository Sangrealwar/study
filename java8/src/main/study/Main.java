package study;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author; weiqian
 * @Date: 2020/8/26 9:19 下午
 * @Version:
 */
public class Main {
    public static void main(String[] args) {
test2();
    }

    private static void test2(){
        List<Integer> aa = new LinkedList<>();

        aa.add(1);
        aa.add(2);

        System.out.println(aa);
    }

    private static void test1() {
        int index  =1;
        String[] test = new String[3];
        String foo = test[index];
        System.out.println(foo);

        //装箱
        Set aa = new HashSet<>();
        aa.add("t");
        aa.add((byte)1);
        aa.add(1);
        aa.add("1");
        aa.add((int)1);
        aa.add((Integer)1);
        System.out.println( aa);

        int one  = 128;
        Integer two = 128;
        Integer three = 128;
        System.out.println(one == two);
        System.out.println(two == three);

        String str1 = "abc";
        String str2 = "abc";
        String str3 = new String("abc");
        String str4 = "a"+"b"+"c";
        System.out.println(str1 == str2);
        System.out.println(str2 == str3);
        System.out.println(str1 == str4);
    }


}
