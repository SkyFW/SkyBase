package org.skyfw.base.pool;

import org.skyfw.base.exception.TException;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.pool.exception.TObjectPoolException;
import org.skyfw.base.pool.exception.TObjectPoolTryGetException;

import java.util.HashMap;

public class TPoolSet<T extends TPoolable> {

    int defaultBaseCount= 10;
    int defaultMaxCount= 100;

    // >>> Any single pool is thread safe by itself and there is no delete and update operation on the list
    // so simple `HashMap` will be ok.
    private HashMap poolsList= new HashMap<>();

    private Class<T> basePoolableClass;

    public TPoolSet(Class<T> basePoolableClass) {
        this.basePoolableClass = basePoolableClass;
    }

    public TPoolSet( Class<T> basePoolableClass, int defaultBaseCount, int defaultMaxCount) {
        this.defaultBaseCount = defaultBaseCount;
        this.defaultMaxCount = defaultMaxCount;
        this.basePoolableClass = basePoolableClass;
    }

    /*public  <S extends T, PC extends C> S tryGet(Class<? extends S> objectClass, Integer timeout) throws TException {

        return tryGet(objectClass,null, timeout);
    }*/

    //ToDo: Generic params design could be improved ?
    public <S extends TPoolable<PC>, PC extends TPoolConfig> S tryGet(Class<S> poolableObjectClass, PC poolConfig, Integer timeout)
            throws TObjectPoolException, TIllegalArgumentException {

        if (poolableObjectClass == null){
            throw new TIllegalArgumentException(TObjectPoolMCode.CLASS_PARAM_IS_NULL);
        }

        // >>> Assigning the main list to a generic typed local variable(Pointer)
        HashMap<Class<T>, HashMap<PC, TObjectPool<S>>> typedPoolsList= poolsList;

        HashMap<PC, TObjectPool<S>> certainClassObjectPools= typedPoolsList.get(poolableObjectClass);
        if (certainClassObjectPools == null) {
            certainClassObjectPools = new HashMap<>();
            poolsList.put(poolableObjectClass, certainClassObjectPools);
        }


        TObjectPool<S> configuredObjectPool= certainClassObjectPools.get(poolConfig);

        if (configuredObjectPool == null){
            // >>> Wasted time during initialising a new pool will not get into count of timeout
            // because it's a one time job that should be done.
            configuredObjectPool= new TObjectPool<>(poolableObjectClass);
            configuredObjectPool.initialPool(poolConfig, defaultBaseCount, defaultMaxCount);
            certainClassObjectPools.put(poolConfig, configuredObjectPool);
        }

        return configuredObjectPool.tryGet(timeout);
    }

}
