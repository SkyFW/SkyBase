package org.skyfw.base.init;

public class THardwareResourceChecker {

    public static String getBriefReport(){

        StringBuilder resultSB= new StringBuilder();

        resultSB.append(".: Hardware Resources Brief Report :.");
        resultSB.append("\n");

        /* Total number of processors or cores available to the JVM */
        resultSB.append("Available processors (cores): " +
                Runtime.getRuntime().availableProcessors());

        /* Total amount of free memory available to the JVM */
        resultSB.append("\n");
        resultSB.append("Free memory (bytes): " +
                Runtime.getRuntime().freeMemory());

        /* This will return Long.MAX_VALUE if there is no preset limit */
        long maxMemory = Runtime.getRuntime().maxMemory();
        /* Maximum amount of memory the JVM will attempt to use */
        resultSB.append("\n");
        resultSB.append("Maximum memory (bytes): " +
                (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));

        /* Total memory currently in use by the JVM */
        resultSB.append("\n");
        resultSB.append("Total memory (bytes): " +
                Runtime.getRuntime().totalMemory());

        /*
        //Get a list of all filesystem roots on this system
        file[] roots = file.listRoots();

        //For each filesystem root, print some info
        for (file root : roots) {
            system.out.println("file system root: " + root.getAbsolutePath());
            system.out.println("Total space (bytes): " + root.getTotalSpace());
            system.out.println("Free space (bytes): " + root.getFreeSpace());
            system.out.println("Usable space (bytes): " + root.getUsableSpace());
        }
        */

        return resultSB.toString();
    }


}
