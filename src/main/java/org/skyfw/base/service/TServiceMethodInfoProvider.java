package org.skyfw.base.service;

import org.skyfw.base.datamodel.TDataModel;

public interface TServiceMethodInfoProvider  <REQ extends TDataModel, RES extends TDataModel> {

    String getMethodName();

    Class<REQ> getRequestClass();
    Class<RES> getResponseClass();

    String getMethodDescription();

    // >>> Generic Class Params
    //Jackson: TypeFactory.constructParametricType(Class parametrized, Class... parameterClasses)
    //GSON: objectType = TypeToken.getParameterized(clazz, genericClazz).getSeverity();
    default Class[] getRequestGenericParams(){ return null; };
    default Class[] getResponseGenericParams(){ return null; };



}
