package org.skyfw.base.system.terminal;

public enum TTerminalFXFont {

    STANDARD("Standard")
    , STAR_WARS("Star_Wars")
    , BLOOD("Blood")
    , STAR_STRIPS("Star_Strips")
    , VARSITY("Varsity")
    , SUB_ZERO("Sub-Zero")
    , STOP("Stop")
    , SOFT("Soft")
    , SMALL_ISOMETRIC1("Small_Isometric1")
    , SLANT("Slant")
    , SHADOW("Shadow")
    , LARRY_3D("Larry_3D")
    , IVRIT("Ivrit")
    , ISOMETRIC1("Isometric1")
    , ISOMETRIC3("Isometric3")
    , CYBERLARGE("Cyberlarge")
    , COLOSSAL("Colossal")
    , BLOCK("Block")
    , BIG_MONEY_SW("Big_Money-sw")
    , BIG_MONEY_SE("Big_Money-se")
    , BIG("Big")
    , AMC_AAA01("AMC_AAA01")
    , ASCII_3D("3D-ASCII")
    , TEST("test")
    , ANSI_SHADOW("ANSI_Shadow")
    ;

    private String fontName;

    TTerminalFXFont(String fontName) {
        this.fontName= fontName;
    }

    @Override
    public String toString() {
        return this.fontName;
    }
}
