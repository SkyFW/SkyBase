package org.skyfw.base.datamodel.defaultdatamodels;

import org.skyfw.base.datamodel.TBaseDataModel;
import org.skyfw.base.datamodel.annotation.DataModel;
import org.skyfw.base.datamodel.annotation.DataModelField;
import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.serializing.TSerializable;
import org.skyfw.base.serializing.modifiers.TBytesSerializeModifier;
import org.skyfw.base.serializing.modifiers.TStringSerializeModifier;
import org.skyfw.base.result.TResult;

@DataModel(autoKey = false)
public class TResultDataModel extends TBaseDataModel implements
          TStringSerializeModifier<TResultDataModel>, TBytesSerializeModifier<TResultDataModel>
        , TSerializable<TResultDataModel> {

    @DataModelField
    TResult result;


    public TResult getResult() {
        return result;
    }

    public void setResult(TResult result) {
        this.result = result;
    }


    @Override
    public byte[] onAfterSerialize(Object object, byte[] bytes) throws Exception {
        return bytes;
    }

    @Override
    public void onAfterDeserialize(byte[] bytes, Class<TResultDataModel> mainClass, Class[] genericParams, TResultDataModel object) throws Exception {

        //value.result.
    }

    @Override
    public String onAfterSerialize(Object object, String s) throws Exception {
        return s;
    }

    @Override
    public void onAfterDeserialize(String s, Class<TResultDataModel> mainClass, Class[] genericParams, TResultDataModel object) throws Exception {

    }
}
