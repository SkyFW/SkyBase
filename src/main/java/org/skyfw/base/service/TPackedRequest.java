package org.skyfw.base.service;

import org.skyfw.base.datamodel.TDataModel;

public class TPackedRequest<REQ extends TDataModel> {

    public TRequestMetaData metaData;
    public REQ data;
}
