package org.skyfw.base.serializing.adapters;

import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.pool.TPoolable;
import org.skyfw.base.serializing.TSerializable;
import org.skyfw.base.serializing.exception.TDeserializeException;
import org.skyfw.base.serializing.exception.TSerializeException;

public interface TStringSerializerAdapter extends TPoolable<TStringSerializerConfig> , TSerializerAdapter{

        String serialize(/*TSerializable*/ Object object) throws TSerializeException, TIllegalArgumentException;
        <S/*extends TSerializable*/> S deserialize(String jsonString, Class<S> mainClass, Class[] genericParams) throws TDeserializeException, TIllegalArgumentException;
}
