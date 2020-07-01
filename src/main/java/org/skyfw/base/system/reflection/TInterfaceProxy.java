package org.skyfw.base.system.reflection;

import org.skyfw.base.system.info.TSystemInfo;
import org.skyfw.base.system.reflection.exception.TProxyInstanceException;

public class TInterfaceProxy {
    private static TInterfaceProxy_IFace interfaceProxy= null;

    static {
        if ( ! TSystemInfo.getJavaVersion().equals("9"))
            interfaceProxy= new TJava8InterfaceProxy();
        else
            interfaceProxy= new TJava9And10InterfaceProxy();
    }

    interface Bird{
        String fly();
    }

    interface Duck extends Bird {
        @Override
        default String fly()  {
            System.out.println("***");
            return "Flying";
        }
    }

    public static <T> T getProxyInstance(Class<T> clazz) throws TProxyInstanceException {

        T proxyInstance= (T)interfaceProxy.getProxyInstance(clazz);
        return proxyInstance;
    }

        public static void main(String[] a) {

        try {
            Bird bird = TInterfaceProxy.getProxyInstance(Duck.class);
            System.out.println(bird.fly());
        }catch (Exception e){

        }
        }



}
