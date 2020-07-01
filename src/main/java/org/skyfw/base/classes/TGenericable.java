package org.skyfw.base.classes;

import org.skyfw.base.serializing.TSerializable;
import org.skyfw.base.serializing.modifiers.TStringSerializeModifier;

import java.util.Map;

public interface TGenericable<ST> extends TSerializable, TStringSerializeModifier {

     String getClassName();

    default Map getValuesMap(){

        return null;
    }

    default ST getSpecificObject(){
        return null;
    }


    @Override
    default String onAfterSerialize(Object object, String s) throws Exception {
        return null;
    }

    @Override
    default void onAfterDeserialize(String s, Class mainClass, Class[] genericParams, Object object) throws Exception {

    }
}
