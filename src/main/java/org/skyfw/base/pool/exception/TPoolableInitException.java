package org.skyfw.base.pool.exception;

import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.pool.TObjectPool;

public class TPoolableInitException extends TObjectPoolException {

    public TPoolableInitException(TMCode mCode, TMCodeSeverity severity, Throwable cause, TObjectPool objectPool) {
        super(mCode, severity, cause, objectPool);
    }
}
