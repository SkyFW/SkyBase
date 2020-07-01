package org.skyfw.base.utils;


import com.google.gson.Gson;
import org.skyfw.base.message.TNameValueMessage;
import org.skyfw.base.message.TGenericResponse;

public class TNameValueContentAdapter {


    public String convertNameValueListToJson(TNameValueMessage valueList)
    {

        return new Gson().toJson(valueList, TNameValueMessage.class);

    }


    public String convertNameValueListToJson(TGenericResponse response)
    {

        return new Gson().toJson(response, TGenericResponse.class);

    }


}
