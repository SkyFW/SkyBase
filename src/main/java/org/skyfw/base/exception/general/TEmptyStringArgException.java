package org.skyfw.base.exception.general;

import org.skyfw.base.classes.validation.TArgValidationMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TEmptyStringArgException extends TIllegalArgumentException {

    public TEmptyStringArgException(TMCode mCode, TMCodeSeverity severity, Throwable cause, String argName) {
        super(mCode, severity, cause, argName);
    }

    public TEmptyStringArgException(TMCodeSeverity severity, Throwable cause, String argName) {
        super(TArgValidationMCode.EMPTY_STRING_ARG_IS_NOT_ACCEPTABLE, severity, cause, argName);
    }

}
