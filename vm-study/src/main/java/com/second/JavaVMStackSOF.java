package com.second;

/**
 * 名称： -Xss128k
 * 功能：
 * 条件：
 * Created by wq on 2017/12/30.
 */
public class JavaVMStackSOF {

    private int stackLengt = 1;

    public void stackLeak() {
        stackLengt++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable {
        JavaVMStackSOF oom = new JavaVMStackSOF();

        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.printf("stack length:" + oom.stackLengt);
            throw e;
        }
    }
}
