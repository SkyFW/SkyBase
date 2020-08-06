package org.skyfw.base.test.datamodels;

import org.skyfw.base.datamodel.TBaseDataModel;
import org.skyfw.base.datamodel.TDataSet;

public class TTestMessagesDataSet extends TBaseDataModel {

    public TDataSet<TTestMessage> messages;

    public TDataSet<TTestMessage> getMessages() {
        return messages;
    }

    public void setMessages(TDataSet<TTestMessage> messages) {
        this.messages = messages;
    }
}
