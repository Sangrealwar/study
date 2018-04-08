package study.multithreading.activeObject.reusable;

import java.util.concurrent.Future;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2017/9/4.
 */
public interface SampleActiveObject {
    Future<String> process(String arg, int i);
}
