package org.skyfw.base.result;

import org.skyfw.base.exception.TException;
import org.skyfw.base.exception.TExceptionDescriptor;
import org.skyfw.base.mcodes.*;
import org.skyfw.base.serializing.TDeserializeEventListener;
import org.skyfw.base.serializing.TSerializeEventListener;

import java.util.LinkedList;
import java.util.Map;


public class TResult<T> implements TDeserializeEventListener, TSerializeEventListener {


    private transient TMCode mCode;
    private String code;
    private TMCodeSeverity severity= TMCodeSeverity.UNKNOWN;

    private transient T resultObject= null;

    private long executionTime;

    private transient TException exception = null;
    // >>> in case of result comes from remote machine
    private TExceptionDescriptor[] exceptionChain= null;

    private transient boolean remoteOrigin= false;

    @Deprecated
    private String resultCode;
    @Deprecated
    private long benchmarkingTime;



    // >>> Constructor methods


    public TResult() {
        this.mCode= TBaseMCode.UNKNOWN;
    }

    public TResult(T resultObject) {
        this.resultObject= resultObject;
        this.mCode= TBaseMCode.SUCCESS;
    }

    public TResult(TMCode mCode, T resultObject) {
        this.mCode = mCode;
        this.resultObject = resultObject;
    }

    public TResult(TMCode mCode) {
        this.mCode = mCode;
    }

    public TResult(TException exception) {
        this.exception = exception;
    }

    //Creator Methods
    //~~~~~~~~~~~~~~~
    /*public static <Z> TResult<Z> create(Class<Z> clazz, String methodName) {
        return new TResult<Z>(methodName);
    }*/

    public static TResult createTResult(TMCode resultCode, Object resultObject) {
        return new TResult(resultCode, resultObject);
    }


    //~~~~~~~~~~~~~~~~~~
    public TResult<T> finish(TMCode resultCode, T resultObject){

        this.setMCode(resultCode);
        this.setResultObject(resultObject);
        return this;
    }

    public TResult<T> finish(TResult anotherResult){

        //Prevent a Mistake (Maybe an Infinite Loop)
        if (anotherResult.equals(this))
            return this;

        this.mCode= anotherResult.getMCode();
        try{
            //if (anotherResult.getResultObject().getClass().isAssignableFrom(Class<T>))
            this.setResultObject((T) anotherResult.getResultObject());
        }catch (Exception e){}

        return this;
    }

    public TResult<T> finish(TResult anotherResult,  T resultObject){
        this.finish(anotherResult);
        //ToDo: From There Will Not Counted In Time Benchmark, Is This Matter ?
        this.setResultObject(resultObject);
        return this;
    }


        public TResult<T> finish(){

        return this;
    }


    public TResult<T> succeed(T resultObject){

        this.setMCode(TBaseMCode.SUCCESS);
        this.setResultObject(resultObject);
        return this;
    }

    public TResult<T> fail(TMCode errorCode, Map details){

        this.setMCode(errorCode);
        this.setResultObject(null);
        return this;
    }

    public TResult<T> fail(TMCode errorCode){

        this.setMCode(errorCode);
        this.setResultObject(null);
        return this;
    }

    public TResult<T> fail(TException exception){

        this.setMCode(exception.getmCode());
        this.exception = exception;
        this.setResultObject(null);
        return this;
    }

    public TResult<T> exception(TException exception){

        this.setMCode(exception.getmCode());
        this.exception = exception;
        return this;
    }




    // >>> Benchmarking methods
    public long getExecutionTime() {
        return executionTime;
    }
    public void setExecutionTime(long time){
        this.executionTime = time;
    }


    /*public boolean tryGetResultObject(Object _obj, Class clazz){
        try {
            if (getResultObject().getClass().getName().equals(clazz.getName())) {
                _obj = this.resultObject; //this.getResultObject();
                return true;
            } else
                return false;
        }catch (exception exception){
            return false;
        }
    }

    public boolean tryGetResultObject(Object obj){
        try {
            Object tempOBJ= obj;
            boolean result= tryGetResultObject(obj, obj.getClass());
            return result;
        }catch (exception exception){
            return false;
        }
    }
    */


    /*
    With Java Generics, if you use the raw form of a generic class, then all generics on the class,
    even unrelated generic methods such as your makeSingletonList and doSomething methods,
    become raw. The reason as I understand it is to provide backwards compatibility with Java code written pre-generics.
    If there is no use for your generic type parameter T, then simply remove it from MyGenericClass,
    leaving your methods generic with K.
    Else you'll have to live with the fact that the class type parameter T must be given to your class to use generics
    on anything else in the class.
     */

    public boolean isSuccessful(){

        if (this.exception != null)
            return false;

        try {

            if (this.mCode == null)
                return false;

            if (this.getMCode().getBaseCode().getCodeFamily().equals(TBaseMCodeFamily.SUCCESS_CODE_FAMILY))
                return true;
            else
                return false;
        }catch (Exception e){
            return false;
        }
    }

    public boolean check(TMCode mCode){

        if (this.mCode.equals(mCode))
            return true;
        else
            return false;
    }

    public T getResultObject() {
        return resultObject;
    }

    public TResult setResultObject(T resultObject) {

        this.resultObject = resultObject;
        return this;
    }

    // >>>

    public TMCode getMCode() {

        if ( (exception != null) && (exception instanceof TException) )
            return exception.getmCode();
        else if ( (this.exceptionChain != null) && (this.exceptionChain.length > 0) )
            return this.exceptionChain[0].getmCode();
        else
            return this.mCode;
    }

    public String getCode() {

        if ( (this.exceptionChain != null) && (this.exceptionChain.length > 0) )
            return this.exceptionChain[0].getCode();
        else
            return this.code;
    }

    public void setMCode(TMCode mCode) {
        this.mCode = mCode;
    }

    public void setCode(String code) {

        this.code = code;
    }

    public TMCodeSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(TMCodeSeverity severity) {
        this.severity = severity;
    }

    public String getMessage() {

        TMCode mCode= this.getMCode();
        if (mCode != null)
            return mCode.getRawMessage();
        else
            return "";
    }

    //----


    @Override
    public String toString() {

        String s= "";

        if (this.getMCode() != null)
            s= s + this.getMCode() + " | ";

        if (this.getMessage() != null)
            s= s + "Msg: " + this.getMessage() + " | ";

        //if (this.getExecutionTime() != null)
            s= s + "ExecTime: " + this.getExecutionTime() + " | ";

        if (this.getResultObject() != null)
            s= s + this.getResultObject();

        return s;
    }


    public TException getException() {
        return exception;
    }


    public TExceptionDescriptor[] getExceptionChain() {
        return exceptionChain;
    }

    public void setExceptionChain(TExceptionDescriptor[] exceptionChain) {
        this.exceptionChain = exceptionChain;
    }




    @Override
    public void onBeforeSerialize() throws TException {

        this.code= this.mCode.toString();
        Throwable e= this.exception;
        if (e == null)
            return;

        Throwable[] throwables= null;/*e.getSuppressed();*/
        LinkedList<Throwable> exceptionsList= new LinkedList<>();
        while ((e != null)){
            exceptionsList.add(e);
            if (e == e.getCause())
                break;
            e = e.getCause();
        }

        this.exceptionChain= new TExceptionDescriptor[exceptionsList.size()];
        int i= 0;
        for (Throwable throwable: exceptionsList) {
            this.exceptionChain[i] = new TExceptionDescriptor(throwable);
            i ++;
        }

    }

    @Override
    public void onAfterDeserialize() throws TException {

        //ToDo: remove in further versions
        if (this.resultCode != null){
            this.mCode= TBaseMCode.valueOf(this.resultCode);
            this.executionTime= this.benchmarkingTime;
            return;
        }


        this.mCode= TMCodeRegistry.fetch(this.code);

        if (this.exceptionChain != null)
        for (TExceptionDescriptor exceptionDescriptor: this.exceptionChain){
            exceptionDescriptor.setmCode(TMCodeRegistry.fetch(exceptionDescriptor.getCode()));
        }
    }




    public boolean isRemoteOrigin() {
        return remoteOrigin;
    }

    public void setRemoteOrigin(boolean remoteOrigin) {
        this.remoteOrigin = remoteOrigin;
    }
}
