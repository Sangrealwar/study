package com.dispatch;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import static java.lang.invoke.MethodHandles.lookup;

/**
 * 名称： 动态分派练习，这里可以利用MethodHandle来实现程序员动态分派，而不是交由虚拟机
 * 功能：
 * 条件：
 * Created by wq on 2018/1/6.
 */
public class DynamicDispatchTest {

    class GrandFather {
        void thinking() {
            System.out.println("i am grandfather");
        }
    }

    class Father extends GrandFather {
        @Override
        void thinking() {
            System.out.println("i am father");
        }
    }

    class Son extends Father {
        @Override
        void thinking() {
            try {
                //深入jvm虚拟机所示：这里如果要输出"i am grandfather"，根据invokevirtual动态分配的原则，分派逻辑是按照接受者的实际参数进行分派
//            System.out.println("i am son");
                //but，根据jvm规范，并不能输出grandfather，findSpecial和findVirtual一样，都是递归查找父类，找到就返回
                MethodType mt = MethodType.methodType(void.class);
                MethodHandle methodHandle = lookup().findSpecial(GrandFather.class,
                        "thinking", mt,getClass());
                methodHandle.invoke(this);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        (new DynamicDispatchTest()).new Son().thinking();
    }

}
