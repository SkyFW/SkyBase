package org.skyfw.base.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.skyfw.base.exception.TException;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.pool.exception.TObjectPoolException;
import org.skyfw.base.serializing.TSerializeMCode;
import org.skyfw.base.serializing.adapters.TStringSerializerAdapter;
import org.skyfw.base.serializing.adapters.json.jackson.TJacksonObjectMapper;
import org.skyfw.base.serializing.adapters.json.jackson.TJacksonObjectMapperPool;
import org.skyfw.base.result.TResult;
import org.skyfw.base.serializing.adapters.json.gson.TGsonUtils;
import org.skyfw.base.serializing.exception.TDeserializeException;
import org.skyfw.base.serializing.exception.TSerializeException;

import java.io.IOException;
import java.util.Iterator;

public class TGenericResponse extends TNameValueMessage {

    TResult result;


    private TGenericResponse(boolean keepMetaDataInSerializing)
    {
        super(keepMetaDataInSerializing);

    }

    public static TGenericResponse createTResponse(boolean keepDataTypesInSerializing) {
        return new TGenericResponse(keepDataTypesInSerializing);
    }

    public static TGenericResponse createFromString(boolean keepDataTypesInSerializing
            , String objectString
            , Class<? extends TStringSerializerAdapter> adapterClass) throws TException{

        TGenericResponse result= createTResponse(keepDataTypesInSerializing);
        result.deserializeFromString(objectString, adapterClass);
        return result;
    }


    public int getSize()
    {
        return values.size();
    }



    public Iterator getNames()
    {

        Iterator keys=values.keySet().iterator();
        /*
        ArrayList<String> result=new ArrayList<String>();
        while (keys.hasNext())
            result.add(keys.next().toString());

        String sResult[]=new String [result.size()];
        for(int i=0;i<sResult.length;i++)
            sResult[i]=result.get(i);

        return sResult;
        */
        return keys;

    }


    public Iterator getValues()
    {

        Iterator valuess=values.values().iterator();
        /*
        ArrayList<Object> result= new ArrayList<Object>();
        while (valuess.hasNext())
            result.add(valuess.next());

        Object sResult[]=new Object [result.size()];
        for(int i=0;i<sResult.length;i++)
            sResult[i]=result.get(i);

        return sResult;
        */
        return valuess;

    }

    public TResult getResult() {
        return result;
    }

    public void setResult(TResult result) {
        this.result = result;
    }


    @Override
    public String serializeToString(Class<? extends TStringSerializerAdapter> adapterClass)
            throws TSerializeException, TObjectPoolException, TIllegalArgumentException {

        String resultJson = "";

        if (!keepDataTypesInSerializing)
            return resultJson;

        resultJson = super.serializeToString(adapterClass);

        //Fast & Safe ;)
        resultJson= resultJson.replaceFirst("Values", "Response");


        TJacksonObjectMapper objectMapper = TJacksonObjectMapperPool.getFromPool(1000);
        try {
            try {
                //create JsonNode
                JsonNode rootNode = objectMapper.readTree(resultJson);
                //((ObjectNode) rootNode).remove("result");
                ((ObjectNode) rootNode).putPOJO("result", this.result);
                //
                resultJson = objectMapper.writeValueAsString(rootNode);
            } catch (IOException e) { return ""; }
        }finally {
            if (objectMapper != null) objectMapper.release();
        }

        return resultJson;
    }


    @Override
    public TGenericResponse deserializeFromString(String jsonString, Class<? extends TStringSerializerAdapter> adapterClass)
            throws TDeserializeException, TObjectPoolException {

        //Fast & Safe ;)
        jsonString= jsonString.replaceFirst("Response", "Values");


            //Get a TJacksonObjectMapper From Pool
            TJacksonObjectMapper objectMapper = TJacksonObjectMapperPool.getFromPool(1000);
            try {
                try {
                    JsonNode rootNode = objectMapper.readTree(jsonString);
                    JsonNode resultJsonNode = rootNode.get("result");
                    //ToDo: Serious performance issue
                    //Neither of this methods wont work because there is no Constructor method...
                    //obj= objectMapper.readValue(valueNode.traverse(), clazz);
                    //obj= objectMapper.treeToValue(valueNode, clazz);
                    //obj= objectMapper.convertValue(valueNode , clazz);
                    Gson gson = new GsonBuilder().create();
                    this.result = gson.fromJson(objectMapper.writeValueAsString(resultJsonNode), TGsonUtils.getTypeTokenByClass(TResult.class));

                }catch (Exception e){
                    throw TDeserializeException.create(TSerializeMCode.DESERIALIZE_EXCEPTION, adapterClass, TGenericResponse.class, e);
                }
            }finally {
                if (objectMapper != null) objectMapper.release();
            }

            super.deserializeFromString(jsonString, adapterClass);
            return (this);
    }

}
