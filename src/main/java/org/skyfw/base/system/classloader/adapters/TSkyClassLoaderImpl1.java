package org.skyfw.base.system.classloader.adapters;

import org.skyfw.base.system.classloader.TSkyClassLoader;

public class TSkyClassLoaderImpl1 implements TSkyClassLoader {

    @Override
    public Class loadClassByName(String className) {

        try {
            return Class.forName(className);
        }catch (Exception e){
            return null;
        }
    }
}
