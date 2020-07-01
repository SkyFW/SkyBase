package org.skyfw.base.classes.objectFactory;

import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TCreatingNewInstanceException extends TException {

    String className;

    public TCreatingNewInstanceException(TMCode mCode, TMCodeSeverity severity, Throwable cause, String className) {
        super(mCode, severity, cause);
        this.className = className;
    }
}
