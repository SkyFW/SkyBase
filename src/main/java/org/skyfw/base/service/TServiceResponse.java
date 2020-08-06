package org.skyfw.base.service;


import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.result.TResult;

public interface TServiceResponse {

    TResult getresult();
    void setResult(TResult result);

    TDataModel getResponseData();
    void setResponseDataModel(TDataModel data);


}
