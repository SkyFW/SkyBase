package org.skyfw.base.datamodel.annotation;

import org.skyfw.base.datamodel.TAuthFieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthField {

    TAuthFieldType authFieldType();
    String          authFieldName() default "";


}
