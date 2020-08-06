package org.skyfw.base.test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.skyfw.base.collection.TCollection;
import org.skyfw.base.datamodel.*;
import org.skyfw.base.exception.TException;
import org.skyfw.base.init.TBaseInitiator;
import org.skyfw.base.log.TLogger;
import org.skyfw.base.serializing.TGenericType;
import org.skyfw.base.security.TDataAccessType;
import org.skyfw.base.serializing.TSerializable;
import org.skyfw.base.serializing.TSerializer;
import org.skyfw.base.serializing.adapters.json.gson.TGsonAdapter;
import org.skyfw.base.service.TService;
import org.skyfw.base.service.method.TServiceMethod;
import org.skyfw.base.system.classloader.TClassLoader;
import org.skyfw.base.system.file.TFileUtils;
import org.skyfw.base.system.TSystemUtils;
import org.skyfw.base.system.terminal.TTerminal;
import org.skyfw.base.system.terminal.TTerminalTextColor;
import org.skyfw.base.test.datamodels.TTestUser;
import org.skyfw.base.serializing.adapters.json.gson.TGsonUtils;
import org.skyfw.base.system.reflection.TInterfaceProxy;

import java.io.File;
import java.lang.reflect.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;


// >>> Test 
public class TTest {
    static TLogger logger;


    public static boolean loadJarToDefaultClassLoader(File jarFile) {



        try {
            if (!jarFile.exists())
                return false;

            URL url = jarFile.toURI().toURL();

            URLClassLoader classLoader = null;
            if (ClassLoader.getSystemClassLoader() instanceof URLClassLoader)
                classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
            else
                return false;

            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, url);
            return true;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }

    }


    public static void main(String[] args) {
        logger = TLogger.getLogger();



        try {

            logger.traceBegin("Testing TObjectSerializer...");
            if (TSerializer_UnitTest.doTest())
                logger.trace("Testing TObjectSerializer Finished Successfully");
            else
                logger.fatal("Testing TObjectSerializer Failed");


            TDataSet_Test.doTest();
            TThreadPool_Tester.doTest();

            TResponseMetaData_Tester.doTest();


            //TLogger.defaultLogFilePrinter= new THtmlLogPrinter(...);

          /*
            TDataModelInitiator.init(TDataModelIndexParamInitException.class);

            TDataModelIndexParamInitException exception=
                new TDataModelIndexParamInitException(TDataModelMCodes.INDEX_PARAM_FIELD_NOT_FOUND
                        , TMCodeSeverity.FATAL, null, "clazz", "fieldName");
            TDataModelHelper.getDataModelDescriptor(exception);*/



            TBaseInitiator.init();


            /*TResult result = new TResult(TBaseMCode.LOCAL_ARGS_CHECK_FAILED, TMCodeSeverity.ERROR);
            result.get(new HashMap<>());
            //responseMetaData.setMCode(TBaseMCode.LOCAL_INTERNAL_ERROR);
            result.getDetails().put("Count", 999);
            result.getDetails().put("Name", "dfgdfg");
            String json = responseMetaData.serializeToString(TGsonAdapter.class);

            TServiceResponse responseMetaData2=
                    TSerializer.deserializeFromString(json, TGsonAdapter.class, TServiceResponse.class);
            System.out.println(responseMetaData2);*/


        }catch (TException e){
            e.log();
        }






        Object obj= new Object();
        String str= new String();
        Long l= new Long(0);
        Byte b= new Byte((byte)1);
        obj= b;
        "fghfhg".equals(new Long(5));

        HashMap<String, Object> hashMap= new HashMap<>();
        hashMap.put("123", "---");
        hashMap.get("");



        try {
            TTestClass testClass = new TTestClass();
            testClass.field = new HashMap<>();
            testClass.field.put("Key1", (byte) 255);

            testClass.uFields = new HashMap<>();
            testClass.uFields.put("key2", (long) 9999);


            TGenericDataModel genericDataModel = new TGenericDataModel();
            genericDataModel.put("Key1", (byte) 127);

            String json = null;
            String json2 = null;
            Gson gson = new Gson();
            json = gson.toJson(testClass);
            json = TSerializer.serializeToString(testClass, TGsonAdapter.class);
            json2 = TSerializer.serializeToString(genericDataModel, TGsonAdapter.class);

            TTestClass testClass2;
            TGenericDataModel genericDataModel2;

            //Type type = new TypeToken<HashMap<Integer, Employee>>(){}.getType();
            //Type type = new TypeToken<TTestClass>(){}.getType();
            Type type = TypeToken.getParameterized(TTestClass.class, new Class[]{}).getType();
            testClass2 = gson.fromJson(json, type);


            //testClass2 = TSerializer.deserializeFromString(json, TGsonAdapter.class, TTestClass.class);

            genericDataModel2 = TSerializer.deserializeFromString(json2, TGsonAdapter.class, TGenericDataModel.class);

            System.out.println(testClass2.field);


            try {
                TGenericDataModel eventDetails = new TGenericDataModel();
                eventDetails.set("k1", new Long(-999999999999999999l));
                eventDetails.set("k2", "V2");
                eventDetails.set("k3", new Double(555.555));
                json = TSerializer.serializeToString(eventDetails, TGsonAdapter.class);

                TDataModel eventDetails2 =
                        TSerializer.deserializeFromString(json, TGsonAdapter.class, TGenericDataModel.class);

                System.out.println(eventDetails2);

            } catch (TException e) {

            }







            TService service = new TService() {
                @Override
                public String getServiceName() {
                    return "test";
                }
            };
            System.out.println(service.getFullPath());


            TJsonUtils_Tester.doTest();

            int a = "dfgdgdgdg".indexOf("{");

            TInterfaceProxy.main(null);




        /*try {
            TTestResponseDataModel testResponseDataModel = new TTestResponseDataModel();
            TTestUser[] testUsers = new TTestUser[2];

            testUsers[0] = new TTestUser();
            testUsers[0].setFamily("TestFamilly 1");

            testUsers[1] = new TTestUser();
            testUsers[1].setFamily("TestFamilly 2");

            testResponseDataModel.setUsers(new TDataSet<TTestUser>(testUsers));

            testResponseDataModel.setName("test Name");


            String jsonString = null;
            jsonString = TSerializer.serializeToString(testResponseDataModel, TGsonAdapter.class);

            //LinkedList<TTestUser> testUserLinkedList= new LinkedList<TTestUser>(Arrays.asList(testUsers));
            //type = TGsonUtils.getTypeTokenByClass(TTestResponseDataModel.class);
            //jsonString = gson.toJson(testUserLinkedList);


            TTestResponseDataModel testResponseDataModel2 = TSerializer.deserializeFromString(jsonString
                    , TGsonAdapter.class, TTestResponseDataModel.class);
            System.out.println(testResponseDataModel2.getName());

            //TTestUser testUserForSearch= new TTestUser();
            //testUserForSearch.setFamily("TestFamilly 2");
            TDataSet searchResultDataSet = testResponseDataModel2.getUsers().search("Family", "TestFamilly 2");
            System.out.println(searchResultDataSet.size());


        } catch (Exception e) {
            System.out.println("exception: " + e.toString());
        }*/




            String s = TFileUtils.convertSeparatorsToSystem("C:\\test\\aaa\\bbb/ccc/ddd/eee\\fff");

            TGenericType genericType = new TGenericType("HashMap<java.lang.String, TTestUser>");

            System.out.println(">>>>" + TSystemUtils.getExecPath());

            TTestUser testUser = new TTestUser();
            testUser.setFamily("test Family");
            String jsonString = null;

            try {
                gson = new Gson();
                type = TGsonUtils.getTypeTokenByClass(testUser.getClass());
                //String jsonString = gson.toJson(testUser, type);
                jsonString = gson.toJson(testUser);
            } catch (Exception e) {
                System.out.println("exception: " + e.toString());
            }


            //Type objectType = TypeToken.getParameterized(classes[0], types).getSeverity();

            ObjectMapper mapper = new ObjectMapper();
            //JavaType type = mapper.getTypeFactory().constructGeneralizedType() constructParametrizedType(Data.class, Data.class, Parameter.class);
            //JavaType type = mapper.getTypeFactory().constructParametricType(Data.class, Data.class, Parameter.class);
            //JavaType type = mapper.getTypeFactory().constructParametrizedType(Data.class, Data.class, Parameter.class);
            //JavaType type = mapper.getTypeFactory().constructFromCanonical("TDataSet<java.util.HashMap<java.lang.String, TTestUser>>");
            type = mapper.getTypeFactory().constructFromCanonical("org.skyfw.base.test.datamodels.TTestUser");
            //Data<Parameter> dataParam = mapper.readValue(jsonString,type)


            //new com.google.common.reflect.TypeToken<TTestUser>(){}.


            //Type objectType = new TType();
            TypeToken objectType = TypeToken.getParameterized(TTestUser.class);
            TypeToken typeToken1 = TypeToken.getParameterized(HashMap.class, String.class, TTestUser.class);
            TypeToken typeToken = TypeToken.getParameterized(TDataSet.class, typeToken1.getType());


            //ParameterizedTypeImpl parameterizedType= new ParameterizedTypeImpl();

            ParameterizedType parameterizedType = new ParameterizedType() {
                @Override
                public Type[] getActualTypeArguments() {
                    return new TType[1];//Type[0];
                }

                @Override
                public Type getRawType() {
                    return null;
                }

                @Override
                public Type getOwnerType() {
                    return null;
                }
            };

            GenericArrayType genericArrayType = new GenericArrayType() {
                @Override
                public Type getGenericComponentType() {
                    return null;
                }
            };


            try {
                gson = new GsonBuilder().create();
                //Object obj = gson.fromJson(jsonString, type.get);
                //system.out.println(obj);

            } catch (Exception e) {
                logger.fatal("exception In: gson.fromJson", e);
                System.out.println("exception: " + e.toString());
            }


            Type token =
                    new com.google.gson.reflect.TypeToken<LinkedList<TDataModel>>() {
                    }.getType();


            System.out.println(TSystemUtils.getExecPath());






        /*
        system.out.println("fghfghfhfhfgh");
        system.out.println("fghfghfhfhfgh");
        logger.infoBegin("Start Loading Any Thing !!!");
        system.out.println("sdfsdfsdfsdfsf");



        try{
            TimeUnit.SECONDS.sleep(1);
            //system.in.read();
        }catch (exception e){

        }

        logger.info("AnyThing Loaded !!!");

        system.out.println("fghfghfhfhfgh");
        system.out.println("fghfghfhfhfgh");
        logger.infoBegin("Start UnLoading Any Thing !!!");

        try{
            TimeUnit.SECONDS.sleep(1);
            //system.in.read();
        }catch (exception e){

        }
        */


            File folder = new File("libs");
            File[] listOfFiles = folder.listFiles();

            if (listOfFiles != null)
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        loadJarToDefaultClassLoader(listOfFiles[i]);
                        System.out.println("Jar file Loaded" + listOfFiles[i].getName());
                    } else if (listOfFiles[i].isDirectory()) {
                        System.out.println("Directory " + listOfFiles[i].getName());
                    }
                }

            EnumSet<TDataAccessType> dataAccessType = EnumSet.of(TDataAccessType.CREATE_ACCESS, TDataAccessType.DELETE_ACCESS);


            System.out.println("**********************************************");
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException ex) {
            }


            TTerminal.setTextColor(TTerminalTextColor.CYAN_BOLD);
            //system.out.println("test .................. Text");


            File file = new File("C:\\Users\\Admin\\Desktop\\Git Projects\\ElectionManager_Service\\ElectionManager_DataModels\\target\\ElectionManager_DataModels-1.01.01-SNAPSHOT.jar");
            loadJarToDefaultClassLoader(file);
            Class clazz = null;
            try {
                clazz = Class.forName("");
            } catch (Exception e) {
            }

            if (clazz != null)
                System.out.println(clazz.getName());


            clazz = TClassLoader.loadByName("TDataModel");





            System.out.println("Starting TDataModel_Tester");

            TDataModel_Tester.doTest();
            logger.info("TDataModel tests was successful");



            TLogger_Tester.doTest();

            TResult_Tester.doTest();

            TObjectPool_Tester.doTest();


            TNameValueMessage_Tester.doTest();


            TRequestResponse_Tester.doTest();


            TDataSet_Test.doTest();

            TCollection<String, String> testCollection = new TCollection<String, String>();
            testCollection.put("TestKey1", "TestValue1");
            testCollection.put("TestKey2", "TestValue2");


            while (testCollection.next()) {
                System.out.println(testCollection.getCurrentKey());
                System.out.println(testCollection.getCurrentValue());
            }

            testCollection.forEach((key, value) -> {
                System.out.println("Key: " + key + "\t" + " Value: " + value);
            });

                /*new BiConsumer() {
            @Override
            public void accept(Object o, Object o2) {
                system.out.println(o.toString());
                system.out.println(o2.toString());
            }
        });*/

            try {

                throw new Throwable();

            } catch (Throwable e) {

            }

            logger.info("test finished.");

        } catch (TException e) {
            e.log();
            logger.fatal("SkyBase functionality test failed...");
        }
    }

    public static class TType implements Type {

        @Override
        public String getTypeName() {
            return "TTestUser";
        }

        @Override
        public String toString() {
            return "TTestUser";
        }
    }


    public static class TTestClass implements TSerializable<TTestClass> {

        public HashMap<String, Byte> field;
        public HashMap<String, Object> uFields;

    }

}

