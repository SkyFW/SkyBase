package org.skyfw.base.serializing.adapters;

import org.skyfw.base.pool.TPoolable;

public interface TBytesSerializerAdapter extends TPoolable {

    byte[] serialize(Object object) throws Exception;
    <T> T deserialize(byte[] bytes, Class<T> mainClass, Class[] genericParams) throws Exception;

}
