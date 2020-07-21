package org.skyfw.base.test;


import org.skyfw.base.result.TResult;

public class TResult_Tester {

    public static boolean doTest(){

        //TResult result= TResult.createTResult(TResultCode.SUCCESS, "");
        Long tempLong= 0L;
        Long tempLong2= 123456l;
        //Class clazz= tempLong.getClass();
        /*if (result.tryGetResultObject(tempLong))
            return false;
        else{
            system.out.println("");
        }*/

        //result= TResult.createTResult(TResultCode.SUCCESS, "");
        TResult<Long> result= new TResult<>();//TResult.createTResult(TResultCode.SUCCESS, tempLong2);
        result.setResultObject(tempLong2);
        tempLong= result.getResultObject();
        System.out.println(result.getResultObject());
        if (tempLong == null)
            return false;
        else{
            System.out.println(tempLong + "");
            return true;
        }

    }

}
