package org.skyfw.base.exception.general;

import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;

public class TNullArgException extends TIllegalArgumentException {


    public TNullArgException(TMCode mCode, String argName) {
        super(mCode, argName);
    }

    public TNullArgException(String argName) {
        super(TBaseMCode.NULL_ARGUMENT_IS_NOT_ACCEPTABLE, argName);
    }
}
