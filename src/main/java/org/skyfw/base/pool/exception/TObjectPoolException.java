package org.skyfw.base.pool.exception;

import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.pool.TObjectPool;

public class TObjectPoolException extends TException {

    String poolMembersClassPath;
    String poolDescription;
    long poolBaseCount;
    long poolMaxCount;


    public TObjectPoolException(TMCode mCode, TMCodeSeverity severity, Throwable cause, TObjectPool objectPool) {
        super(mCode, severity, cause);
        if (objectPool != null) {
            if (objectPool.getClazz() != null)
                this.poolMembersClassPath = objectPool.getClazz().getName();
            this.poolDescription = objectPool.getDescription();
            this.poolBaseCount = objectPool.getBaseCount();
            this.poolMaxCount = objectPool.getMaxCount();
        }

    }

}
