package org.skyfw.base.datamodel.exception;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TDataModelInitializationException extends TDataModelException {

    public TDataModelInitializationException(TMCode mCode, TMCodeSeverity severity, Throwable cause
            , String dataModelClassPath) {
        super(mCode, severity, cause, dataModelClassPath);
    }

    public static TDataModelInitializationException create(TMCode mCode, Class<? extends TDataModel> dataModelClass){

        return create(mCode, TMCodeSeverity.FATAL, dataModelClass, null);
    }

    public static TDataModelInitializationException create(TMCode mCode, Class<? extends TDataModel> dataModelClass, Throwable cause){

        return create(mCode, TMCodeSeverity.FATAL, dataModelClass, cause);
    }

    public static TDataModelInitializationException create( TMCode mCode
            , TMCodeSeverity severity,Class<? extends TDataModel> dataModelClass, Throwable cause){

        String className;
        if (dataModelClass == null)
            className= "null";
        else
            className= dataModelClass.getName();

        return new TDataModelInitializationException(mCode, severity, cause, className);
    }

    @Override
    public TDataModelInitializationException log() {
        super.log();
        return this;
    }
}
