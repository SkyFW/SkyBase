package org.skyfw.base.collection;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class TCollection<K,V> extends LinkedHashMap{

    //LinkedHashMap<K,V> collection;
    Iterator<Map.Entry<K,V>> defaultIterator = null;
    Map.Entry<K,V> currentEntry = null;

    public boolean refreshDefaultItrator(){
        this.defaultIterator = this.entrySet().iterator();
        if (this.defaultIterator.hasNext()) {
            this.currentEntry = this.defaultIterator.next();
            return true;
        }
        else
            return false;
    }

    public /*Map.Entry<K,V>*/ boolean next(){

        if (this.defaultIterator == null) {
            return this.refreshDefaultItrator();
        }
        else if (this.defaultIterator.hasNext()){
            this.currentEntry = this.defaultIterator.next();
            return true;
        }

        return false;
    }

    public K getCurrentKey(){
        if (this.defaultIterator == null)
            this.refreshDefaultItrator();

        return this.currentEntry.getKey();
    }

    public V getCurrentValue(){
        if (this.defaultIterator == null)
            this.refreshDefaultItrator();

        return this.currentEntry.getValue();
    }

    public boolean hasNext(){

        if (this.defaultIterator == null)
            this.refreshDefaultItrator();

        return defaultIterator.hasNext();
    }



}
