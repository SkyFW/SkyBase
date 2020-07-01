package org.skyfw.base.datamodel;

import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;

public enum TDataModelMCodes implements TMCode {


    // >>> DataModel general MCodes
    //------------------------------------------------------------------------------------------------------------------
    DATA_MODEL_EXCEPTION("")



    // >>> DataModel initialization MCodes
    //------------------------------------------------------------------------------------------------------------------
    , DATAMODEL_INITIALIZATION_EXCEPTION("")

    , CLASS_IS_NOT_EXTENDED_FROM_TDATAMODEL("")

    , STATIC_FIELD_WITH_TYPE_OTHER_THAN_TFIELDDESCRIPTOR_IS_AGAINST_DATAMODEL_CONVENTION("")
    , STATIC_FIELD_WITH_NO_TRANSIENT_MODIFIER_IS_AGAINST_DATAMODEL_CONVENTION("")
    , NO_CORRESPONDING_FIELD_FOUND_FOR_DEFINED_STATIC_HANDLE("")
    , EXCEPTION_ON_SETTING_FIELD_DESCRIPTOR_STATIC_HANDLE("")
    , STATIC_FIELD_HANDLER_AFTER_SET_CHECK_FAILED("")

    , GETTER_METHOD_NOT_FOUND("There is no getter method in datamodel ({#1}) for field ({#2})"
            + "\nso direct field access will be used.")

    , SETTER_METHOD_NOT_FOUND("There is no setter method in datamodel ({#1}) for field ({#2})"
            + "\nso direct field access will be used.")

    , GETTER_METHOD_INVALID_ARGUMENTS_COUNT("")
    , GETTER_METHOD_WRONG_RETURN_TYPE("Getter method's return type not match field type.")
    , SETTER_METHOD_INVALID_ARGUMENTS_COUNT("")
    , SETTER_METHOD_WRONG_ARGUMENT_TYPE("")
    , SETTER_METHOD_WRONG_RETURN_TYPE("")


    // >>> Index initializing
    , INDEX_PARAM_FIELD_NOT_FOUND("")



    // >>> DataModel Get & Set operation MCodes
    //------------------------------------------------------------------------------------------------------------------
    , EXCEPTION_ON_SET_FIELD_VALUE("")

    , INCOMPATIBLE_VALUE("")


    , EXCEPTION_ON_CALLING_GETTER_METHOD("")
    , EXCEPTION_ON_GET_BY_REFLECTION_FIELD("")

    , EXCEPTION_ON_CALLING_SETTER_METHOD("")
    , EXCEPTION_ON_SET_BY_REFLECTION_FIELD("")

    , NO_KEY_FIELD_IS_DETERMINED("")
    , FIELD_NAME_NOT_EXISTS("")

    , NEITHER_GETTER_METHOD_OR_REFLECTION_FIELD_AVAILABLE("")
    , NEITHER_SETTER_METHOD_OR_REFLECTION_FIELD_AVAILABLE("")




    //------------------------------------------------------------------------------------------------------------------
    , DATAMODEL_COMPARATOR_EXCEPTION("")






    ;

    String rawMessage;

    TDataModelMCodes(String rawMessage) {
        this.rawMessage = rawMessage;
    }

    @Override
    public String getModuleName() {
        return null;
    }

    @Override
    public TBaseMCode getBaseCode() {
        return null;
    }

    @Override
    public TMCodeSeverity getSeverity() {
        return null;
    }

    @Override
    public String getRawMessage() {
        return rawMessage;
    }
}
