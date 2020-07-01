package org.skyfw.base.system.file;

import org.skyfw.base.system.operatingsystem.TOperatingSystem;

import static org.apache.commons.io.FilenameUtils.separatorsToUnix;
import static org.apache.commons.io.FilenameUtils.separatorsToWindows;

public class TFileUtils {

    public static String convertSeparatorsToSystem(String path) {

        //return FilenameUtils.separatorsToSystem(path);

        if (path == null) {
            return null;
        }
        if (TOperatingSystem.getOS().equals(TOperatingSystem.TOSType.WINDOWS_OS)) {
            return separatorsToWindows(path);
        } else {
            return separatorsToUnix(path);
        }

    }

}
