package org.skyfw.base.service;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.result.TResult;

public class TPackedResponse<T extends TDataModel> {

    public TResult result;
    public T data;

}
