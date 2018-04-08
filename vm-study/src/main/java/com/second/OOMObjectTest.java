package com.second;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称：一个64k的占位对象，内存占用   -Xms100m -Xmx100m -XX:+UseSerialGC
 * 功能：
 * 条件：
 * Created by wq on 2017/12/31.
 */
public class OOMObjectTest {

    static class OOMObject {
        public byte[] placeholder = new byte[64 * 1024];
    }

    public static void fillHeap(int num) throws InterruptedException {
        List<OOMObject> list = new ArrayList<OOMObject>();
        for (int i = 0; i < num; i++) {
            Thread.sleep(50);
            list.add(new OOMObject());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        fillHeap(1000);
        System.gc();
        System.out.printf("ww");
    }
}
