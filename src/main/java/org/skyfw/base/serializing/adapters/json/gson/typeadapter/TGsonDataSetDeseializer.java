package org.skyfw.base.serializing.adapters.json.gson.typeadapter;

import com.google.gson.*;
import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.datamodel.TDataSet;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TGsonDataSetDeseializer <T extends TDataModel> implements JsonDeserializer<TDataSet<T>> {

    private final Class<T> clazz;

    public TGsonDataSetDeseializer(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public TDataSet<T> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject body = json.getAsJsonObject().getAsJsonObject("body");
        JsonArray arr = body.entrySet().iterator().next().getValue().getAsJsonArray();
        List<T> list = new ArrayList<>();
        for(JsonElement element : arr) {
            JsonElement innerElement = element.getAsJsonObject().entrySet().iterator().next().getValue();
            list.add(context.deserialize(innerElement, clazz));
        }
        return new TDataSet<>(list);
    }
}