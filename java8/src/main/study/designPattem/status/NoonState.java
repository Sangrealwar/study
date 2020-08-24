package study.designPattem.status;

/**
 * 名称：正午工作状态
 * 功能：
 * 条件：
 * Created by wq on 2018/5/24.
 */
public class NoonState extends State {
    @Override
    public void writeProgram(Work work) {
        if (work.getHour() < 13) {
            System.out.printf("当前时间：{0}点，饿了，准备午睡", work.getHour());
        } else {
            work.setState(new AfternoonState());
            work.writeProgram();
        }
    }
}
