package org.skyfw.base.datamodel.exception;

import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TDataModelFieldException extends TDataModelException {

    String fieldName;

    protected TDataModelFieldException(TMCode mCode, TMCodeSeverity severity, Throwable cause
            , String dataModelClassPath, String fieldName) {
        super(mCode, severity, cause, dataModelClassPath);
        this.fieldName = fieldName;
    }

    public static TDataModelFieldException create(TMCode mCode, TMCodeSeverity severity
            , String dataModelClassPath, String fieldName
            , Throwable cause){

        return new TDataModelFieldException(mCode, severity, cause
        , dataModelClassPath, fieldName);
    }


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    @Override
    public TDataModelFieldException log() {
        super.log();
        return this;
    }
}
