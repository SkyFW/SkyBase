package org.skyfw.base.test;

import org.skyfw.base.exception.TException;
import org.skyfw.base.serializing.adapters.json.gson.TGsonAdapter;
import org.skyfw.base.test.datamodels.TTestUser;

import java.io.File;

public class TSerializable_Tester {

    public static boolean doTest(){

        TTestUser testUser= new TTestUser();
        testUser.setName("test Name");
        testUser.setFamily("test Family");

        //testUser.deserializeFromString()


        File file= new File("TestJsonFile.txt");
        try {
            testUser.serializeToTextFile(file, TGsonAdapter.class);
        }catch (TException e){

        }

        return true;

    }
}
