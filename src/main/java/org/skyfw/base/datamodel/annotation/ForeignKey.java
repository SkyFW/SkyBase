package org.skyfw.base.datamodel.annotation;

import org.skyfw.base.datamodel.TBaseDataModel;
import org.skyfw.base.datamodel.TDataModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignKey {

    //Foreign datamodel(Foreign Table) Params
    //<? extends TDataModel>  referencedDataModel() default ForeignKey.TDefaultDataModel.class;
    //Key Field Of Foreign datamodel Is Specified By It Self
    //String  fIndexName ()  default "";
    String  representativeFieldName ()  default "";


    // >>> Java do not let us to define a null value for an annotation with class type
    // so wee need to define this dummy static class
    // "XmlJavaTypeAdapter" with "default.class" and some others used this approach too.
    //-------------------------------------------------------------------------------------------
    public static class TDefaultDataModel extends TBaseDataModel {

    }

}
