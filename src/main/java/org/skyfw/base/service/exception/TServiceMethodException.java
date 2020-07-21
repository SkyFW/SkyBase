package org.skyfw.base.service.exception;

import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TServiceMethodException extends TException {

    String methodPath;

    public TServiceMethodException(TMCode mCode, TMCodeSeverity severity, Throwable cause, String methodPath) {
        super(mCode, severity, cause);
        this.methodPath = methodPath;
    }

    public TServiceMethodException(TMCode mCode, TException cause, String methodPath) {
        super(mCode, cause);
        this.methodPath = methodPath;
    }

    public TServiceMethodException(TException cause, String methodPath) {
        super(cause);
        this.methodPath = methodPath;
    }
}
