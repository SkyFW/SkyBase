package org.skyfw.base.test;

import org.skyfw.base.datamodel.TDataSet;
import org.skyfw.base.exception.TException;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.result.TResult;
import org.skyfw.base.serializing.TSerializer;
import org.skyfw.base.serializing.adapters.json.gson.TGsonAdapter;
import org.skyfw.base.service.TPackedResponse;
import org.skyfw.base.service.exception.TServiceException;
import org.skyfw.base.test.datamodels.TTestMessage;
import org.skyfw.base.test.datamodels.TTestMessagesDataSet;

public class TSerializer_UnitTest {

    public static boolean doTest() throws TException{

        TTestMessage testMessage= new TTestMessage();
        testMessage.get("messageText");
        testMessage.setMessageText("test text");
        testMessage.setParams(new String[]{"param1", "param2"});

        String jsonString= TSerializer.serializeToString(testMessage, TGsonAdapter.class);

        TTestMessage testMessage2=
                TSerializer.deserializeFromString(jsonString, TGsonAdapter.class, TTestMessage.class);


        TTestMessagesDataSet messageList1= new TTestMessagesDataSet();
        messageList1.messages= new TDataSet<>();
        messageList1.messages.add(testMessage);

        jsonString= TSerializer.serializeToString(messageList1, TGsonAdapter.class);


        TTestMessagesDataSet messageList2=
                TSerializer.deserializeFromString(jsonString, TGsonAdapter.class, TTestMessagesDataSet.class, TTestMessage.class);


        TResult result= new TResult();
        TIllegalArgumentException exception1= new TIllegalArgumentException(TBaseMCode.NULL_ARGUMENT_IS_NOT_ACCEPTABLE);
        TServiceException exception2= new TServiceException(exception1, "Test services");

        result.exception(exception2);

        jsonString= TSerializer.serializeToString(result, TGsonAdapter.class);
        System.out.println(jsonString);


        TResult result2=
                TSerializer.deserializeFromString(jsonString, TGsonAdapter.class, TResult.class);


        TPackedResponse packedResponse=  new TPackedResponse();
        packedResponse.result= result;
        packedResponse.data= new TTestMessage();
        ((TTestMessage) packedResponse.data).setMessageText("Test message text");

        jsonString= TSerializer.serializeToString(packedResponse, TGsonAdapter.class);

        TPackedResponse packedResponse2=
                TSerializer.deserializeFromString(jsonString, TGsonAdapter.class, TPackedResponse.class, TTestMessage.class);




        return true;

    }
}
