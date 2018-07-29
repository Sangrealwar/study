package study.concurrency.test.PuzzleTest.concurrent;

import study.concurrency.test.PuzzleTest.Puzzle;
import study.concurrency.test.PuzzleTest.ValueLatch;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 名称： 并发解决谜题问题（在多个答案中，找到符合题目的答案）
 *      广度优先算法，利用多线程的优势，每一个子节点开一个线程去查找，利用并发容器，存储结果空间，保证问题的解题域不会并发，导致遗漏数据
 *       利用ValueLatch闭锁，当找到答案后，会开启闭锁，后续的线程会检查闭锁的状态，从而退出解题的过程
 * 功能：
 * 条件：
 * Created by wq on 2018/7/18.
 */
public class ConcurrentPuzzleSolver<P, M> {
    private final Puzzle<P, M> puzzle;
    private final ExecutorService exec;
    //解题的过程，已经搜索过的答案
    private final ConcurrentHashMap<P, Boolean> seen;
        final ValueLatch<Node<P, M>> solution = new ValueLatch<>();

    public ConcurrentPuzzleSolver(Puzzle<P, M> puzzle) {
        this.puzzle = puzzle;
        this.exec = Executors.newCachedThreadPool();
        this.seen = new ConcurrentHashMap<>();
    }

    public List<M> solve() throws InterruptedException {
        try {
            P p = puzzle.initialPosition();
            exec.execute(newTaskFor(p, null, null));
            //阻塞直到找到解答
            Node<P, M> solnNode = solution.getValue();
            return (solnNode == null) ? null : solnNode.asMoveList();
        } finally {
            exec.shutdown();
        }
    }

    protected Runnable newTaskFor(P p, M m, Node<P, M> n) {
        return new SolverTask(p, m, n);
    }

    class SolverTask extends Node<P, M> implements Runnable {
        public SolverTask(P pos, M move, Node<P, M> prev) {
            super(pos, move, prev);
        }

        @Override
        public void run() {
            if (solution.isSet()
                    || seen.putIfAbsent(pos, true) != null) {
                return;          //已经找到了解答，或者已经遍历到了这个位置
            }
            if(puzzle.isGoal(pos)){
                solution.setValue(this);
            }else{
                for(M m : puzzle.legalMoves(pos)){
                    //所有从当前位置走向下一个位置的，广度遍历
                    exec.execute(newTaskFor(puzzle.move(pos,m),m,this));
                }
            }
        }
    }
}
