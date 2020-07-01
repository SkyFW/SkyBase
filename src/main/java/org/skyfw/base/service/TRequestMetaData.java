package org.skyfw.base.service;

import org.skyfw.base.datamodel.TDataModel;

public class TRequestMetaData<D> {

    private String securityToken;
    private String userId;
    //private String methodName;
    private String requestPath;
    private String[] parsedRequest;
    //ToDo: Getter And Setter Methods Need To Be Introduce Because Of Their Names
    private boolean isLocalRequest;
    private D detail;


    public boolean isValid(){

        if (parsedRequest == null)
            return false;

        if (parsedRequest.length > 1)
            return true;
        else
            return false;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServicePath() {
        if ((this.parsedRequest == null) || (this.parsedRequest.length < 2))
            return "";

        StringBuilder servicePath= new StringBuilder();

        for (int i=0; i < this.parsedRequest.length-1; i++) {
            servicePath.append("/");
            servicePath.append(this.parsedRequest[i]);
        }

        return servicePath.toString();
    }

    public String getMethodName() {

        if ((this.parsedRequest == null) || (this.parsedRequest.length == 0))
            return "";

        return this.parsedRequest[this.parsedRequest.length-1];
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
        //ToDo: performance : apache.commons.lang StringUtils.split May Be Better ???
        this.parsedRequest= requestPath.replaceFirst("^/", "").split("/");
    }

    public String[] getParsedRequest() {
        return parsedRequest;
    }


    public boolean isLocalRequest() {
        return isLocalRequest;
    }

    public void setLocalRequest(boolean localRequest) {
        isLocalRequest = localRequest;
    }

    public D getDetail() {
        return detail;
    }

    public void setDetail(D detail) {
        this.detail = detail;
    }
}
