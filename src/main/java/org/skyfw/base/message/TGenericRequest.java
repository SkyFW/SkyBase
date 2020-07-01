package org.skyfw.base.message;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.skyfw.base.exception.TException;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.pool.exception.TObjectPoolException;
import org.skyfw.base.serializing.adapters.TStringSerializerAdapter;
import org.skyfw.base.serializing.adapters.json.gson.TGsonAdapter;
import org.skyfw.base.serializing.adapters.json.jackson.TJacksonObjectMapper;
import org.skyfw.base.serializing.adapters.json.jackson.TJacksonObjectMapperPool;
import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.serializing.exception.TDeserializeException;
import org.skyfw.base.serializing.exception.TSerializeException;

import java.io.IOException;
import java.util.Iterator;

public class TGenericRequest extends TNameValueMessage {
    public String serviceName;
    public String methodName;
    public String securityToken;
    public String userId;



    private TGenericRequest(boolean keepDataTypesInSerializing)
    {
        super(keepDataTypesInSerializing);
    }

    public static TGenericRequest createTRequest(boolean keepDataTypesInSerializing) {
        return new TGenericRequest(keepDataTypesInSerializing);
    }

    public static TGenericRequest createFromString(boolean keepDataTypesInSerializing, String objectString) throws TException {
        TGenericRequest result= createTRequest(keepDataTypesInSerializing);
        result.deserializeFromString(objectString, TGsonAdapter.class);
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




    @Override
    public String serializeToString(Class<? extends TStringSerializerAdapter> adapterClass)
            throws TSerializeException, TObjectPoolException, TIllegalArgumentException {

        String resultJson = "";

        if (!keepDataTypesInSerializing)
            return resultJson;

        resultJson = super.serializeToString(adapterClass);

        //Fast & Safe ;)
        //resultJson= resultJson.replaceFirst("serviceName", "ServiceName");
        //resultJson= resultJson.replaceFirst("methodName", "MethodName");
        resultJson= resultJson.replaceFirst("Values", "Request");

        TJacksonObjectMapper objectMapper= TJacksonObjectMapperPool.getFromPool(1000);
        //ObjectMapper objectMapper= poolMember.objectMapper;
        try {
            try {
                //create JsonNode
                JsonNode rootNode = objectMapper.readTree(resultJson);
                //((ObjectNode) rootNode).remove("serviceName");
                //((ObjectNode) rootNode).remove("methodName");
                ((ObjectNode) rootNode).put("ServiceName", this.serviceName);
                ((ObjectNode) rootNode).put("MethodName", this.methodName);
                ((ObjectNode) rootNode).put("SecurityToken", this.securityToken);

                resultJson = objectMapper.writeValueAsString(rootNode);
            } catch (IOException e) { return ""; }
        }finally {
            if (objectMapper != null) objectMapper.release();
        }

        return resultJson;
    }


    /*@Override
    public TNameValueMessage deserializeFromString(String jsonString, Class<? extends TStringSerializerAdapter> adapterClass, Class<TNameValueMessage> mainClass, Class[] genericParams) throws TException {
        return null;
    }*/

    @Override
    public TNameValueMessage deserializeFromString(String jsonString, Class<? extends TStringSerializerAdapter> adapterClass)
            throws TDeserializeException, TObjectPoolException {

        //Fast & Almost Safe ;)
        //objectString= objectString.replaceFirst("ServiceName", "serviceName");
        //objectString= objectString.replaceFirst("MethodName", "methodName");
        jsonString= jsonString.replaceFirst("Request", "Values");

        //Get a TJacksonObjectMapper From Pool
        TJacksonObjectMapper objectMapper = TJacksonObjectMapperPool.getFromPool(1000);
        try {
            try {
                JsonNode rootNode = objectMapper.readTree(jsonString);
                this.serviceName = ((ObjectNode) rootNode).get("ServiceName").asText("");
                this.methodName = ((ObjectNode) rootNode).get("MethodName").asText("");
                this.securityToken= ((ObjectNode) rootNode).get("SecurityToken").asText("");
            } catch (Exception e) {
                throw TDeserializeException.create(TBaseMCode.BAD_REQUEST, adapterClass, TNameValueMessage.class, e);
            }
        }finally { if (objectMapper != null) objectMapper.release(); }


        return super.deserializeFromString(jsonString, adapterClass);
    }





    public String getServiceName() {
        return serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    public String getSecurityToken() { return securityToken; }

    public void setSecurityToken(String securityToken) { this.securityToken = securityToken; }

    public String getUserId() { return userId; }

    public void setUserId(String userId) { this.userId = userId; }

}
