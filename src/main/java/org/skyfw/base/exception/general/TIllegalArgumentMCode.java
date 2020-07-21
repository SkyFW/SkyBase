package org.skyfw.base.exception.general;

import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public enum TIllegalArgumentMCode implements TMCode {


    ARGUMENT_MUST_BE_NUMERIC
    , ARGUMENT_MUST_BE_A_NUMERIC_CLASS
    , STRING_ARGUMENT_MUST_BE_CONVERTABLE_TO_NUMERIC
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
