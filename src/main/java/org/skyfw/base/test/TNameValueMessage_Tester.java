package org.skyfw.base.test;

import org.skyfw.base.datamodel.TDataSet;
import org.skyfw.base.datamodel.TGenericDataModel;
import org.skyfw.base.exception.TException;
import org.skyfw.base.message.TNameValueMessage;
import org.skyfw.base.serializing.adapters.json.gson.TGsonAdapter;
import org.skyfw.base.test.datamodels.TTestUser;


public class TNameValueMessage_Tester {


    public static boolean doTest(){
        try {
            TNameValueMessage nameValueMessage1 = new TNameValueMessage(true);
            TNameValueMessage nameValueMessage2 = new TNameValueMessage(true);

            TTestUser testUser = new TTestUser();
            testUser.setName("test Name");
            testUser.setFamily("Test Family");

            testUser.set("number", new Integer(100));
            if (testUser.getNumber() != 100)
                return false;

            testUser.set("number", new Double(101));
            if (testUser.getNumber() != 101)
                return false;

            testUser.set("number", new Float(102));
            if (testUser.getNumber() != 102)
                return false;

            testUser.set("number", "103");
            if (testUser.getNumber() != 103)
                return false;

            try {
                testUser.set("number", new TGenericDataModel());
                return false;
            } catch (Exception e){

            }

            nameValueMessage1.set("User", testUser);

            TDataSet dataSet = new TDataSet(new TTestUser[]{testUser});
            nameValueMessage1.set("UserDS", dataSet);


            String s = nameValueMessage1.serializeToString(TGsonAdapter.class);

            nameValueMessage2.deserializeFromString(s, TGsonAdapter.class);

            if (!nameValueMessage1.tryGetValue("User", TTestUser.class).getName().equals
                    (nameValueMessage2.tryGetValue("User", TTestUser.class).getName()))
                return false;

            return true;
        } catch (TException e){
            return false;
        }
    }


}
