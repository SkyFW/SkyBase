package org.skyfw.base.datamodel.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataModel {

    String   dataStoreName() default "";
    boolean  autoKey() default true;
    boolean  nonNullFields() default false;

    String[]   indexes() default {};


}
