package org.skyfw.base.service;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.result.TResult;

//It Seems Generics Can't Be Useful In This Case Because It's a Run Time Matter
public interface TServiceMethodRunner  {

    TResult run(TRequestMetaData requestMetaData
            , TDataModel request
            , TDataModel response
            , TServiceMethod serviceMethod);

}
