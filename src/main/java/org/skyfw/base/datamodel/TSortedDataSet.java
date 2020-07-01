package org.skyfw.base.datamodel;

import java.util.*;

/**
 * It seems there is a bad design in "SortedSet" & "NavigableSet" interfaces.
 * both consist "head" & "tail" & "subSet" methods but in case of "SortedSet" these are defective as there is no
 * inclusive determination parameters and adding this parameter in "NavigableSet" making no sense.
 * maybe it's why another like GUAVA did not followed this path and their "MultiSet" does not extends
 * from java "StoredSet"
 * @param <E>
 */
public interface TSortedDataSet<E extends TDataModel> extends SortedSet<E> {


    TSortedDataSet<E> clone();


    TFieldDescriptor getSortField();


    // >>> Element based seek
    //------------------------------------------------------------------------------------------------------------------
    //@Override
    TSortedDataSet<E> subSet(E fromElement, boolean fromInclusive, E toElement,   boolean toInclusive);

    @Override
    TSortedDataSet<E> subSet(E fromElement, E toElement);

    //@Override
    TSortedDataSet<E> headSet(E toElement, boolean inclusive);

    @Override
    TSortedDataSet<E> headSet(E toElement);

    //@Override
    TSortedDataSet<E> tailSet(E fromElement, boolean inclusive);

    @Override
    TSortedDataSet<E> tailSet(E fromElement);

    // >>>
    //------------------------------------------------------------------------------------------------------------------
    TSortedDataSet<E> subSetByFieldValue(Object fromFieldValue, boolean fromInclusive, Object toFieldValue, boolean toInclusive);

    TSortedDataSet<E> subSetByFieldValue(Object fromFieldValue, Object toFieldValue);

    TSortedDataSet<E> subSetByFieldValue(Object fieldValue);

    TSortedDataSet<E> headSetByFieldValue(Object toFieldValue, boolean inclusive);

    TSortedDataSet<E> headSetByFieldValue(Object toFieldValue);

    TSortedDataSet<E> tailSetByFieldValue(Object fromFieldValue, boolean inclusive);

    TSortedDataSet<E> tailSetByFieldValue(Object fromFieldValue);

    //------------------------------------------------------------------------------------------------------------------
    TDataSet<E> toDataSet();








}
