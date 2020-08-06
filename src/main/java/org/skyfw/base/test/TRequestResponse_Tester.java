package org.skyfw.base.test;

import org.skyfw.base.exception.TException;
import org.skyfw.base.log.TLogger;
import org.skyfw.base.message.TGenericRequest;
import org.skyfw.base.message.TGenericResponse;
import org.skyfw.base.result.TResult;
import org.skyfw.base.result.TResultBuilder;
import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.serializing.adapters.json.gson.TGsonAdapter;

import java.io.*;

public class TRequestResponse_Tester {
    static TLogger logger= TLogger.getLogger();

    public static boolean doTest(){
        boolean failed= false;

        logger.infoBegin("Testing TGenericRequest ...");
        if (TRequestResponse_Tester.testTRequest()){
            logger.info("TGenericRequest test Was Successful");
        }
        else{
            failed= true;
            logger.fatal("TGenericRequest test Failed");
        }

        logger.infoBegin("Testing TGenericResponse ...");
        if (TRequestResponse_Tester.testTResponse()){
            logger.info("TGenericResponse test Was Successful");
        }
        else{
            failed= true;
            logger.fatal("TGenericResponse test Failed");
        }

        return !failed;
    }


    public static boolean testTRequest(){

        try {

            TGenericRequest request1 = new TGenericRequest(true);
            TGenericRequest request2 = null;

            TResult result = TResultBuilder.getTResultBuilder().setResultCode(TBaseMCode.SUCCESS).createTResult();

            request1.setKeepDataTypesInSerializing(true);

        /*
        TTestSimpleUser simpleUser= new TTestSimpleUser();
        request1.setValue("user", simpleUser);
        request1.setValue("result", result);
        request1.setValue("userName", "user1");
        request1.setValue("userCode", "ertertertert");
        request1.setSecurityToken("SSSSSSSSSSSSSSSSSSSSSS");
        system.out.println(request1.getValue("userCode").toString());
        system.out.println(request1.getValues().next());
        */



            request1.set("TestName", "TestValue");

            String json = null;

            json = request1.serializeToString(TGsonAdapter.class);

            // req.getSerializer().serializeToString(TStringSerializerLib.GSON_JSON_ENCODER);
            //system.out.println(json);



            return testDeserializingUnknownDataTypes(json);

        } catch (Exception e){

            return false;
        }

        //

        //getDeserializer().deserializeFromString(json, TStringSerializerLib.GSON_JSON_ENCODER);
    }

    //☢ ☠ ☢ ☠ ☢ ☠ ☢ ☠ ☢ ☠ ☢ ☠ ☢ ☠ ☢ ☠ ☢ ☠ ☢ ☠ ☢ ☠
    //test Deserializing Unknown Data Types 🔫 🥆
    //------------------------------------------------------------------------------------------------------------------
    public static boolean testDeserializingUnknownDataTypes(String json){
        try {
            TResult result;
            TGenericRequest request2 = new TGenericRequest(true);

            json.replace("String", "XString");
            // >>> Invalidating the existing JSON
            String jsonWithUnknownTypes= json.replace("java.lang.String", "java.lang.XString");

            request2.deserializeFromString(jsonWithUnknownTypes, TGsonAdapter.class);

            return false;

        } catch (TException e){

            if (e.getmCode().getBaseCode().equals(TBaseMCode.UNKNOWN_DATATYPES))
                return true;
            else
                return false;
        }
    }

    

    public static boolean testTResponse (){
        try {
            TGenericResponse res1 = TGenericResponse.createTResponse(true);

            TResult result = TResultBuilder.getTResultBuilder().setResultCode(TBaseMCode.AUTHENTICATION_FAILED).createTResult();
            res1.set("userName", "user1");
            res1.set("userCode", "ertertertert");

            res1.setKeepDataTypesInSerializing(true);
            //res1.setServiceName("DataStore_Service");
            //res1.setMethodName("Update");
            String json = res1.serializeToString(TGsonAdapter.class);
            // req.getSerializer().serializeToString(TStringSerializerLib.GSON_JSON_ENCODER);
            //system.out.println(json);

            TGenericResponse res2 = new TGenericResponse(true);
            //res2.setDataTypes(res1.getDataTypes());
            res2.deserializeFromString(json, TGsonAdapter.class);

            return true;
        }catch (TException e){
            return false;
        }

    }


    public static String getFileContent(
            FileInputStream fis,
            String          encoding ) throws IOException
    {
        try( BufferedReader br =
                     new BufferedReader( new InputStreamReader(fis, encoding )))
        {
            StringBuilder sb = new StringBuilder();
            String line;
            while(( line = br.readLine()) != null ) {
                sb.append( line );
                sb.append( '\n' );
            }
            return sb.toString();
        }
    }


}
