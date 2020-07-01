package org.skyfw.base.classes.function;

import org.skyfw.base.exception.TException;

public interface TConsumer<T>  {

    void accept(T t) throws TException;

}
