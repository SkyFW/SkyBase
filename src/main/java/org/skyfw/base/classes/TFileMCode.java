package org.skyfw.base.classes;

import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public enum TFileMCode implements TMCode {


    FILE_WRITING_EXCEPTION(
            TBaseMCode.LOCAL_INTERNAL_ERROR
            , ""
            , TMCodeSeverity.UNKNOWN
    )
    ;


    TBaseMCode baseMCode;
    String rawMessage;
    TMCodeSeverity severity;


    TFileMCode(TBaseMCode baseMCode, String rawMessage, TMCodeSeverity severity) {
        this.baseMCode = baseMCode;
        this.rawMessage = rawMessage;
        this.severity = severity;
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
