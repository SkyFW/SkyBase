package org.skyfw.base.mcodes;

import org.skyfw.base.exception.TException;

public class TMCodeRegistryException extends TException {

    String code;

    public TMCodeRegistryException(TMCode mCode, TMCodeSeverity severity, Throwable cause, String code) {
        super(mCode, severity, cause);
        this.code = code;
    }

    public TMCodeRegistryException(TMCode mCode, TException cause, String code) {
        super(mCode, cause);
        this.code = code;
    }

    public TMCodeRegistryException(TException cause, String code) {
        super(cause);
        this.code = code;
    }
}
