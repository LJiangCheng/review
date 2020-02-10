package com.ljc.review.common.concurrent.inpratice.章8线程池的使用.puzzle;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * "搬箱子"谜题并行解决方案（广度优先搜索，受限于内存大小，且需要考虑并行任务的终止条件）
 * 关注点：闭锁、提交任务、获取结果、任务计数器、线程安全、生命周期
 */
public class ConcurrentPuzzleSolver<P, M> {
    //自己实现闭锁，用于保存搜索结果。线程安全，可以阻塞主线程直到结果出现
    final ValueLatch<PuzzleNode<P, M>> valueLatch = new ValueLatch<>();
    /**
     * 使用任务计数器解决没有找到正确解时任务如何结束的问题
     */
    final AtomicInteger taskCount = new AtomicInteger(0);
    private final Puzzle<P, M> puzzle;
    private final ExecutorService exec;
    private final ConcurrentMap<P, Boolean> seen = new ConcurrentHashMap();  //判断当前位置是否已经处理过

    public ConcurrentPuzzleSolver(Puzzle<P, M> puzzle, ExecutorService exec) {
        this.puzzle = puzzle;
        this.exec = exec;
    }

    public List<M> solve() throws InterruptedException {
        P position = puzzle.initialPosition();
        //提交任务
        exec.execute(newTask(position, null, null));
        //阻塞，等待任务完成
        PuzzleNode<P, M> resultNode = valueLatch.getValue();
        //返回结果
        if (resultNode != null) {
            return resultNode.asMoveList();
        }
        return null;
    }

    private Runnable newTask(P position, M move, PuzzleNode node) {
        return new SolverTask(position, move, node);
    }

    private class SolverTask extends PuzzleNode<P, M> implements Runnable {
        public SolverTask(P pos, M move, PuzzleNode<P, M> node) {
            super(pos, move, node);
        }

        @Override
        public void run() {
            //结果尚未计算出来 && 当前位置尚未计算
            if (!valueLatch.isSet() && seen.putIfAbsent(pos, true) == null) {
                //判断当前位置，提交后续任务
                if (puzzle.isGoal(pos)) {
                    valueLatch.setValue(this);
                } else {
                    for (M move : puzzle.legalMoves(pos)) {
                        exec.execute(newTask(puzzle.move(pos, move), move, this));
                    }
                }
            }
        }
    }

    private class PuzzleSolver extends SolverTask {
        public PuzzleSolver(P pos, M move, PuzzleNode<P, M> node) {
            super(pos, move, node);
            taskCount.incrementAndGet();
        }

        @Override
        public void run() {
            try {
                super.run();
            } finally {
                if (taskCount.decrementAndGet() == 0) {
                    valueLatch.setValue(null);
                }
            }
        }

    }

}
















