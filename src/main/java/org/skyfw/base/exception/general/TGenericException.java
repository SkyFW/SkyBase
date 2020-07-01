package org.skyfw.base.exception.general;

import com.google.gson.internal.LinkedTreeMap;
import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class TGenericException extends TException {

    Map<String, Object> delegateMap = new LinkedTreeMap<>();


    public TGenericException(TMCode mCode, TMCodeSeverity severity, Throwable cause) {
        super(mCode, severity, cause);
    }

    @Override
    public int size() {
        return delegateMap.size();
    }

    @Override
    public boolean isEmpty() {
        return delegateMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return delegateMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return delegateMap.containsValue(value);
    }

    @Override
    public Object get(Object key) {
        return delegateMap.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return delegateMap.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return delegateMap.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ?> m) {
        delegateMap.putAll(m);
    }

    @Override
    public void clear() {
        delegateMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return delegateMap.keySet();
    }

    @Override
    public Collection values() {
        return delegateMap.values();
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {
        return delegateMap.entrySet();
    }

    @Override
    public void forEach(BiConsumer<? super String, ? super Object> action) {
        delegateMap.forEach(action);
    }
}
