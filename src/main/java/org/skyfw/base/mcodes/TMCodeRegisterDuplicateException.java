package org.skyfw.base.mcodes;

public class TMCodeRegisterDuplicateException extends TMCodeRegistryException {

    String alreadyClassPath;
    String newClassPath;

    public TMCodeRegisterDuplicateException(String code, String alreadyClassPath, String newClassPath) {
        super(TBaseMCode.CODE_ALREADY_REGISTERED_WITH_DIFFERENT_MCODE_CLASS, TMCodeSeverity.FATAL, null, code);
        this.alreadyClassPath = alreadyClassPath;
        this.newClassPath = newClassPath;
    }


}
