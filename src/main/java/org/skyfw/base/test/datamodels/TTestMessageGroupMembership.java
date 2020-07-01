package org.skyfw.base.test.datamodels;

import org.skyfw.base.datamodel.annotation.AuthField;
import org.skyfw.base.datamodel.annotation.DataModel;
import org.skyfw.base.datamodel.annotation.DataModelField;
import org.skyfw.base.datamodel.TAuthFieldType;
import org.skyfw.base.datamodel.annotation.KeyField;
import org.skyfw.base.security.TDataAccessType;

@DataModel(autoKey = true, dataStoreName = "TestMessageGroupMemberships")
public class TTestMessageGroupMembership {

    @KeyField
    String id;

    @DataModelField
    @AuthField(authFieldName = "userId", authFieldType = TAuthFieldType.USER_ID)
    String userId;

    @DataModelField
    @AuthField(authFieldName = "groupId", authFieldType = TAuthFieldType.USER_ID)
    String groupId;

    @DataModelField()
    @AuthField(authFieldName = "groupId", authFieldType = TAuthFieldType.USER_ID)
    TDataAccessType accessType;

}
