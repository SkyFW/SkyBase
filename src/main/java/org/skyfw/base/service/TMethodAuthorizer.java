package org.skyfw.base.service;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.exception.TException;
import org.skyfw.base.result.TResult;

public interface TMethodAuthorizer<Q extends TDataModel, S extends TDataModel> {

    TResult authorizeRequest(TServiceRequestSession requestMetaData, Q request) throws TException;

    TResult authorizeResponse(TServiceRequestSession requestMetaData, S response) throws TException;
}
