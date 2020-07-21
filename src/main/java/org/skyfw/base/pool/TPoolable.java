package org.skyfw.base.pool;

import org.skyfw.base.configuration.TConfigurable;
import org.skyfw.base.exception.TException;
import org.skyfw.base.pool.exception.TPoolableInitException;
import stormpot.Poolable;

public interface TPoolable<T extends TPoolConfig> extends Poolable, TConfigurable<T> {


    @Override
    void config(T config) throws TPoolableInitException;



    /**
     * The main purpose of defining this methods is to warn everyone to keep mother pool instance some where in
     * their <strong>`Poolable`</strong> objects.
     * @param objectPool
     */
    void setMotherPool(TObjectPool objectPool);
    TObjectPool getMotherPool();


    @Override
    default void release() {

        this.getMotherPool().releaseObject(this);
    }

    Class<T> getConfigClass();
}
