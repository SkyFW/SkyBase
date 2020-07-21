package org.skyfw.base.exception.general;

import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;

public class TIllegalArgumentException extends TException {

    String argName;

    public TIllegalArgumentException(TMCode mCode, String argName) {
        super(mCode);
        this.argName = argName;
    }

    public TIllegalArgumentException(TMCode mCode) {
        super(mCode);
        this.argName = "";
    }

    public String getArgName() {
        return argName;
    }

    public void setArgName(String argName) {
        this.argName = argName;
    }


    @Override
    public TIllegalArgumentException log() {
        super.log();
        return this;
    }
}
