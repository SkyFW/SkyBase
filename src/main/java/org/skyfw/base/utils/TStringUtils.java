package org.skyfw.base.utils;

import java.io.*;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.skyfw.base.exception.general.TFileWriteException;
import org.skyfw.base.result.TResult;
import org.apache.commons.io.FileUtils;
import org.skyfw.base.mcodes.TBaseMCode;

public class TStringUtils {

    /*
    public static String formatToLineWidth(String s, int maxLineLength){

        List<String> res = new ArrayList<String>();

        Pattern p = Pattern.compile("\\b.{1," + (lineWidth-1) + "}\\b\\W?");
        Matcher m = p.matcher(s);

        while(m.find()) {
            system.out.println(m.group().trim());   // Debug
            res.add(m.group());
        }
        return res.toString();

    }
    */


    /*public static String formatToLineWidth(String s, int maxCharInLine) {
        StringTokenizer tok = new StringTokenizer(s, " ");
        StringBuilder output = new StringBuilder(s.length());
        int lineLen = 0;
        while (tok.hasMoreTokens()) {
            String word = tok.nextToken();

            while(word.length() > maxCharInLine){
                output.append(word.substring(0, maxCharInLine-lineLen) + "\n");
                word = word.substring(maxCharInLine-lineLen);
                lineLen = 0;
            }

            if (lineLen + word.length() > maxCharInLine) {
                output.append("\n");
                lineLen = 0;
            }
            output.append(word + " ");

            lineLen += word.length() + 1;
        }
        // output.split();
        // return output.toString();
        return output.toString();
    }*/



    /*
    public static String formatToLineWidth(String s, int maxCharInLine) {

       return WordUtils.wrap(s, maxCharInLine);
    }
    */



    //📚📚📚📚📚📚📚
    //Note: This Crazy Thing Took Significant Amount Of
    // My Valuable Time To Fix, While I Was In Pressure To Get Ready For PhD Interview :/
    // The Problem Was That None Of Codes On The Internet Could not Handle The Custom NewLines
    // Inserted To The Main String, Even apache.commons Library  ಠ_ಠ
    public static String formatToLineWidth(String s, int maxCharInLine) {

        //Var
        //~~~~~~~~~~
        //There Is 2 Delimiters: Empty Space And NewLine Character
        StringTokenizer stringTokenizer = new StringTokenizer(s," \n,", true);
        StringBuilder currLineSB = new StringBuilder();
        StringBuilder mainSB= new StringBuilder();
        //It Check If There Is Currently a NewLine Character At The End Of mainSB
        boolean newLineExist= false;

        while (stringTokenizer.hasMoreTokens()) {
            String word = stringTokenizer.nextToken();

            if (word.equals(" "))
                continue;

            if (word.equals("\n")){
                mainSB.append(currLineSB.toString()).append("\n");
                currLineSB = new StringBuilder();
                newLineExist= true;
                continue;
            }


            boolean wordPut=false;
            while (!wordPut) {
                //If The Word Exactly Fits -> dont add the space
                if(currLineSB.length()+word.length()==maxCharInLine) {
                    currLineSB.append(word);
                    wordPut=true;
                }
                //whole word can be put
                else if(currLineSB.length()+word.length()<=maxCharInLine) {
                    if(stringTokenizer.hasMoreTokens()) {
                        currLineSB.append(word + " ");
                    }else{
                        currLineSB.append(word);
                    }
                    wordPut=true;
                }else{
                    if(word.length()>maxCharInLine) {
                        int lineLengthLeft = maxCharInLine - currLineSB.length();
                        String firstWordPart = word.substring(0, lineLengthLeft);
                        currLineSB.append(firstWordPart);
                        //lines.add(currLine.toString());
                        word = word.substring(lineLengthLeft);
                        //currLine = new StringBuilder();
                    }


                    if(currLineSB.length()>0) { //add whats left
                        if ((mainSB.length() > 0) && (!newLineExist))
                            mainSB.append("\n");
                        mainSB.append(currLineSB.toString());
                        newLineExist= false;
                    }
                    //currLineSB = new StringBuilder();
                    currLineSB.setLength(0);

                }

            }
            //
        }//End Of: Exploring All Tokens

        //add whats left
        if(currLineSB.length()>0) {
            if ((mainSB.length() > 0) && (!newLineExist))
                mainSB.append("\n");
            mainSB.append(currLineSB.toString());
        }

        return mainSB.toString();
    }


    public static String loadStringFromInputStream(InputStream inputStream){
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                result.write(buffer, 0, length);
            }
            // StandardCharsets.UTF_8.name() > JDK 7
            return result.toString("UTF-8");
        }catch (Exception e){
            return "";
        }
    }


    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (File file) throws Exception {
        FileInputStream fin = new FileInputStream(file);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }



    public static void writeStringToFile(File file, String data) throws TFileWriteException {
        try {
            FileUtils.writeStringToFile(file, data, "UTF-8", false);
        }catch (Exception e){
            throw TFileWriteException.create(file, e);
        }
    }


    public static String applyConstantsNamingConvention(String name){
        return name;


    }

    public static String[] splitCamelCase(String name){
        return name.split("(?<=.)(?=\\p{Lu})");
    }

    public static String capitalize(String str) {
        return StringUtils.capitalize(str);
    }

    public static String uncapitalize(String str) {
        return StringUtils.uncapitalize(str);
    }

}