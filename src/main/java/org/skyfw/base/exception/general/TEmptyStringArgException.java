package org.skyfw.base.exception.general;

import org.skyfw.base.mcodes.TMCode;

public class TEmptyStringArgException extends TIllegalArgumentException {


    public TEmptyStringArgException(TMCode mCode, String argName) {
        super(mCode, argName);
    }

    public TEmptyStringArgException(TMCode mCode) {
        super(mCode);
    }
}
