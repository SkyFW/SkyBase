package org.skyfw.base.serializing.adapters.json.jackson;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.skyfw.base.exception.TException;
import org.skyfw.base.pool.TObjectPool;
import org.skyfw.base.pool.TPoolable;
import org.skyfw.base.pool.exception.TPoolableInitException;
import org.skyfw.base.result.TResult;
import org.skyfw.base.serializing.adapters.TStringSerializerConfig;
import org.skyfw.base.serializing.adapters.json.TJsonSerializerConfig;

public class TJacksonObjectMapper extends ObjectMapper implements TPoolable<TJsonSerializerConfig> {


    TObjectPool<TJacksonObjectMapper> motherPool;
    //public ObjectMapper objectMapper = null;





    @Override
    public TObjectPool<TJacksonObjectMapper> getMotherPool() {
        return motherPool;
    }


    @Override
    public void setMotherPool(TObjectPool objectPool) {
        this.motherPool= objectPool;
    }


    // >>> Implementation Of `TConfigurable`
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public Class<TJsonSerializerConfig> getConfigClass() {
        return null;
    }

    @Override
    public void config(TJsonSerializerConfig poolConfig) throws TPoolableInitException {

        //For Testing The Pool Abilities
        /*
        try {
            TimeUnit.SECONDS.sleep(1);
        }catch (exception e){
        }
        */


        //this.objectMapper= new ObjectMapper();
        //The Crazy Jackson Use getter Methods By Default !!!
        //This will Cause Infinite Recursive Call(StackOverFlow) In Our Case
        //The Solution Is Below
        this.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        this.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

    }

    @Override
    public void release() {
        motherPool.releaseObject(this);
    }
}
