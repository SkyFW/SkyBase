package org.skyfw.base.pool.exception;

import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.pool.TObjectPool;

public class TObjectPoolTryGetException extends TObjectPoolException {

    long requestTimeOut;

    public TObjectPoolTryGetException(TMCode mCode, Throwable cause, TObjectPool objectPool, long requestTimeOut) {
        super(mCode, TMCodeSeverity.UNKNOWN, cause, objectPool);
        this.requestTimeOut = requestTimeOut;
    }
}
