package org.skyfw.base.service.method;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.result.TResult;
import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.service.*;
import org.skyfw.base.service.exception.TMethodAuthorizationException;
import org.skyfw.base.service.TServiceMCode;

public interface TServiceMethod <REQ extends TDataModel, RES extends TDataModel> extends TServiceMethodInfoProvider<REQ, RES> {

    default TMethodAuthorizer getMethodAuthorizer(){return null;}



    default TServiceMethodRunner getServiceMethodRunner(){
        return null;
    }




    // >>> Default Run Method
    default void run(TBaseServiceSession<REQ,RES> serviceSession) throws TException {

        if (getServiceMethodRunner() != null)
            getServiceMethodRunner().run(serviceSession, this);
        else
            TResult.createTResult(TBaseMCode.SERVER_INTERNAL_ERROR, "SERVICE_METHOD_RUNNER_IS_NULL");
    }

    // >>> Authorize The Request
    default boolean authorizeRequest(TBaseServiceSession<REQ,RES> serviceSession) throws TMethodAuthorizationException {

        try {
            if (getMethodAuthorizer() != null)
                return getMethodAuthorizer().authorizeRequest(serviceSession);
            else
                return true;
        }catch (TException e){
            throw new TMethodAuthorizationException(TServiceMCode.EXCEPTION_ON_CALLING_AUTHORIZE_REQUEST_METHOD
                    , TMCodeSeverity.FATAL, e, this.getMethodInfo().getFullPath());
        }
    }

    // >>> Authorize The Response
    default boolean authorizeResponse(TBaseServiceSession<REQ,RES> serviceSession) throws TMethodAuthorizationException {

        try {
            if (getMethodAuthorizer() != null)
                return getMethodAuthorizer().authorizeResponse(serviceSession);
            else
                return true;
        }catch (TException e){
            throw new TMethodAuthorizationException(TServiceMCode.EXCEPTION_ON_CALLING_AUTHORIZE_REQUEST_METHOD
                    , TMCodeSeverity.FATAL, e, this.getMethodInfo().getFullPath());
        }
    }


    default TResult doTest(){
        return TResult.createTResult(TBaseMCode.SUCCESS, null);
    }




    default String getFullPath() {

        TService parentService = this.getParentService();

        if (parentService == null)
            return this.getMethodName();

        return parentService.getFullPath() + "/" + this.getMethodName();
    }


    default Class[] getRequestGenericParams() {
        return null;
    }


    default Class[] getResponseGenericParams() {
        return null;
    }



}
