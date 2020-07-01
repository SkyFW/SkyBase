package org.skyfw.base.datamodel.implimentations.guava;

import com.google.common.collect.BoundType;
import com.google.common.collect.TreeMultiset;
import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.datamodel.TFieldDescriptor;
import org.skyfw.base.datamodel.TDataSet;
import org.skyfw.base.datamodel.TSortedDataSet;
import org.skyfw.base.datamodel.comparator.TDataModelComparator;

import java.util.*;

public class TSortedDataSetImpl<E extends TDataModel>
        implements TSortedDataSet<E> {

    private Class<? extends E> dataModelClass;
    private TFieldDescriptor sortField;
    //TDataModelComparator comparator;

    TreeMultiset<E> deligateTreeMultiset;

    /*public TSortedDataSetImpl(TFieldDescriptor<T> sortField) {

        super(prepareComaprator(sortField));
        this.sortField= sortField;
    }*/

    @SuppressWarnings("Unchecked cast")
    public TSortedDataSetImpl(TFieldDescriptor sortField, E[] dataModels) {
        this.dataModelClass= (Class<? extends E>) dataModels[0].getClass();
        this.sortField = sortField;

        TDataModelComparator comparator= new TDataModelComparator(sortField);
        this.deligateTreeMultiset= TreeMultiset.create(comparator);
        List<E> list = Arrays.asList(dataModels);
        this.deligateTreeMultiset.addAll(list);
    }

    @SuppressWarnings("Unchecked cast")
    public TSortedDataSetImpl(TFieldDescriptor sortField, Collection<E> dataModels) {
        this.dataModelClass= (Class<? extends E>) dataModels.iterator().next().getClass();
        this.sortField = sortField;

        TDataModelComparator comparator= new TDataModelComparator(sortField);
        this.deligateTreeMultiset= TreeMultiset.create(comparator);
        this.deligateTreeMultiset.addAll(dataModels);
    }


    @Override
    public TSortedDataSet<E> clone() {
        return new TSortedDataSetImpl<E>(this.getSortField(), this);
    }


    public TFieldDescriptor getSortField() {
        return sortField;
    }




    @Override
    public TDataModelComparator comparator() {
        return (TDataModelComparator) this.deligateTreeMultiset.comparator();
    }




    // Interface implementation
    //------------------------------------------------------------------------------------------------------------------

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public E first() {
        return this.deligateTreeMultiset.firstEntry().getElement();
    }

    @Override
    public E last() {
        return this.deligateTreeMultiset.lastEntry().getElement();
    }

    @Override
    public int size() {
        return this.deligateTreeMultiset.size();
    }

    @Override
    public boolean isEmpty() {
        return this.deligateTreeMultiset.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return this.deligateTreeMultiset.contains(o);
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }



    public static BoundType boundTypeof(boolean inclusive) {
        return inclusive ? BoundType.CLOSED : BoundType.OPEN;
    }


    //------------------------------------------------------------------------------------------------------------------
    @Override
    public TSortedDataSet<E> subSet(E fromElement, boolean fromInclusive,
                                    E toElement,   boolean toInclusive) {

        return new TSortedDataSetImpl<E>(this.sortField
                , this.deligateTreeMultiset.subMultiset(fromElement
                , boundTypeof(fromInclusive), toElement, boundTypeof(toInclusive)));
    }

    @Override
    public TSortedDataSet<E> subSet(E fromElement, E toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public TSortedDataSet<E> headSet(E toElement, boolean inclusive) {
        return new TSortedDataSetImpl<E>(this.sortField
                , this.deligateTreeMultiset.headMultiset(toElement, boundTypeof(inclusive)));
    }

    @Override
    public TSortedDataSet<E> headSet(E toElement) {
        return headSet(toElement, false);
    }

    @Override
    public TSortedDataSet<E> tailSet(E fromElement, boolean inclusive) {
        return new TSortedDataSetImpl<E>(this.sortField
                , this.deligateTreeMultiset.tailMultiset(fromElement, boundTypeof(inclusive)));
    }

    @Override
    public TSortedDataSet<E> tailSet(E fromElement) {
        return tailSet(fromElement, true);
    }


    @Override
    public TSortedDataSet<E> subSetByFieldValue(Object fromFieldValue, boolean fromInclusive, Object toFieldValue, boolean toInclusive) {
        return null;
    }

    @Override
    public TSortedDataSet<E> subSetByFieldValue(Object fromFieldValue, Object toFieldValue) {
        return null;
    }

    @Override
    public TSortedDataSet<E> subSetByFieldValue(Object fieldValue) {
        return null;
    }

    @Override
    public TSortedDataSet<E> headSetByFieldValue(Object toFieldValue, boolean inclusive) {
        return null;
    }

    @Override
    public TSortedDataSet<E> headSetByFieldValue(Object toFieldValue) {
        return null;
    }

    @Override
    public TSortedDataSet<E> tailSetByFieldValue(Object fromFieldValue, boolean inclusive) {
        return null;
    }

    @Override
    public TSortedDataSet<E> tailSetByFieldValue(Object fromFieldValue) {
        return null;
    }

    @Override
    public TDataSet<E> toDataSet() {
        return null;
    }


    //------------------------------------------------------------------------------------------------------------------

}
