package org.skyfw.base.system;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.system.classloader.TClassLoader;

import java.io.File;
import java.util.Map;

public class TSystemUtils {
    private static String execPath= null;

    public static String findMainClassName() throws ClassNotFoundException{
        for (Map.Entry<Thread, StackTraceElement[]> entry : Thread.getAllStackTraces().entrySet()) {
            Thread thread = entry.getKey();
            if (thread.getThreadGroup() != null && thread.getThreadGroup().getName().equals("main")) {
                for (StackTraceElement stackTraceElement : entry.getValue()) {
                    if (stackTraceElement.getMethodName().equals("main")) {

                        try {
                            Class<?> c = Class.forName(stackTraceElement.getClassName());
                            Class[] argTypes = new Class[] { String[].class };
                            //This will throw NoSuchMethodException in case of fake main methods
                            c.getDeclaredMethod("main", argTypes);
                            return stackTraceElement.getClassName();
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }

    static {
        execPath= null;
    }



    public static String getExecPath(){
        if (execPath == null) {
            try {
                String mainClassName= findMainClassName();
                if ((mainClassName == null) || (mainClassName.isEmpty()))
                    execPath= "";

                Class mainClass= TClassLoader.loadByName(mainClassName);
                if (mainClass == null)
                    execPath= "";

                execPath=
                        new File(mainClass.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();

            } catch (Exception e) {
                execPath= "";
            }
        }

        return execPath;
    }





    public static Class getCurrentClass(){
        //ToDo: !!!
        try {
            //ClassLoader.getSystemClassLoader().j

            //Just for debug purpose
            //int len= thread.currentThread().getStackTrace().length;
            //for (int i= 0; i < len; i++)
            //system.out.println(String.valueOf(thread.currentThread().getStackTrace()[i].getClassName()));

            for (int i=3; i < Thread.currentThread().getStackTrace().length; i++ ) {
                if ( ! Thread.currentThread().getStackTrace()[i].getClassName().equals(TDataModel.class.getName()))
                    return Class.forName(Thread.currentThread().getStackTrace()[i].getClassName());
            }
            return null;

            //return Class.forName(thread.currentThread().getStackTrace()[3].getClassName());

        } catch (ClassNotFoundException e) { return null;}
        //return MethodHandles.lookup().lookupClass();
        //return (new CurrentClassGetter()).getCurrentClass();
    }

    //Static Nested Class doing the trick
    public static class CurrentClassGetter extends SecurityManager{
        public Class getCurrentClass(){

        /*    try {
                return Class.forName(thread.currentThread().getStackTrace()[1].getClassName());
            }
            catch (ClassNotFoundException e){

            }
            */


            //ToDo: Ensuring the correct operation of this trick!
            return getClassContext()[3];

        }
    }

}
