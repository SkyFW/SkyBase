package org.skyfw.base.service.method;

@Deprecated
public class TServiceMethodParameterDescriptor {

    private String parameterName;
    private String parameterClassName;
    private transient Class  parameterClass;


    private TServiceMethodParameterDescriptor(String parameterName, String parameterClassName) {
        this.parameterName = parameterName;
        this.parameterClassName = parameterClassName;
    }

    private TServiceMethodParameterDescriptor(String parameterName, Class parameterClass) {
        this.parameterName = parameterName;
        this.parameterClassName = parameterClass.getName();
        this.parameterClass = parameterClass;
    }


    public static TServiceMethodParameterDescriptor create(String parameterName, String parameterClassName){

        return new TServiceMethodParameterDescriptor(parameterName, parameterClassName);
    }

    public static TServiceMethodParameterDescriptor create(String parameterName, Class parameterClass){

        return new TServiceMethodParameterDescriptor(parameterName, parameterClass);
    }


    //Public Getter(s) & Setter(s)
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterClassName() {
        return parameterClassName;
    }

    public void setParameterClassName(String parameterClassName) {
        this.parameterClassName = parameterClassName;
    }

    public Class getParameterClass() {
        return parameterClass;
    }

    public void setParameterClass(Class parameterClass) {
        this.parameterClass = parameterClass;
    }
}
