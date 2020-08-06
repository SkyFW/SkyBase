package org.skyfw.base.service;


import org.skyfw.base.log.TLogger;
import org.skyfw.base.service.method.TServiceMethodInfo;

import java.util.concurrent.ConcurrentHashMap;

public interface TService {
    static TLogger logger= TLogger.getLogger();

    ConcurrentHashMap<String, TServiceMethodInfo> servicesCatalog=
            new ConcurrentHashMap<>();

    //Get service Name
    String getServiceName();

    //Set service Name (Extracted From annotation)
    default void setServiceName(String serviceName){};
    //Set service Path (Extracted From annotation)
    default void setServicePath(String servicePath){};

    //Get service Path
    default String getFullPath(){

        String parentServicePath= "";

        try {
            if ((this.getServiceName() == null) || (this.getServiceName().isEmpty()))
                return "";
        }catch (Throwable e){
            //If The service Is Old And Not Implemented The "getServiceName()" Method
            return "";
        }

        try {

            TService parentService = getParentService();
            if ((parentService != null) && ( ! parentService.equals(this))) {
               parentServicePath= parentService.getFullPath();
               return null;
            }

            if (parentServicePath.isEmpty()) {
                if (this.getParentServiceClass() == null)
                    return null;
                if (this.getParentServiceClass().isInterface())
                    return null;

                parentService= this.getParentServiceClass().newInstance();
                if (parentService == null)
                    return null;

                parentServicePath = parentService.getFullPath();
            }

        }catch (Exception e){
            logger.fatal("EXCEPTION_ON_GET_SERVICE_FULL_PATH", e);
        }
        finally {
            return parentServicePath.concat("/").concat(this.getServiceName());
        }

        /*
        if (!parentServiceFullPath.substring(parentServiceFullPath.length() - 1).equals("/"))
            serviceFullPathSB.append("/");
            */


    }





    default Class<? extends TService> getParentServiceClass() { return null; };
    default TService getParentService() { return null; };
    default String   getServiceDescription() { return null;};
}
