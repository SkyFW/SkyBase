package org.skyfw.base.serializing.adapters.json.gson;

import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class TGsonUtils {

    public static Type getTypeTokenByClassName(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return getTypeTokenByClass(clazz);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unsupported type: " + className);
        }
    }

    public static Type getTypeTokenByClass(Class clazz) {
        try {
            TypeToken<?> typeToken = TypeToken.get(clazz);
            return typeToken.getType();
        } catch (Exception e) {
            throw new IllegalArgumentException("Unsupported type: " + clazz.getName());
        }
    }
}
