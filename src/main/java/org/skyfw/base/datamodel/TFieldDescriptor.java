package org.skyfw.base.datamodel;

import org.skyfw.base.datamodel.gui.TGUIFieldDescriptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;


public class TFieldDescriptor<T extends TDataModel> {

    private String fieldName;
    private String fieldDescription;
    private String dSFieldName;
    private boolean isKeyField= false;
    private boolean isDDId;
    private int len;
    private Class type;
    private Field reflectionField;
    private Method getterMethod;
    private Method setterMethod;

    private HashMap<String, TAuthFieldType> authorizationAttributes= new HashMap<String, TAuthFieldType>();

    private Class<? extends TDataModel> foreignDataModel;
    private String fValueFieldName;

    private TGUIFieldDescriptor guiDescriptor;


/*
    TFieldDescriptor(String name, String dSFieldName, int len, boolean isKeyField) {
        this.fieldName = name;
        this.isKeyField = isKeyField;
        this.dSFieldName= dSFieldName;
        this.len=len;
    }
*/

    public TFieldDescriptor() {
    }




    public boolean isSimpleField(){
        if (this.getClass().getName().contains("TDataViewModelFieldDescriptor"))
            return false;
        else
            return true;
    }

    public HashMap<String, TAuthFieldType> getAuthorizationAttributes() {
        return this.authorizationAttributes;
    }

    public void setAuthorizationAttributes(HashMap<String, TAuthFieldType> authorizationAttributes) {
        this.authorizationAttributes = authorizationAttributes;
    }


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String name) {
        this.fieldName = name;
    }

    public String getFieldDescription() {
        return fieldDescription;
    }

    public void setFieldDescription(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    public String getdSFieldName() {
        return dSFieldName;
    }

    public void setdSFieldName(String dSFieldName) {
        this.dSFieldName = dSFieldName;
    }

    public boolean isKeyField() {
        return isKeyField;
    }

    public void setKeyField(boolean isKeyField) {
        this.isKeyField = isKeyField;
    }

    public boolean isDDId() {
        return isDDId;
    }

    public void setDDId(boolean DDId) {
        isDDId = DDId;
    }

    public Method getGetterMethod() {
        return getterMethod;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }


    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }


    public Field getReflectionField() {
        return reflectionField;
    }

    public void setReflectionField(Field reflectionField) {
        this.reflectionField = reflectionField;
    }

    public void setGetterMethod(Method getterMethod) {
        this.getterMethod = getterMethod;
    }


    public void setSetterMethod(Method setterMethod) {
        this.setterMethod = setterMethod;
    }

    public Method getSetterMethod() {
        return setterMethod;
    }


    public Class<? extends TDataModel> getForeignDataModel() {
        return foreignDataModel;
    }

    public void setForeignDataModel(Class<? extends TDataModel> foreignDataModel) {
        this.foreignDataModel = foreignDataModel;
    }

    public String getfValueFieldName() {
        return fValueFieldName;
    }

    public void setfValueFieldName(String fValueFieldName) {
        this.fValueFieldName = fValueFieldName;
    }

    public TGUIFieldDescriptor getGuiDescriptor() {
        return guiDescriptor;
    }

    public void setGuiDescriptor(TGUIFieldDescriptor guiDescriptor) {
        this.guiDescriptor = guiDescriptor;
    }
}
