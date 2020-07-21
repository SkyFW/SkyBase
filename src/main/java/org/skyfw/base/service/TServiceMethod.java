package org.skyfw.base.service;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.result.TResult;
import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.service.exception.TMethodAuthorizationException;
import org.skyfw.base.service.mcode.TServiceMCodes;

public interface TServiceMethod <REQ extends TDataModel, RES extends TDataModel> extends TServiceMethodInfoProvider<REQ, RES> {

    Class<? extends TService> getParentServiceClass();
    default TService getParentService(){ return null; };

    default TMethodAuthorizer getMethodAuthorizer(){return null;}

    default TServiceMethodRunner getServiceMethodRunner(){
        return null;
    }


    default String getFullPath(){

        String parentServicePath= "";

        try {
            if (this.getParentService() != null) {
                parentServicePath = this.getParentService().getFullPath();
                return null;
            }

            if (this.getParentServiceClass() == null)
                return null;

            if (this.getParentServiceClass().isInterface())
                return null;

            TService parentService = this.getParentServiceClass().newInstance();
            parentServicePath = parentService.getFullPath();

        }catch (Exception e){
            System.out.println(e);
        }
        finally {
            return  parentServicePath + "/" + this.getMethodName();
        }

    }


    // >>> Default Run Method
    default TResult run(TServiceRequestSession requestMetaData, REQ request, RES response) throws TException {

        if (getServiceMethodRunner() != null)
            return getServiceMethodRunner().run(requestMetaData, request, response, this);
        else
            return TResult.createTResult(TBaseMCode.SERVER_INTERNAL_ERROR, "SERVICE_METHOD_RUNNER_IS_NULL");
    }

    // >>> Authorize The Request
    default TResult authorizeRequest(TServiceRequestSession requestMetaData, REQ request) throws TMethodAuthorizationException {

        try {
            if (getMethodAuthorizer() != null)
                return getMethodAuthorizer().authorizeRequest(requestMetaData, request);
            else
                return TResult.createTResult(TBaseMCode.SUCCESS, null);
        }catch (TException e){
            throw new TMethodAuthorizationException(TServiceMCodes.EXCEPTION_ON_CALLING_AUTHORIZE_REQUEST_METHOD
                    , TMCodeSeverity.FATAL, e, this.getFullPath());
        }
    }

    // >>> Authorize The Response
    default TResult authorizeResponse(TServiceRequestSession requestMetaData, RES response) throws TMethodAuthorizationException {

        try {
            if (getMethodAuthorizer() != null)
                return getMethodAuthorizer().authorizeResponse(requestMetaData, response);
            else
                return TResult.createTResult(TBaseMCode.SUCCESS, null);
        }catch (TException e){
            throw new TMethodAuthorizationException(TServiceMCodes.EXCEPTION_ON_CALLING_AUTHORIZE_REQUEST_METHOD
                    , TMCodeSeverity.FATAL, e, this.getFullPath());
        }
    }


    default TResult doTest(){
        return TResult.createTResult(TBaseMCode.SUCCESS, null);
    }


}
