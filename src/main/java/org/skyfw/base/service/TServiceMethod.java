package org.skyfw.base.service;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.exception.TException;
import org.skyfw.base.result.TResult;
import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.service.exception.TServiceMethodAuthorizationException;

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
    default TResult run(TRequestMetaData requestMetaData, REQ request, RES response) throws TException {

        if (getServiceMethodRunner() != null)
            return getServiceMethodRunner().run(requestMetaData, request, response, this);
        else
            return TResult.createTResult(TBaseMCode.SERVER_INTERNAL_ERROR, "SERVICE_METHOD_RUNNER_IS_NULL");
    }

    // >>> Authorize The Request
    default TResult authorizeRequest(TRequestMetaData requestMetaData, REQ request) throws TServiceMethodAuthorizationException {

        if (getMethodAuthorizer() != null)
            return getMethodAuthorizer().authorizeRequest(requestMetaData, request);
        else
            return TResult.createTResult(TBaseMCode.SUCCESS, null);
    }

    // >>> Authorize The Response
    default TResult authorizeResponse(TRequestMetaData requestMetaData, RES response) throws TServiceMethodAuthorizationException {
        if (getMethodAuthorizer() != null)
            return getMethodAuthorizer().authorizeResponse(requestMetaData, response);
        else
            return TResult.createTResult(TBaseMCode.SUCCESS, null);
    }


    default TResult doTest(){
        return TResult.createTResult(TBaseMCode.SUCCESS, null);
    }


}
