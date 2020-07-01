package org.skyfw.base.serializing.modifiers;

public interface TBytesSerializeModifier<T> {

    byte[] onAfterSerialize(Object object, byte[] bytes) throws Exception;
    void onAfterDeserialize(byte[] bytes, Class<T> mainClass, Class[] genericParams, T object) throws Exception;
}
