package org.skyfw.base.serializing;

import org.skyfw.base.system.classloader.TClassLoader;

import java.util.LinkedList;

public class TGenericType {

    private static final String GENERIC_START_TAG= "<";
    private static final String GENERIC_END_TAG= ">";
    private static final String GENERIC_PARAMS_SEPERATOR= ",";

    private Class clazz;
    TGenericType[] childTypes;

    public TGenericType(String s) {

        this.parseFromString(s);
    }

    public TGenericType() {
    }

    public TGenericType parseFromString(String s){


        //Var(s)
        String className= null;
        int startTagPos= s.indexOf(GENERIC_START_TAG);
        int endTagPos= s.indexOf(GENERIC_END_TAG);

        if ((startTagPos != -1) && (endTagPos != -1)){
            className= s.substring(0, startTagPos);
            String genericParamsStr= s.substring(startTagPos+1, endTagPos);
            String[] genericParams= genericParamsStr.split(GENERIC_PARAMS_SEPERATOR);

            LinkedList<TGenericType> genericTypeLinkedList= new LinkedList<TGenericType>();
            for (int i= 0; i < genericParams.length; i++){
                TGenericType childGenericType= new TGenericType();
                childGenericType.parseFromString(genericParams[i].trim());
                genericTypeLinkedList.add(childGenericType);
                this.childTypes= genericTypeLinkedList.toArray(new TGenericType[genericTypeLinkedList.size()]);
            }
        }else //ToDo: Duplicate Operation? performance?
            className= s.trim();



        this.clazz= TClassLoader.loadByName(className);


        return this;
    }
}
