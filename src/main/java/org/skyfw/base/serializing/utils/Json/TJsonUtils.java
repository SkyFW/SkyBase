package org.skyfw.base.serializing.utils.Json;


public class TJsonUtils {

    public static String merge(String json1, String json2){

        if ( (json1 == null) || (json1.isEmpty()) )
            return json2;

        if ( (json2 == null) || (json2.isEmpty()) )
            return json1;

        try {
            String j1Contents = json1;
            j1Contents = json1.substring(j1Contents.indexOf("{") + 1, j1Contents.lastIndexOf("}")).trim();
            String j2Contents = json2;
            j2Contents = json2.substring(j2Contents.indexOf("{") + 1, j2Contents.lastIndexOf("}")).trim();

            StringBuilder resultSB = new StringBuilder();
            resultSB.append("{ ");
            resultSB.append(j1Contents);

            if ((!j1Contents.isEmpty()) && (!j2Contents.isEmpty()))
                resultSB.append("\n , \n");

            resultSB.append(j2Contents);
            resultSB.append(" }");

            return resultSB.toString();

        }catch (Exception e){
            return "";
        }

    }


}
