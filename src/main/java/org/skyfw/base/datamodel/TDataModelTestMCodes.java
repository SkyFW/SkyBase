package org.skyfw.base.datamodel;

import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public enum  TDataModelTestMCodes implements TMCode {

    GET_FIELD_VALUE_FAILED
    , SET_FIELD_VALUE_FAILED
    , GET_KEY_FIELD_VALUE_FAILED
    , GET_FIELD_VALUE_AS_STRING_FAILED
    , DESERIALIZE_FROM_STRING_FAILED

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
