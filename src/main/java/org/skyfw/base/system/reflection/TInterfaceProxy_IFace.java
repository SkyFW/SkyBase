package org.skyfw.base.system.reflection;

import org.skyfw.base.system.reflection.exception.TProxyInstanceException;

public interface TInterfaceProxy_IFace {

    Object getProxyInstance(Class clazz) throws TProxyInstanceException;

}
