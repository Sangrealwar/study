package study.concurrency.test.PuzzleTest;

import java.util.Set;

/**
 * 名称：谜题
 * 功能：
 * 条件：
 * Created by wq on 2018/7/18.
 */
public interface Puzzle<P, M> {

    /**
     * 初始化起点
     * @return
     */
    P initialPosition();

    /**
     * 是目标
     * @param position
     * @return
     */
    boolean isGoal(P position);

    /**
     * 得到出下一个节点
     * @param position
     * @return
     */
    Set<M> legalMoves(P position);

    /**
     * 移动
     * @param position
     * @param move
     * @return
     */
    P move(P position, M move);
}
