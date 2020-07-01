package org.skyfw.base.system.jar;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.datamodel.TGenericDataModel;
import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

import java.io.File;

public class TJarFileException extends TException {

    private String jarFile;

    protected TJarFileException(TMCode mCode, TMCodeSeverity severity, Throwable cause) {
        super(mCode, severity, cause);
    }

    public static TJarFileException create(File jarFile, Throwable cause){

        return TJarFileException.create(TJarFileMCode.JAR_FILE_INSTANCINATION_EXCEPTION
                , TMCodeSeverity.UNKNOWN, jarFile, cause);
    }

    public static TJarFileException create(TMCodeSeverity severity, File jarFile, Throwable cause){

        return TJarFileException.create(TJarFileMCode.JAR_FILE_INSTANCINATION_EXCEPTION, severity, jarFile, cause);
    }

    public static TJarFileException create(TMCode mCode, File jarFile){

        return TJarFileException.create(mCode,TMCodeSeverity.UNKNOWN, jarFile, null);
    }

    public static TJarFileException create(TMCode mCode, TMCodeSeverity severity, File jarFile){

        return TJarFileException.create(mCode,severity, jarFile, null);
    }


    public static TJarFileException create(TMCode mCode, TMCodeSeverity severity, File jarFile, Throwable cause){

        TJarFileException exception= new TJarFileException(mCode, severity, cause);
        if (jarFile != null)
            exception.setJarFile(jarFile.getName());
        return exception;
    }


    public String getJarFile() {
        return jarFile;
    }

    public void setJarFile(String jarFile) {
        this.jarFile = jarFile;
    }
}
