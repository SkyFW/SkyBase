package org.skyfw.base.datamodel;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.skyfw.base.classes.TObjects;
import org.skyfw.base.classes.TType;
import org.skyfw.base.collection.TMapWalker;
import org.skyfw.base.datamodel.annotation.*;
import org.skyfw.base.datamodel.annotation.GUI.DataModelGUIField;
import org.skyfw.base.datamodel.exception.TDataModelFieldInitializationException;
import org.skyfw.base.datamodel.exception.TDataModelIndexParamInitException;
import org.skyfw.base.datamodel.exception.TDataModelInitializationException;
import org.skyfw.base.datamodel.gui.TGUIFieldDescriptor;
import org.skyfw.base.datamodel.index.TDataModelIndexDescriptor;
import org.skyfw.base.datamodel.index.TDataModelIndexInitiator;
import org.skyfw.base.datamodel.index.TDataModelIndexParam;
import org.skyfw.base.exception.TException;
import org.skyfw.base.log.TLogger;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.result.TResult;
import org.skyfw.base.system.TSystem;
import org.skyfw.base.utils.TObjectCloner;
import org.skyfw.base.utils.TStringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Stack;

public class TDataModelInitiator {
    private static TLogger logger= TLogger.getLogger();

    Class<? extends TDataModel> dataModelClass;
    TDataModelDescriptor dataModelDescriptor;


    public TDataModelInitiator(Class<? extends TDataModel> dataModelClass) {
        this.dataModelClass = dataModelClass;
    }


    // >>> Init
    //------------------------------------------------------------------------------------------------------------------
    public TDataModelDescriptor initByAnnotations() throws TDataModelInitializationException {

        if ( ! TDataModel.class.isAssignableFrom(this.dataModelClass))
            throw  TDataModelInitializationException.create(TDataModelMCodes.CLASS_IS_NOT_EXTENDED_FROM_TDATAMODEL
                    ,this.dataModelClass).log();


        // >>> Get Existing DataModel Descriptor Or Add a New One To DataModels Descriptors Container
        this.dataModelDescriptor= TDataModelRegistry.get(dataModelClass.getName(), true);
        this.dataModelDescriptor.setClazz(dataModelClass);
        this.dataModelDescriptor.setClassName(dataModelClass.getName());


        Class clazz= this.dataModelClass;
        Stack<Class> classesHierarchy= new Stack<Class>();

        classesHierarchy.push(clazz);
        while (clazz != null) {

            clazz = clazz.getSuperclass();
            if ( ! TDataModel.class.isAssignableFrom(clazz))
                break;
            classesHierarchy.push(clazz);
        }

        while ( ! classesHierarchy.isEmpty()){

            initByAnnotations(classesHierarchy.pop());
        }


        // >>> Final checks
        if (this.dataModelDescriptor.getKeyFieldName() == null) {
            /*throw*/ TDataModelInitializationException.create(TDataModelMCodes.NO_KEY_FIELD_IS_DETERMINED
                    , TMCodeSeverity.WARNING, this.dataModelClass, null).log();
            //TSystem.haltAndCatchFire();
        }


        return this.dataModelDescriptor;
    }




    private void initByAnnotations(Class currentClass) throws TDataModelInitializationException{

        // >>> Get DataModel annotation
        DataModel dataModelAnnotation= (DataModel) currentClass.getAnnotation(DataModel.class);
        if (dataModelAnnotation != null){
            this.dataModelDescriptor.dataStoreName= dataModelAnnotation.dataStoreName();
            this.dataModelDescriptor.autoKey= dataModelAnnotation.autoKey();

            // >>> Set key field name
            /*if ( ! TObjects.nullOrEmpty(dataModelAnnotation.keyFieldName()))
                this.dataModelDescriptor.keyFieldName= dataModelAnnotation.keyFieldName();*/
        }


        // >>> In case of no "@DataModel" annotation
        if (TObjects.nullOrEmpty(dataModelDescriptor.dataStoreName))
            this.dataModelDescriptor.dataStoreName=
                    currentClass.getName().substring(currentClass.getName().lastIndexOf(".") + 1);

        if (dataModelDescriptor.autoKey == null)
            this.dataModelDescriptor.autoKey= true;


        this.initFields(currentClass);

        this.initIndexes(currentClass, dataModelAnnotation);

        this.initGetterSetterMethods(currentClass);

        this.initStaticHandles(currentClass);

        return;
    }


    private void initFields(Class<? extends TDataModel> currentClass) throws TDataModelInitializationException{

        //Setting Fields Descriptors
        //--------------------------
        boolean keyFieldFound= false;

        Field[] fields = currentClass.getDeclaredFields();
        for (Field field : fields) {
            try {
                // >>> Ignoring ignored fields !
                IgnoredField ignoredFieldAnnotation = field.getAnnotation(IgnoredField.class);
                if (ignoredFieldAnnotation != null)
                    continue;

                // >>> Skip static fields
                if (java.lang.reflect.Modifier.isStatic(field.getModifiers()))
                    continue;



                    // >>> Get field descriptor or create one if not exists.
                TFieldDescriptor fieldDescriptor= this.dataModelDescriptor.fields.get(field.getName());
                if (fieldDescriptor == null) {
                    fieldDescriptor = new TFieldDescriptor();
                    fieldDescriptor.setFieldName(field.getName());
                    this.dataModelDescriptor.fields.put(field.getName(), fieldDescriptor);
                }

                // >>> Set reflection field
                field.setAccessible(true);
                fieldDescriptor.setReflectionField(field);



                // >>> Check if this is the "Key Field"
                KeyField keyFieldAnnotation = field.getAnnotation(KeyField.class);
                if (keyFieldAnnotation != null) {
                    if (keyFieldFound){
                        logger.warn("Multiple key fields makes no sense so simply ignored !\n"
                                + "Data Model: " + this.dataModelClass.getName());
                        continue;
                    }

                    if ( ! TObjects.nullOrEmpty(this.dataModelDescriptor.getKeyFieldName())) {
                        TFieldDescriptor previousKeyFieldDescriptor=
                                this.dataModelDescriptor.fields.get(this.dataModelDescriptor.getKeyFieldName());
                        previousKeyFieldDescriptor.setKeyField(false);
                        logger.trace("Data model key field updated: " + this.dataModelClass.getName());
                    }

                    keyFieldFound= true;
                    fieldDescriptor.setKeyField(true);
                    this.dataModelDescriptor.setKeyFieldName(fieldDescriptor.getFieldName());
                }


                /*if (fieldAnnotation == null){
                    logger.warn("There is no \"@DataModelField\" annotation In datamodel ("+currentClass.getName()+") "
                            + "for field (" + field.getName() + ") "
                            + "so it will initialized by default behaviour");
                }*/

                DataModelField fieldAnnotation = field.getAnnotation(DataModelField.class);
                if (fieldAnnotation != null){

                    fieldDescriptor.setFieldDescription(fieldAnnotation.fieldDescription());
                    fieldDescriptor.setdSFieldName(fieldAnnotation.dSFieldName());
                    fieldDescriptor.setLen(fieldAnnotation.fieldLen());
                }

                DDIdField ddIdField = field.getAnnotation(DDIdField.class);
                if (ddIdField != null){
                    fieldDescriptor.setDDId(true);
                }

                // >>> Check if this field is referring to an external DataModel
                if (TDataModel.class.isAssignableFrom(field.getType())) {
                    fieldDescriptor.setForeignDataModel(field.getType());

                    ForeignKey foreignKey = field.getAnnotation(ForeignKey.class);
                    if (foreignKey != null) {
                        fieldDescriptor.setfValueFieldName(foreignKey.representativeFieldName());
                    }
                }

                DataModelGUIField guiFieldAnnotation = field.getAnnotation(DataModelGUIField.class);

                if (guiFieldAnnotation != null){

                    TGUIFieldDescriptor guiFieldDescriptor= new TGUIFieldDescriptor();
                    TObjectCloner.loadFromAnnotation(guiFieldAnnotation, guiFieldDescriptor);
                    fieldDescriptor.setGuiDescriptor(guiFieldDescriptor);
                }


                // >>> Set field type
                switch (field.getType().getName()){
                    // >>> Primitive types
                    case "int": fieldDescriptor.setType(TType.INTEGER);
                        break;
                    case "long": fieldDescriptor.setType(TType.LONG);
                        break;
                    case "double": fieldDescriptor.setType(TType.DOUBLE);
                        break;
                    case "float": fieldDescriptor.setType(TType.FLOAT);
                        break;
                    case "byte": fieldDescriptor.setType(TType.BYTE);
                        break;
                    // >>> Object types
                    default:
                        fieldDescriptor.setType(field.getType());
                }



                // >>> Check For Default Values
                //If dSFieldName Has Default Value (Empty) It Will Be Set As Field Name
                if (TObjects.nullOrEmpty(fieldDescriptor.getdSFieldName()))
                    fieldDescriptor.setdSFieldName(field.getName()/*.substring(0, 1).toUpperCase() + field.getName().substring(1)*/);


                // >>> Detecting The DDIdField FieldName
                if (fieldDescriptor.isDDId())
                    dataModelDescriptor.setdDIdFieldName(fieldDescriptor.getFieldName());

                // >>> Authorization Fields
                AuthField fieldAuthAnnotation = field.getAnnotation(AuthField.class);
                if (fieldAuthAnnotation != null) {
                    fieldDescriptor.getAuthorizationAttributes().put(fieldAuthAnnotation.authFieldName()
                            , fieldAuthAnnotation.authFieldType());
                }


            } catch (Exception e){
                throw TDataModelInitializationException.create(TDataModelMCodes.DATAMODEL_INITIALIZATION_EXCEPTION
                        , this.dataModelClass
                        , e);
            }

        }// >>> End of: Looping Around The Fields Of datamodel Class
    }


    /**
     * >>> Setting a handle to field descriptor in corresponding static field
     * this helps for referring to fields without passing their names as string arg that may cause misspelling in addition
     * to the performance penalty that will happen every time fetching field descriptor from field name  ...
     * @param currentClass
     * @throws TDataModelFieldInitializationException
     */
    private void initStaticHandles(Class<? extends TDataModel> currentClass) throws TDataModelFieldInitializationException {
        Field[] fields = currentClass.getDeclaredFields();
        for (Field field : fields) {

            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()))
                continue;

            if (!java.lang.reflect.Modifier.isTransient(field.getModifiers())) {
                throw new TDataModelFieldInitializationException(
                        TDataModelMCodes.STATIC_FIELD_WITH_NO_TRANSIENT_MODIFIER_IS_AGAINST_DATAMODEL_CONVENTION
                        , TMCodeSeverity.FATAL, null, currentClass.getName(), field.getName());
            }

            if (!field.getType().equals(TFieldDescriptor.class)) {
                continue;
                /*throw new TDataModelFieldInitializationException(
                        TDataModelMCodes.STATIC_FIELD_WITH_TYPE_OTHER_THAN_TFIELDDESCRIPTOR_IS_AGAINST_DATAMODEL_CONVENTION
                        , TMCodeSeverity.FATAL, null, currentClass.getName(), field.getName());*/
            }

            try {
                String[] nameParts = field.getName().split("_");
                StringBuilder mainFieldNameSB = new StringBuilder();//String.join("_", nameParts);
                mainFieldNameSB.append(nameParts[0].toLowerCase());
                for (int i = 1; i < nameParts.length; i++) {
                    String namePart = nameParts[i];
                    mainFieldNameSB.append(TStringUtils.capitalize(namePart.toLowerCase()));
                }

                TFieldDescriptor fieldDescriptor = this.dataModelDescriptor.fields.get(mainFieldNameSB.toString());
                if (fieldDescriptor == null) {
                    throw new TDataModelFieldInitializationException(
                            TDataModelMCodes.NO_CORRESPONDING_FIELD_FOUND_FOR_DEFINED_STATIC_HANDLE
                            , TMCodeSeverity.FATAL, null, currentClass.getName(), field.getName());
                }

                FieldUtils.removeFinalModifier(field);
                FieldUtils.writeStaticField(field, fieldDescriptor);

                // >>> Check the job to being successful
                Object currentValue= FieldUtils.readStaticField(field);
                if (currentValue != fieldDescriptor)
                    throw new TDataModelFieldInitializationException(
                            TDataModelMCodes.STATIC_FIELD_HANDLER_AFTER_SET_CHECK_FAILED
                            , TMCodeSeverity.FATAL, null, currentClass.getName(), field.getName());

            } catch (TDataModelFieldInitializationException e) {
                throw e;
            } catch (Throwable e) {
                throw new TDataModelFieldInitializationException(
                        TDataModelMCodes.EXCEPTION_ON_SETTING_FIELD_DESCRIPTOR_STATIC_HANDLE
                        , TMCodeSeverity.FATAL, e, currentClass.getName(), field.getName());
            }
        }
    }


    // >>> Setting The DataModel IndexEntries
    // -----------------------------------------------------------------------------------------------------------------
    private void initIndexes(Class<? extends  TDataModel> currentClass, DataModel dataModelAnnotation)
            throws TDataModelIndexParamInitException {


        // (New Way)
        //--------------------------------------------
        String index = null;
        //outerLoop:
        if (dataModelAnnotation != null && dataModelAnnotation.indexes() != null)
        for (int i = 0; i < dataModelAnnotation.indexes().length; i++) {
            TDataModelIndexInitiator indexInitiator= new TDataModelIndexInitiator(dataModelDescriptor);

            index = dataModelAnnotation.indexes()[i];
            if ((index == null) || (index.isEmpty()))
                continue;

            String[] parsedIndex = index.split("@");
            boolean failed = false;
            for (int j = 0; j < parsedIndex.length; j++) {

                if (parsedIndex[j].isEmpty())
                    continue;
                indexInitiator.addParam(parsedIndex[j], 0);
            }
        }


        // (Old Way)
        //--------------------------------------------
        try {
            Annotation[] annotations = currentClass.getDeclaredAnnotationsByType(DataModelIndexEntry.class);

            if (annotations != null)
                for (Annotation a : annotations) {
                    DataModelIndexEntry indexEntryAnnotation = (DataModelIndexEntry) a;
                    TDataModelIndexInitiator indexInitiator = new TDataModelIndexInitiator(dataModelDescriptor);

                    for (IndexParam indexParamAnnotation : indexEntryAnnotation.value())
                        indexInitiator.addParam(indexParamAnnotation.fieldName(), indexParamAnnotation.dataSegmentSize());

                    indexInitiator.init();
                }

        } catch (TDataModelIndexParamInitException e) {
            throw e;
        } catch (Throwable e) {
            logger.warn("getDeclaredAnnotationsByType() Failed".concat(e.getStackTrace().toString()));
        }

    }





/*
    public synchronized static TResult finalInit(Class dataModelClass) {

        //String dataStoreName, Boolean autoKey

        //Class dataModelClass= dataModel.getClass();
        String className = dataModelClass.getName();//.toUpperCase();
        if ( ! TDataModelRegistry.containsKey(className))
            TDataModelRegistry.put(className, new TDataModelDescriptor());

        TDataModelDescriptor dataModelDescriptor= TDataModelRegistry.get(className);
        //dataModelDescriptor.dataStoreName= dataStoreName;
        //dataModelDescriptor.autoKey= autoKey;

        try {
            String fieldName= null;
            Iterator fieldsNamesIterator = dataModelDescriptor.fields.keySet().iterator();
            while (fieldsNamesIterator.hasNext()) {
                fieldName = fieldsNamesIterator.next().toString();
                TFieldDescriptor dataModelFieldDescriptor = dataModelDescriptor.fields.get(fieldName);
                //Detecting The KeyFieldName
                if ((dataModelDescriptor.keyFieldName == null) || (dataModelDescriptor.keyFieldName.isEmpty()))
                    if (dataModelFieldDescriptor.isKeyField())
                        dataModelDescriptor.keyFieldName= dataModelFieldDescriptor.getFieldDescriptor();

                //Detecting The DDIdField FieldName
                if ((dataModelDescriptor.getdDIdFieldName() == null) || (dataModelDescriptor.getdDIdFieldName().isEmpty()))
                    if (dataModelFieldDescriptor.isDDId())
                        dataModelDescriptor.setdDIdFieldName(dataModelFieldDescriptor.getFieldDescriptor());

                //Detecting And Saving The Fields Getter & Setter Methods Automatically.
                Method methods[] = dataModelClass.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.getName().toLowerCase().equals(("get" + dataModelFieldDescriptor.getFieldDescriptor()).toLowerCase()))
                        dataModelFieldDescriptor.setGetterMethod(method);
                    if (method.getName().toLowerCase().equals(("set" + dataModelFieldDescriptor.getFieldDescriptor()).toLowerCase()))
                        dataModelFieldDescriptor.setSetterMethod(method);
                }

                //Setting The Getter Method And Setter Method Accessible
                //Just In Case If They Defined As "private"
                if (dataModelFieldDescriptor.getGetterMethod() != null )
                    dataModelFieldDescriptor.getGetterMethod().setAccessible(true);
                if (dataModelFieldDescriptor.getSetterMethod() != null )
                    dataModelFieldDescriptor.getSetterMethod().setAccessible(true);


                //Detecting And Saving The Fields Type(As String Name) Automatically.
                Field[] fields = dataModelClass.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().toLowerCase().equals(dataModelFieldDescriptor.getFieldDescriptor().toLowerCase()))
                        dataModelFieldDescriptor.setType(field.getSeverity().getName());
                }
                //dataModelDescriptor.fields.put(dataModelFieldDescriptor.getFieldDescriptor(), dataModelFieldDescriptor);
                TDataModelRegistry.addNewFieldDescriptor(className
                        , dataModelFieldDescriptor.getFieldDescriptor()
                        , dataModelFieldDescriptor);


                if (dataModelFieldDescriptor.getGetterMethod() == null)
                    logger.warn("There Is No Getter Method In datamodel ("+className+") "
                                + "For Field ("+dataModelFieldDescriptor.getFieldDescriptor()+")"
                                + "\nThis Can Cause Strange Behavior In Your Business Logic ...");

                if (dataModelFieldDescriptor.getSetterMethod() == null)
                    logger.warn("There Is No Setter Method In datamodel ("+className+") "
                            + "For Field ("+dataModelFieldDescriptor.getFieldDescriptor()+")"
                            + "\nThis Can Cause Strange Behavior In Your Business Logic ...");

            }//End Of: Looping Around All Fields Of Data Model


        } catch (exception c) {
            logger.error("");
            try {
                throw new exception();
            } catch (exception e) {
                e.printStackTrace();
            }
            return TResultBuilder.getTResultBuilder().setGeneralErrorCode(TResultCode.BAD_REQUEST).createTResult();
        }

        return TResultBuilder.getTResultBuilder().setGeneralErrorCode(TResultCode.SUCCESS).createTResult();
    }
*/



    private void initGetterSetterMethods(Class currentClass) throws TDataModelInitializationException {

        TMapWalker<String, TFieldDescriptor> mapWalker =
                new TMapWalker<>(this.dataModelDescriptor.fields);

        while (mapWalker.next()) {
            try {
                String fieldName = mapWalker.getKey();
                TFieldDescriptor fieldDescriptor = mapWalker.getValue();

                // >>> Detecting the field's getter & setter methods
                Method methods[] = currentClass.getDeclaredMethods();
                for (Method method : methods) {
                    boolean matched = false;
                    String expectedMethodName;

                    if ((fieldDescriptor.getGetterMethod() != null) && (fieldDescriptor.getSetterMethod() != null))
                        break;

                    // >>> Check if this is corresponding "getter method"
                    expectedMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    if (method.getName().equals(expectedMethodName))
                        matched = true;
                    else {
                        expectedMethodName = "is" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        if (method.getName().equals(expectedMethodName))
                            matched = true;
                    }

                    if (matched) {
                        fieldDescriptor.setGetterMethod(method);
                        TDataModelHelper.validateGetterMethod(currentClass.getName(), fieldDescriptor, null);
                        continue;
                    }

                    // >>> Check if this is corresponding "setter method"
                    expectedMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    if (method.getName().equals(expectedMethodName))
                        matched = true;

                    if (matched) {
                        fieldDescriptor.setSetterMethod(method);
                        TDataModelHelper.validateSetterMethod(currentClass.getName(), fieldDescriptor, null);
                        continue;






                    }

                }


                if (fieldDescriptor.getGetterMethod() != null) {
                    fieldDescriptor.getGetterMethod().setAccessible(true);
                } else
                    logger.debug(TDataModelMCodes.GETTER_METHOD_NOT_FOUND
                                , currentClass.getName(), fieldDescriptor.getFieldName());


                if (fieldDescriptor.getSetterMethod() != null) {
                    fieldDescriptor.getSetterMethod().setAccessible(true);
                } else
                    logger.debug(TDataModelMCodes.SETTER_METHOD_NOT_FOUND
                            , currentClass.getName(), fieldDescriptor.getFieldName());



            } catch (Exception e) {

                throw TDataModelInitializationException.create(TDataModelMCodes.DATAMODEL_INITIALIZATION_EXCEPTION
                        , this.dataModelClass
                        , e);
            }

        }// >>> End Of: Looping Around All Fields Of Data Model
        return;
    }








    public static TDataModelDescriptor init(Class<? extends TDataModel> dataModelClass) throws TDataModelInitializationException{

        TDataModelInitiator dataModelInitiator= new TDataModelInitiator(dataModelClass);
        return dataModelInitiator.initByAnnotations();
    }


}
