package org.skyfw.base.service.exception;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.datamodel.TGenericDataModel;
import org.skyfw.base.datamodel.annotation.DataModel;
import org.skyfw.base.datamodel.annotation.DataModelField;
import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

@DataModel
public class TServiceInformationRetrievalException extends TException {

    @DataModelField
    private String classPath;

    public TServiceInformationRetrievalException(TMCode mCode, TMCodeSeverity severity, Throwable cause) {
        super(mCode, severity, cause);
    }


    public static TServiceInformationRetrievalException create(TMCode mCode, Class clazz){

        return TServiceInformationRetrievalException.create(mCode, clazz, null);
    }


    public static TServiceInformationRetrievalException create(TMCode mCode, Class clazz, Throwable cause){

        TServiceInformationRetrievalException exception= new TServiceInformationRetrievalException(mCode, null, cause);
        exception.setClassPath(clazz.getName());
        return exception;
    }


    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }
}
