package org.skyfw.base.thread.gui.adapters;

import android.os.Handler;
import android.os.Looper;
import org.skyfw.base.thread.gui.TGUIThreadAdapter;

public class TAndroidGUIThreadControler implements TGUIThreadAdapter {

    // >>> Creating a handler to gui thread for the further use
    private final Handler handler=  new Handler(Looper.getMainLooper());

    @Override
    public void addToQueue(Runnable runnable) {
        handler.post(runnable);
    }
}
