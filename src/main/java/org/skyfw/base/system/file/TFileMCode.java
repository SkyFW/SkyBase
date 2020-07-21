package org.skyfw.base.system.file;

import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public enum  TFileMCode implements TMCode {

    THIS_IS_NOT_A_VALID_FILE("THIS_IS_NOT_A_VALID_FILE"
            , TBaseMCode.BAD_REQUEST);


    TFileMCode(String rawMessage, TBaseMCode baseMCode) {
        this.rawMessage = rawMessage;
        this.baseMCode = baseMCode;
    }

    String rawMessage;
    TBaseMCode baseMCode;

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
