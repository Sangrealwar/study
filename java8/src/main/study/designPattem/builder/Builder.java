package study.designPattem.builder;

/**
 * 名称：抽象的构造者
 * 功能：
 * 条件：
 * Created by wq on 2018/5/23.
 */
public abstract class Builder {

    public abstract void builderPartA();

    public abstract void builderPartB();

    public abstract Product getResult();
}
