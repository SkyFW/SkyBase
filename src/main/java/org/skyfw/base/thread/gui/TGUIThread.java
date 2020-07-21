package org.skyfw.base.thread.gui;

import org.skyfw.base.system.operatingsystem.TOperatingSystem;
import org.skyfw.base.thread.gui.adapters.TAndroidGUIThreadControler;
import org.skyfw.base.thread.gui.adapters.TNoGUIThreadController;

public class TGUIThread {

    private static TGUIThreadAdapter guiThreadAdapter;

    static {

        if (TOperatingSystem.getOS().equals(TOperatingSystem.TOSType.ANDROID_OS))
            guiThreadAdapter= new TAndroidGUIThreadControler();
        else
            guiThreadAdapter= new TNoGUIThreadController();

    }

    public static void addToSchedule(Runnable runnable){
        guiThreadAdapter.addToQueue(runnable);
    }

}
