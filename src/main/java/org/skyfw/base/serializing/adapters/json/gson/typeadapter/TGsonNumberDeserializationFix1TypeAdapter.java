package org.skyfw.base.serializing.adapters.json.gson.typeadapter;

// >>> References:
// [1] https://stackoverflow.com/a/46850645




/*
 * Copyright (C) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Adapts types whose static type is only 'Object'. Uses getClass() on
 * serialization and a primitive/Map/List on deserialization.
 */
public class TGsonNumberDeserializationFix1TypeAdapter extends TypeAdapter<Object> {

    // >>> "ObjectTypeAdapter" is a final class so for using it's default behavior on write method
    // we create and keep an instance of it.
    private static final TypeAdapter<Object> delegate = new Gson().getAdapter(Object.class);



    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked")
        @Override public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            //ToDo: performance Check
            //>>> Custom TypeAdapter will be used only on certain types
            if (Map.class.isAssignableFrom(type.getRawType())) {
                return (TypeAdapter<T>) instance;
            }
            return null;
        }
    };



    @Override
    public void write(JsonWriter out, Object value) throws IOException {
        delegate.write(out, value);
    }


    @Override
    public Object read(JsonReader in) throws IOException {
        JsonToken token = in.peek();
        switch (token) {
            case BEGIN_ARRAY:
                List<Object> list = new ArrayList<Object>();
                in.beginArray();
                while (in.hasNext()) {
                    list.add(read(in));
                }
                in.endArray();
                return list;

            case BEGIN_OBJECT:
                Map<String, Object> map = new LinkedTreeMap<String, Object>();
                in.beginObject();
                while (in.hasNext()) {
                    map.put(in.nextName(), read(in));
                }
                in.endObject();
                return map;

            case STRING:
                return in.nextString();

            case NUMBER:
                // return in.nextDouble();
                // >>> replaced by:
                String n = in.nextString();
                if (n.indexOf('.') != -1) {
                    return Double.parseDouble(n);
                }
                return Long.parseLong(n);

            case BOOLEAN:
                return in.nextBoolean();

            case NULL:
                in.nextNull();
                return null;

            default:
                throw new IllegalStateException();
        }
    }


    public static TGsonNumberDeserializationFix1TypeAdapter instance= new TGsonNumberDeserializationFix1TypeAdapter();


}