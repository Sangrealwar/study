package study.designPattem.status;

/**
 * 名称：睡觉状态
 * 功能：
 * 条件：
 * Created by wq on 2018/5/24.
 */
public class SleepState extends State {
    @Override
    public void writeProgram(Work work) {
        System.out.printf("当前时间{0}，睡觉了", work.getHour());
    }
}
