package org.skyfw.base.mcodes;

public enum TMCodeSeverity {

    UNKNOWN((byte)0)
    , DEBUG((byte)1)
    , TRACE((byte)2)
    , INFO((byte)3)
    , WARNING((byte)4)
    , ERROR((byte)5)
    , FATAL((byte)6);

    byte value;

    TMCodeSeverity(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return this.value;
    }
}
