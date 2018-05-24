package study.designPattem.builder;

/**
 * 名称：指挥者，通过具体的建造者创建产品
 * 功能：
 * 条件：
 * Created by wq on 2018/5/23.
 */
public class Director {

    public void contruct(Builder builder) {
        builder.builderPartA();
        builder.builderPartB();
    }
}
