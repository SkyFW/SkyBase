package org.skyfw.base.collection;

import java.util.Iterator;
import java.util.Map;

public class TMapWalker<K, V> {

    private Iterator<Map.Entry<K, V>> iterator;
    private Map.Entry<K, V> currentEntry;
    private Map<K, V> map;

    public TMapWalker(Map<K, V> map) {

        this.iterator= map.entrySet().iterator();
        this.map= map;
    }

    public boolean hasNext(){

        return this.iterator.hasNext();
    }

    public boolean next(){

        if (this.iterator.hasNext()) {
            currentEntry = this.iterator.next();
            return true;
        }
        else
            return false;
    }

    public K getKey(){

        if (this.currentEntry != null)
            return currentEntry.getKey();
        else
            return null;
    }

    public V getValue(){

        if (this.currentEntry != null)
            return this.currentEntry.getValue();
        else
            return null;
    }

    public void reset(){
        //this.iterator.
        this.iterator= map.entrySet().iterator();
        this.currentEntry= null;
    }


}
