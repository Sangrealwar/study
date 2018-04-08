package com.dispatch;

import com.third.ConstClass;

/**
 * 名称：动态分派练习，多态性的重写，例子就不写了
 * 功能：
 * 条件：
 * Created by wq on 2018/1/6.
 */
public class DynamicDispatch {

    Object aa = new ConstClass();
    //主要是invokevirtual指令
    //该指令的多态查找过程如下：
    //1.找到操作数栈顶的第一个元素所指的元素的实际类型（如ConstClass），记作C；
    //2.在类型C中找与常量中描述符和简单名称相符的方法，进行访问权限校验，如果通过，返回该方法的直接引用，否则
    //报IllegalAccessError异常
    //3.按照继承关系从下往上以此对C的父类进行第2步搜索
    //还没找到就报错

    //由于invoke virtual指令执行的第一步就是在运行期确定接受者的实际类型，所以在执行invoke virtual指令都把常量池
    //中的类方法符号引用解析到了不同的直接引用上。这种运行期根据实际类型确定方法执行的过程称为动态分派。

}
