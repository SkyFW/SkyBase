package org.skyfw.base.test;

import org.skyfw.base.datamodel.*;
import org.skyfw.base.datamodel.exception.TDataModelTestingException;
import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.serializing.TSerializer;
import org.skyfw.base.serializing.adapters.json.gson.TGsonAdapter;
import org.skyfw.base.test.datamodels.*;

import java.io.File;

public class TDataModel_Tester {

    public static void doTest() throws TException{

        testTDataModel();

    }



    public static void testTDataModel() throws TException {

        String json = null;

        //Testing Generic datamodel
        TGenericDataModel gdataDataModel = new TGenericDataModel();
        gdataDataModel.set("name", "Alex");
        gdataDataModel.set("number", 123456);
        gdataDataModel.set("userId", "220");

        try {
            json = TSerializer.serializeToString(gdataDataModel, TGsonAdapter.class);
            gdataDataModel = TSerializer.deserializeFromString(json, TGsonAdapter.class, TGenericDataModel.class);
        } catch (TException e) {

        }


        TTestUser user1 = new TTestUser();
        TTestUser user2 = null;
        user1.set("userType", "MODERATOR_USER");

        // >>> Setting ENUM Field With String Value
        user1.loadFrom(gdataDataModel);

        try {
            json = TSerializer.serializeToString(user1, TGsonAdapter.class);
            user2 = TSerializer.deserializeFromString(json, TGsonAdapter.class, TTestUser.class);
            System.out.println(user2);
        } catch (Exception e) {

        }


        TTestFullUser fullUser = new TTestFullUser();
        fullUser.setUserType(TTestUserType.ADMIN_USER);
        System.out.println(fullUser.get("userType"));

        File file = new File("AnotherJsonFile.txt");
        try {
            user1.serializeToTextFile(file, TGsonAdapter.class);
        } catch (Exception e) {

        }

        //TResult<TTestUser> loadFromTextFile_Result= TDataModel.loadFromTextFile(file);


        //user1.getDataModelDescriptor().fields.get("").);
        System.out.println(user1.toString());


        user1.setUserId("user1");
        if (!user1.getKeyFieldValue().equals("user1"))
            throw TDataModelTestingException.create(TDataModelTestMCodes.GET_KEY_FIELD_VALUE_FAILED
                    , TMCodeSeverity.FATAL, null, user1.getClass().getName());


        user1.set("name", "TestName1");
        user1.set("family", "TestFamily1");
        if (user1.getName() != user1.getFieldValueAsString("name"))
            throw TDataModelTestingException.create(TDataModelTestMCodes.GET_FIELD_VALUE_AS_STRING_FAILED
                    , TMCodeSeverity.FATAL, null, user1.getClass().getName());


        String jsonString =
                user1.serializeToString(TGsonAdapter.class);
        user2 =
                (new TTestUser()).deserializeFromString(jsonString, TGsonAdapter.class);

        if (!user1.getName().equals(user2.getName()))
            throw TDataModelTestingException.create(TDataModelTestMCodes.DESERIALIZE_FROM_STRING_FAILED
                    , TMCodeSeverity.FATAL, null, user1.getClass().getName());







        // >>> Testing Extended datamodel
        TTestFullUser testFullUser = new TTestFullUser();
        testFullUser.setFamily("test Family");

        if (!testFullUser.getFamily().equals(testFullUser.get("family")))
            throw TDataModelTestingException.create(TDataModelTestMCodes.SET_FIELD_VALUE_FAILED
                    , TMCodeSeverity.FATAL, null, user1.getClass().getName());

        TTestJob job1 = new TTestJob();
        job1.set("jobName", "TestJob1Name");
        if (job1.getJobName() == job1.getFieldValueAsString("jobName")) {
            System.out.println(job1.getJobName());
        }

        // >>> Printing data model
        TDataModelPrinter.print(user1);


    }



}
