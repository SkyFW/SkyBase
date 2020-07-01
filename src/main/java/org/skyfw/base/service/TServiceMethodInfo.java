package org.skyfw.base.service;

import org.skyfw.base.datamodel.TBaseDataModel;
import org.skyfw.base.datamodel.TDataModel;

public class TServiceMethodInfo <Q extends TDataModel, S extends TDataModel> extends TBaseDataModel {

    private String methodName;
    private String fullPath;
    private Class<Q> requestClass;
    private Class[]  requestGenericParams;
    private Class<S> responseClass;
    private Class[]  responseGenericParams;
    private String   methodDescription;


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
}
