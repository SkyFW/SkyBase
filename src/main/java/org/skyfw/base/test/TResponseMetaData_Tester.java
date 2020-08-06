package org.skyfw.base.test;

import org.skyfw.base.exception.TException;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.mcodes.TBaseMCode;

public class TResponseMetaData_Tester {

    public static void doTest() throws TException {

        try {
            throw  new NullPointerException("Var 1 is null !");
        }catch (Throwable e){

            TIllegalArgumentException illegalArgumentException= new TIllegalArgumentException(
                    TBaseMCode.LOCAL_ARGS_CHECK_FAILED, "var1");

           /* TServiceResponse responseMetaData= new TServiceResponse(illegalArgumentException);
            String json= responseMetaData.serializeToString(TGsonAdapter.class);
            responseMetaData.deserializeFromString(json, TGsonAdapter.class);*/
        }



    }

}