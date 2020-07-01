package org.skyfw.base.classes;

import org.apache.commons.lang3.ObjectUtils;
import org.skyfw.base.exception.general.TIllegalArgumentException;

public class TObjects {

    private TObjects() {
    }

    public static boolean nullOrEmpty(String s) {

        if (s == null)
            return true;
        if (s.isEmpty())
            return true;

        return false;
    }


    public static boolean isNumerical(Object object){

        return isNumerical(object.getClass());
    }


    public static boolean isNumerical(Class clazz){

        if ((clazz.equals(Long.class))
                || (clazz.equals(Integer.class))
                || (clazz.equals(Double.class))
                || (clazz.equals(Float.class))
                || (clazz.equals(Byte.class)))

            return true;
        else
            return false;
    }


    public static <T extends Comparable<? super T>> int compare(T c1, T c2) {

        return ObjectUtils.compare(c1, c2);
    }

}
