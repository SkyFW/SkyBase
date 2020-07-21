package org.skyfw.base.datamodel.index;

import org.skyfw.base.datamodel.TDataModelDescriptor;
import org.skyfw.base.datamodel.TDataModelMCodes;
import org.skyfw.base.datamodel.TFieldDescriptor;
import org.skyfw.base.datamodel.exception.TDataModelIndexParamInitException;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.utils.TStringUtils;

public class TDataModelIndexInitiator {

    TDataModelDescriptor dataModelDescriptor;
    TDataModelIndexDescriptor indexDescriptor;

    public TDataModelIndexInitiator(TDataModelDescriptor dataModelDescriptor) {
        this.dataModelDescriptor = dataModelDescriptor;
        this.indexDescriptor= new TDataModelIndexDescriptor();
        this.indexDescriptor.indexName= "";
        /*this.indexDescriptor.params= new TDataModelIndexParamsList();*/
    }

    public void addParam (String fieldName, Integer dataSegmentSize) throws TDataModelIndexParamInitException{

        TFieldDescriptor fieldDescriptor= dataModelDescriptor.fields.get(fieldName);

        if (fieldDescriptor == null)
            fieldDescriptor= dataModelDescriptor.fields.get(TStringUtils.uncapitalize(fieldName));

        if (fieldDescriptor == null)
            throw new TDataModelIndexParamInitException(TDataModelMCodes.INDEX_PARAM_FIELD_NOT_FOUND
                    , TMCodeSeverity.FATAL, null, dataModelDescriptor.clazz.getName(), fieldName);

        indexDescriptor.add(new TDataModelIndexParam(fieldDescriptor, dataSegmentSize));
        indexDescriptor.indexName += "@" + fieldName;
    }

    public TDataModelIndexDescriptor init(){

        if (indexDescriptor.indexName == null)
            return null;
        this.dataModelDescriptor.getIndexDescriptors().put(indexDescriptor.indexName, indexDescriptor);
        return this.indexDescriptor;
    }

}
