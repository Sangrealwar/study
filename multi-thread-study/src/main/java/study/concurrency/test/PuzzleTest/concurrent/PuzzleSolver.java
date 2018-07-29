package study.concurrency.test.PuzzleTest.concurrent;

import study.concurrency.test.PuzzleTest.Puzzle;
import study.concurrency.test.PuzzleTest.ValueLatch;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 名称：并发解决谜题问题，完善了没有答案会一直循环的问题
 * 功能：
 * 条件：
 * Created by wq on 2018/7/18.
 */
public class PuzzleSolver<P, M> extends ConcurrentHashMap {

    private final Puzzle<P, M> puzzle;
    private final ExecutorService exec;
    private final ConcurrentHashMap<P, Boolean> seen;
    final ValueLatch<Node<P, M>> solution = new ValueLatch<>();

    public PuzzleSolver(Puzzle<P, M> puzzle) {
        this.puzzle = puzzle;
        this.exec = Executors.newCachedThreadPool();
        this.seen = new ConcurrentHashMap<>();
    }

    //当没有找到答案时，闭锁的永远不会开启，这个时候需要记录任务数
    private final AtomicInteger taskCount = new AtomicInteger(0);

    protected Runnable newTask(P p, M m, Node<P, M> n) {
        return new CountingSolverTask(p, m, n);
    }

    class CountingSolverTask extends SolverTask {
        public CountingSolverTask(P pos, M move, Node prev) {
            super(pos, move, prev);
            taskCount.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                super.run();
            } catch (Exception e) {
                //当所有的解题（任务）都搜索过之后，停止解题
                if (taskCount.decrementAndGet() == 0) {
                    solution.setValue(this);
                }
            }
        }
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
            if (puzzle.isGoal(pos)) {
                solution.setValue(this);
            } else {
                for (M m : puzzle.legalMoves(pos)) {
                    //所有从当前位置走向下一个位置的，广度遍历
                    exec.execute(newTask(puzzle.move(pos, m), m, this));
                }
            }
        }
    }
}
