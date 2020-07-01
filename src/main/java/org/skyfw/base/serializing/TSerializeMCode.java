package org.skyfw.base.serializing;

import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public enum TSerializeMCode implements TMCode {

    SERIALIZE_EXCEPTION
    , DESERIALIZE_EXCEPTION
    , ON_BEFORE_SERIALIZE_METHOD_EXCEPTION
    , ON_AFTER_DESERIALIZE_METHOD_EXCEPTION

    , LOADING_DESERIALIZED_MAP_TO_DATAMODEL_EXCEPTION
    ;


    @Override
    public String getModuleName() {
        return null;
    }

    @Override
    public TBaseMCode getBaseCode() {
        return null;
    }

    @Override
    public TMCodeSeverity getSeverity() {
        return null;
    }

    @Override
    public String getRawMessage() {
        return null;
    }
}
