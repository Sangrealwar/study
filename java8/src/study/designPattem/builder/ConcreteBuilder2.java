package study.designPattem.builder;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2018/5/23.
 */
public class ConcreteBuilder2 extends Builder {
    private Product product = new Product();

    @Override
    public void builderPartA() {
        product.add("部件X");
    }

    @Override
    public void builderPartB() {
        product.add("部件Y");
    }

    @Override
    public Product getResult() {
        return product;
    }
}
