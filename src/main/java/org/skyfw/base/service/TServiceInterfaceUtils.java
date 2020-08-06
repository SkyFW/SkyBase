package org.skyfw.base.service;

import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.exception.general.TNullArgException;
import org.skyfw.base.log.TLogger;
import org.skyfw.base.service.exception.TServiceInformationRetrievalException;
import org.skyfw.base.service.method.TServiceMethod;
import org.skyfw.base.service.method.TServiceMethodInfo;
import org.skyfw.base.service.method.TServiceMethodInfoProvider;
import org.skyfw.base.system.reflection.TInterfaceProxy;

/**
 * This class helps to get information about "Services" and "Methods" from the interface.
 * First default implementation of interface methods will be used and in case of failure
 * related Annotations will be used.
 *
 */
public class TServiceInterfaceUtils {
    static TLogger logger= TLogger.getLogger();

    public static <Q extends TDataModel,S extends TDataModel, T extends TServiceMethodInfoProvider<Q,S>>
    TServiceMethodInfo<Q,S> getServiceMethodInfoByClass(Class<T> serviceMethodClazz) throws TServiceInformationRetrievalException{
        try {
            T serviceMethodObj = TInterfaceProxy.getProxyInstance(serviceMethodClazz);
            TServiceMethodInfo <Q,S> serviceMethodInfo=
                    getServiceMethodInfoByObject(serviceMethodObj);

            return serviceMethodInfo;
        }catch (Exception e){
            logger.warn("exception In Generating Instance From service Method", e);
           throw TServiceInformationRetrievalException.create(
                   TServiceMCode.EXCEPTION_IN_GENERATING_INSTANCE_FROM_SERVICE_METHOD_CLASS
                   , serviceMethodClazz
                   , e);
        }

    }



    public static <Q extends TDataModel,S extends TDataModel, T extends TServiceMethodInfoProvider<Q,S>>
    TServiceMethodInfo <Q,S> getServiceMethodInfoByObject(T serviceMethodObj)
            throws TServiceInformationRetrievalException, TIllegalArgumentException {

        if (serviceMethodObj == null)
            throw new TNullArgException(TServiceMCode.SERVICE_INSTANCE_IS_NULL, "serviceMethodObj");

        //TException.create(TServiceMCode.SERVICE_METHOD_INSTANCE_IS_NULL, null, e);

        TServiceMethodInfo<Q,S> serviceMethodInfo;
        try {
            serviceMethodInfo= serviceMethodObj.getMethodInfo();
            return serviceMethodInfo;
        }catch (Exception e){
            throw TServiceInformationRetrievalException.create(
                    TServiceMCode.EXCEPTION_ON_GETTING_INFO_FROM_SERVICE_METHOD_OBJECT
                    , serviceMethodObj.getClass()
                    , e);
        }
    }






    public static TServiceInfo getServiceInfoByClass(Class<? extends TService> serviceClass)
            throws TServiceInformationRetrievalException, TIllegalArgumentException {

        if (serviceClass == null)
            throw new TNullArgException(TServiceMCode.SERVICE_INSTANCE_IS_NULL, "serviceClass");

        try {
            TService serviceObj = TInterfaceProxy.getProxyInstance(serviceClass);
            TServiceInfo serviceInfo= getServiceInfoByObject(serviceObj);
            return serviceInfo;

        }catch (Exception e){
            logger.warn("exception In Generating Instance From service", e);
            e.printStackTrace();
            throw TServiceInformationRetrievalException.create(
                    TServiceMCode.EXCEPTION_IN_GENERATING_INSTANCE_FROM_SERVICE_CLASS
                    , serviceClass
                    , e);
        }

    }


    public static TServiceInfo getServiceInfoByObject(TService serviceObj)
            throws TServiceInformationRetrievalException, TIllegalArgumentException{

        if (serviceObj == null)
            throw new TNullArgException (TServiceMCode.SERVICE_INSTANCE_IS_NULL, "serviceObj");

        TServiceInfo serviceInfo= new TServiceInfo();
        try{
            serviceInfo.setPath(serviceObj.getFullPath());
            serviceInfo.setDescription(serviceObj.getServiceDescription());
            return serviceInfo;
        }catch (Exception e){
            throw TServiceInformationRetrievalException.create(
                    TServiceMCode.EXCEPTION_ON_GETTING_INFO_FROM_SERVICE_OBJECT
                    , serviceObj.getClass());
        }

    }

}
