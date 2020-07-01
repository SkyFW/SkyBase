package org.skyfw.base.collection;

import java.util.concurrent.ConcurrentHashMap;

public class THashMap<K, V> extends ConcurrentHashMap<K, V> {

    public THashMap() {
        super();
        //super(16, 16, 16);

    }
}
