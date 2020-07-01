package org.skyfw.base.system.reflection;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Proxy;

public class TJava9And10InterfaceProxy implements TInterfaceProxy_IFace {

    @Override
    public Object getProxyInstance(Class clazz) {
        Object proxyInstance = Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[] { clazz },
                (proxy, method, args) -> {

                    Object resultObject=
                    MethodHandles.lookup()
                            .findSpecial(
                                    clazz,
                                    "quack",
                                    MethodType.methodType(void.class, new Class[0]),
                                    clazz)
                            .bindTo(proxy)
                            .invokeWithArguments();
                    return resultObject;
                }
        );
        return proxyInstance;
    }
}
