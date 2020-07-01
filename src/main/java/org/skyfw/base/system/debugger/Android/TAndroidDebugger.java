package org.skyfw.base.system.debugger.Android;

import org.skyfw.base.system.debugger.TDebugger_Interface;

public class TAndroidDebugger implements TDebugger_Interface {

    @Override
    public boolean IsDebuggerPresent() {

        //boolean isBeingDebugged = android.os.Debug.isDebuggerConnected()
        //boolean isConnected = new Debug().isDebuggerConnected();

        return false;
    }
}
