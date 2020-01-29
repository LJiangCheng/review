package com.ljc.review.common.concurrent.inpratice.章8线程池的使用.puzzle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * "搬箱子"谜题串行解决方案(深度优先搜索，受限于程序栈的大小)
 */
public class SequentialPuzzleSolver<P, M> {
    private final Puzzle<P, M> puzzle;
    private final Set<P> seen = new HashSet<>();

    public SequentialPuzzleSolver(Puzzle<P, M> puzzle) {
        this.puzzle = puzzle;
    }

    public List<M> solve() {
        P position = puzzle.initialPosition();
        return search(new PuzzleNode<P, M>(position, null, null));
    }

    private List<M> search(PuzzleNode<P, M> node) {
        P pos = node.pos;
        if (!seen.contains(pos)) {
            seen.add(pos);
            if (puzzle.isGoal(pos)) {
                return node.asMoveList();
            }
            for (M move : puzzle.legalMoves(pos)) {
                P child = puzzle.move(pos, move);
                List<M> result = search(new PuzzleNode<P, M>(child, move, node));
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

}
















