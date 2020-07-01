package org.skyfw.base.log;

import org.skyfw.base.mcodes.TMCodeSeverity;

public class TLogRecord {

    private String description= "";
    private String className= "";
    private String methodName= "";
    private String fileName= "";
    private int lineNumber= -1;
    private long timeStamp= 0;
    private TMCodeSeverity type;
    private boolean isException;
    private TProgressStatusLogType progressStatus;
    private byte progress;

    StackTraceElement[] stackTraceElements= null;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public StackTraceElement[] getStackTraceElements() {
        return stackTraceElements;
    }

    public void setStackTraceElements(StackTraceElement[] stackTraceElements) {
        this.stackTraceElements = stackTraceElements;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public TMCodeSeverity getType() {
        return type;
    }

    public void setType(TMCodeSeverity type) {
        this.type = type;
    }

    public boolean isException() {
        return isException;
    }

    public void setException(boolean exception) {
        isException = exception;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public TProgressStatusLogType getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(TProgressStatusLogType progressStatus) {
        this.progressStatus = progressStatus;
    }

    public byte getProgress() {
        return progress;
    }

    public void setProgress(byte progress) {
        this.progress = progress;
    }

    public static enum TProgressStatusLogType{

        BEGINING
        , MIDDLE
        , End
        ;
    }
}
