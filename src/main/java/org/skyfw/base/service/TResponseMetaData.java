package org.skyfw.base.service;

import com.google.gson.internal.LinkedTreeMap;
import org.skyfw.base.exception.TException;
import org.skyfw.base.mcodes.TBaseMCodeFamily;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeRegistry;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.result.TResult;
import org.skyfw.base.serializing.TDeserializeEventListener;
import org.skyfw.base.serializing.TSerializable;
import org.skyfw.base.serializing.TSerializeEventListener;
import org.skyfw.base.system.classloader.TClassLoader;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TResponseMetaData implements TSerializable<TResponseMetaData>, TDeserializeEventListener
    , TSerializeEventListener {

    private transient TMCode mCode;
    private String code;

    private Map<String, Object> details;

    private long totalRunningTime;

    private TMCodeSeverity severity;

    private TExceptionDescriptor[] causalChain;


    public TResponseMetaData() {
    }

    public TResponseMetaData(TMCode mCode, TMCodeSeverity severity) {
        this.mCode = mCode;
        this.severity = severity;
    }

    public TResponseMetaData(Throwable throwable) {

        if (throwable instanceof TException){
            this.mCode =((TException)throwable).getmCode();
            this.severity= ((TException)throwable).getSeverity();
            this.details= new LinkedTreeMap<>();
            loadDetailsFromException(this.details, (TException)throwable);
        }

        List<TExceptionDescriptor> exceptionsList= new LinkedList<>();

        Throwable cause= throwable;
        while (cause != null){
            exceptionsList.add(new TExceptionDescriptor(cause));
            cause= cause.getCause();
        }

        this.causalChain = exceptionsList.toArray(new TExceptionDescriptor[0]);
    }


    public TResult toResult(){
        return this.toResult(null);
    }

    public TResult toResult(Object object){

        TResult result= new TResult();
        result.setCode(this.code);
        result.setmCode(this.mCode);
        result.setBenchmarkingTime(this.totalRunningTime);

        // >>> Rebuilding exceptions chain
        for (TExceptionDescriptor exceptionDescriptor: this.getCausalChain()){

            //Exception e= TClassLoader.loadByName(exceptionDescriptor.className).newInstance();

        }

        result.setResultObject(object);

        return result;
    }


    public boolean isSuccessful() {
        if (this.mCode == null)
            return true;

        if (this.mCode.getBaseCode().getCodeFamily().equals(TBaseMCodeFamily.SUCCESS_CODE_FAMILY))
            return true;
        else
            return false;

    }


    public TMCode getMCode() {
        return mCode;
    }

    public void setMCode(TMCode mCode) {
        this.mCode = mCode;
        this.code= mCode.toString();
    }

    public String getCode() {
        return code;
    }

    /*public void setCode(String code) {
        this.code = code;
    }*/

    public Map<String, Object> getDetails() {
        return details;
    }

    public void setDetails(Map<String, Object> details) {
        this.details = details;
    }

    public long getTotalRunningTime() {
        return totalRunningTime;
    }

    public void setTotalRunningTime(long totalRunningTime) {
        this.totalRunningTime = totalRunningTime;
    }





    public TMCodeSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(TMCodeSeverity severity) {
        this.severity = severity;
    }

    /*public TMCode[] getCausalChain() {
        return causalChain;
    }

    public void setCausalChain(TMCode[] causalChain) {
        this.causalChain = causalChain;
    }
    */



    /*public void setCausalChain(Map[] causalChain) {
        this.causalChain = causalChain;
    }*/


    public TExceptionDescriptor[] getCausalChain() {
        return causalChain;
    }



    @Override
    public void onBeforeSerialize() throws TException {
        this.code= this.mCode.toString();
    }

    @Override
    public void onAfterDeserialize() throws TException {

        this.mCode= TMCodeRegistry.fetch(this.code);

        for (TExceptionDescriptor exceptionDescriptor: this.causalChain){
            exceptionDescriptor.mCode= TMCodeRegistry.fetch(exceptionDescriptor.code);
        }
    }



    private class TExceptionDescriptor{

        private String className;
        private StackTraceElement[] stackTrace;
        private transient TMCode mCode;
        private String code;
        private TMCodeSeverity severity;
        private Map<String,Object> detail;

        public TExceptionDescriptor(Throwable e) {
            this.className= e.getClass().getName();
            this.stackTrace= e.getStackTrace();
            this.detail= new HashMap<>();
            if (e instanceof TException){
                this.mCode= ((TException) e).getmCode();
                this.code= mCode.toString();
                this.severity= ((TException) e).getSeverity();
                loadDetailsFromException(this.detail, (TException) e);
            }
            else {
                this.detail.put("message", e.getMessage());
                this.mCode= null;
            }
        }
    }




    public static void loadDetailsFromException(Map<String,Object> details, TException e){
        details.putAll(e);
        details.remove("exceptionClassName");
        details.remove("severity");
        details.remove("mCode");
    }

}
