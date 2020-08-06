package org.skyfw.base.service;

import org.skyfw.base.datamodel.TDataModel;

public class TResponse<T extends TDataModel> {


    private TServiceResponse responseMetaData;
    private T response;


    public TServiceResponse getResponseMetaData() {
        return responseMetaData;
    }

    public void setResponseMetaData(TServiceResponse responseMetaData) {
        this.responseMetaData = responseMetaData;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
