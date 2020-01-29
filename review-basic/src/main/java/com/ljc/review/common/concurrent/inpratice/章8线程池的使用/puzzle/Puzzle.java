package com.ljc.review.common.concurrent.inpratice.章8线程池的使用.puzzle;

import java.util.Set;

/**
 * Created by lijiangcheng on 2020/1/29.
 */
public interface Puzzle<P, M> {
    P initialPosition();

    boolean isGoal(P position);

    Set<M> legalMoves(P position);

    P move(P position, M move);

}
