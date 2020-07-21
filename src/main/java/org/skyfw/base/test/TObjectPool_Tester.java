package org.skyfw.base.test;

import org.skyfw.base.exception.TException;
import org.skyfw.base.log.TLogger;
import org.skyfw.base.pool.TBasePoolConfig;
import org.skyfw.base.pool.TPoolSet;
import org.skyfw.base.serializing.adapters.TStringSerializerAdapter;
import org.skyfw.base.serializing.adapters.TStringSerializerConfig;
import org.skyfw.base.serializing.adapters.json.gson.TGsonAdapter;
import org.skyfw.base.serializing.adapters.json.jackson.TJacksonObjectMapper;
import org.skyfw.base.serializing.adapters.json.jackson.TJacksonObjectMapperPool;

public class TObjectPool_Tester {
    static TLogger logger= TLogger.getLogger();

    public static boolean doTest(){

        try {
            TPoolSet<TStringSerializerAdapter> poolSet =
                    new TPoolSet<>(TStringSerializerAdapter.class);

            TStringSerializerConfig testConfig = new TStringSerializerConfig();

            poolSet.tryGet(TGsonAdapter.class, testConfig, 100);

        }catch (TException e){
            return false;
        }



        int failedTries= 0;

        for(int i=0; i < 200; i++) {
            try {
                TJacksonObjectMapper objectMapper = TJacksonObjectMapperPool.getFromPool(15000);
                if (objectMapper == null){
                    failedTries++;
                    logger.warn("getFromPool Failed After 15 Seconds");
                }

                objectMapper.createObjectNode();
                objectMapper.release();
            }catch (Exception e){
                logger.fatal("TObjectPool test Failed...", e);
                break;
            }
        }

        if (failedTries == 0)
            logger.info("TObjectPool test Done Successfully");
        else
            logger.warn("TObjectPool test Done With " + failedTries + "Failed Tries");

        return true;
    }

    private class TTestPoolConfig extends TBasePoolConfig {

    }

}
