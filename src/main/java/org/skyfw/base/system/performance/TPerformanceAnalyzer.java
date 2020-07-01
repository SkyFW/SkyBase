package org.skyfw.base.system.performance;

public class TPerformanceAnalyzer {

    long startTime= 0;
    long stopTime= 0;

    public void start(){

        startTime= System.currentTimeMillis();

    }

    public void stop(){

        stopTime= System.currentTimeMillis();

    }

    public long getDuration(){

        if ((startTime == 0) || (stopTime == 0))
            return -1;

        return (stopTime - startTime);

    }

    public static TPerformanceAnalyzer getAnalyzer(){
        return new TPerformanceAnalyzer();
    }
}
