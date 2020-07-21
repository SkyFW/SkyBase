package org.skyfw.base.serializing.adapters.json.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.skyfw.base.classes.TNumbers;
import org.skyfw.base.classes.objectFactory.TObjectFactory;
import org.skyfw.base.datamodel.*;
import org.skyfw.base.exception.TException;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.exception.general.TNullArgException;
import org.skyfw.base.log.TLogger;
import org.skyfw.base.pool.TObjectPool;
import org.skyfw.base.pool.exception.TPoolableInitException;
import org.skyfw.base.serializing.adapters.TStringSerializerConfig;
import org.skyfw.base.serializing.adapters.json.TJsonAdapter;
import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.serializing.exception.TDeserializeException;
import org.skyfw.base.serializing.exception.TSerializeException;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

public class TGsonAdapter implements TJsonAdapter {
    TLogger logger= TLogger.getLogger();

    TObjectPool motherPool;

    Gson gson;

    @Override
    public String serialize(/*TSerializable*/Object object) throws TSerializeException, TIllegalArgumentException {

        if (object == null)
            throw new TNullArgException("value");


        //Class[] classes = new Class[]{value.getClass()};

        try {
            //Type type = TGsonUtils.getTypeTokenByClass(classes[0]);
            String jsonString = this.gson.toJson(object/*, type*/);
            return jsonString;

        } catch (Exception e) {
            throw TSerializeException.create(TGsonAdapter_MCodes.UNKNOWN_EXCEPTION, TGsonAdapter.class, object.getClass(), e);
        }

    }


    TSkyJsonDeserializer skyJsonDeserializer= new TSkyJsonDeserializer();
    @Override
    public <S/* extends TSerializable*/> S deserialize(String jsonString, Class<S> mainClass, Class[] genericParams)
            throws TDeserializeException, TIllegalArgumentException {

        if (TDataModel.class.isAssignableFrom(mainClass) || TDataSet.class.isAssignableFrom(mainClass))
            return skyJsonDeserializer.internalDeserialize(jsonString, mainClass, genericParams);

        try {
            //It Will Do The Magic When Deserializing The Generic Types ;)
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            Type objectType;
            objectType = TypeToken.getParameterized(mainClass, genericParams).getType();

            //ToDo: Performance :/
            if ((genericParams.length != 0)
                    && (mainClass.equals(TDataSet.class))) {
                objectType = TypeToken.getParameterized(LinkedList.class, genericParams).getType();
                LinkedList<Map> linkedList = this.gson.fromJson(jsonString, objectType);
                TDataSet dataSet = new TDataSet();

                linkedList.forEach(o -> {
                    try {
                        TDataModel dataModel = (TDataModel) genericParams[0].newInstance();
                        dataModel.loadFrom(o);
                        dataSet.add(dataModel);
                    } catch (Exception e) {
                    }
                });

                return (S) dataSet;
            } else {
                S obj = this.gson.fromJson(jsonString, objectType);
                return obj;
            }

        } catch (JsonSyntaxException e) {
            logger.fatal("exception In: gson.fromJson", e);

            if (e.getMessage().substring(0, 14).equals("duplicate key:"))
                throw TDeserializeException.create(TGsonAdapter_MCodes.DUPLICATE_KEY, TGsonAdapter.class, mainClass, e);

            return null;
        } catch (IllegalArgumentException e) {

            if (e.getMessage().substring(0, 11).equals("Can not set"))
                throw TDeserializeException.create(TGsonAdapter_MCodes.SETTER_FIELD_WRONG_ARGUEMENT_TYPE, TGsonAdapter.class, mainClass, e);

        } catch (Exception e) {
                throw TDeserializeException.create(TGsonAdapter_MCodes.UNKNOWN_EXCEPTION, TGsonAdapter.class, mainClass, e);
        }

        return null;
    }





    public static class SuperclassExclusionStrategy implements ExclusionStrategy
    {
        public boolean shouldSkipClass(Class<?> arg0)
        {
            return false;
        }

        public boolean shouldSkipField(FieldAttributes fieldAttributes)
        {
            String fieldName = fieldAttributes.getName();
            Class<?> theClass = fieldAttributes.getDeclaringClass();

            return isFieldInSuperclass(theClass, fieldName);
        }

        private boolean isFieldInSuperclass(Class<?> subclass, String fieldName)
        {
            Class<?> superclass = subclass.getSuperclass();
            Field field;

            while(superclass != null)
            {
                field = getField(superclass, fieldName);

                if(field != null)
                    return true;

                superclass = superclass.getSuperclass();
            }

            return false;
        }

        private Field getField(Class<?> theClass, String fieldName)
        {
            try
            {
                return theClass.getDeclaredField(fieldName);
            }
            catch(Exception e)
            {
                return null;
            }
        }
    }

    // >>> Implementation Of `TConfigurable`
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public Class<TStringSerializerConfig> getConfigClass() {
        return null;
    }


    // >>> Implementation Of `TPoolable`
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void config(TStringSerializerConfig poolConfig) throws TPoolableInitException {

        GsonBuilder gsonBuilder= new GsonBuilder();

        gsonBuilder.setPrettyPrinting();

        //ToDo: performance Check
        gsonBuilder.addDeserializationExclusionStrategy(new SuperclassExclusionStrategy());
        gsonBuilder.addSerializationExclusionStrategy(new SuperclassExclusionStrategy());
        //
        //gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);

        // >>> Fixing numbers problem (Gson converts any number to Double by default!)
        // >>> Simply will be ignored ! :
        /* gsonBuilder.registerTypeAdapter(Map.class, TGsonNumberDeserializationFix1TypeAdapter.instance);*/
        // >>> It works but is limited to an specific type:
        //gsonBuilder.registerTypeAdapter(new TypeToken<Map<String, Object>>(){}.getSeverity(), TGsonNumberDeserializationFix1TypeAdapter.instance);
        //gsonBuilder.registerTypeAdapter(new TypeToken<ConcurrentHashMap<String, Object>>(){}.getType(), TGsonNumberDeserializationFix1TypeAdapter.instance);

        //gsonBuilder.registerTypeAdapter(Object.class, TGsonNumberDeserializationFix1TypeAdapter.instance);
        //gsonBuilder.registerTypeAdapter(new TypeToken<HashMap<String, Object>>(){}.getType(), TGsonNumberDeserializationFix2TypeAdapter.instance);

        /*gsonBuilder.registerTypeAdapter(ConcurrentHashMap.class, TGsonNumberDeserializationFix1TypeAdapter.instance);
        gsonBuilder.registerTypeAdapter(TGenericDataModel.class, TGsonNumberDeserializationFix1TypeAdapter.instance);
        gsonBuilder.registerTypeAdapter(new TypeToken<TGenericDataModel>(){}.getType(), TGsonNumberDeserializationFix1TypeAdapter.instance);
        */

        // >>> It works perfectly for Numbers problem but...
        // will make deserializing the complex types completely cripple ! :
        /* gsonBuilder.registerTypeAdapterFactory(TGsonNumberDeserializationFix1TypeAdapter.FACTORY);*/


        //--Type dataSetType = new TypeToken<TDataSet<TDataModel>>(){}.getType();
        /*gsonBuilder.registerTypeAdapter(dataSetType, new TGsonDataSetDeseializer<>(TDataModel.class));*/


        this.gson= gsonBuilder.create();
    }

    @Override
    public void setMotherPool(TObjectPool objectPool) {
        this.motherPool= objectPool;
    }

    @Override
    public TObjectPool getMotherPool() {
        return this.motherPool;
    }



    // >>> MCodes
    //------------------------------------------------------------------------------------------------------------------
    public enum TGsonAdapterMCodes implements TMCode{

        GSON_JSON_SYNTAX_EXCEPTION
        ;


        @Override
        public String getModuleName() {
            return null;
        }

        @Override
        public TBaseMCode getBaseCode() {
            return null;
        }

        @Override
        public TMCodeSeverity getSeverity() {
            return null;
        }

        @Override
        public String getRawMessage() {
            return null;
        }


    }


    static {

    }











    public static class TSkyJsonDeserializer {

        JsonReader jsonReader;
        Stack<TItem> itemStack = new Stack<>();
        Stack<Class> typeStack = new Stack<>();
        String currentItemName = null;

        private <S/* extends TSerializable*/> S internalDeserialize(String jsonString, Class<S> mainClass, Class[] genericParams)
                throws TDeserializeException, TIllegalArgumentException {


            // >>> preparing
            this.jsonReader = new JsonReader(new StringReader(jsonString));
            this.currentItemName = null;
            this.itemStack.clear();
            this.typeStack.clear();
            /*this.typesArr = new Class[1 + genericParams.length];*/
            for (int i = genericParams.length-1; i >= 0 ; i--)
                this.typeStack.push(genericParams[i]);
            this.typeStack.push(mainClass);
            try {
                // >>> creating and pushing the root element
                /*TItem rootItem= new TItem();
                rootItem.name= "root";
                rootItem.value= TObjectFactory.newInstance(mainClass);
                if (TDataModel.class.isAssignableFrom(mainClass))
                    rootItem.dataModelDescriptor= TDataModelHelper.getDataModelDescriptor((Class<? extends TDataModel>) mainClass);
                this.itemStack.push(rootItem);*/



                while (true/*jsonReader.hasNext()*/) {

                    JsonToken nextToken = jsonReader.peek();

                    //--------------------------------------------------------------------------------------------------
                    if (JsonToken.BEGIN_ARRAY.equals(nextToken)) {

                        // >>> behaving normal
                        Class type= this.getCurrentTokenType();
                        if (!Collection.class.isAssignableFrom(type))
                            throw new RuntimeException("JSON_ARRAY_FOUND_BUT_REQUESTED_TYPE_IS_NOT_A_SET");


                        TItem item = new TItem();
                        item.name = currentItemName;
                        item.value = new TDataSet();
                        itemStack.push(item);

                        jsonReader.beginArray();
                        this.currentItemName= null;
                        continue;
                    }
                    //--------------------------------------------------------------------------------------------------
                    if ((JsonToken.END_OBJECT.equals(nextToken)) || (JsonToken.END_ARRAY.equals(nextToken))) {

                        // >>>
                        if (JsonToken.END_ARRAY.equals(nextToken)) {
                            typeStack.pop();
                            jsonReader.endArray();
                        }else {
                            jsonReader.endObject();
                        }

                        TItem currentItem = itemStack.pop();
                        if (itemStack.isEmpty()) {
                            return (S) currentItem.value;
                        }
                        TItem parentItem = itemStack.peek();
                        addToParent(parentItem, currentItem.name, currentItem.value);
                        continue;
                    }
                    //--------------------------------------------------------------------------------------------------
                    if (JsonToken.BEGIN_OBJECT.equals(nextToken)) {

                        jsonReader.beginObject();
                        /*nextToken = jsonReader.peek();
                        if (!JsonToken.NAME.equals(nextToken))
                            throw new RuntimeException("MALFORMED_JSON_OBJECT_NAME_NOT_FOUND");*/

                        TItem item = new TItem();
                        item.name = currentItemName;
                        Class type= this.getCurrentTokenType();
                        if (type != null) {
                            item.value = TObjectFactory.newInstance(type);
                            item.dataModelDescriptor = TDataModelHelper.getDataModelDescriptor(type);
                        }
                        itemStack.push(item);
                        this.currentItemName= null;
                        continue;
                    }

                    //--------------------------------------------------------------------------------------------------
                    if (JsonToken.END_DOCUMENT.equals(nextToken)) {
                        System.out.println(nextToken);
                        jsonReader.skipValue();
                        continue;
                    }

                    //--------------------------------------------------------------------------------------------------
                    if (JsonToken.NAME.equals(nextToken)) {

                        this.currentItemName= jsonReader.nextName();

                        nextToken = jsonReader.peek();
                        //throw new RuntimeException("MALFORMED_EXCEPTION_INVALID_TOKEN_AFTER_NAME_TOKEN");
                        continue;
                    }

                    // >>> processing value token
                    {
                        Object value= null;
                        Class type= this.getCurrentTokenType();

                        if (JsonToken.STRING.equals(nextToken)) {

                            String strValue = jsonReader.nextString();

                            if ((type != null) && (Enum.class.isAssignableFrom(type)))
                                value = Enum.valueOf(type, strValue);
                            else
                                value= strValue;
                        }
                        else if (JsonToken.NUMBER.equals(nextToken)) {

                            String strValue = jsonReader.nextString();
                            // >>> In case of setting field in "TGenericDataModel"
                            if (type == null)
                                type= Double.class;
                            value= TNumbers.create(strValue, type);
                        }
                        else if (JsonToken.BOOLEAN.equals(nextToken)) {

                            value = jsonReader.nextBoolean();

                        }
                        else if (JsonToken.NULL.equals(nextToken)) {

                            jsonReader.skipValue();
                            value= null;
                        }

                        if (itemStack.isEmpty())
                            return (S) value;
                        else
                            this.addToParent(itemStack.peek(), this.currentItemName, value);
                        this.currentItemName= null;
                    }



                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;

            } catch (Throwable e) {
                e.printStackTrace();
                return null;
            }

            //return null;
        }

        private Class getCurrentTokenType(){
            // >>> In case of parent node is not exists in requested type
            if ( ! itemStack.isEmpty() && (itemStack.peek().value== null))
                return null;

            if (itemStack.isEmpty())
                return typeStack.pop();

            if ((itemStack.peek().dataModelDescriptor== null))
                return typeStack.peek();

            if ( TGenericDataModel.class.equals(itemStack.peek().value.getClass()))
                return null;

            TFieldDescriptor fieldDescriptor= itemStack.peek().dataModelDescriptor.fields.get(currentItemName);
            if (fieldDescriptor == null)
                return null;

            return itemStack.peek().dataModelDescriptor.fields.get(currentItemName).getType();
        }


        private void addToParent(TItem parentItem , String name, Object value) throws TException {

            // >>> In case of parent node is not exists in requested type
            if (parentItem.value == null)
                return;

            if (parentItem.value instanceof  Collection){
                ((Collection) parentItem.value).add(value);

            } else if (parentItem.value instanceof TDataModel){
                if (parentItem.value instanceof TGenericDataModel)
                    ((TGenericDataModel) parentItem.value).put(name, value);
                else {
                    TFieldDescriptor fieldDescriptor= parentItem.dataModelDescriptor.fields.get(name);
                    if (fieldDescriptor != null)
                        TDataModelHelper.setFieldValue((TDataModel) parentItem.value, fieldDescriptor, value);
                }
            } else {
                throw new RuntimeException("PARENT_ITEM_IS_NEITHER_DATAMODEL_OR_COLLECTION");
            }
        }


        private static final class TItem {

            public String name= null;
            public Object value= null;
            public TDataModelDescriptor dataModelDescriptor= null;
        }


    }


}
