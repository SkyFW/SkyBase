package org.skyfw.base.pool;

import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public enum  TPoolMCode implements TMCode {

    POOL_MEMBER_INIT_FAILED_CONFIG_IS_NULL
    , POOL_MEMBER_INIT_FAILED_CONFIG_IS_NOT_VALID
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
