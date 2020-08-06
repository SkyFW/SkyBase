package org.skyfw.base.datamodel;

import org.skyfw.base.serializing.TSerializable;

import java.util.concurrent.ConcurrentHashMap;

public class TGenericDataModel extends ConcurrentHashMap<String, Object>
        implements TDataModel {


    //PerInstance Value Container
    /*8private LinkedHashMap<String, Object> values = new LinkedHashMap<>();


    public LinkedHashMap<String, Object> getAllFieldsValues() {
        try {
            if (this.values == null)
                return null;

            return this.values;
        }catch (Exception e){
            return null;
        }
    }


    public List<String> getAllFieldsNames() {

         if (this.values == null)
             values = new LinkedHashMap<>();

         return new ArrayList(this.values.keySet());
    }



    public Object getFieldValue(String fieldName) {

        if (this.values == null)
            values = new LinkedHashMap<>();

        return this.values.get(fieldName.toUpperCase());
    }



    public TResult setFieldValue(String fieldName, Object value) {
        TResult result= TResult.create();

        try {
            if (this.values == null)
                values = new LinkedHashMap<>();

            this.values.put(fieldName.toUpperCase(), value);
            return result.succeed(null);
        }catch (Exception e){
            return result.fail(TDataModelMCodes.EXCEPTION_ON_SET_FIELD_VALUE, null);
        }
    }
*/


    @Override
    public Object set(String fieldName, Object value) {
        return this.put(fieldName, value);
    }




    @Override
    public Long getCreationTimeStamp() {
        return (Long) this.get("creationTimeStamp");
    }

    @Override
    public void setCreationTimeStamp(Long creationTimeStamp) {
        this.put("creationTimeStamp", creationTimeStamp);
    }

    @Override
    public Long getLastAccessTimeStamp() {
        return (Long) this.get("lastAccessTimeStamp");
    }

    @Override
    public void setLastAccessTimeStamp(Long lastAccessTimeStamp) {
        this.put("lastAccessTimeStamp", lastAccessTimeStamp);
    }

    @Override
    public Long getLastUpdateTimeStamp() {
        return (Long) this.get("lastUpdateTimeStamp");
    }

    @Override
    public void setLastUpdateTimeStamp(Long lastUpdateTimeStamp) {
        this.put("lastUpdateTimeStamp", lastUpdateTimeStamp);
    }

    @Override
    public Long getDeletionTimeStamp() {
        return (Long) this.get("deletionTimeStamp");
    }

    @Override
    public void setDeletionTimeStamp(Long deletionTimeStamp) {
        this.put("deletionTimeStamp", deletionTimeStamp);
    }
}
