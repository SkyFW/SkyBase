package org.skyfw.base.service.method;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.exception.TException;
import org.skyfw.base.service.TBaseServiceSession;

public interface TMethodAuthorizer<REQ extends TDataModel, RES extends TDataModel> {

    boolean authorizeRequest(TBaseServiceSession<REQ,RES> serviceSession) throws TException;

    boolean authorizeResponse(TBaseServiceSession<REQ,RES> serviceSession) throws TException;
}
