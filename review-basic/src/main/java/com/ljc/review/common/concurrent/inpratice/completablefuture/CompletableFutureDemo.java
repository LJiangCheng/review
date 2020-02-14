package com.ljc.review.common.concurrent.inpratice.completablefuture;

import org.junit.Test;

import java.util.concurrent.*;
import static org.junit.Assert.*;

/**
 * TODO demo未完成
 *
 * CompletableFuture，提供了非常强大的Future的扩展功能，可以帮助我们简化异步编程的复杂性，提供了函数式编程的能力，可以通过回调的方式处理计算结果，
 * 并且提供了转换和组合CompletableFuture的方法
 *
 * CompletableFuture实现了CompletionStage接口.它代表了一个特定的计算的阶段，可以同步或者异步的被完成。
 * 可以把它看成一个计算流水线上的一个单元，最终会产生一个最终结果，这意味着几个CompletionStage可以串联起来，一个完成的阶段可以触发下一阶段的执行，接着触发下一次、下下次
 *
 * CompletableFuture同时也实现了Future接口，代表一个未完成的异步事件。CompletableFuture提供了方法能够显式地完成这个future，所以它叫CompletableFuture
 */
public class CompletableFutureDemo {

    /**
     * 创建一个完成的CF
     */
    @Test
    public void cfExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("complete");
        assertTrue(cf.isDone());
        //cf.getNow("") 立即返回执行结果，如果任务尚未完成，则返回传入的参数
        assertEquals("complete", cf.getNow(null));
    }

    /**
     * 执行一个简单的异步阶段(Stage)
     * 如果没有指定Executor，异步执行通过ForkJoinPool实现，它使用守护线程去执行任务
     */
    @Test
    public void runAsyncExample() {
        CompletableFuture cf = CompletableFuture.runAsync(() -> {
            assertTrue(Thread.currentThread().isDaemon());
            randomSleep();
        });
        assertFalse(cf.isDone());
        sleepEnough();
        assertTrue(cf.isDone());
    }

    /**
     * 在前一阶段上执行函数 thenApply为同步串行操作，函数的执行会被阻塞。这意味着调用getNow方法时函数必然已经执行完成
     * 函数式编程：thenApply方法接受一个Function<T,R>参数，输入T返回R
     * PS：函数式编程中，函数本身是无状态的，它的类型可以根据方法所需参数动态判断(如下)，或者指定类型变量来接收，如Runnable r = () -> System.out.println("");
     */
    @Test
    public void thenApplyExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("complete").thenApply(String::toUpperCase);
        assertEquals("COMPLETE", cf.getNow(null));
    }

    /**
     * 在前一个阶段上异步执行函数
     */
    @Test
    public void thenApplyAsync() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("complete").thenApplyAsync(String::toUpperCase);
        assertFalse(cf.isDone());
        //cf.join()会阻塞直到函数执行完成，然后获取程序结果
        assertEquals("COMPLETE",cf.join());
    }

    /**
     * 使用定制的Executor在前一阶段上异步执行函数
     */
    @Test
    public void thenApplyAsyncExecutor() {
        //创建线程池
        ExecutorService executor = Executors.newFixedThreadPool(5, new ThreadFactory() {
            int count = 1;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "executor-" + count++);
            }
        });
        //创建完成任务并执行函数
        CompletableFuture<String> cf = CompletableFuture.completedFuture("complete").thenApplyAsync(s -> {
            //不再使用守护线程
            assertFalse(Thread.currentThread().isDaemon());
            assertTrue(Thread.currentThread().getName().startsWith("executor"));
            randomSleep();
            return s.toUpperCase();
        }, executor);
        //获取结果
        assertNull(cf.getNow(null));
        assertEquals("COMPLETE", cf.join());
    }

    /**
     * 消费前一阶段的结果
     * thenAccept方法接受一个Consumer参数，输入T，无返回
     * 本例中消费者同步地执行，所以我们不需要在CompletableFuture调用join方法。
     */
    @Test
    public void thenAcceptExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("complete").thenAccept(result::append);
        //result已经拼接到complete
        assertEquals("complete",result.toString());
    }

    /**
     * 异步消费
     */
    @Test
    public void thenAcceptAsyncExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture cf = CompletableFuture.completedFuture("thenAcceptAsync message").thenAcceptAsync(result::append);
        //等待执行完成
        cf.join();
        assertTrue("Result was empty", result.length() > 0);
    }

    /**
     * 异步操作显式返回异常
     */
    @Test
    public void completeExceptionallyExample() {
        //延迟异步执行 JDK1.9以后可以通过CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS)方法直接创建异步执行线程池
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignore) {}
            return s.toUpperCase();
        });
        //创建处理异常的handler 接受一个BiFunction<T,U,R>函数作为异常的处理任务，输入T,U 输出R  具体到这里是输入String,Throwable 输出String
        CompletableFuture<String> exceptionHandler = cf.handle((s, th) -> (th != null) ? "message upon cancel" : "");
        //如果此时异步任务尚未完成，则在调用get()及相关方法时抛出给定异常
        cf.completeExceptionally(new RuntimeException("completed exceptionally"));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        try {
            //会抛出异常
            cf.join();
            //如果没有抛出异常，说明前面的执行有误
            fail("Should have thrown an exception");
        } catch(CompletionException ex) { // just for testing
            assertEquals("completed exceptionally", ex.getCause().getMessage());
        }
        //cf抛出异常后handler会处理异常
        assertEquals("message upon cancel", exceptionHandler.join());
    }

    /**
     * 取消计算
     */
    @Test
    public void cancelExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignore) {}
            return s.toUpperCase();
        });
        //处理异常：如果发生了异常则返回此处处理之后的值，否则返回原值
        CompletableFuture cf2 = cf.exceptionally(throwable -> "canceled message");
        //使用cancel取消操作 cf.cancel(boolean b)中的布尔参数并没有被使用，这是因为它并没有使用中断去取消操作，相反，cancel等价于completeExceptionally(new CancellationException())
        assertTrue("Was not canceled", cf.cancel(true));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        assertEquals("canceled message", cf2.join());
    }

    private void sleepEnough() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException ignored) {}
    }

    private void randomSleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("=====>ThreadInterrupted!");
        }
    }

}








































