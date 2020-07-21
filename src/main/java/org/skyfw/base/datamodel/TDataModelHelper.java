package org.skyfw.base.datamodel;

import org.skyfw.base.classes.validation.TPreconditions;
import org.skyfw.base.datamodel.exception.TDataModelException;
import org.skyfw.base.datamodel.exception.TDataModelFieldException;
import org.skyfw.base.datamodel.exception.TDataModelFieldExceptionBuilder;
import org.skyfw.base.datamodel.exception.TFieldNotExistsException;
import org.skyfw.base.exception.TException;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.exception.general.TNullArgException;
import org.skyfw.base.mcodes.TMCodeSeverity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TDataModelHelper {



    //------------------------------------------------------------------------------------------------------------------
    public static TDataModelDescriptor getDataModelDescriptor(TDataModel dataModel)
            throws TDataModelException, TIllegalArgumentException{

        // >>> For sake of performance
        /*TPreconditions.checkArgForNotNull(dataModel, "dataModel");*/
        if (dataModel == null)
            throw new TNullArgException("dataModel");

        TDataModelDescriptor descriptor= dataModel.getDescriptorCache();
        if (descriptor != null)
            return descriptor;

        Class<? extends TDataModel> dataModelClass = dataModel.getClass();
        descriptor= TDataModelRegistry.get(dataModelClass.getName(), false);

        if (descriptor != null)
            return descriptor;

        return TDataModelInitiator.init(dataModelClass);
    }

    //------------------------------------------------------------------------------------------------------------------
    public static TDataModelDescriptor getDataModelDescriptor(Class<? extends TDataModel> dataModelClass)
            throws TDataModelException, TIllegalArgumentException{

        if (dataModelClass == null)
            throw new TNullArgException("dataModelClass");

        TDataModelDescriptor descriptor= TDataModelRegistry.get(dataModelClass.getName(), false);

        if (descriptor != null)
            return descriptor;

        return TDataModelInitiator.init(dataModelClass);
    }


    //------------------------------------------------------------------------------------------------------------------
    public static TFieldDescriptor getFieldDescriptor(TDataModel dataModel, Object fieldName)
            throws TDataModelException, TIllegalArgumentException{

        if (dataModel == null)
            throw new TNullArgException("dataModel");

        if (fieldName == null)
            throw new TNullArgException("fieldName");

        TDataModelDescriptor dataModelDescriptor= getDataModelDescriptor(dataModel);

        TFieldDescriptor fieldDescriptor= dataModelDescriptor.getFields().get(fieldName);

        if (fieldDescriptor == null) {
            /*throw*/
            new TFieldNotExistsException(TDataModelMCodes.FIELD_NAME_NOT_EXISTS
                    , TMCodeSeverity.TRACE, null, dataModel.getClass().getName(), fieldName.toString()).log();
            return null;
        }

        return fieldDescriptor;
    }






    //------------------------------------------------------------------------------------------------------------------
    public static Object getFieldValue(TDataModel dataModel, TFieldDescriptor fieldDescriptor)
            throws TDataModelException, TIllegalArgumentException {

        if (dataModel == null)
            throw new TNullArgException("dataModel").log();

        if (fieldDescriptor == null)
            throw new TNullArgException("fieldDescriptor").log();

        Object fieldValue= null;

        // >>> Getter method way
        //---------------------------------------------
        Method getterMethod= fieldDescriptor.getGetterMethod();
        if (getterMethod != null)
            try {
                fieldValue = getterMethod.invoke(dataModel);
                return fieldValue;

            }catch (InvocationTargetException e){
                validateGetterMethod(dataModel.getClass().getName(), fieldDescriptor, e);
            }/*catch (IllegalAccessException e){

            }*/ catch (Exception e){

                throw TDataModelFieldException.create(
                        TDataModelMCodes.EXCEPTION_ON_CALLING_GETTER_METHOD
                        , TMCodeSeverity.FATAL
                        , dataModel.getClass().getName()
                        , fieldDescriptor.getFieldName(), e).log();
            }


        // >>> Reflection field way
        //---------------------------------------------
        Field reflectionField= fieldDescriptor.getReflectionField();
        if (reflectionField != null)
            try {
                fieldValue = reflectionField.get(dataModel);
                return fieldValue;

            }catch (IllegalAccessException e) {
                throw TDataModelFieldException.create(
                        TDataModelMCodes.EXCEPTION_ON_GET_BY_REFLECTION_FIELD
                        , TMCodeSeverity.FATAL
                        , dataModel.getClass().getName()
                        , fieldDescriptor.getFieldName(), e).log();
            }



        // >>> Throw exception in case of neither
        // getter method or reflection field available
        //---------------------------------------------
        throw TDataModelException.create(
                TDataModelMCodes.NEITHER_GETTER_METHOD_OR_REFLECTION_FIELD_AVAILABLE
                , TMCodeSeverity.ERROR
                , dataModel.getClass().getName(), null).log();
    }



    //------------------------------------------------------------------------------------------------------------------
    public static void setFieldValue(TDataModel dataModel, TFieldDescriptor fieldDescriptor, Object value)
            throws TDataModelException, TIllegalArgumentException {

        if (dataModel == null)
            throw new TNullArgException("dataModel").log();

        if (fieldDescriptor == null)
            throw new TNullArgException("fieldDescriptor").log();

        // >>> Its logically better to just throw an exception in case of type mismatch
        // converting types to each other may cause horrible mistakes ...
        /*Class fieldType= fieldDescriptor.getType();
        if ( ! fieldType.isAssignableFrom(value.getClass())){
            if ( (value instanceof Number) && (Number.class.isAssignableFrom(fieldType  )))
                value= TNumbers.create(value, fieldType);
        }*/


        // >>> Setter method way
        //---------------------------------------------
        Method setterMethod= fieldDescriptor.getSetterMethod();
        if (setterMethod != null)
            try {
                setterMethod.invoke(dataModel, value);
                return;

            }catch (InvocationTargetException e){
                validateSetterMethod(dataModel.getClass().getName(), fieldDescriptor, e);
            }/*catch (IllegalAccessException e){


            }*/ catch (Throwable e){

                throw TDataModelFieldException.create(
                        TDataModelMCodes.EXCEPTION_ON_CALLING_SETTER_METHOD
                        , TMCodeSeverity.FATAL
                        , dataModel.getClass().getName()
                        , fieldDescriptor.getFieldName(), e);
            }



        // >>> Reflection field way
        //---------------------------------------------
        Field reflectionField= fieldDescriptor.getReflectionField();
        if (reflectionField != null)
            try {
                reflectionField.set(dataModel, value);
                return;

            }catch (Throwable e) {
                throw TDataModelFieldException.create(
                        TDataModelMCodes.EXCEPTION_ON_SET_BY_REFLECTION_FIELD
                        , TMCodeSeverity.FATAL
                        , dataModel.getClass().getName()
                        , fieldDescriptor.getFieldName(), e);
            }



        // >>> Throw exception in case of neither
        // getter method or reflection field available
        //---------------------------------------------
        throw TDataModelException.create(
                TDataModelMCodes.NEITHER_SETTER_METHOD_OR_REFLECTION_FIELD_AVAILABLE
                , TMCodeSeverity.ERROR
                , fieldDescriptor.getFieldName(), null);

    }



    //------------------------------------------------------------------------------------------------------------------
    public static void validateGetterMethod(String className, TFieldDescriptor fieldDescriptor, Throwable cause)
            throws TDataModelFieldException{

        Method getterMethod= fieldDescriptor.getGetterMethod();
        Field field= fieldDescriptor.getReflectionField();

        TDataModelFieldExceptionBuilder exceptionBuilder= new TDataModelFieldExceptionBuilder();
        exceptionBuilder.setDataModelClassPath(className).setFieldName(fieldDescriptor.getFieldName()).setCause(cause)
        .setSeverity(TMCodeSeverity.FATAL);


        if (getterMethod.getParameterTypes().length != 0)
            throw exceptionBuilder.setmCode(TDataModelMCodes.GETTER_METHOD_INVALID_ARGUMENTS_COUNT).create();

        if ( ! getterMethod.getReturnType().equals(field.getType()))
            throw exceptionBuilder.setmCode(TDataModelMCodes.GETTER_METHOD_WRONG_RETURN_TYPE).create();
    }



    //------------------------------------------------------------------------------------------------------------------
    public static void validateSetterMethod(String className, TFieldDescriptor fieldDescriptor, Throwable cause)
            throws TDataModelFieldException{

        Method setterMethod= fieldDescriptor.getSetterMethod();
        Field field= fieldDescriptor.getReflectionField();

        TDataModelFieldExceptionBuilder exceptionBuilder= new TDataModelFieldExceptionBuilder();
        exceptionBuilder.setDataModelClassPath(className).setFieldName(fieldDescriptor.getFieldName()).setCause(cause)
                .setSeverity(TMCodeSeverity.FATAL);

        if (setterMethod.getParameterTypes().length != 1)
            throw exceptionBuilder.setmCode(TDataModelMCodes.SETTER_METHOD_INVALID_ARGUMENTS_COUNT).create();

        if ( ! setterMethod.getParameterTypes()[0].equals(field.getType()))
            throw exceptionBuilder.setmCode(TDataModelMCodes.SETTER_METHOD_WRONG_ARGUMENT_TYPE).create();

        if ( ! setterMethod.getReturnType().getName().equals("void"))
            throw exceptionBuilder.setmCode(TDataModelMCodes.SETTER_METHOD_WRONG_RETURN_TYPE).create();
    }




















    // >>> Violation of code duplication prevention principle for sake of performance.

    public static Object getFieldValueOrNull(TDataModel dataModel, TFieldDescriptor fieldDescriptor
            , TMCodeSeverity severity){
        try{
            return getFieldValue(dataModel, fieldDescriptor);
        }catch (TException e){
            e.setSeverity(severity);
            e.log();
            return null;
        }
    }

    public static Object getFieldValueOrNull(TDataModel dataModel, TFieldDescriptor fieldDescriptor){
        try{
            return getFieldValue(dataModel, fieldDescriptor);
        }catch (TException e){
            e.log();
            return null;
        }
    }

    public static Object getFieldValueOrValue(TDataModel dataModel, TFieldDescriptor fieldDescriptor, Object value)
            throws TDataModelException, TIllegalArgumentException{

        Object fieldValue=  getFieldValue(dataModel, fieldDescriptor);
        if (fieldValue != null)
            return fieldValue;
        else
            return value;
    }

}
