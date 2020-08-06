package org.skyfw.base.system.reflection;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.skyfw.base.classes.validation.TPreconditions;
import org.skyfw.base.exception.general.TIllegalArgumentException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TReflectionUtils {

    public static void setFieldValue(Field field, Object value){

        //FieldUtils.removeFinalModifier(fieldDescriptor);

        //joor library
        //Reflect.on(yourObject).set("finalFieldName", finalFieldValue);

    }


    public static boolean checkMethodParamsType(Method method, Class ...expectedTypes)
            throws TIllegalArgumentException {

        TPreconditions.checkArgForNotNull(method, "method");
        TPreconditions.checkArgForNotNull(method, "types");

        if (method.getParameterCount() != expectedTypes.length)
            return false;

        Class[] realTypes= method.getParameterTypes();

        for (int i= 0; i< realTypes.length; i++)
            if ( ! realTypes[i].equals(expectedTypes[i]))
                return false;

        return true;
    }

}
