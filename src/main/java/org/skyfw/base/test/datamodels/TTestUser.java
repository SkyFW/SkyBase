package org.skyfw.base.test.datamodels;

import org.skyfw.base.datamodel.TBaseDataModel;
import org.skyfw.base.datamodel.TFieldDescriptor;
import org.skyfw.base.datamodel.annotation.*;
import org.skyfw.base.datamodel.annotation.GUI.DataModelGUIField;
import org.skyfw.base.datamodel.gui.TGUIInputType;
import org.skyfw.base.serializing.TSerializable;


@DataModel(dataStoreName = "users_table", autoKey = true
    , indexes = {
        "@number@height"
        , "@number@height@userType"

})


@DataModelIndexEntry({
        @IndexParam(fieldName = "height", dataSegmentSize = 10)
})

@DataModelIndexEntry({
        @IndexParam(fieldName = "weight", dataSegmentSize = 1000)
})

@DataModelIndexEntry({
        @IndexParam(fieldName = "height", dataSegmentSize = 10),
        @IndexParam(fieldName = "weight", dataSegmentSize = 1000)
})

@DataModelIndexEntry({
        @IndexParam(fieldName = "locationAltitude", dataSegmentSize = 100),
        @IndexParam(fieldName = "locationLongitude", dataSegmentSize = 100)
})

@DataModelIndexEntry({
        @IndexParam(fieldName = "userType")
})

public class TTestUser extends TBaseDataModel implements TSerializable<TTestUser> {


    @KeyField
    @DataModelField(dSFieldName = "UserId", fieldLen = 50)
    @DataModelGUIField(caption = "user id"
            , unitCaption = ""
            , inputType = TGUIInputType.EDIT_BOX)
    private String userId;
    public static transient TFieldDescriptor USER_ID;

    @DataModelField(dSFieldName = "Name", fieldLen = 50)
    private String name;
    public static final transient TFieldDescriptor NAME= null;

    @DataModelField
    @DataModelGUIField(caption = "user type"
                        , availableValues = TTestUserType.class)
    private TTestUserType userType;
    public static final transient TFieldDescriptor USER_TYPE= null;

    @DataModelField(fieldLen = 20)
    @DDIdField
    private String city;

    @DataModelField(fieldLen = 50)
    private String family;

    @DataModelField(dSFieldName = "Code", fieldLen = 4)
    private Integer number;

    @DataModelField(dSFieldName = "Height", fieldLen = 5)
    private Long height;

    @DataModelField(dSFieldName = "Weight", fieldLen = 6)
    private Long weight;

    @DataModelField(dSFieldName = "Altitude", fieldLen = 10)
    private Long locationAltitude;

    @DataModelField(dSFieldName = "Longitude", fieldLen = 10)
    private Long locationLongitude;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TTestUserType getUserType() {
        return userType;
    }

    public void setUserType(TTestUserType userType) {
        this.userType = userType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getLocationAltitude() {
        return locationAltitude;
    }

    public void setLocationAltitude(Long locationAltitude) {
        this.locationAltitude = locationAltitude;
    }

    public Long getLocationLongitude() {
        return locationLongitude;
    }

    public void setLocationLongitude(Long locationLongitude) {
        this.locationLongitude = locationLongitude;
    }
}
