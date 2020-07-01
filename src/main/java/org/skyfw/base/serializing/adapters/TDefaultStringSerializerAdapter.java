package org.skyfw.base.serializing.adapters;

import org.skyfw.base.serializing.adapters.json.gson.TGsonAdapter;

public enum TDefaultStringSerializerAdapter {

      GSON_JSON_ENCODER(TGsonAdapter.class)
    , JAVA_XML_ENCODER(null)
    , JAVA_OBJECT_SERIALIZER(null)
    , JAVA_OBJECT_SERIALIZER_BASE64(null)
    ;

    Class<? extends TStringSerializerAdapter> adapterClass;

    TDefaultStringSerializerAdapter(Class<? extends TStringSerializerAdapter> stringSerializerAdapterClass){

        this.adapterClass= stringSerializerAdapterClass;
    }

}
