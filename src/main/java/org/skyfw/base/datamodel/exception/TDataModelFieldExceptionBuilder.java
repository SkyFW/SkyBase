package org.skyfw.base.datamodel.exception;

import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TDataModelFieldExceptionBuilder {
    private TMCode mCode;
    private TMCodeSeverity severity;
    private Throwable cause;

    private String dataModelClassPath;
    String fieldName;

    public TDataModelFieldExceptionBuilder setmCode(TMCode mCode) {
        this.mCode = mCode;
        return this;
    }

    public TDataModelFieldExceptionBuilder setSeverity(TMCodeSeverity severity) {
        this.severity = severity;
        return this;
    }

    public TDataModelFieldExceptionBuilder setCause(Throwable cause) {
        this.cause = cause;
        return this;
    }

    public TDataModelFieldExceptionBuilder setDataModelClassPath(String dataModelClassPath) {
        this.dataModelClassPath = dataModelClassPath;
        return this;
    }

    public TDataModelFieldExceptionBuilder setFieldName(String fieldName) {
        this.fieldName = fieldName;
        return this;
    }


    public TDataModelFieldException create() {
        return new TDataModelFieldException(mCode, severity, cause, dataModelClassPath, fieldName);
    }
}