package org.skyfw.base.service.method;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.service.TService;

public interface TServiceMethodInfoProvider  <REQ extends TDataModel, RES extends TDataModel> {

    default TServiceMethodInfo<REQ, RES> getMethodInfo(){

        TServiceMethodInfo<REQ, RES> methodInfo= new TServiceMethodInfo<>();
        methodInfo.setMethodName(this.getMethodName());
        methodInfo.setFullPath(this.getFullPath());
        methodInfo.setRequestClass(this.getRequestClass());
        methodInfo.setResponseClass(this.getResponseClass());
        methodInfo.setMethodDescription(this.getMethodDescription());
        methodInfo.setRequestGenericParams(this.getRequestGenericParams());
        methodInfo.setResponseGenericParams(this.getResponseGenericParams());
        methodInfo.setParentService(this.getParentService());

        return methodInfo;
    }


    String getMethodName();
    String getFullPath();
    Class<REQ> getRequestClass();
    Class<RES> getResponseClass();
    String getMethodDescription();
    Class[] getRequestGenericParams();
    Class[] getResponseGenericParams();
    TService getParentService();

}
