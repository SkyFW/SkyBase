package org.skyfw.base.datamodel.comparator;


import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.datamodel.TFieldDescriptor;
import org.skyfw.base.datamodel.TDataModelHelper;
import org.skyfw.base.datamodel.TDataModelMCodes;
import org.skyfw.base.datamodel.exception.TDataModelComparatorException;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.system.TSystem;

import java.util.Comparator;

public class TDataModelComparator<T extends Comparable<T>> implements Comparator<TDataModel> {

    TFieldDescriptor fieldDescriptor;
    String fieldName;

    public TDataModelComparator(TFieldDescriptor fieldDescriptor) {
        this.fieldDescriptor = fieldDescriptor;
        this.fieldName= fieldDescriptor.getFieldName();
    }

    @Override
    public int compare(TDataModel dm1, TDataModel dm2) {

        //return (x < y) ? -1 : ((x == y) ? 0 : 1);
        try {
            T value1 = (T) TDataModelHelper.getFieldValue(dm1, fieldDescriptor);
            T value2 = (T) TDataModelHelper.getFieldValue(dm2, fieldDescriptor);
            //T value1= (T)dm1.get(fieldName);
            //T value2= (T)dm2.get(fieldName);

            // >>> Performance considered in implementation
            if (value1 == null) {
                if (value2 == null) return 0; else return -1;
            }

            if (value2 == null) {
                return 1;
            }

            return (value1).compareTo(value2);


        } catch (Throwable e){
            TDataModelComparatorException.create(
                    TDataModelMCodes.DATAMODEL_COMPARATOR_EXCEPTION
                    , TMCodeSeverity.FATAL
                    , e
                    , dm1.getClass().getName()
                    , dm2.getClass().getName()
                    , this.fieldDescriptor.getFieldName()).log();
            TSystem.haltAndCatchFire();
            return 0; // >>> it will never run
        }
    }




    public static TDataModelComparator create(TFieldDescriptor fieldDescriptor){

        Class fieldType= fieldDescriptor.getType();

        if (fieldType.equals(String.class)) {
            return new TDataModelComparator<String>(fieldDescriptor);

        } else if(fieldType.equals(Long.class)){
            return new TDataModelComparator<Long>(fieldDescriptor);

        } else if(fieldType.equals(Integer.class)){
            return new TDataModelComparator<Integer>(fieldDescriptor);

        } else if(fieldType.equals(Short.class)){
            return new TDataModelComparator<Short>(fieldDescriptor);

        } else if(fieldType.equals(Byte.class)){
            return new TDataModelComparator<Byte>(fieldDescriptor);

        }

        return null;
    }



}
