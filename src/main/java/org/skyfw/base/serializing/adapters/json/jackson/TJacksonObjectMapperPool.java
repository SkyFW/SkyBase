package org.skyfw.base.serializing.adapters.json.jackson;

import org.skyfw.base.pool.TObjectPool;
import org.skyfw.base.pool.exception.TObjectPoolTryGetException;
import org.skyfw.base.pool.exception.TPoolInitializationException;

public class TJacksonObjectMapperPool {

    //Static Pool
    //-----------
    private static TObjectPool<TJacksonObjectMapper> pool;

    static {
        try {
            if (pool == null) {
                pool = new TObjectPool<>(TJacksonObjectMapper.class);
                pool.initialPool(null, 10, 100);
            }
        }catch (TPoolInitializationException e){
            e.log();
        }
    }


    /**
     * @param timeout - Timeout for returning a "pool member" or returning "Null"
     * @return
     */
    public static TJacksonObjectMapper getFromPool(Integer timeout) throws TObjectPoolTryGetException {

        return pool.tryGet(timeout);
    }
    //-----------
}
