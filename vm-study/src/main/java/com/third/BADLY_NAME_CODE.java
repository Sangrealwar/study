package com.third;

/**
 * 名称：命名不规范的例子
 * 功能：
 * 条件：
 * Created by wq on 2018/1/7.
 */
public class BADLY_NAME_CODE {

    enum colors {
        red, blue, green;
    }

    static final int _FORTY_TWO = 42;

    public static int NOT_A_CONSTANT = _FORTY_TWO;

    protected void BADLY_NAME_CODE() {
        return;
    }

    public void NOTcamelCASEmethodNAME() {
        return;
    }
}
