package org.skyfw.base.test.datamodels;
import org.skyfw.base.datamodel.TBaseDataModel;
import org.skyfw.base.datamodel.TFieldDescriptor;
import org.skyfw.base.datamodel.annotation.*;


@DataModel(dataStoreName = "users_table", autoKey = true)

@DataModelIndexEntry({
        @IndexParam(fieldName = "messageGroup", dataSegmentSize = 100),
        @IndexParam(fieldName = "creationTime")
})

public class TTestMessage extends TBaseDataModel {

    @KeyField
    private String messageId;

    @DataModelField(fieldLen = 50)
    private String messageText;
    public static transient TFieldDescriptor MESSAGE_TEXT;


    @DataModelField
    //@AuthField(authFieldType = TAuthFieldType.AUTH_GROUP_ID, authFieldName = "MessageGroup")
    private String messageGroup;

    @DataModelField
    private Long creationTime;

    @DataModelField
    private Long editTime;



    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public Long getEditTime() {
        return editTime;
    }

    public void setEditTime(Long editTime) {
        this.editTime = editTime;
    }

    public String getMessageGroup() {
        return messageGroup;
    }

    public void setMessageGroup(String messageGroup) {
        this.messageGroup = messageGroup;
    }
}
