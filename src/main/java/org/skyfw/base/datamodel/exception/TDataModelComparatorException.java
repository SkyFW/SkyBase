package org.skyfw.base.datamodel.exception;

import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TDataModelComparatorException extends TException {

    String dm1ClassName;
    String dm2ClassName;
    String fieldName;

    public TDataModelComparatorException(TMCode mCode, TMCodeSeverity severity, Throwable cause
            , String dm1ClassName, String dm2ClassName, String fieldName) {

        super(mCode, severity, cause);
        this.dm1ClassName = dm1ClassName;
        this.dm2ClassName = dm2ClassName;
        this.fieldName = fieldName;
    }

    public static TDataModelComparatorException create(TMCode mCode, TMCodeSeverity severity, Throwable cause
            , String dm1ClassName, String dm2ClassName, String fieldName){

        return new TDataModelComparatorException(mCode, severity, cause, dm1ClassName, dm2ClassName, fieldName);
    }

}
