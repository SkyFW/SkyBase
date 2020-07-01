package org.skyfw.base.classes.comparator;

import org.apache.commons.collections4.comparators.NullComparator;

public class TNullComparator<E> extends NullComparator<E> {

    @Override
    public int compare(E o1, E o2) {
        if (o1 == o2) {
            return 0;
        } else if (o1 == null) {
            return -1;
        } else if (o2 == null) {
            return  1;
        } else {
            //ToDo: Performance
            if (o1 instanceof String && o2 instanceof String){
                String str1= (String) o1;
                String str2= (String) o2;
                if (str1.length() > str2.length())
                    return 1;
                else if (str2.length() > str1.length())
                    return -1;
                else
                    return str1.compareTo(str2);
            }

            Comparable<E> comparable1= (Comparable<E>) o1;
            E comparable2= (E) o2;
            return comparable1.compareTo(comparable2);
        }
    }
}
