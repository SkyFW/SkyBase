package org.skyfw.base.service.method;

import org.skyfw.base.datamodel.TBaseDataModel;
import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.service.TService;

public class TServiceMethodInfo <Q extends TDataModel, S extends TDataModel> extends TBaseDataModel {

    private String methodName;
    private String fullPath;
    private Class<Q> requestClass;
    private Class[]  requestGenericParams;
    private Class<S> responseClass;
    private Class[]  responseGenericParams;
    private String   methodDescription;

    // >>> Run time filled info
    private TService parentService;
    /*private TMethodAuthorizer methodAuthorizer;*/

    // >>> Generic Class Params
    //Jackson: TypeFactory.constructParametricType(Class parametrized, Class... parameterClasses)
    //GSON: objectType = TypeToken.getParameterized(clazz, genericClazz).getSeverity();


    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public Class<Q> getRequestClass() {
        return requestClass;
    }

    public void setRequestClass(Class<Q> requestClass) {
        this.requestClass = requestClass;
    }

    public Class<S> getResponseClass() {
        return responseClass;
    }

    public void setResponseClass(Class<S> responseClass) {
        this.responseClass = responseClass;
    }

    public String getMethodDescription() {
        return methodDescription;
    }

    public void setMethodDescription(String methodDescription) {
        this.methodDescription = methodDescription;
    }

    public Class[] getRequestGenericParams() {
        return requestGenericParams;
    }

    public void setRequestGenericParams(Class[] requestGenericParams) {
        this.requestGenericParams = requestGenericParams;
    }

    public Class[] getResponseGenericParams() {
        return responseGenericParams;
    }

    public void setResponseGenericParams(Class[] responseGenericParams) {
        this.responseGenericParams = responseGenericParams;
    }

    public TService getParentService() {
        return parentService;
    }

    public void setParentService(TService parentService) {
        this.parentService = parentService;
    }
}
