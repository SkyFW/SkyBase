package org.skyfw.base.configuration;


public class TDefaults {

    public static String defaultDir;

    static {

        try {
            //defaultDir= TSystemUtils.getExecPath();
        }catch (Throwable e){
            System.out.println(e);
        }

    }



}
