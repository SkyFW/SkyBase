package org.skyfw.base.serializing.modifiers;

public interface TStringSerializeModifier<T> {

    String onAfterSerialize(Object object, String s) throws Exception;
    void onAfterDeserialize(String s, Class<T> mainClass, Class[] genericParams, T object) throws Exception;
}
