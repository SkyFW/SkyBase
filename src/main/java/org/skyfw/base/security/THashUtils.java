package org.skyfw.base.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class THashUtils {

    public static String HashString(THashAlg hashAlg, String string, String salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance(hashAlg.getCode());
            md.update(salt.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(string.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return generatedPassword;
    }


}
