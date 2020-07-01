package org.skyfw.base.service;

import org.skyfw.base.datamodel.TBaseDataModel;
import org.skyfw.base.datamodel.TDataModel;

public class TServiceInfo extends TBaseDataModel {

    String name;
    String path;
    String description;

    static {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
