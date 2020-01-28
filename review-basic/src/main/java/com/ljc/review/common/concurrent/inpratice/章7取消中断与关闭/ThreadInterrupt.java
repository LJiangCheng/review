package com.ljc.review.common.concurrent.inpratice.章7取消中断与关闭;

public class ThreadInterrupt {

    /**
     * Java中线程的中断只是一个标志位，没有强制中断手段，是否中断只能由对应线程检查并自行判断
     * Thread.sleep()、Object.wait()、BlockingQueue.put()等阻塞方法会检查线程何时中断，并在发现中断时提前返回。它们在响应中断时执行的操作包括：清除中断
     * 状态，抛出InterruptedException。JVM并不能保证阻塞方法检测到中断的速度，但实际情况中响应的速度还是非常快的
     */
    public void interrupt() {
        Thread t = new Thread();
        t.interrupt();  //中断此线程(仅仅传递了请求中断的消息，并非强制中断)
        t.isInterrupted(); //判断此线程的中断状态（只判断，不清除）
        //判断当前线程的中断状态。注意：会清除清除中断状态，如果返回了true，那么除非要屏蔽这个中断，否则必须对它
        //进行处理（抛出异常或调用interrupt恢复中断状态）
        Thread.interrupted();
    }

}














