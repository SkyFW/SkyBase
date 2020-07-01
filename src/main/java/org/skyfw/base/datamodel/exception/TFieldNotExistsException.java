package org.skyfw.base.datamodel.exception;

import org.skyfw.base.datamodel.TDataModelMCodes;

import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;


public class TFieldNotExistsException extends TDataModelFieldException {


    public TFieldNotExistsException(TMCode mCode, TMCodeSeverity severity, Throwable cause
            , String dataModelClassPath, String fieldName) {
        super(mCode, severity, cause, dataModelClassPath, fieldName);
    }

    public static TFieldNotExistsException create(String dataModelClassPath, String fieldName){

        return new TFieldNotExistsException(
                TDataModelMCodes.FIELD_NAME_NOT_EXISTS, TMCodeSeverity.UNKNOWN, null, dataModelClassPath, fieldName);
    }



    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
