package org.skyfw.base.datamodel.exception;

import org.skyfw.base.datamodel.TDataModelMCodes;
import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TDataModelException extends TException {

    private String dataModelClassPath;

    protected TDataModelException(TMCode mCode, TMCodeSeverity severity, Throwable cause, String dataModelClassPath) {
        super(mCode, severity, cause);
        this.dataModelClassPath = dataModelClassPath;
    }

    public static TDataModelException create(TMCodeSeverity severity, String dataModelClassPath, Throwable cause){

        return create(TDataModelMCodes.DATA_MODEL_EXCEPTION, severity, dataModelClassPath, cause);
    }

    public static TDataModelException create(TMCode mCode
            , TMCodeSeverity severity, String dataModelClassPath, Throwable cause){

        return new TDataModelException(mCode, severity, cause, dataModelClassPath);
    }


    @Override
    public TDataModelException log() {
        super.log();
        return this;
    }

    public String getDataModelClassPath() {
        return dataModelClassPath;
    }

    public void setDataModelClassPath(String dataModelClassPath) {
        this.dataModelClassPath = dataModelClassPath;
    }
}
