package org.skyfw.base.datamodel;

import org.skyfw.base.datamodel.index.TDataModelIndexDescriptorsList;

import java.util.LinkedHashMap;
import java.util.Map;

public class TDataModelDescriptor {

    //Constructor
    public TDataModelDescriptor() {
        //
        fields = new LinkedHashMap<String, TFieldDescriptor>();
    }

    //Attributes
    public Class<? extends TDataModel> clazz;
    public String className;
    public String dataStoreName;
    public String keyFieldName;
    public Boolean autoKey;
    //datamodel Fields
    public Map<String, TFieldDescriptor> fields;
    //index Entries
    private TDataModelIndexDescriptorsList indexEntries;
    //Data Division Id Field Name
    String dDIdFieldName= "";

    public Map<String, TFieldDescriptor> getFields() {
        return fields;
    }


    //Getter & Setter Methods
    //------------------------------------------------------------------------------------------------------------------
    public void setFields(LinkedHashMap<String, TFieldDescriptor> fields) {
        this.fields = fields;
    }

    public TDataModelIndexDescriptorsList getIndexDescriptors() {
        if (this.indexEntries == null)
            this.indexEntries = new TDataModelIndexDescriptorsList();
        return this.indexEntries;
    }

    public Class<? extends TDataModel> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends TDataModel> clazz) {
        this.clazz = clazz;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDataStoreName() {
        return dataStoreName;
    }

    public void setDataStoreName(String dataStoreName) {
        this.dataStoreName = dataStoreName;
    }

    public String getKeyFieldName() {
        return keyFieldName;
    }

    public void setKeyFieldName(String keyFieldName) {
        this.keyFieldName = keyFieldName;
    }

    public Boolean getAutoKey() {
        return autoKey;
    }

    public void setAutoKey(Boolean autoKey) {
        this.autoKey = autoKey;
    }

    public void setIndexEntries(TDataModelIndexDescriptorsList indexEntries) {

        this.indexEntries = indexEntries;
    }

    public TDataModelIndexDescriptorsList getIndexEntries() {
        return indexEntries;
    }

    public String getdDIdFieldName() {
        return dDIdFieldName;
    }

    public void setdDIdFieldName(String dDIdFieldName) {
        this.dDIdFieldName = dDIdFieldName;
    }
}
