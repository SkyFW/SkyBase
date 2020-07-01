package org.skyfw.base.service.async;

public interface TAsyncResponseHandler<RES> {

    /*Runnable*/ void onResponse(RES response);

}
