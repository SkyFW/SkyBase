package org.skyfw.base.exception.general;

import org.skyfw.base.classes.TFileMCode;
import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

import java.io.File;

public class TFileWriteException extends TException {

    String filePath;

    public TFileWriteException(TMCode mCode, TMCodeSeverity severity, Throwable cause) {
        super(mCode, severity, cause);
    }




    public static TFileWriteException create(File file, Throwable cause){

        return create(TFileMCode.FILE_WRITING_EXCEPTION, TMCodeSeverity.UNKNOWN, file, cause);
    }

    public static TFileWriteException create(TMCode mCode, TMCodeSeverity severity, File file, Throwable cause){

        TFileWriteException exception= new TFileWriteException(mCode, severity, cause);
        if (file != null)
            exception.setFilePath(file.getPath());
        return exception;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
