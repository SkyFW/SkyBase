package org.skyfw.base.collection;

import java.util.Collection;
import java.util.Iterator;

public class TCollectionWalker <E> {

    private Iterator<E> iterator;
    private E currentEntry;

    public TCollectionWalker(Collection<E> collection) {

        this.iterator = collection.iterator();
    }

    public boolean hasNext() {

        return this.iterator.hasNext();
    }

    public boolean next() {

        if (this.iterator.hasNext()) {
            currentEntry = this.iterator.next();
            return true;
        } else
            return false;
    }

    public E getValue() {

        if (this.currentEntry != null)
            return this.currentEntry;
        else
            return null;
    }

}