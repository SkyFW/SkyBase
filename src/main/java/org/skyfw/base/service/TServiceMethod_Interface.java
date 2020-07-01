package org.skyfw.base.service;

import org.skyfw.base.datamodel.TDataModel;

@Deprecated
public interface TServiceMethod_Interface <Q extends TDataModel, S extends TDataModel> {

    public String getMethodName();

    public String getServicePath();

    public Class<Q> getRequestClass();
    public Class<S> getResponseClass();

    //public Class<? extends TFieldLocalMessageFeeder> getLocalMessageFeeder();
}
