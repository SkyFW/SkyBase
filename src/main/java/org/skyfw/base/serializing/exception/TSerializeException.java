package org.skyfw.base.serializing.exception;

import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.serializing.TSerializable;
import org.skyfw.base.serializing.adapters.TSerializerAdapter;

public class TSerializeException extends TException {

    private String objectClassPath;
    private String adapterClassPath;

    public TSerializeException(TMCode mCode, TMCodeSeverity severity, Throwable cause) {
        super(mCode, severity, cause);
    }


    public static TSerializeException create(TMCode mCode
            , Class<? extends TSerializerAdapter> adapterClass
            , Class/*<? extends TSerializable>*/ objectClass
            , Throwable cause){

        return create(mCode, TMCodeSeverity.UNKNOWN, adapterClass, objectClass, cause);
    }

    public static TSerializeException create(TMCode mCode
            , TMCodeSeverity severity
            , Class<? extends TSerializerAdapter> adapterClass
            , Class/*<? extends TSerializable>*/ objectClass
            , Throwable cause){

        TSerializeException exception= new TSerializeException(mCode, severity, cause);
        if (adapterClass != null)
            exception.setAdapterClassPath(adapterClass.getSimpleName());
        if (objectClass != null)
         exception.setObjectClassPath(objectClass.getSimpleName());
        return exception;
    }

    @Override
    public TSerializeException log() {
        super.log();
        return this;
    }




    public String getObjectClassPath() {
        return objectClassPath;
    }

    public void setObjectClassPath(String objectClassPath) {
        this.objectClassPath = objectClassPath;
    }

    public String getAdapterClassPath() {
        return adapterClassPath;
    }

    public void setAdapterClassPath(String adapterClassPath) {
        this.adapterClassPath = adapterClassPath;
    }
}
