package org.skyfw.base.message;

public class TGenericResponse extends TNameValueMessage {

    public TGenericResponse(boolean keepMetaDataInSerializing)
    {
        super(keepMetaDataInSerializing);

    }

    public static TGenericResponse createTResponse(boolean keepDataTypesInSerializing) {
        return new TGenericResponse(keepDataTypesInSerializing);
    }
}
