package org.skyfw.base.test.datamodels;

import org.skyfw.base.datamodel.TBaseDataModel;
import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.datamodel.annotation.DataModel;
import org.skyfw.base.datamodel.annotation.DataModelField;
import org.skyfw.base.datamodel.TDataSet;
import org.skyfw.base.serializing.TSerializable;

@DataModel(autoKey = false, dataStoreName = "TestResponseDataModel")
public class TTestResponseDataModel extends TBaseDataModel implements TSerializable<TTestResponseDataModel> {

    @DataModelField
    private String name;

    @DataModelField
    private TDataSet<TTestUser> users;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TDataSet<TTestUser> getUsers() {
        return users;
    }

    public void setUsers(TDataSet<TTestUser> users) {
        this.users = users;
    }
}
