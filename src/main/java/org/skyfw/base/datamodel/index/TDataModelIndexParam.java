package org.skyfw.base.datamodel.index;

import org.skyfw.base.datamodel.TFieldDescriptor;

public class TDataModelIndexParam {

    TFieldDescriptor fieldDescriptor;
    int    segmentSize;


    public TDataModelIndexParam(TFieldDescriptor fieldDescriptor, int segmentSize) {
        this.fieldDescriptor = fieldDescriptor;
        this.segmentSize = segmentSize;
    }

    public TFieldDescriptor getFieldDescriptor() {
        return fieldDescriptor;
    }

    public void setFieldDescriptor(TFieldDescriptor fieldDescriptor) {
        this.fieldDescriptor = fieldDescriptor;
    }

    public int getSegmentSize() {
        return segmentSize;
    }

    public void setSegmentSize(int segmentSize) {
        this.segmentSize = segmentSize;
    }
}
