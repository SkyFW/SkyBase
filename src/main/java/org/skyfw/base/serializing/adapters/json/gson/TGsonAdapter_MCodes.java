package org.skyfw.base.serializing.adapters.json.gson;

import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public enum TGsonAdapter_MCodes implements TMCode {

    UNKNOWN_EXCEPTION(TBaseMCode.UNKNOWN_EXCEPTION)
    , DUPLICATE_KEY(null)
    , SETTER_FIELD_WRONG_ARGUEMENT_TYPE(null)

    ;
    TBaseMCode baseMCode;

    TGsonAdapter_MCodes(TBaseMCode baseMCode) {
        this.baseMCode = baseMCode;
    }

    @Override
    public String getModuleName() {
        return TBaseMCode.moduleName;
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
