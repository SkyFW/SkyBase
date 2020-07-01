package org.skyfw.base.system.jar;

import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public enum  TJarFileMCode implements TMCode {

    JAR_FILE_INSTANCINATION_EXCEPTION
    , JAR_MODULE_FILE_NAME_IS_NOT_IN_VALID_FORMAT
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
