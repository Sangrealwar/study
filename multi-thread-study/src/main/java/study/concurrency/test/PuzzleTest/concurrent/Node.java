package study.concurrency.test.PuzzleTest.concurrent;

import java.util.ArrayList;
import java.util.List;

/**
 * 名称：节点
 * 功能：
 * 条件：
 * Created by wq on 2018/7/25.
 */
public class Node<P, M> {
    final P pos;
    final M move;
    final Node<P, M> prev;

    public Node(P pos, M move, Node<P, M> prev) {
        this.pos = pos;
        this.move = move;
        this.prev = prev;
    }

    List<M> asMoveList() {
        List<M> solution = new ArrayList<>();
        for (Node<P, M> n = this; n.move != null; n = n.prev) {
            solution.add(n.move);
        }
        return solution;
    }
}
