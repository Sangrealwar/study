package com.third;

import java.io.IOException;
import java.io.InputStream;

/**
 * 名称：类加载器，比较两个类是否相等，只有在两个类是由同一类加载器加载的前提下，才可以相等
 * "相等"包括Class的equals()方法，isAssignableFrom()方法，isInstance()方法，同时也包括instanceof方法判断从属关系
 * Created by wq on 2018/1/2.
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader myLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";

                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    //这里坑了自己一波，输入流没有把数据输入到b中
                    //这里大概意思应该是开始读取数据，并读到传入的byte数组中（b）
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        //虽然类相同，但是类加载器不同，所以是不同的类
        Object obj =  myLoader.loadClass("com.third.ClassLoaderTest").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof ClassLoaderTest);
    }
}
