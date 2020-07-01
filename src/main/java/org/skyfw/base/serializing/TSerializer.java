package org.skyfw.base.serializing;

import org.jetbrains.annotations.Nullable;
import org.skyfw.base.classes.validation.TPreconditions;
import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.log.TLogger;
import org.skyfw.base.pool.TPoolSet;
import org.skyfw.base.pool.exception.TObjectPoolException;
import org.skyfw.base.serializing.adapters.TStringSerializerAdapter;
import org.skyfw.base.serializing.exception.TDeserializeException;
import org.skyfw.base.serializing.exception.TSerializeException;

import java.util.Map;

public class TSerializer {
    static TLogger logger = TLogger.getLogger();

    private static Class[] emptyClassArr = new Class[]{};

    // >>> Object PoolSet(s)
    static TPoolSet<TStringSerializerAdapter> stringAdapterPoolSet =
            new TPoolSet<>(TStringSerializerAdapter.class);
    //static TPoolSet<TBytesSerializerAdapter> byteArrAdapterPoolSet= new TPoolSet<>(TBytesSerializerAdapter.class);


    public static String serializeToString(Object object, Class<? extends TStringSerializerAdapter> adapterClass)
            throws TSerializeException, TObjectPoolException, TIllegalArgumentException {

        TPreconditions.checkArgForNotNull(object, "object");
        TPreconditions.checkArgForNotNull(adapterClass, "adapterClass");

        if (object instanceof TSerializeEventListener)
            try {
                ((TSerializeEventListener) object).onBeforeSerialize();
            } catch (Throwable e) {
                throw TSerializeException.create(TSerializeMCode.ON_BEFORE_SERIALIZE_METHOD_EXCEPTION
                        , adapterClass, object.getClass(), e);
            }

        TStringSerializerAdapter adapter = stringAdapterPoolSet.tryGet(adapterClass, null, 1000);
        try {
            return adapter.serialize(object);

        } finally {
            if (adapter != null)
                adapter.release();
        }

        /*
            case JAVA_OBJECT_SERIALIZER_BASE64:
                return Base64.getEncoder().encodeToString
                        (deserializeFromBytesArr(TDefaultStringSerializerAdapter.JAVA_OBJECT_SERIALIZER).toByteArray());
        */
    }

    /*public static <T extends TSerializable & TSerializeEventListener>
    String serializeToString(T object, Class<? extends TStringSerializerAdapter> adapterClass)
            throws TSerializeException, TObjectPoolException, TIllegalArgumentException {

    }*/


    public static <T /*extends TSerializable*/> T deserializeFromString(String jsonString
            , Class<? extends TStringSerializerAdapter> adapterClass
            , Class<T> mainClass, @Nullable Class... genericParams)
            throws TDeserializeException, TObjectPoolException, TIllegalArgumentException {

        TPreconditions.checkArgForNotNullOrEmpty(jsonString, "jsonString");
        TPreconditions.checkArgForNotNull(adapterClass, "adapterClass");
        TPreconditions.checkArgForNotNull(mainClass, "mainClass");

        TStringSerializerAdapter adapter = stringAdapterPoolSet.tryGet(adapterClass, null, 1000);
        try {
            if (genericParams == null)
                genericParams = emptyClassArr;

            Object object = adapter.deserialize(jsonString, mainClass, genericParams);

            // >>> Check if returned object class is wrong
            if ((!mainClass.equals(object.getClass()))
                    && (object instanceof Map)
                    && (TDataModel.class.isAssignableFrom(mainClass))) {
                try {
                    TDataModel dataModel = (TDataModel) mainClass.newInstance();
                    dataModel.loadFrom((Map<String, Object>) object);
                    return (T) dataModel;
                } catch (Exception e) {
                    throw TDeserializeException.create(TSerializeMCode.LOADING_DESERIALIZED_MAP_TO_DATAMODEL_EXCEPTION
                            , adapterClass, mainClass, e);
                }
            }

            if (object instanceof TDeserializeEventListener)
                try {
                    ((TDeserializeEventListener) object).onAfterDeserialize();
                } catch (Throwable e) {
                    throw TDeserializeException.create(TSerializeMCode.ON_AFTER_DESERIALIZE_METHOD_EXCEPTION
                            , adapterClass, mainClass, e);
                }

            return (T) object;

        } finally {
            if (adapter != null) adapter.release();
        }
    }


    public static <T /*extends TSerializable/* & TDataModel*/> T deserializeFromString(String jsonString
            , Class<? extends TStringSerializerAdapter> adapterClass
            , Class<T> mainClass)
            throws TDeserializeException, TObjectPoolException, TIllegalArgumentException {

        return deserializeFromString(jsonString, adapterClass, mainClass, null);
    }


}
