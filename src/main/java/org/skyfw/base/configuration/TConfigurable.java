package org.skyfw.base.configuration;

import org.skyfw.base.exception.TException;

public interface TConfigurable<C extends TConfiguration> {

    Class<C> getConfigClass();
    void config(C config) throws TException;
}
