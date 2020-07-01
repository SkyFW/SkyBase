package org.skyfw.base.utils;

import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.exception.general.TNullArgException;

public class TArgumentChecker {

    public static void check(Object ...objects) throws TIllegalArgumentException {

        for (Object object: objects){

            if (object == null)
                throw TNullArgException.create("");
        }

    }

}
