package study.designPattem.commandMixMemento;

import java.util.List;

/**
 * 名称：侍从的命令备忘录
 * 功能：
 * 条件：
 * Created by wq on 2017/12/3.
 */
public class WaiterCommandMemento {

    /**
     * 记录下来的命令
     */
    private List<Command> commands;

    public WaiterCommandMemento(List<Command> commands) {
        this.commands = commands;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }
}
