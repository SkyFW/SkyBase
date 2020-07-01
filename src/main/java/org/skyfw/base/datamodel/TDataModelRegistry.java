package org.skyfw.base.datamodel;

import org.skyfw.base.log.TLogger;
import org.skyfw.base.result.TResult;
import org.skyfw.base.result.TResultBuilder;
import org.skyfw.base.mcodes.TBaseMCode;

import java.util.HashMap;


public class TDataModelRegistry {
    private static TLogger logger= TLogger.getLogger();

    //Global Static Data Models Descriptor
    private static HashMap<String, TDataModelDescriptor> dataModelsDescriptors = new HashMap<String, TDataModelDescriptor>();


    public static boolean containsKey(String key){
        return TDataModelRegistry.dataModelsDescriptors.containsKey(key);
    }


    public static TDataModelDescriptor get(String dataModelClassName, boolean addIfNotExists)
        /*throws*/ {

        if (dataModelsDescriptors == null){
            logger.fatal("dataModelsDescriptors Is Null !");
            return null;
        }

        if (dataModelsDescriptors.containsKey(dataModelClassName))
            return dataModelsDescriptors.get(dataModelClassName);
        else if (addIfNotExists) {
            TDataModelDescriptor dataModelDescriptor= new TDataModelDescriptor();
            dataModelsDescriptors.put(dataModelClassName, dataModelDescriptor);
            return dataModelDescriptor;
        }

        return null;
    }





    public static TDataModelDescriptor put(String key, TDataModelDescriptor dataModelDescriptor){
        if (dataModelsDescriptors != null) {
            return dataModelsDescriptors.put(key, dataModelDescriptor);
        }

        return null;
    }

    public static boolean addDataModelDescriptor(String dataModelClassName, TDataModelDescriptor dataModelDescriptor){
        if (dataModelsDescriptors != null)
            //if (dataModelsDescriptors.containsKey(dataModelClassName))
            dataModelsDescriptors.put(dataModelClassName, dataModelDescriptor);

        return true;
    }



    public static TResult addNewFieldDescriptor(String className, String fieldName, TFieldDescriptor fieldDescriptor){

        //Get The dataModelDescriptor Or Create a New One If Not Exists
        try {
            TDataModelDescriptor dataModelDescriptor = TDataModelRegistry.get(className, true);

            //Convert Field Name Casing To Upper Case
            dataModelDescriptor.fields.put(fieldName, fieldDescriptor);
        }catch (Exception e){TResult.createTResult(TBaseMCode.BAD_REQUEST, e.toString());}

        return TResultBuilder.getTResultBuilder().setResultCode(TBaseMCode.SUCCESS).createTResult();
    }


    public static TFieldDescriptor getFieldDescriptor(String className, String fieldName) {

        try {
            TDataModelDescriptor dataModelDescriptor= TDataModelRegistry.get(className, false);
            if (dataModelDescriptor == null){
                System.out.println("datamodel Not Exists In DataModelsDescriptorsContainer: " + className);
                return null;
            }

            if (dataModelDescriptor.fields.containsKey(fieldName))
                return dataModelDescriptor.fields.get(fieldName);

            return null;
        }catch (Exception e){
            return null;
        }
    }


    public static boolean isFieldExists(String className, String fieldName){

        if ( ! containsKey(className))
            return false;

        return get(className, false).fields.containsKey(fieldName);
    }

}
