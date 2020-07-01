package org.skyfw.base.system.reflection;


import org.skyfw.base.system.reflection.exception.TProxyInstanceException;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;

public class TJava8InterfaceProxy implements TInterfaceProxy_IFace {


    @Override
    public Object getProxyInstance(Class clazz) throws TProxyInstanceException {

        Object proxyInstance = Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[] { clazz },
                //On Method Call Handler
                (proxy, method, args) -> {
                    if( ! method.isDefault()) {
                        if (method.getName().equals("toString"))
                            return "This Is Proxied Instance Of Class: " + clazz.getName();
                        else
                            return null;
                    }

                    Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class
                            .getDeclaredConstructor(Class.class);
                    constructor.setAccessible(true);

                    Object resultObject=
                    constructor.newInstance(clazz)
                            .in(clazz)
                            .unreflectSpecial(method, clazz)
                            .bindTo(proxy)
                            .invokeWithArguments();

                    return resultObject;
                }
        );

        return proxyInstance;
    }







    /*
    @Override
    public Object getProxyInstance(Class clazz) {
        Object proxyInstance = Proxy.newProxyInstance(C.class.getClassLoader(), new Class[]{clazz},
                //On Method Call Handler
                (proxy, method, args) -> {
                    if (method.isDefault()) {
                        final Class<?> declaringClass = method.getDeclaringClass(); //clazz;
                        Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
                        constructor.setAccessible(true);
                        Object resultObject=
                                constructor.newInstance(declaringClass, MethodHandles.Lookup.PRIVATE)
                                        //.in(clazz)
                                        .unreflectSpecial(method, declaringClass)
                                        .bindTo(proxy)
                                        .invokeWithArguments(args);

                        return resultObject;
                    }

                    system.out.println("Proxying: " + method.getName() + " " + Arrays.toString(args));
                    return "Success";
                }
        );

        return proxyInstance;
    }
    */


}
