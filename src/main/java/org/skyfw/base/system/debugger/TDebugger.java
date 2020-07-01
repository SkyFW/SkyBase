package org.skyfw.base.system.debugger;

import org.skyfw.base.system.debugger.Android.TAndroidDebugger;
import org.skyfw.base.system.debugger.Standard.TStandardDebugger;
import org.skyfw.base.system.operatingsystem.TOperatingSystem;

public class TDebugger {

    private static TDebugger_Interface debugger;

    static {

        if (TOperatingSystem.getOS().equals(TOperatingSystem.TOSType.ANDROID_OS))
            debugger= new TAndroidDebugger();
        else
            debugger= new TStandardDebugger();

    }

    public static boolean IsDebuggerPresent(){

        return debugger.IsDebuggerPresent();

    }

}
