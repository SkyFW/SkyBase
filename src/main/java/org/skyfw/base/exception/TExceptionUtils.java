package org.skyfw.base.exception;

public class TExceptionUtils {

    public static String getMethodName(Exception e){

        return e.getStackTrace()[0].getClassName();
    }

    public static String getClassName(Exception e){

        return e.getStackTrace()[0].getClassName();
    }
}
