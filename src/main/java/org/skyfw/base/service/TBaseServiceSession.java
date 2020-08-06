package org.skyfw.base.service;


import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.result.TResult;
import org.skyfw.base.service.method.TServiceMethodInfo;

public class TBaseServiceSession<REQ extends TDataModel, RES extends TDataModel> {

    // >>> Request params
    protected String securityToken;
    protected transient String requesterUserId;
    protected transient TDataModel requesterUser;
    protected String requestPath;
    // >>> Request metadata
    protected String requesterAddress;
    protected boolean isLocalRequest;
    // >>> Request & Response Data
    protected REQ requestData;
    protected RES responseData;
    // >>>
    protected transient TServiceMethodInfo<REQ, RES> methodInfo;
    // >>> Result
    protected TResult<RES> result;


    public TBaseServiceSession() {
    }

    public TBaseServiceSession(TRequestMetaData requestMetaData) {

        this.requestPath= requestMetaData.requestPath;
    }

    public void toResponseMetaData(TServiceResponse responseMetaData){

        responseMetaData.setResult(this.result);
        responseMetaData.setResponseDataModel(this.responseData);
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getRequesterUserId() {
        return requesterUserId;
    }

    public void setRequesterUserId(String requesterUserId) {
        this.requesterUserId = requesterUserId;
    }

    public TDataModel getRequesterUser() {
        return requesterUser;
    }

    public void setRequesterUser(TDataModel requesterUser) {
        this.requesterUser = requesterUser;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getRequesterAddress() {
        return requesterAddress;
    }

    public void setRequesterAddress(String requesterAddress) {
        this.requesterAddress = requesterAddress;
    }

    public boolean isLocalRequest() {
        return isLocalRequest;
    }

    public void setLocalRequest(boolean localRequest) {
        isLocalRequest = localRequest;
    }

    public TResult<RES> getResult() {
        return result;
    }

    public void setResult(TResult<RES> result) {
        this.result = result;
    }

    public REQ getRequestData() {
        return requestData;
    }

    public void setRequestData(REQ requestData) {
        this.requestData = requestData;
    }

    public RES getResponseData() {
        return responseData;
    }

    public void setResponseData(RES responseData) {
        this.responseData = responseData;
    }


    public TServiceMethodInfo<REQ, RES> getMethodInfo() {
        return methodInfo;
    }

    public void setMethodInfo(TServiceMethodInfo<REQ, RES> methodInfo) {
        this.methodInfo = methodInfo;
    }
}
