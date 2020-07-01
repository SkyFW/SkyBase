package org.skyfw.base.service;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.serializing.TSerializable;

public class TResponse<T extends TDataModel> {


    private TResponseMetaData responseMetaData;
    private T response;


    public TResponseMetaData getResponseMetaData() {
        return responseMetaData;
    }

    public void setResponseMetaData(TResponseMetaData responseMetaData) {
        this.responseMetaData = responseMetaData;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
}
