package study.designPattem.status;

/**
 * 名称：晚上工作状态
 * 功能：
 * 条件：
 * Created by wq on 2018/5/24.
 */
public class EveningState extends State {
    @Override
    public void writeProgram(Work work) {
        if (work.isFinish()) {
            work.setState(new RestState());
            work.writeProgram();
        } else {
            if (work.getHour() < 21) {
                System.out.printf("当前时间{0}，加个毛班，困死了", work.getHour());
            } else {
                work.setState(new SleepState());
                work.writeProgram();
            }
        }
    }
}
