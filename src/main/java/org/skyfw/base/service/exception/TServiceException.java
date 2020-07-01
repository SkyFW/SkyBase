package org.skyfw.base.service.exception;

import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TServiceException extends TException {

    String serviceName;

    public TServiceException(TMCode mCode, TMCodeSeverity severity, Throwable cause, String serviceName) {
        super(mCode, severity, cause);
        this.serviceName = serviceName;
    }

    public TServiceException(TMCode mCode, TException cause, String serviceName) {
        super(mCode, cause);
        this.serviceName = serviceName;
    }

    public TServiceException(TException cause, String serviceName) {
        super(cause);
        this.serviceName = serviceName;
    }
}
