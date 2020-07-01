package org.skyfw.base.pool;

import org.skyfw.base.exception.TException;
import org.skyfw.base.pool.exception.TObjectPoolTryGetException;
import org.skyfw.base.pool.exception.TPoolInitializationException;
import org.skyfw.base.pool.exception.TPoolableInitException;
import stormpot.*;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
//import com.google.common.reflect.TypeToken;
//import java.lang.reflect.Type;

public class TObjectPool<T extends TPoolable> implements Allocator<T>  {

    private Pool<T> pool;
    private HashMap<T, Slot> poolSlots;
    private Class<? extends T> clazz;
    private TPoolConfig poolMembersConfig;
    private Integer baseCount;
    private Integer maxCount;
    private int initTimeOut= 10 * 1000;
    String description;


    public TObjectPool(Class<? extends T> poolMemberClass) {

        internalConstructor(poolMemberClass);
    }

    private void internalConstructor(Class<? extends T> poolMemberClass){

        this.poolSlots= new HashMap<T, Slot>();

        this.clazz= poolMemberClass;
                /*(Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];*/
    }



    protected T createNewPoolMember(){

        T newPoolMember= null;
        try {
            newPoolMember = clazz.newInstance();
        }catch (Exception e) {

        }

        return newPoolMember;
    }


    /**
     * >>> initialising the pool and some of pool members
     * @param baseCount >>> number of objects that will create & initialize immediately after
     *                 initializing the pool
     * @param maxCount >>> number of maximum objects that will be created due to the number of
     *                simultaneous requests
     */
    public void initialPool(TPoolConfig poolConfig, Integer baseCount, Integer maxCount) throws TPoolInitializationException {

        this.poolMembersConfig = poolConfig;
        this.baseCount= baseCount;
        this.maxCount= maxCount;

        //TJacksonObjectMapper_PoolMember.TJacksonObjectMapper_PoolAlocator poolAlocator = this;//new TJacksonObjectMapper_PoolMember.TJacksonObjectMapper_PoolAlocator();
        Config<T> config = new Config<T>().setAllocator(this);
        //ToDo: Pool Size Must Increase Automatically When needed
        //It Don't Increase Pool Size When Needed!!!
        config.setSize(maxCount);

        pool = new BlazePool<T>(config);

        // >>> Testing the pool
        try {
            TPoolable[] testAllocatedObjects = new TPoolable[this.getBaseCount()];
            for (int i = 0; i < this.getBaseCount(); i++) {
                testAllocatedObjects[i] = this.tryGet(this.initTimeOut);
            }

            for (int i = 0; i < this.getBaseCount(); i++) {
                testAllocatedObjects[i].release();
            }


        }catch (TException e){

        }


    }

    public T tryGet(Integer timeout) throws TObjectPoolTryGetException {

        //Temporary Code Just For Wrapping StormPot Pool Library
        try {
            T object;
            Timeout tempTimeout;
            tempTimeout = new Timeout(timeout, TimeUnit.MILLISECONDS);

            object= pool.claim(tempTimeout);

            if (object != null)
                return object;

            //Check if there is initiation of new members
            if (this.poolSlots.size() < this.maxCount) {
                tempTimeout = new Timeout(initTimeOut, TimeUnit.MILLISECONDS);
                object= pool.claim(tempTimeout);
            }

            if (object != null)
                return object;
            else
                throw new TObjectPoolTryGetException(TObjectPoolMCode.NO_FREE_POOL_MEMBER, null, this, timeout);

        }catch (Exception e) {
            throw new TObjectPoolTryGetException(TObjectPoolMCode.POOL_INTERNAL_ERROR, e, this, timeout);
        }
    }

    public void releaseObject(T object){

        if (poolSlots.containsKey(object)) {
            poolSlots.get(object).release(object);
        }

    }






//Temporary Code Just For Wrapping StormPot Pool Library
//------------------------------------------------------
    @Override
    public T allocate(Slot slot) throws Exception {
        //ToDo: Check For Memory Leakage Or Other Systemic Bugs
        //Unbelievable! shameful! Catastrophic!
        //The Breakpoint Don't Work Correctly In This Method !
        //Some Times Do And Some Times Not !
        //And After system.out.println Line It Do !!!
        T newPoolMember= createNewPoolMember();
        try {
            newPoolMember.setMotherPool(this);
            newPoolMember.init(this.poolMembersConfig);
        }catch (TPoolableInitException e){
            e.log();
            return null;
        }
        //system.out.println("createNewPoolMember: " +this.clazz.toString());
        this.poolSlots.put(newPoolMember, slot);
        return newPoolMember;
    }

    @Override
    public void deallocate(T poolable) throws Exception {

    }

//--------------------------------------------------------


    public Class<? extends T> getClazz() {
        return clazz;
    }

    public TPoolConfig getPoolMembersConfig() {
        return poolMembersConfig;
    }

    public Integer getBaseCount() {
        return baseCount;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public String getDescription() {
        return description;
    }
}
