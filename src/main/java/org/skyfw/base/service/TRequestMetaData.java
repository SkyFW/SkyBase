package org.skyfw.base.service;

public class TRequestMetaData<D> {

    private String securityToken;
    private String requestPath;
    private transient String[] parsedRequestPath;
    private transient D detail;



    public boolean isValid(){
        if (this.requestPath == null || this.requestPath.isEmpty())
            return false;
        else
            return true;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {

        this.requestPath = requestPath;
    }


    public String[] getParsedRequestPath() {

        if (this.parsedRequestPath == null && this.requestPath != null)
            //ToDo: performance : apache.commons.lang StringUtils.split May Be Better ???
            this.parsedRequestPath= requestPath.replaceFirst("^/", "").split("/");

        return parsedRequestPath;
    }

    public String getMethodName() {
        this.getParsedRequestPath();
        if ((this.parsedRequestPath == null) || (this.parsedRequestPath.length == 0))
            return null;
        return this.parsedRequestPath[this.parsedRequestPath.length-1];
    }

    public String getServiceName() {
        this.getParsedRequestPath();
        if ((this.parsedRequestPath == null) || (this.parsedRequestPath.length == 0))
            return null;
        return this.parsedRequestPath[0];
    }

    public D getDetail() {
        return detail;
    }

    public void setDetail(D detail) {
        this.detail = detail;
    }
}
