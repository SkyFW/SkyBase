package org.skyfw.base.datamodel.exception;

import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TDataModelTestingException extends TDataModelException {


    protected TDataModelTestingException(TMCode mCode, TMCodeSeverity severity, Throwable cause, String dataModelClassPath) {
        super(mCode, severity, cause, dataModelClassPath);
    }


    public static TDataModelTestingException create(TMCode mCode, TMCodeSeverity severity, Throwable cause, String dataModelClassPath){
        return new TDataModelTestingException(mCode, severity, cause, dataModelClassPath);
    }

}
