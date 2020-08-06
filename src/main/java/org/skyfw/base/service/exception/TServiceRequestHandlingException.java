package org.skyfw.base.service.exception;

import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.service.TBaseServiceSession;

public class TServiceRequestHandlingException extends TServiceException {

    public TServiceRequestHandlingException(TMCode mCode, TMCodeSeverity severity, Throwable cause, TBaseServiceSession serviceSession) {
        super(mCode, severity, cause, serviceSession.getRequestPath());
    }
}
