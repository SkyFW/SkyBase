package org.skyfw.base.classes.objectFactory;

import org.skyfw.base.classes.validation.TPreconditions;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.mcodes.TMCodeSeverity;

public class TObjectFactory {

    public static <T, C extends Class<T>> T newInstance(C clazz) throws TCreatingNewInstanceException, TIllegalArgumentException {

        TPreconditions.checkArgForNotNull(clazz, "clazz");

        try {
            return clazz.newInstance();
        }catch (Throwable e){
            throw new TCreatingNewInstanceException(TObjectFactoryMCodes.EXCEPTION_ON_CREATING_NEW_INSTANCE_OF_OBJECT
                    , TMCodeSeverity.ERROR, e, clazz.getName());
        }

    }

}
