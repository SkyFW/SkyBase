package org.skyfw.base.result;

import org.skyfw.base.exception.TException;
import org.skyfw.base.serializing.TDeserializeEventListener;
import org.skyfw.base.serializing.TSerializeEventListener;

public class TResultContainer implements TDeserializeEventListener, TSerializeEventListener {

    public TResult result;

    @Override
    public void onAfterDeserialize() throws TException {
        result.onAfterDeserialize();
    }

    @Override
    public void onBeforeSerialize() throws TException {
        result.onBeforeSerialize();
    }
}
