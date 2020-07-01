package org.skyfw.base.datamodel.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;


@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IndexParam {

    String  fieldName();
    int     dataSegmentSize() default 0;

}
