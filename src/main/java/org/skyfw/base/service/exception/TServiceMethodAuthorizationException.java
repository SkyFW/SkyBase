package org.skyfw.base.service.exception;

import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TServiceMethodAuthorizationException extends TServiceMethodException {

    public TServiceMethodAuthorizationException(TMCode mCode, TMCodeSeverity severity, Throwable cause
            , String serviceName, String methodPath) {
        super(mCode, severity, cause, serviceName, methodPath);
    }

    public TServiceMethodAuthorizationException(TMCode mCode, TException cause, String serviceName, String methodPath) {
        super(mCode, cause, serviceName, methodPath);
    }

    public TServiceMethodAuthorizationException(TException cause, String serviceName, String methodPath) {
        super(cause, serviceName, methodPath);
    }
}
