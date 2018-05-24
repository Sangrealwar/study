package study.designPattem.builder;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称：产品
 * 功能：
 * 条件：
 * Created by wq on 2018/5/23.
 */
public class Product {
    private List<String> parts = new ArrayList<>();

    public void add(String part) {
        parts.add(part);
    }

    public void show() {
        System.out.println("产品创建 -----");
        for (String part : parts) {
            System.out.println(part);
        }
    }
}
