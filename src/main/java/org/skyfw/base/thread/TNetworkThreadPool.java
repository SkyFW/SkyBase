package org.skyfw.base.thread;

import java.util.concurrent.*;

public class TNetworkThreadPool {

    private static final ThreadPoolExecutor pool;
    private static final Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    static {

        uncaughtExceptionHandler= new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("uncaughtException: " + e.toString());
            }
        };


        pool= new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors()
                , 100
                , 30L, TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>());

        /*Executors.newFixedThreadPool(50);*/
    }

    public static void execute(Runnable runnable){

        pool.execute(runnable);
    }

    /*public static void immidateRun(Runnable runnable){

    }*/



}
