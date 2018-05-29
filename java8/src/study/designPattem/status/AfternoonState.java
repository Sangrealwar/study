package study.designPattem.status;

/**
 * 名称：下午工作状态
 * 功能：
 * 条件：
 * Created by wq on 2018/5/24.
 */
public class AfternoonState extends State {
    @Override
    public void writeProgram(Work work) {
        if (work.getHour() < 17) {
            System.out.printf("当前时间{0}，下午状态还可以，继续干", work.getHour());
        } else {
            work.setState(new EveningState());
            work.writeProgram();
        }
    }
}
