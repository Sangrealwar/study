package study.designPattem.status;

/**
 * 名称：工作类
 * 功能：
 * 条件：
 * Created by wq on 2018/5/24.
 */
public class Work {
    private State current;

    public Work() {
        this.current = current;
    }

    /**
     * 当前几点
     */
    private double hour;

    /**
     * 判断是否可以结束
     */
    private boolean finish;

    /**
     * 设置状态
     *
     * @param current
     */
    public void setState(State current) {
        this.current = current;
    }

    public void writeProgram() {
        current.writeProgram(this);
    }


    public double getHour() {
        return hour;
    }

    public void setHour(double hour) {
        this.hour = hour;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
}
