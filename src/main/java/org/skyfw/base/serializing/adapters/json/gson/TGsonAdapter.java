package org.skyfw.base.serializing.adapters.json.gson;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.datamodel.TDataSet;
import org.skyfw.base.exception.TException;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.exception.general.TNullArgException;
import org.skyfw.base.log.TLogger;
import org.skyfw.base.pool.TObjectPool;
import org.skyfw.base.pool.exception.TObjectPoolException;
import org.skyfw.base.pool.exception.TPoolableInitException;
import org.skyfw.base.serializing.TSerializable;
import org.skyfw.base.serializing.adapters.TStringSerializerConfig;
import org.skyfw.base.serializing.adapters.json.TJsonAdapter;
import org.skyfw.base.result.TResult;
import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.serializing.exception.TDeserializeException;
import org.skyfw.base.serializing.exception.TSerializeException;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.Map;

public class TGsonAdapter implements TJsonAdapter {
    TLogger logger= TLogger.getLogger();

    TObjectPool motherPool;

    Gson gson;

    @Override
    public Class<TStringSerializerConfig> getConfigClass() {
        return null;
    }

    @Override
    public String serialize(/*TSerializable*/Object object) throws TSerializeException, TIllegalArgumentException {

        TResult<String> result = TResult.create(String.class);

        if (object == null)
            throw TNullArgException.create("object");


        //Class[] classes = new Class[]{object.getClass()};

        try {
            //Type type = TGsonUtils.getTypeTokenByClass(classes[0]);
            String jsonString = this.gson.toJson(object/*, type*/);
            return jsonString;

        } catch (Exception e) {
            throw TSerializeException.create(TGsonAdapter_MCodes.UNKNOWN_EXCEPTION, TGsonAdapter.class, object.getClass(), e);
        }

    }


    @Override
    public <S/* extends TSerializable*/> S deserialize(String jsonString, Class<S> mainClass, Class[] genericParams)
            throws TDeserializeException, TIllegalArgumentException {

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
            //return result.fail(TResultCode.SERVER_INTERNAL_ERROR, "exception: " + e.toString());

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




    // >>> Implementation Of `TPoolable`
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public void init(TStringSerializerConfig poolConfig) throws TPoolableInitException {

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


}
