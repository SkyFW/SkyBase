package org.skyfw.base.test;

import org.skyfw.base.serializing.utils.Json.TJsonUtils;

public class TJsonUtils_Tester {

    public static void doTest(){

        String json1= "{ result= {code=200} }";
        String json2= "{UserName= \"Alex\"}";
        String mergedJson= TJsonUtils.merge(json1, json2);

        json1= "{ result= {code=200} }";
        json2= "{ \n \n }";
        mergedJson= TJsonUtils.merge(json1, json2);
        System.out.println(mergedJson);
    }
}
