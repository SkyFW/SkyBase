package org.skyfw.base.service.mcode;

import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public enum TServiceMCodes implements TMCode {

    SERVICE_INSTANCE_IS_NULL
    , SERVICE_METHOD_INSTANCE_IS_NULL

    , PARENT_SERVICE_IS_NULL

    // >>> Reflection matters
    , EXCEPTION_IN_GENERATING_INSTANCE_FROM_SERVICE_CLASS
    , EXCEPTION_IN_GENERATING_INSTANCE_FROM_SERVICE_METHOD_CLASS

    , EXCEPTION_ON_GETTING_INFO_FROM_SERVICE_OBJECT


    , EXCEPTION_ON_GETTING_INFO_FROM_SERVICE_METHOD_OBJECT




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
