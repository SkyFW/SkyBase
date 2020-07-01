package org.skyfw.base.serializing;

import org.skyfw.base.exception.TException;

public interface TSerializeEventListener {

    void onBeforeSerialize() throws TException;

}
