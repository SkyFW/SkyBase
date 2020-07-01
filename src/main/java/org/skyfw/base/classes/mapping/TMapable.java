package org.skyfw.base.classes.mapping;

import java.util.Map;

public interface TMapable {

    default Map<String, Object> getValuesMap(){

        return TObjectMapper.getObjectAsMap(this);
    }
}
