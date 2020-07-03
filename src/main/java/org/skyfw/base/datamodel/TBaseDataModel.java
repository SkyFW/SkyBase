package org.skyfw.base.datamodel;

import org.skyfw.base.datamodel.annotation.DataModel;
/**

*/
@DataModel
public abstract class TBaseDataModel implements TDataModel {

    // >>> Default fields for any DataModel(timestamps)
    Long creationTimeStamp;
    Long lastAccessTimeStamp;
    Long lastUpdateTimeStamp;
    Long deletionTimeStamp;


    @Override
    public String toString() {

        //ToDo: Serious time performance concerns

        try {
            StringBuilder resultSB = new StringBuilder();
            String keyFieldName = this.getKeyFieldName();

            resultSB.append(this.getClass().getSimpleName());
            resultSB.append(" => ");
            resultSB.append("Key(");
            resultSB.append(keyFieldName);
            resultSB.append("): ");
            resultSB.append(this.getKeyFieldValue());

            this.entrySet().forEach(stringObjectEntry -> {

                String fieldName= stringObjectEntry.getKey();
                Object fieldValue= stringObjectEntry.getValue();

                if ( ! fieldName.equalsIgnoreCase(keyFieldName)) {
                    resultSB.append(" | ");
                    resultSB.append(fieldName);
                    resultSB.append(": ");
                    if (fieldValue != null)
                        resultSB.append(fieldValue);
                    else
                        resultSB.append("NULL");
                }
            });


            return resultSB.toString();

        } catch (Exception e){
            return super.toString();
        }

    }





    // >>> Setter & Getter for default fields.
    //------------------------------------------------------------------------------------------------------------------
    public Long getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(Long creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public Long getLastAccessTimeStamp() {
        return lastAccessTimeStamp;
    }

    public void setLastAccessTimeStamp(Long lastAccessTimeStamp) {
        this.lastAccessTimeStamp = lastAccessTimeStamp;
    }

    public Long getLastUpdateTimeStamp() {
        return lastUpdateTimeStamp;
    }

    public void setLastUpdateTimeStamp(Long lastUpdateTimeStamp) {
        this.lastUpdateTimeStamp = lastUpdateTimeStamp;
    }

    public Long getDeletionTimeStamp() {
        return deletionTimeStamp;
    }

    public void setDeletionTimeStamp(Long deletionTimeStamp) {
        this.deletionTimeStamp = deletionTimeStamp;
    }
}
