package org.skyfw.base.datamodel;

import org.skyfw.base.datamodel.exception.TDataModelException;
import org.skyfw.base.exception.TException;

import java.util.Iterator;
import java.util.Set;

public class TDataModelPrinter {

    public static void print(TDataModel dataModel) throws TDataModelException {

        System.out.println("---");
        Set<String> keySet= dataModel.keySet();
        keySet.forEach(fieldName -> {
            try {

                TFieldDescriptor fieldDescriptor =
                        TDataModelHelper.getFieldDescriptor(dataModel, fieldName);

                System.out.print("Name :" + fieldDescriptor.getFieldName());
                System.out.print("  |  " + "Type :" + fieldDescriptor.getType());
                System.out.print("  |  " + "Value :" + dataModel.get(fieldDescriptor.getFieldName()));
                System.out.println("  |  " + "fValueFieldName :" + fieldDescriptor.getfValueFieldName());
            }catch (TException e){
                e.log();
            }
        });

        System.out.println("---");


        Iterator values = dataModel.values().iterator();
        while (values.hasNext()) {
            System.out.println(values.next());
        }


    }


}
