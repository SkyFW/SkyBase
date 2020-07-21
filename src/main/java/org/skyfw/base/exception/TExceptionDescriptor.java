package org.skyfw.base.exception;

import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

import java.util.HashMap;
import java.util.Map;

public class TExceptionDescriptor {

    private String className;
    private StackTraceElement[] stackTrace;
    private transient TMCode mCode;
    private String code;
    private TMCodeSeverity severity;
    private Map<String,Object> detail;

    public TExceptionDescriptor(Throwable e) {
        this.className= e.getClass().getName();
        this.stackTrace= e.getStackTrace();
        this.detail= new HashMap<>();
        if (e instanceof TException){
            this.mCode= ((TException) e).getmCode();
            this.code= mCode.toString();
            this.severity= ((TException) e).getSeverity();
            /*loadDetailsFromException(this.detail, (TException) e);*/
            this.detail.putAll((TException) e);
            this.detail.remove("exceptionClassName");
            this.detail.remove("severity");
            this.detail.remove("mCode");
        }
        else {
            this.detail.put("message", e.getMessage());
            this.mCode= null;
        }
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public StackTraceElement[] getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    public TMCode getmCode() {
        return mCode;
    }

    public void setmCode(TMCode mCode) {
        this.mCode = mCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public TMCodeSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(TMCodeSeverity severity) {
        this.severity = severity;
    }

    public Map<String, Object> getDetail() {
        return detail;
    }

    public void setDetail(Map<String, Object> detail) {
        this.detail = detail;
    }
}
