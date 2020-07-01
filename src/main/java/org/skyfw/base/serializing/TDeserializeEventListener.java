package org.skyfw.base.serializing;

import org.skyfw.base.exception.TException;

public interface TDeserializeEventListener {

    void onAfterDeserialize() throws TException;

}
