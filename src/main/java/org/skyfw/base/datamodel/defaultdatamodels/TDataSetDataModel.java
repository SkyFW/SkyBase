package org.skyfw.base.datamodel.defaultdatamodels;

import org.skyfw.base.datamodel.TBaseDataModel;
import org.skyfw.base.datamodel.annotation.DataModel;
import org.skyfw.base.datamodel.annotation.DataModelField;
import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.datamodel.TDataSet;

@DataModel(autoKey = false)
public class TDataSetDataModel<T extends TDataModel> extends TBaseDataModel {

    @DataModelField
    TDataSet<T> dataSet;

    public TDataSet<T> getDataSet() {
        return dataSet;
    }

    public void setDataSet(TDataSet<T> dataSet) {
        this.dataSet = dataSet;
    }

}
