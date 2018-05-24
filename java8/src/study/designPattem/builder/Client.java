package study.designPattem.builder;

/**
 * 名称：建造者模式，当创建复杂对象应该独立于该对象的组成部分以及装配方式时使用
 * 功能：好处是使得建造代码与表现分离，隐藏了产品是如何组装的
 * 条件：
 * Created by wq on 2018/5/23.
 */
public class Client {

    public static void main(String[] args) {
        Director director = new Director();

        Builder b1 = new ConcreteBuilder1();
        Builder b2 = new ConcreteBuilder2();

        director.contruct(b1);
        Product product1 = b1.getResult();
        product1.show();

        director.contruct(b2);
        Product product2 = b2.getResult();
        product2.show();
    }
}
