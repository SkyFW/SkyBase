package org.skyfw.base.datamodel;


import org.skyfw.base.classes.TNumbers;
import org.skyfw.base.datamodel.exception.TDataModelException;
import org.skyfw.base.datamodel.exception.TFieldNotExistsException;
import org.skyfw.base.exception.TException;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.log.TLogger;
import org.skyfw.base.mcodes.TMCodeSeverity;

import java.io.Serializable;
import java.util.*;


public interface TDataModel extends Serializable, Map<String, Object> {

    static final TLogger logger= TLogger.getLogger();



    default int getFieldValueAsInteger(String fieldName) {
        Object value= this.get(fieldName);
        if (value == null)
            return 0;
        return new Integer(get(fieldName).toString());
    }

    default long getFieldValueAsLong(String fieldName) {

        Object value= this.get(fieldName);
        if (value == null)
            return 0;
        return new Long(value.toString());
    }

    default String getFieldValueAsString(String fieldName) {

        Object fieldValue= this.get(fieldName);
        if (fieldValue != null)
            return fieldValue.toString();
        else
            return "null";
    }

    default String getKeyFieldName() throws TDataModelException{
        try {
            return TDataModelHelper.getDataModelDescriptor(this).getKeyFieldName();
        }catch (TIllegalArgumentException e){
            e.log();
            return null;
        }
    }

    default Object getKeyFieldValue() throws TDataModelException{
        return this.get(this.getKeyFieldName());
    }

    default void setKeyFieldValue(Object keyFieldValue) throws TDataModelException{
        this.set(this.getKeyFieldName(), keyFieldValue);
    }

    default void loadFrom(Map<String, Object> data){

        this.putAll(data);
    }



    // >>> Implementing Map interface
    //-----------------------------------------------------------------------
    //
    // -------------------------------------------
    @Override
    default int size() {
        try {
            return TDataModelHelper.getDataModelDescriptor(this).getFields().size();
        }catch (TException e){
            e.log();
            return 0;
        }
    }

    @Override
    default boolean isEmpty() {
        try {
            return TDataModelHelper.getDataModelDescriptor(this).getFields().isEmpty();
        }catch (TException e){
            e.log();
            return true;
        }
    }

    @Override
    default boolean containsKey(Object key) {
        try {
            return TDataModelHelper.getDataModelDescriptor(this).getFields().containsKey(key);
        }catch (TException e){
            e.log();
            return false;
        }
    }

    @Override
    default boolean containsValue(Object value) {
        return this.values().contains(value);
    }

    @Override
    default Object get(Object key) {
        try {
            TFieldDescriptor fieldDescriptor= TDataModelHelper.getFieldDescriptor(this, key);
            return TDataModelHelper.getFieldValue(this, fieldDescriptor);
        } catch (TException e) {
            e.log();
            return null;
        }
    }


    @Override
    default Object put(String key, Object value) {

        try {
            return this.set(key, value);
        }catch (TException e) {
            return null;
        }
    }


    // >>> ;)
    default Object set(String fieldName, Object value) throws TDataModelException {

        try {
            TFieldDescriptor fieldDescriptor= TDataModelHelper.getFieldDescriptor(this, fieldName);
            if (fieldDescriptor == null)
                return null;
            Class fieldType= fieldDescriptor.getReflectionField().getType();

            if (!value.getClass().equals(fieldType)) {

                if (Number.class.isInstance(value) || String.class.isInstance(value)) {

                    if (Number.class.isAssignableFrom(fieldType)) {

                        value = TNumbers.create(value, fieldType);
                    } else if (fieldType.isEnum()) {

                        String strValue = (String) value;
                        value = Enum.valueOf((Class<Enum>) fieldType, strValue);
                    }

                } else if (Collection.class.isInstance(value) && TDataSet.class.equals(fieldType)) {

                    TDataSet dataSet = new TDataSet((Collection) value);
                    value = dataSet;
                } else {

                    throw new TIllegalArgumentException(TDataModelMCodes.INCOMPATIBLE_VALUE);
                }
            }

            // >>> It's very important to provide a full Map behaviour
            Object lastValue= TDataModelHelper.getFieldValue(this, fieldDescriptor);

            TDataModelHelper.setFieldValue(this, fieldDescriptor, value);

            return lastValue;

        } catch (TException e) {
            e.log();
            return null;
        }
    }


    @Override
    default Object remove(Object key) {
        Object value= this.get(key.toString());
        try {
            this.set(key.toString(), null);
        }catch (Exception e){
        }
        return value;
    }

    @Override
    default void putAll(Map<? extends String, ?> m) {

        if (m == null)
            return;

        m.forEach((key, value) -> {
            try {
                TFieldDescriptor fieldDescriptor= TDataModelHelper.getFieldDescriptor(this, key);
                TDataModelHelper.setFieldValue(this, fieldDescriptor, value);
            }catch (TFieldNotExistsException e){
                e.setSeverity(TMCodeSeverity.DEBUG);
                e.log();
            }catch (TException e){
                e.setSeverity(TMCodeSeverity.WARNING);
                e.log();
            }
        });

    }

    @Override
    default void clear() {
        this.keySet().forEach(fieldName -> {
            this.put(fieldName, null);
        });
    }

    @Override
    default Set<String> keySet() {
        try {
            return TDataModelHelper.getDataModelDescriptor(this).getFields().keySet();
        }catch (TException e){
            e.log();
            return new TreeSet<>();
        }
    }

    @Override
    default Collection values() {

        Collection collection= new LinkedList();

        this.entrySet().forEach(stringObjectEntry -> {
            collection.add(stringObjectEntry.getValue());
        });

        return collection;
    }

    @Override
    default Set<Entry<String, Object>> entrySet() {

        try {
            // >>> HashSet is faster than TreeSet and should be preferred choice if sorting of element is not required.
            Set<Entry<String, Object>> set = new HashSet<>();
            TDataModelDescriptor dataModelDescriptor = TDataModelHelper.getDataModelDescriptor(this);

            dataModelDescriptor.getFields().forEach((fieldName, fieldDescriptor) -> {
                try {
                    Object value = TDataModelHelper.getFieldValue(this, fieldDescriptor);
                    Entry<String, Object> entry = new AbstractMap.SimpleEntry<>(fieldName, value);
                    set.add(entry);
                } catch (TException e) {
                    e.log();
                }
            });

            return set;

        } catch (TException e) {
            e.log();
            return null;
        }
    }


    // >>> Performance improving approach
    //------------------------------------------------------------------------------------------------------------------
    default TDataModelDescriptor getDescriptorCache(){
        return null;
    }

    default void setDescriptorCache(TDataModelDescriptor dataModelDescriptor){
        return;
    }



    // >>> Setter & Getter for default fields.
    //------------------------------------------------------------------------------------------------------------------
    Long getCreationTimeStamp();

    void setCreationTimeStamp(Long creationTimeStamp);

    Long getLastAccessTimeStamp();

    void setLastAccessTimeStamp(Long lastAccessTimeStamp);

    Long getLastUpdateTimeStamp();

    void setLastUpdateTimeStamp(Long lastUpdateTimeStamp);

    Long getDeletionTimeStamp();

    void setDeletionTimeStamp(Long deletionTimeStamp);
}
