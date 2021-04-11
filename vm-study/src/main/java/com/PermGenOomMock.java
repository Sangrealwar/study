package com;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author; weiqian
 * @Date: 2021/3/14 10:57 下午
 * @Version:
 */
public class PermGenOomMock {
    public static void main(String[] args) {
        URL url = null;
        List<ClassLoader> classLoaderList = new ArrayList<ClassLoader>();
        try {
            url = new File("/Users/weiqian/Documents/3_study/my/study/vm-study/StaticClass").toURI().toURL();
            URL[] urls = {url};
            while (true){
                ClassLoader loader = new URLClassLoader(urls);
                classLoaderList.add(loader);
                loader.loadClass("com.StaticClass");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
