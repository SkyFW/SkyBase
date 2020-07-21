package org.skyfw.base.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadPoolExecutor;

public class TMainThreadPool {

    private static final ThreadPoolExecutor pool;
    private static final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    static {

        uncaughtExceptionHandler= new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("uncaughtException: " + e.toString());
            }
        };


        pool= (ThreadPoolExecutor)(ExecutorService)
                (new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
                ForkJoinPool.defaultForkJoinWorkerThreadFactory,
                        uncaughtExceptionHandler, true));

        Executors.newWorkStealingPool();
    }

    public static void execute(Runnable runnable){

        pool.execute(runnable);
    }

    /*public static void immidateRun(Runnable runnable){

    }*/


}
