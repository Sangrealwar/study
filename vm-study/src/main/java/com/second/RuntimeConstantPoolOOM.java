package com.second;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2017/12/30.
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        //使用list保存常量池引用，避免gc回收
        List<String> list = new ArrayList<String>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
