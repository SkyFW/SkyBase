package org.skyfw.base.utils;

//import com.sun.org.apache.xpath.internal.operations.Bool;
import org.skyfw.base.collection.THashMap;
import org.skyfw.base.log.TLogger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TObjectCloner {
    static TLogger logger= TLogger.getLogger();

    public static void copy(Object from, Object to) throws Exception {
        TObjectCloner.copy(from, to, Object.class);
    }


    public static <T extends Annotation> void loadFromAnnotation(T srcObject, Object dstObject) throws Exception {

        try {
            //Vars(s)
            boolean isAccessible = false;
            Class srcClass = srcObject.annotationType();
            Class dstClass = dstObject.getClass();

            Class depth = Object.class;

            THashMap<String, Object> srcValues = new THashMap<String, Object>();
            THashMap<String, Object> dstValues = new THashMap<String, Object>();


            for (Method method : srcClass.getDeclaredMethods()) {
                Object value = null;
                try {
                    value = method.invoke(srcObject);
                } catch (Exception e) {
                    System.out.println("aaa");
                }
                srcValues.put(method.getName(), value);
            }

            List<Field> dstFields = collectFields(dstClass, depth);


            Field target;
            for (Field dstField : dstFields) {
                isAccessible = dstField.isAccessible();
                dstField.setAccessible(true);
                try {

                    Object value = srcValues.get(dstField.getName());

                    if (value == null) {
                        //if ((dstField.getSeverity().equals(Boolean.class))
                        //||  (dstField.getSeverity().equals(boolean.class)))
                        //dstField.set(dstObject, false);
                        //dstField.set(dstObject, null);
                        //else
                        continue;
                    }

                    if (value.getClass().equals(dstField.getType()))
                        dstField.set(dstObject, value);
                } finally {
                    dstField.setAccessible(isAccessible);
                }

            }

        }catch (Exception e){
            logger.fatal("", e);
        }

    }




    public static void copy(Object from, Object to, Class depth) throws Exception {
        Class fromClass = from.getClass();
        Class toClass = to.getClass();



        List<Field> fromFields = collectFields(fromClass, depth);
        List<Field> toFields = collectFields(toClass, depth);
        Field target;
        for (Field source : fromFields) {
            if ((target = findAndRemove(source, toFields)) != null) {
                target.set(to, source.get(from));
            }
        }
    }

    private static List<Field> collectFields(Class c, Class depth) {
        List<Field> accessibleFields = new ArrayList<>();
        do {
            int modifiers;
            for (Field field : c.getDeclaredFields()) {
                modifiers = field.getModifiers();
                if (!Modifier.isStatic(modifiers) /*&& Modifier.isPublic(modifiers)*/) {
                    accessibleFields.add(field);
                }
            }
            c = c.getSuperclass();
        } while (c != null && c != depth);
        return accessibleFields;
    }

    private static Field findAndRemove(Field field, List<Field> fields) {
        Field actual;
        for (Iterator<Field> i = fields.iterator(); i.hasNext();) {
            actual = i.next();
            if (field.getName().equals(actual.getName())
                    && field.getType().equals(actual.getType())) {
                i.remove();
                return actual;
            }
        }
        return null;
    }
}


