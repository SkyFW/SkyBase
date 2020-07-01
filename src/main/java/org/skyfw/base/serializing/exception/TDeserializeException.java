package org.skyfw.base.serializing.exception;

import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.serializing.TSerializable;
import org.skyfw.base.serializing.adapters.TSerializerAdapter;

public class TDeserializeException extends TSerializeException {


    public TDeserializeException(TMCode mCode, TMCodeSeverity severity, Throwable cause) {
        super(mCode, severity, cause);
    }


    public static TDeserializeException create(TMCode mCode
            , Class<? extends TSerializerAdapter> adapterClass
            , Class<? extends TSerializable> objectClass){

        return create(mCode, TMCodeSeverity.UNKNOWN, adapterClass, objectClass, null);
    }


    public static TDeserializeException create(TMCode mCode
            , Class<? extends TSerializerAdapter> adapterClass
            , Class/*<? extends TSerializable>*/ objectClass
            , Throwable cause){

        return create(mCode, TMCodeSeverity.UNKNOWN, adapterClass, objectClass, cause);
    }

    public static TDeserializeException create(TMCode mCode
            , TMCodeSeverity severity
            , Class<? extends TSerializerAdapter> adapterClass
            , Class/*<? extends TSerializable>*/ objectClass
            , Throwable cause){

        TDeserializeException exception= new TDeserializeException(mCode, severity, cause);
        if (adapterClass != null)
            exception.setAdapterClassPath(adapterClass.getSimpleName());
        if (objectClass != null)
            exception.setObjectClassPath(objectClass.getSimpleName());
        return exception;
    }



    @Override
    public TDeserializeException log() {
        super.log();
        return this;
    }


}
