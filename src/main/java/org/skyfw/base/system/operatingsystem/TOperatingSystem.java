package org.skyfw.base.system.operatingsystem;

import java.io.IOException;
import java.util.Locale;

public class TOperatingSystem {

    public enum TOSType {
        WINDOWS_OS,
        UNIX_OS,
        ANDROID_OS,
        POSIX_UNIX_OS,
        MAC_OS,
        OTHER_OS;

        private String version;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    private static TOSType osType = TOSType.OTHER_OS;

    static {
        try {
            String osName = System.getProperty("os.name");
            if (osName == null) {
                throw new IOException("os.name not found");
            }
            osName = osName.toLowerCase(Locale.ENGLISH);
            if (osName.contains("windows")) {
                osType = TOSType.WINDOWS_OS;
            } else if (osName.contains("linux")
                    || osName.contains("mpe/ix")
                    || osName.contains("freebsd")
                    || osName.contains("irix")
                    || osName.contains("digital unix")
                    || osName.contains("unix")) {
                osType = TOSType.UNIX_OS;
            } else if (osName.contains("mac TOSType")) {
                osType = TOSType.MAC_OS;
            } else if (osName.contains("sun TOSType")
                    || osName.contains("sunos")
                    || osName.contains("solaris")) {
                osType = TOSType.POSIX_UNIX_OS;
            } else if (osName.contains("hp-ux")
                    || osName.contains("aix")) {
                osType = TOSType.POSIX_UNIX_OS;
            } else {
                osType = TOSType.OTHER_OS;
            }

            //Check For Android If The TOSType Is From Linux Familly
            if (System.getProperty("java.vm.name").equalsIgnoreCase("Dalvik")){
                osType = TOSType.ANDROID_OS;
            }

        } catch (Exception ex) {
            osType = TOSType.OTHER_OS;
        } finally {
            osType.setVersion(System.getProperty("os.version"));
        }
    }

    public static TOSType getOS() {
        return osType;
    }
}