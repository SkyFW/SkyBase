package org.skyfw.base.security;

import java.util.EnumSet;

public enum TDataAccessType {

    NO_ACCESS(0),
    CREATE_ACCESS(1),
    READ_ACCESS(2),
    UPDATE_ACCESS(4),
    DELETE_ACCESS(8),
    FULL_ACCESS(15),
    //16
    ;

    public static final EnumSet<TDataAccessType> ALL_OPTS = EnumSet.allOf(TDataAccessType.class);


    int code;

    TDataAccessType(int code) {
        this.code = code;
    }



}
