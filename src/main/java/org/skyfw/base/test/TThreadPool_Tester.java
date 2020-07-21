package org.skyfw.base.test;

import org.skyfw.base.exception.TException;
import org.skyfw.base.system.terminal.TTerminal;
import org.skyfw.base.thread.TNetworkThreadPool;

import java.util.concurrent.TimeUnit;


public class TThreadPool_Tester {

    public static void doTest() throws TException {


        for (int i = 1; i <= 10; i++)
        {
            TestTask task = new TestTask("Task " + i);
            System.out.println("Created : " + task.getName());

            TNetworkThreadPool.execute(task);
        }


        //TTerminal.readLn();
    }


    public static class TestTask implements Runnable {
        private String name;

        public TestTask(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void run() {
            try {
                Long duration = (long) (Math.random() * 2);
                System.out.println("Executing started : " + name);
                TimeUnit.SECONDS.sleep(duration);
                System.out.println("Executing finished : " + name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
