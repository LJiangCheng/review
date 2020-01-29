package com.ljc.review.common.concurrent.inpratice.章8线程池的使用;

import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * 并行的递归调用示例
 */
public class ParallelRecursive {

    //串行递归
    public <T> void sequentialRecursive(List<Node<T>> nodes, Collection<T> results) {
        for (Node<T> node : nodes) {
            results.add(node.compute());
            sequentialRecursive(node.getChildren(), results);
        }
    }

    //并行递归
    public <T> void parallelRecursive(final Executor exec, List<Node<T>> nodes, final Collection<T> results) {
        for (final Node<T> node : nodes) {
            exec.execute(() -> results.add(node.compute()));
            parallelRecursive(exec, node.getChildren(), results);
        }
    }

    //并行递归的调用者如何获取结果
    public <T> Collection<T> getParallelResults(List<Node<T>> nodes) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Queue<T> results = new ConcurrentLinkedQueue<>();  //并发执行，不能用ArrayList
        parallelRecursive(executor, nodes, results);
        executor.shutdown();  //发出关闭请求
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);  //等待任务执行完毕或超时
        return results;
    }

    private class Node<T> {
        public T compute() {
            return null;
        }

        public <T> List<Node<T>> getChildren() {
            return null;
        }
    }

}
