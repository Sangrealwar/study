package com.dispatch;

/**
 * 名称：单分派，多分派练习
 * 功能：
 * 条件：
 * Created by wq on 2018/1/6.
 */
public class MultiPatch {

    static  class Soft{}
    static class QQ extends Soft{}

    static class _360 extends Soft{}

    public static class Father{
        public void hardChoise(Soft arg){
            System.out.println("father choose soft");
        }

        public void hardChoise(QQ arg){
            System.out.println("father choose QQ");
        }

        public void hardChoise(_360 arg){
            System.out.println("father choose 360");
        }
    }

    public static class Son extends Father{
        public void hardChoise(Soft arg){
            System.out.println("son choose soft");
        }

        public void hardChoise(QQ arg){
            System.out.println("son choose QQ");
        }

        public void hardChoise(_360 arg){
            System.out.println("son choose 360");
        }
    }

    public static void main(String[] args) {
        Father father = new Father();
        Father son = new Son();

        Soft aa = new QQ();
        Soft bb = new _360();

        QQ qq = new QQ();
        _360 _360 = new _360();
        father.hardChoise(_360);
        son.hardChoise(qq);

        father.hardChoise(aa);
        son.hardChoise(bb);

        //首先是静态分派，Father有两个重载的方法，因为是编译期确定，根据传入的参数的静态类型，确定执行的那个版本
        //所以，调用的应该是hardChoise(_360 arg)和（hardChoise(QQ arg)）
        //然后是动态分派，根据invokevirtual传入的接收者的实际类型（Father，Son），所以实际执行的是Father，son的hardChoise
        //简单理解
        //重载方法的选择是静态的，只取决于传入参数类型和引用类型（静态类型）
        //重写方法的选择是动态的，取决于调用者的实际类型
    }
}
