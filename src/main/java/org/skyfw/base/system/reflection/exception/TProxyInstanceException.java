package org.skyfw.base.system.reflection.exception;

import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.system.reflection.mcode.TProxyInstanceMCode;

public class TProxyInstanceException extends TException {

    String classPath;

    public TProxyInstanceException(TMCode mCode, TMCodeSeverity severity, Throwable cause) {
        super(mCode, severity, cause);
    }

    public static TProxyInstanceException create(String classPath, Throwable cause){

        return TProxyInstanceException.create(TProxyInstanceMCode.PROXY_INSTANCE_EXCEPTION
                , TMCodeSeverity.UNKNOWN, classPath, cause);
    }


    public static TProxyInstanceException create(TMCode mCode, TMCodeSeverity severity, String classPath, Throwable cause){

        TProxyInstanceException exception= new TProxyInstanceException(mCode, severity, cause);
        exception.setClassPath(classPath);
        return exception;
    }


    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    @Override
    public TException log() {
        super.log();
        return this;
    }
}
