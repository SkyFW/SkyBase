package org.skyfw.base.datamodel.defaultdatamodels;

import org.skyfw.base.datamodel.TBaseDataModel;
import org.skyfw.base.datamodel.annotation.DataModel;
import org.skyfw.base.datamodel.annotation.DataModelField;
import org.skyfw.base.datamodel.TDataModel;

@DataModel(autoKey = true, dataStoreName = "")
public class TStringDataModel extends TBaseDataModel {

    @DataModelField
    private String value;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
