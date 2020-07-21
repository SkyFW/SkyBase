package org.skyfw.base.message;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.reflect.TypeToken;


import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.datamodel.TDataSet;
import org.skyfw.base.exception.TException;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.log.TLogger;
import org.skyfw.base.pool.exception.TObjectPoolException;
import org.skyfw.base.serializing.TSerializeMCode;
import org.skyfw.base.serializing.TSerializer;
import org.skyfw.base.serializing.adapters.TStringSerializerAdapter;
import org.skyfw.base.serializing.adapters.json.gson.TGsonAdapter;
import org.skyfw.base.serializing.adapters.json.jackson.TJacksonObjectMapper;
import org.skyfw.base.serializing.adapters.json.jackson.TJacksonObjectMapperPool;
import org.skyfw.base.serializing.TSerializable;
import org.skyfw.base.serializing.exception.TDeserializeException;
import org.skyfw.base.serializing.exception.TSerializeException;
import org.skyfw.base.system.classloader.TClassLoader;
import org.skyfw.base.serializing.adapters.json.gson.TGsonUtils;
import org.skyfw.base.mcodes.TBaseMCode;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.*;


public class TNameValueMessage implements Serializable, TSerializable<TNameValueMessage> {

    TLogger logger= TLogger.getLogger();

    //public HashMap<String, TJsonObjectContainer> values;
    public HashMap<String, Object> values;

    //Java Native Serialization Do Not Need Keeping "dataTypes" As Data Because It Will Save This As MetaData
    //In Fact The original class and package names are encoded along with the data
    //and the incoming value is constructed as that class, so, to avoid a class cast exception,
    // what you cast it to must be identical
    private HashMap<String, String> dataTypes;


    public TNameValueMessage(boolean keepDataTypesInSerializing) {
        this.keepDataTypesInSerializing = keepDataTypesInSerializing;

        values = new HashMap<String, Object>();

        if (keepDataTypesInSerializing)
            dataTypes = new HashMap<String, String>();
    }



    protected boolean keepDataTypesInSerializing;

    public void setValue(String name, Object obj) {

        //TJsonObjectContainer jsonObjectContainer = new TJsonObjectContainer();
        //jsonObjectContainer.className = obj.getClass().getName();
        //jsonObjectContainer.jsonObj = new Gson().toJson(obj);
        //values.put(name, jsonObjectContainer);
        values.put(name, obj);

        String className;
        if (keepDataTypesInSerializing){
            if (obj.getClass().equals(TDataSet.class)) {
                Class<? extends TDataModel> genericClass= ((TDataSet) obj).getDataModelClass();
                String genericClassName= "";
                if (genericClass != null)
                    genericClassName= genericClass.getName();
                className = obj.getClass().getName() + "<" + genericClassName + ">";
            }
            else
                className= obj.getClass().getName();
            dataTypes.put(name, className);
        }
    }


    public Object getValue(String name) {
        if (values.containsKey(name)) {
          //  TJsonObjectContainer jsonObjectContainer = values.get(name);
            try {

                return this.values.get(name);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else
            System.out.println("");//TLogger.warn();


        return null;
    }

    public <T> T tryGetValue(String name, Class<T> type) {
        
        try {
            return type.cast(this.getValue(name));
        } catch (Exception e){
            return null;
        }
    }




    public boolean getKeepMetaDataInSerializing() {
        return keepDataTypesInSerializing;
    }

    public void setkeepDataTypesInSerializing(boolean keepDataTypesInSerializing) {
        this.keepDataTypesInSerializing = keepDataTypesInSerializing;
    }

    public HashMap<String, String> getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(HashMap<String, String> dataTypes) {
        this.dataTypes = dataTypes;
    }


    public void removeValue(String name, Object obj) {


    }







    public String serializeToString(Class<? extends TStringSerializerAdapter> adapterClass)
            throws TSerializeException, TObjectPoolException, TIllegalArgumentException {
        //if (!keepDataTypesInSerializing)
        //    return json;

        String jsonObject = "";

        //byte[] jsonData = Files.readAllBytes(Paths.get("employee.txt"));

        TJacksonObjectMapper objectMapper= TJacksonObjectMapperPool.getFromPool(1000);
        try {

            try {
                //create JsonNode
                //String catJson = objectMapper.writeValueAsString(this.values);
                //JsonNode rootNode = objectMapper.valueToTree(this);
                JsonNode rootNode = objectMapper.createObjectNode();
                //
                //((ObjectNode) rootNode).fi
                ((ObjectNode) rootNode).putPOJO("Values", this.values);
                //
                ((ObjectNode) rootNode).putPOJO("DataTypes", this.dataTypes);
                //Logically Is Not Necessary To Remove Because It Not Exist !!!
                //((ObjectNode) rootNode).remove("keepDataTypesInSerializing");

                //JsonNode dataTypesNode = objectMapper.valueToTree( this.dataTypes);
                //RawValue rawDataTypes = new RawValue();
                //((ObjectNode) rootNode).putRawValue("DataTypes", dataTypesNode.toString());

                jsonObject= objectMapper.writeValueAsString(rootNode);
            }
            catch (Exception e){
                return "";
            }


        //Releasing The pool Member
        } finally { if (objectMapper != null) objectMapper.release(); }




        return jsonObject;
    }

    public TNameValueMessage deserializeFromString(String jsonString
            , Class<? extends TStringSerializerAdapter> adapterClass) throws TDeserializeException, TObjectPoolException {

        //Var
        LinkedList<String> undefinedClasses = new LinkedList<String>();

        //Clear Previous Values If Exists
        this.values.clear();

        //Get a TJacksonObjectMapper From Pool
        TJacksonObjectMapper objectMapper = TJacksonObjectMapperPool.getFromPool(1000);
        try {
            try {
                JsonNode rootNode = objectMapper.readTree(jsonString);

                //ToDo: Can Cause security Issue ???
                JsonNode dataTypesNode = rootNode.path("DataTypes");
                this.dataTypes = objectMapper.treeToValue(dataTypesNode, this.dataTypes.getClass());

                JsonNode valuesNode = rootNode.path("Values");
                Iterator<Map.Entry<String, JsonNode>> fields = valuesNode.fields();
                while (fields.hasNext()) {
                    //ToDo: Why It Is So Complicated ???
                    Map.Entry<String, JsonNode> currentNode = fields.next();
                    String currentKey = currentNode.getKey();
                    JsonNode currentValueJsonNode = currentNode.getValue();

                    Class clazz = null;
                    Class genericClazz = null;
                    try {
                        String className = this.dataTypes.get(currentKey);
                        String genericClassName = null;
                        //Checking For Generic Class Name
                        if (className.contains("<")) {
                            genericClassName = className.substring(className.indexOf("<") + 1, className.indexOf(">"));
                            genericClazz = TClassLoader.loadByName(genericClassName);
                            className = className.substring(0, className.indexOf("<"));
                        }


                        //clazz = Class.forName(this.dataTypes.get(currentKey));
                        //clazz = ClassLoader.getSystemClassLoader().loadClass(this.dataTypes.get(currentKey));
                        clazz = TClassLoader.loadByName(className);
                        if (clazz == null) {
                            //If Class Is Not Available In Class Loader
                            undefinedClasses.add(className);
                            continue;
                        }

                        if ((genericClassName != null) && (genericClazz == null)) {
                            //If Class Is Not Available In Class Loader
                            undefinedClasses.add(genericClassName);
                            continue;
                        }


                    } catch (Exception e) {
                        undefinedClasses.add(this.dataTypes.get(currentKey));
                        continue;
                    }

                    //ToDo: Serious performance issue
                    Object obj = null;
                    //Attention: Neither of this methods wont work if there be no Constructor method...
                    //obj= objectMapper.readValue(valueNode.traverse(), clazz);
                    //obj= objectMapper.treeToValue(valueNode, clazz);
                    //obj= objectMapper.convertValue(valueNode , clazz);
                    String currentValueJsonString = objectMapper.writeValueAsString(currentValueJsonNode);

                    //It Will Do The Magic When Deserializing The Generic Types ;)
                    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                    Type objectType;
                    if (genericClazz != null)
                        objectType = TypeToken.getParameterized(clazz, genericClazz).getType();
                    else
                        objectType = TGsonUtils.getTypeTokenByClass(clazz);

                    try {
                        //obj = gson.fromJson(currentValueJsonString, objectType);
                        if (genericClazz == null)
                            obj= TSerializer.deserializeFromString(currentValueJsonString, TGsonAdapter.class, clazz);
                        else
                            obj= TSerializer.deserializeFromString(currentValueJsonString, TGsonAdapter.class, clazz, genericClazz);
                    } catch (Exception e) {
                        logger.fatal("exception In: gson.fromJson", e);
                    }

                    if (obj != null)
                        this.values.put(currentKey, obj);
                }

            } catch (Exception e) {
                System.out.println(e.toString());
                throw TDeserializeException.create(TSerializeMCode.DESERIALIZE_EXCEPTION, adapterClass,TNameValueMessage.class, e);
            }

        } finally {
            if (objectMapper != null) objectMapper.release();
        }

        //Set The result Based On Undefined baseclasses
        if (undefinedClasses.size() != 0)
            throw TDeserializeException.create(TBaseMCode.UNKNOWN_DATATYPES, adapterClass, TNameValueMessage.class);

        //It's not a valid implementation of TSerializable
        return null;
    }


}
