package org.skyfw.base.pool;

import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public enum TObjectPoolMCode implements TMCode {

    CLASS_PARAM_IS_NULL(
            TBaseMCode.BAD_REQUEST
            , ""),

    CLASS_NOT_SUPPORTED_BY_POOL_SET(
            TBaseMCode.BAD_REQUEST
            , ""),

       NO_FREE_POOL_MEMBER(
            TBaseMCode.LOCAL_LACK_OF_RESOURCES
            , "There is no free pool member to be assigned"),

    POOL_INTERNAL_ERROR(
            TBaseMCode.LOCAL_INTERNAL_ERROR
            , ""),
    ;




    TBaseMCode baseMCode;
    String message;

    TObjectPoolMCode(TBaseMCode baseMCode, String message) {

        this.baseMCode = baseMCode;
        this.message= message;
    }

    @Override
    public String getModuleName() {
        return TBaseMCode.moduleName;
    }

    @Override
    public TBaseMCode getBaseCode() {
        return baseMCode;
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
