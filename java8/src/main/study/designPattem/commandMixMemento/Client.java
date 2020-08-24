package study.designPattem.commandMixMemento;

/**
 * 名称：
 * 功能：
 * 条件：
 * Created by wq on 2017/12/3.
 */
public class Client {

    public static void main(String[] args) {

        Barbecuer boy = new Barbecuer("烤肉师傅：大雄");

        Command bakeMutton = new BakeMuttonCommand(boy);
        Command bakeMutton1 = new BakeMuttonCommand(boy);
        Command bakeChickenWing = new BakeChickenWingCommand(boy);
        Command bakeBread = new BakeBread(boy);

        Waiter girl = new Waiter();

        girl.setOrder(bakeMutton);
        girl.setOrder(bakeMutton1);
        girl.setOrder(bakeChickenWing);
        girl.setOrder(bakeBread);

        //侍从的守护记录一个备忘
        WaiterCommandCaretaker commandCaretaker = new WaiterCommandCaretaker();
        commandCaretaker.setMemento(girl.savePreviousCommands());

        System.out.println("面包不烤了");
        girl.cancelCommand(bakeChickenWing);
        girl.cancelCommand(bakeBread);
        girl.notifyCommand();
        System.out.println("做完啦");

        System.out.println("命令还原");
        girl.recoverCommands(commandCaretaker.getMemento());
        girl.notifyCommand();
        System.out.println("再做一次做完啦");
    }


}
