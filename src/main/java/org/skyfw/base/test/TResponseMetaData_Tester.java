package org.skyfw.base.test;

import org.skyfw.base.exception.TException;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.serializing.adapters.json.gson.TGsonAdapter;
import org.skyfw.base.service.TResponseMetaData;

public class TResponseMetaData_Tester {

    public static void doTest() throws TException {

        try {
            throw  new NullPointerException("Var 1 is null !");
        }catch (Throwable e){

            TIllegalArgumentException illegalArgumentException= new TIllegalArgumentException(
                    TBaseMCode.BAD_ARGUMENT, TMCodeSeverity.FATAL, e, "var1");

            TResponseMetaData responseMetaData= new TResponseMetaData(illegalArgumentException);
            String json= responseMetaData.serializeToString(TGsonAdapter.class);
            responseMetaData.deserializeFromString(json, TGsonAdapter.class);
        }



    }

}