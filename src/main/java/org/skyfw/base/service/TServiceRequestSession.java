package org.skyfw.base.service;


public class TServiceRequestSession<D> extends TRequestMetaData<D> {

    private String requesterAddress;
    private String requesterUserId;
    //ToDo: getter and setter methods need to be introduce because of their names
    private boolean isLocalRequest;


    public String getRequesterAddress() {
        return requesterAddress;
    }

    public void setRequesterAddress(String requesterAddress) {
        this.requesterAddress = requesterAddress;
    }

    public String getRequesterUserId() {
        return requesterUserId;
    }

    public void setRequesterUserId(String requesterUserId) {
        this.requesterUserId = requesterUserId;
    }

    public boolean isLocalRequest() {
        return isLocalRequest;
    }

    public void setLocalRequest(boolean localRequest) {
        isLocalRequest = localRequest;
    }
}
