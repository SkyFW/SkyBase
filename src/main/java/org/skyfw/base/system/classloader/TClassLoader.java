package org.skyfw.base.system.classloader;

import org.skyfw.base.system.classloader.adapters.TSkyClassLoaderImpl1;
import org.skyfw.base.system.classloader.adapters.TSkyClassLoaderImpl2;

public class TClassLoader {

    //private static MethodHandle classLoaderMethod;
    private static TSkyClassLoader bestSkyClassLoader = null;

    static {

        try {
            Class testClazz= null;
            String testClazzName= "TDataModel";

            bestSkyClassLoader = new TSkyClassLoaderImpl2();
            if (bestSkyClassLoader != null)
                testClazz= bestSkyClassLoader.loadClassByName(testClazzName);
            //testClazz= null;

            if (testClazz == null) {
                bestSkyClassLoader = new TSkyClassLoaderImpl1();
                if (bestSkyClassLoader != null)
                    testClazz = bestSkyClassLoader.loadClassByName(testClazzName);
            }

        } catch (Exception e){
            System.out.println(e.toString());
        }

        /*
        try {
            Class clazz= ClassLoader.getSystemClassLoader().getClass();//.loadClass()

            // get list of methods
            Method[] methods = clazz.getMethods();

            // get the name of every method present in the list
            for (Method method : methods)
            if (method.getName().equals("loadClass")) {

                classLoaderMethod= method.get
                String MethodName = method.getName();
                system.out.println("Name of the method: "
                        + MethodName);
            }
        }
        catch (exception e) {
            e.printStackTrace();
        }*/

    }


    public static Class loadByName(String className){

        //system.getProperty("java.vm.name").indexOf("dalvik");
        try {
            return bestSkyClassLoader.loadClassByName(className);
        }catch (Throwable e){
            return null;
        }
    }




}
