package org.skyfw.base.datamodel;

import org.jetbrains.annotations.NotNull;
import org.skyfw.base.collection.TDataTreeSet;
import org.skyfw.base.datamodel.comparator.TDataModelComparator;
import org.skyfw.base.datamodel.exception.TDataModelException;
import org.skyfw.base.datamodel.implimentations.guava.TSortedDataSetImpl;
import org.skyfw.base.exception.general.TIllegalArgumentException;
import org.skyfw.base.exception.general.TNullArgException;
import org.skyfw.base.serializing.TSerializable;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;


public class TDataSet<E extends TDataModel> extends LinkedList<E> implements TSerializable<TDataSet<E>> {

    private transient Class<E> dataModelClass;
    //private E models[];
    //private LinkedList<E> orginalSet;// = new LinkedList<>();
    private transient LinkedHashMap<String, TSortedDataSet<E>> sortedDataSetContainer = new LinkedHashMap<>();

    /*
    public TDataSet(Class<E> dataModelClass) {
        orginalSet= new LinkedList<E>();
    }*/


    //The Lack of This No Arg Constructor Could Cause Problem When We Try To Deserialize From Json
    //At Least This Tested With "GSON"
    public TDataSet() {
        super();

        /*ChildClass childClass= new ChildClass<E>() {};
        System.out.println(childClass.dataModelClass);

        this.dataModelClass =
                (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];*/
    }


    public TDataSet(@NotNull E[] dataModels) {
        /*models = dataModels;
        for (int i = 0; i < models.length; i++)
            orginalSet.add(models[i]);
        */

        super(Arrays.asList(dataModels));

        if (dataModels == null)
            Objects.requireNonNull(dataModels);

        this.dataModelClass = (Class<E>) dataModels.getClass().getComponentType();
    }

    public TDataSet(Collection<E> dataModels) {
        //super((LinkedList<E>) dataModels.clone());
        super(dataModels);

        if ( ! dataModels.isEmpty())
            this.dataModelClass = (Class<E>) dataModels.iterator().next().getClass();

    }





    public void sortBy(String fieldName) throws TDataModelException, TIllegalArgumentException {

        if (this.size() == 0)
            return;

        TFieldDescriptor fieldDescriptor= TDataModelHelper.getFieldDescriptor(this.getFirst(), fieldName);
        if (fieldDescriptor == null)
            return;

        Class fieldClass= fieldDescriptor.getType();
        if (fieldClass == null)
            return;


        TDataModelComparator comparator= TDataModelComparator.create(fieldDescriptor);

        /*
        if (fieldClass.equals(Long.class) || fieldClass.equals(Integer.class))
            comparator = new TDataModelNumericComparator();

        if (fieldClass.equals(String.class))
            comparator = new TDataModelStringComparator();

        if (comparator == null)
            return;
        */


        // >>> "ArrayList.sort" added in api level 24 (android 7)
        // so instead "collections.sort" should be used
        // this.sort(comparator);
        Collections.sort(this, comparator);

    }




    public TSortedDataSet<E> getSortedSet(TFieldDescriptor fieldDescriptor){

        TSortedDataSet<E> sortedDataSet= this.sortedDataSetContainer.get(fieldDescriptor.getFieldName());
        if (sortedDataSet == null){
            sortedDataSet = new TDataTreeSet<>(fieldDescriptor, this);
            this.sortedDataSetContainer.put(fieldDescriptor.getFieldName(), sortedDataSet);
        }
        return sortedDataSet;
    }


    public TSortedDataSet<E> search(String fieldName, Object param1, Object param2)
            throws TDataModelException, TIllegalArgumentException {

        TFieldDescriptor fieldDescriptor= TDataModelHelper.getFieldDescriptor(this.getFirst(), fieldName);

        if (this.isEmpty())
            return new TDataTreeSet<>(fieldDescriptor);

        return this.getSortedSet(fieldDescriptor)
                .subSetByFieldValue(param1, true,  param2, false);
    }


    public TSortedDataSet<E> search(String fieldName, Object fieldValue)
            throws TDataModelException, TIllegalArgumentException {

        TFieldDescriptor fieldDescriptor= TDataModelHelper.getFieldDescriptor(this.getFirst(), fieldName);

        if (this.isEmpty())
            return new TDataTreeSet<>(fieldDescriptor);

        return this.getSortedSet(fieldDescriptor)
                .subSetByFieldValue(fieldValue);
    }




    public Object[] toArray() {
        /*
        this.getDataModelClass();
        Array.newInstance()
        this.getClass().newInstance()
        TDataModel array[] = new TDataModel[models.size()];
        for (int i = 0; i < models.size(); i++)
            array[i] = models.get(i);
        return array;*/

        /*Object[] array= super.toArray();

        if (this.isEmpty())
            return (E[]) array;

        E[]result= (E[])Array.newInstance(this.getDataModelClass(), this.size());
        for (int i = 0; i < array.length; i++)
            result[i] = (E)array[i];

        return result;
        */

        return super.toArray();
    }

    // >>>
    //------------------------------------------------------------------------------------------------------------------
    protected TDataSet<E> seek(TFieldDescriptor fieldDescriptor
            , @NotNull Comparable minValue, @NotNull Comparable maxValue
            , boolean assumeSorted) throws TDataModelException, TIllegalArgumentException {

        // >>> Check input args
        if (minValue == null)
            throw TNullArgException.create("minValue");
        if (maxValue == null)
            throw TNullArgException.create("maxValue");

        TDataSet<E> result = new TDataSet<>();

        // >>> Neither minValue and maxValue is not null
        for (int i = 0; i < this.size(); i++) {
            E currentItem= this.get(i);
            Comparable currentValue = (Comparable) TDataModelHelper.getFieldValue(currentItem, fieldDescriptor);
            if (currentValue != null) {
                if (currentValue.compareTo(minValue) >= 0) {
                    if (currentValue.compareTo(maxValue) <= 0)
                        result.add(currentItem);
                    else if (assumeSorted)
                        break;
                }
            }
        }

        return result;
    }

    // >>>
    //------------------------------------------------------------------------------------------------------------------
    public TDataSet<E> seek(String fieldName
            , @NotNull Comparable minValue, @NotNull Comparable maxValue
            , boolean assumeSorted) throws TDataModelException, TIllegalArgumentException {

        if (this.isEmpty())
            return this;

        TFieldDescriptor fieldDescriptor= TDataModelHelper.getFieldDescriptor(this.getFirst(), fieldName);
        return this.seek(fieldDescriptor, minValue, maxValue, assumeSorted);
    }

    // >>>
    //------------------------------------------------------------------------------------------------------------------
    public TDataSet<E> seek(TFieldDescriptor fieldDescriptor, Comparable fieldValue)
            throws TDataModelException, TIllegalArgumentException {

        TDataSet<E> dataSet= new TDataSet<>();
        // >>> Performance considered
        if (fieldValue != null) {
            for (int i = 0; i < this.size(); i++) {
                E currentItem= this.get(i);
                Comparable currentValue= (Comparable) TDataModelHelper.getFieldValue(currentItem, fieldDescriptor);
                if ((currentValue != null) && (currentValue.equals(fieldValue)))
                    dataSet.add(currentItem);
            }

        } else {
            for (int i = 0; i < this.size(); i++) {
                E currentItem= this.get(i);
                if (TDataModelHelper.getFieldValue(currentItem, fieldDescriptor) == null)
                    dataSet.add(currentItem);
            }

        }

        return dataSet;
    }




    // >>> Overriding all manipulator methods
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public boolean add(E e) {
        return super.add(e);
    }

    @Override
    public void addFirst(E e) {
        super.addFirst(e);
    }

    @Override
    public void addLast(E e) {
        super.addLast(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return super.addAll(index, c);
    }

    @Override
    public E set(int index, E element) {
        return super.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        super.add(index, element);
    }

    @Override
    public E remove(int index) {
        return super.remove(index);
    }

    @Override
    public E poll() {
        return super.poll();
    }

    @Override
    public E remove() {
        return super.remove();
    }

    @Override
    public E pollFirst() {
        return super.pollFirst();
    }

    @Override
    public E pollLast() {
        return super.pollLast();
    }

    @Override
    public void push(E e) {
        super.push(e);
    }

    @Override
    public E pop() {
        return super.pop();
    }











    public Class<E> getDataModelClass(){

        //TDataSet<E> a = new TDataSet<E>(E) {};
        if (this.dataModelClass != null)
            return this.dataModelClass;
        else if ( ! this.isEmpty())
            return (Class<E>) this.get(0).getClass();
        else
            return null;
    }


    private class B extends TDataSet<E> {
        /* ... */
        public void anything() {
            // here I may use getClazz();
        }
    }
    //------------------------------------------------------------------------------------------------------------------


    @Override
    public TDataSet<E> clone() {
        return (TDataSet<E>) super.clone();
    }




    private abstract class ChildClass<T>{
        protected Class<T> dataModelClass;
        public ChildClass() {
            T a=  (T) "";
            getType(a);

            Type sooper = getClass().getGenericSuperclass();
            Type t = ((ParameterizedType)sooper).getActualTypeArguments()[ 0 ];

            this.dataModelClass =
                    (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }

        private Class<T> getType(T object){
            return null;
        }
    }

}

