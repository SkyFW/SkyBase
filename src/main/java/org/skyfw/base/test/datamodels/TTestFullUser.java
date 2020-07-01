package org.skyfw.base.test.datamodels;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.datamodel.annotation.*;

@DataModel(autoKey = true, dataStoreName = "TestFullUser")
@DataModelIndexEntry({
        @IndexParam(fieldName = "Area")
})
public class TTestFullUser extends TTestUser {

    @DataModelField
    private String webSiteAddress;

    @DDIdField
    private String area;


}
