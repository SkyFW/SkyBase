package org.skyfw.base.datamodel.exception;

import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TDataModelIndexParamInitException extends TDataModelInitializationException{

    private String claimedFieldName;

    public TDataModelIndexParamInitException(TMCode mCode, TMCodeSeverity severity, Throwable cause, String dataModelClassPath, String claimedFieldName) {
        super(mCode, severity, cause, dataModelClassPath);
        this.claimedFieldName = claimedFieldName;
    }
}
