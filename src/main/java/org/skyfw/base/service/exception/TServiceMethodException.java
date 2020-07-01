package org.skyfw.base.service.exception;

import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TServiceMethodException extends TServiceException {

    String methodPath;

    public TServiceMethodException(TMCode mCode, TMCodeSeverity severity, Throwable cause, String serviceName, String methodPath) {
        super(mCode, severity, cause, serviceName);
        this.methodPath = methodPath;
    }

    public TServiceMethodException(TMCode mCode, TException cause, String serviceName, String methodPath) {
        super(mCode, cause, serviceName);
        this.methodPath = methodPath;
    }

    public TServiceMethodException(TException cause, String serviceName, String methodPath) {
        super(cause, serviceName);
        this.methodPath = methodPath;
    }
}
