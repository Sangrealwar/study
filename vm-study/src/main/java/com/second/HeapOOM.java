package com.second;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称：-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 功能：
 * 条件：
 * Created by wq on 2017/12/30.
 */
public class HeapOOM {

    static class OOMObject {
    }

    public static void main(String[] args) {
        List<OOMObject> list = new ArrayList<OOMObject>();

        while(true){
            list.add(new OOMObject());
        }
    }
}

