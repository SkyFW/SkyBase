package org.skyfw.base.datamodel.annotation.GUI;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataModelGUI {

    String   caption(); //default "";
    boolean  iconURL();
}



