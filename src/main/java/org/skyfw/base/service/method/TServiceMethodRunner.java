package org.skyfw.base.service.method;

import org.skyfw.base.service.TBaseServiceSession;
import org.skyfw.base.service.method.TServiceMethod;

public interface TServiceMethodRunner  {

    void run(TBaseServiceSession serviceSession, TServiceMethod serviceMethod);

}
