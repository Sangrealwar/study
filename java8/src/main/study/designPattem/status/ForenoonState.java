package study.designPattem.status;

/**
 * 名称：上午工作状态
 * 功能：
 * 条件：
 * Created by wq on 2018/5/24.
 */
public class ForenoonState extends State {

    @Override
    public void writeProgram(Work work) {
        if (work.getHour() < 12) {
            System.out.printf("当前时间{0}点，上午工作，精神百倍", work.getHour());
        } else {
            work.setState(new NoonState());
            work.writeProgram();
        }
    }
}
