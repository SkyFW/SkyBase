package org.skyfw.base.service;

import java.util.HashMap;

@Deprecated
public class TServiceMethodParametersList {


    HashMap<String, TServiceMethodParameterDescriptor> requestParameters=
            new HashMap<String, TServiceMethodParameterDescriptor>();

    HashMap<String, TServiceMethodParameterDescriptor> responseParameters=
            new HashMap<String, TServiceMethodParameterDescriptor>();


    private TServiceMethodParametersList() {

    }

    public static TServiceMethodParametersList create(){

        return new TServiceMethodParametersList();
    }

    public void addRequestParameter(String parameterName, String parameterClassName){

        TServiceMethodParameterDescriptor methodParameterDescriptor=
                TServiceMethodParameterDescriptor.create(parameterName, parameterClassName);

        requestParameters.put(parameterName, methodParameterDescriptor);
    }

    public void addRequestParameter(String parameterName, Class parameterClass){

        TServiceMethodParameterDescriptor methodParameterDescriptor=
                TServiceMethodParameterDescriptor.create(parameterName, parameterClass);

        requestParameters.put(parameterName, methodParameterDescriptor);
    }


    public void addResponseParameter(String parameterName, String parameterClassName){

        TServiceMethodParameterDescriptor methodParameterDescriptor=
                TServiceMethodParameterDescriptor.create(parameterName, parameterClassName);

        responseParameters.put(parameterName, methodParameterDescriptor);
    }

    public void addResponseParameter(String parameterName, Class parameterClass){

        TServiceMethodParameterDescriptor methodParameterDescriptor=
                TServiceMethodParameterDescriptor.create(parameterName, parameterClass);

        responseParameters.put(parameterName, methodParameterDescriptor);
    }


    //Public Getter(s) & Setter(s)
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public HashMap<String, TServiceMethodParameterDescriptor> getRequestParameters() {
        return requestParameters;
    }

    public HashMap<String, TServiceMethodParameterDescriptor> getResponseParameters() {
        return responseParameters;
    }


}
