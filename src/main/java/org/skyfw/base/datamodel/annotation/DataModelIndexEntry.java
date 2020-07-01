package org.skyfw.base.datamodel.annotation;
import java.lang.annotation.*;


@Repeatable(DataModelIndexEntry.DataModelIndexEntries.class)
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataModelIndexEntry {

    IndexParam[] value() default {};



    @Target(value = ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface DataModelIndexEntries {

        DataModelIndexEntry[] value() default {};
    }

}