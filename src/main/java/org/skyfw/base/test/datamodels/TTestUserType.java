package org.skyfw.base.test.datamodels;

import org.skyfw.base.datamodel.gui.TGUIFieldAvailableValues_IFace;

public enum TTestUserType implements TGUIFieldAvailableValues_IFace {

    ADMIN_USER("Admin")
    , MODERATOR_USER("Moderator")
    , NORMAL_USER("Normal user");

    private String caption;

    TTestUserType(String value) {
        this.caption = value;
    }

    @Override
    public String toString() {
        return super.toString();
    }


    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getCaption() {
        return this.caption;
    }
}
