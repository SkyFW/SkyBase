package org.skyfw.base.security;

public enum THashAlg {

    SHA_512("SHA-512");

    private String code;

    THashAlg(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
