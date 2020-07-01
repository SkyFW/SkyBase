package org.skyfw.base.service;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.result.TResult;

public interface TMethodAuthorizer<Q extends TDataModel, S extends TDataModel> {

    TResult authorizeRequest(TRequestMetaData requestMetaData, Q request);

    TResult authorizeResponse(TRequestMetaData requestMetaData, S response);
}
