package org.skyfw.base.thread.async;

import org.skyfw.base.result.TResult;

public interface TAsyncResultHandler<RES> {

    void onSuccess(TResult<RES> result);
    boolean onFailure(TResult result);
}
