package org.skyfw.base.exception.general;

import org.skyfw.base.datamodel.exception.TDataModelException;
import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TIllegalArgumentException extends TException {

    String argName;

    public TIllegalArgumentException(TMCode mCode, TMCodeSeverity severity, Throwable cause, String argName) {
        super(mCode, severity, cause);
        this.argName = argName;
    }

    public static TIllegalArgumentException create(TMCode errorCode) {

        return create(errorCode, TMCodeSeverity.UNKNOWN, null);
    }

    public static TIllegalArgumentException create(TMCode errorCode, String argName) {

        return create(errorCode, TMCodeSeverity.UNKNOWN, argName);
    }

    public static TIllegalArgumentException create(TMCode errorCode, TMCodeSeverity severity, String argName) {

        return new TIllegalArgumentException(errorCode, severity, null, argName);
    }


    public String getArgName() {
        return argName;
    }

    public void setArgName(String argName) {
        this.argName = argName;
    }


    @Override
    public TIllegalArgumentException log() {
        super.log();
        return this;
    }
}
