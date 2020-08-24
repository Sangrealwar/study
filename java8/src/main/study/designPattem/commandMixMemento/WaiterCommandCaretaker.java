package study.designPattem.commandMixMemento;

/**
 * 名称：侍从的命令备忘录维护者
 * 功能：
 * 条件：
 * Created by wq on 2018/5/29.
 */
public class WaiterCommandCaretaker {

    private WaiterCommandMemento memento;

    public WaiterCommandMemento getMemento() {
        return memento;
    }

    public void setMemento(WaiterCommandMemento memento) {
        this.memento = memento;
    }
}
