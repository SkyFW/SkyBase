package org.skyfw.base.datamodel.exception;

import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TDataModelFieldInitializationException extends TDataModelInitializationException {

    String fieldName;

    public TDataModelFieldInitializationException(TMCode mCode, TMCodeSeverity severity, Throwable cause
            , String dataModelClassPath, String fieldName) {
        super(mCode, severity, cause, dataModelClassPath);
        this.fieldName = fieldName;
    }


    @Override
    public TDataModelFieldInitializationException log() {
        super.log();
        return this;
    }
}
