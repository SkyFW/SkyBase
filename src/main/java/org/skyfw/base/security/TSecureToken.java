package org.skyfw.base.security;

import java.security.SecureRandom;

public class TSecureToken {

    static SecureRandom rnd = new SecureRandom();

    public static final String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";

    public static final String digits = "0123456789";

    public static final String alphanumericChars = upperCaseChars + lowerCaseChars + digits;


    public static String generate( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( alphanumericChars.charAt( rnd.nextInt(alphanumericChars.length()) ) );
        return sb.toString();
    }

    public static String generateNumericToken( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( digits.charAt( rnd.nextInt(digits.length()) ) );
        return sb.toString();
    }

}
