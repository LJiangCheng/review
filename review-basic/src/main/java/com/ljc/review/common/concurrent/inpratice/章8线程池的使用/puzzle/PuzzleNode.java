package com.ljc.review.common.concurrent.inpratice.章8线程池的使用.puzzle;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by lijiangcheng on 2020/1/29.
 */
public class PuzzleNode<P, M> {
    final P pos;
    final M move;
    final PuzzleNode<P, M> prev;

    public PuzzleNode(P pos, M move, PuzzleNode<P, M> prev) {
        this.pos = pos;
        this.move = move;
        this.prev = prev;
    }

    public List<M> asMoveList() {
        List<M> solution = new LinkedList<>();
        for (PuzzleNode<P, M> n = this; (n != null && n.move != null); n = n.prev) {
            solution.add(0, n.move);
        }
        return solution;
    }
}
