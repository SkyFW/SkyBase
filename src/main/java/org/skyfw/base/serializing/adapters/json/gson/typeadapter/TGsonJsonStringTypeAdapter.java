package org.skyfw.base.serializing.adapters.json.gson.typeadapter;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class TGsonJsonStringTypeAdapter extends TypeAdapter<String> {
    @Override
    public String read(JsonReader in) throws IOException {

        //throw new UnsupportedOperationException("Unsupported Operation !!!");
        return "";
    }

    @Override
    public void write(JsonWriter out, String value) throws IOException {
        out.jsonValue(value);
    }

}

