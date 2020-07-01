package org.skyfw.base.exception.general;

import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TNullArgException extends TIllegalArgumentException {


    public TNullArgException(TMCode mCode, TMCodeSeverity severity, Throwable cause, String argName) {
        super(mCode, severity, cause, argName);
    }

    public static TNullArgException create(String argName){

        return TNullArgException.create(TBaseMCode.NULL_ARGUMENT_IS_NOT_ACCEPTABLE, TMCodeSeverity.UNKNOWN, argName);
    }

    public static TNullArgException create(String argName, TMCodeSeverity severity){

        return TNullArgException.create(TBaseMCode.NULL_ARGUMENT_IS_NOT_ACCEPTABLE, severity, argName);
    }

    public static TNullArgException create(TMCode mCode){

        return TNullArgException.create(mCode, TMCodeSeverity.UNKNOWN);
    }

    public static TNullArgException create(TMCode mCode, TMCodeSeverity severity){

        return TNullArgException.create(mCode, severity,(String) null);
    }

    public static TNullArgException create(TMCode mCode, TMCodeSeverity severity, String argName){

        return new TNullArgException(mCode, severity, null, argName);
    }




    public String getArgName() {
        return argName;
    }

    public void setArgName(String argName) {
        this.argName = argName;
    }
}
