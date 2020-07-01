package org.skyfw.base.datamodel.gui;

import java.util.HashMap;

public class TGUIFieldDescriptor {

    private String caption;
    private String unitCaption;

    private TGUIInputType inputType;
    private int    displaySize;
    private String defaultText;
    private String hint;
    private boolean readOnly;
    private boolean numbersOnly;
    //Just for Multi Value Items Like "COMBO_BOX"
    private HashMap<String, String> availableValues;
    private boolean onlyAvailableValues;

    //Lookup properties
    //String lookupFormName;
    //String lookupValueFieldName;

    private String iconURL;



    public TGUIInputType getInputType() {
        return inputType;
    }

    public void setInputType(TGUIInputType inputType) {
        this.inputType = inputType;
    }

    public String getDefaultText() {
        return defaultText;
    }

    public void setDefaultText(String defaultText) {
        this.defaultText = defaultText;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isNumbersOnly() {
        return numbersOnly;
    }

    public void setNumbersOnly(boolean numbersOnly) {
        this.numbersOnly = numbersOnly;
    }

    public HashMap<String, String> getAvailableValues() {
        return availableValues;
    }

    public void setAvailableValues(HashMap<String, String> availableValues) {
        this.availableValues = availableValues;
    }

    public boolean isOnlyAvailableValues() {
        return onlyAvailableValues;
    }

    public void setOnlyAvailableValues(boolean onlyAvailableValues) {
        this.onlyAvailableValues = onlyAvailableValues;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUnitCaption() {
        return unitCaption;
    }

    public void setUnitCaption(String unitCaption) {
        this.unitCaption = unitCaption;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(int displaySize) {
        this.displaySize = displaySize;
    }

    public String getIconURL() {
        return iconURL;
    }

    public void setIconURL(String iconURL) {
        this.iconURL = iconURL;
    }
}
