package study.designPattem.commandMixMemento;

/**
 * 名称：烤面包
 * 功能：
 * 条件：
 * Created by wq on 2018/5/29.
 */
public class BakeBread extends Command {

    public BakeBread(Barbecuer barbecuer) {
        super(barbecuer);
    }


    @Override
    public void executeCommand() {
        barbecuer.bakeBread();
    }
}
