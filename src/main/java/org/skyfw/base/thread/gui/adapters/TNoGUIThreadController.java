package org.skyfw.base.thread.gui.adapters;

import org.skyfw.base.thread.gui.TGUIThreadAdapter;

public class TNoGUIThreadController implements TGUIThreadAdapter {

    @Override
    public void addToQueue(Runnable runnable) {
        runnable.run();
    }
}
