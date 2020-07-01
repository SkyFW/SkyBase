package org.skyfw.base.system.classloader.adapters;

import org.skyfw.base.system.classloader.TSkyClassLoader;

public class TSkyClassLoaderImpl2 implements TSkyClassLoader {

    public static ClassLoader systemClassLoader;

    static {
        systemClassLoader= ClassLoader.getSystemClassLoader();
    }

    @Override
    public Class loadClassByName(String className) {

        try {
            return systemClassLoader.loadClass(className);
        }catch (Exception e){
            return null;
        }
    }

}
