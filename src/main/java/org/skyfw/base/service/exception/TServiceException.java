package org.skyfw.base.service.exception;

import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TServiceException extends TException {

    String servicePath;

    public TServiceException(TMCode mCode, TMCodeSeverity severity, Throwable cause, String servicePath) {
        super(mCode, severity, cause);
        this.servicePath = servicePath;
    }

    public TServiceException(TMCode mCode, TException cause, String servicePath) {
        super(mCode, cause);
        this.servicePath = servicePath;
    }

    public TServiceException(TException cause, String servicePath) {
        super(cause);
        this.servicePath = servicePath;
    }
}
