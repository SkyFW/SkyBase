package org.skyfw.base.mcodes;

import org.skyfw.base.classes.validation.TPreconditions;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.log.TLogger;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiConsumer;

public class TMCodeRegistry {
    private static final TLogger logger= TLogger.getLogger();

    private static HashMap<String, TMCode> mCodesList = new HashMap<>();


    public static <T extends Enum<T> & TMCode> void register(Class<T> mCodesEnumClass)
            throws TMCodeRegisterDuplicateException, TIllegalArgumentException {

        TPreconditions.checkArgForNotNull(mCodesEnumClass, "mCodesEnumClass");

        final Set<T> mCodesSet = EnumSet.allOf(mCodesEnumClass);
        Iterator<T> iterator= mCodesSet.iterator();
        while (iterator.hasNext()){
            T mCode= iterator.next();
            String code= mCode.toString();

            if (mCodesList.containsKey(code)) {
                if (mCodesList.get(code).getClass() != mCodesEnumClass)
                    throw new TMCodeRegisterDuplicateException(code
                        , mCodesList.get(code).getClass().toString()
                        , mCodesEnumClass.toString());
                logger.warn(TBaseMCode.CODE_ALREADY_REGISTERED_WITH_SAME_MCODE_CLASS.toString());
            } else
                mCodesList.put(code, mCode);
        }
    }

    public static TMCode fetch(String code) {

        if (code == null || code.isEmpty())
            return null;

        TMCode mCode = mCodesList.get(code);

        if (mCode == null)
            return TBaseMCode.UNKNOWN_MESSAGE_CODE;

        return mCode;
    }

    //BiConsumer<? super K, ? super V>
    public static void forEach(BiConsumer<? super String, ? super TMCode> action) {

        mCodesList.forEach(action);
    }

}
