package org.skyfw.base.service.exception;

import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TMethodAuthorizationException extends TServiceMethodException {

    public TMethodAuthorizationException(TMCode mCode, TMCodeSeverity severity, Throwable cause, String methodPath) {
        super(mCode, severity, cause, methodPath);
    }

    public TMethodAuthorizationException(TMCode mCode, TException cause, String methodPath) {
        super(mCode, cause, methodPath);
    }

    public TMethodAuthorizationException(TException cause, String methodPath) {
        super(cause, methodPath);
    }
}
