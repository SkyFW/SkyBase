package org.skyfw.base.result;

import org.skyfw.base.exception.TException;
import org.skyfw.base.log.TMessageDetails;
import org.skyfw.base.mcodes.TBaseMCodeFamily;
import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.serializing.modifiers.TStringSerializeModifier;


public class TResult<T> implements TStringSerializeModifier<TResult> {

    private transient String methodName;

    private transient TMCode mCode;
    private String code;
    private TException e= null;

    private transient T resultObject= null;

    private transient long benchmarkingStartTime;
    private transient long benchmarkingStopTime;
    private long benchmarkingTime;


    //Constructor Methods
    //~~~~~~~~~~~~~~~~~~~
    public TResult() {

        this.mCode= TBaseMCode.UNKNOWN_MESSAGE_CODE;
        this.startBenchmarking();
    }

    public TResult(T resultObject) {
        this.resultObject= resultObject;
        this.mCode= TBaseMCode.SUCCESS;
    }

    public TResult(TMCode mCode, T resultObject) {
        this.startBenchmarking();
        this.mCode = mCode;
        this.resultObject = resultObject;
    }

    public TResult(TMCode mCode) {
        this.startBenchmarking();
        this.mCode = mCode;
    }

    public TResult(TException e) {
        this.e= e;
    }

    //Creator Methods
    //~~~~~~~~~~~~~~~
    public static TResult create() {
        return new TResult();
    }

    public static <Z> TResult<Z> create(Class<Z> clazz) {
        return new TResult<Z>();
    }

    public static TResult create(String methodName) {
        return new TResult(methodName);
    }

    /*public static <Z> TResult<Z> create(Class<Z> clazz, String methodName) {
        return new TResult<Z>(methodName);
    }*/

    public static TResult createTResult(TMCode resultCode, Object resultObject) {
        return new TResult(resultCode, resultObject);
    }


    //~~~~~~~~~~~~~~~~~~
    public TResult<T> finish(TMCode resultCode, T resultObject){

        this.setmCode(resultCode);
        this.setResultObject(resultObject);
        this.stopBenchmarking();
        return this;
    }

    public TResult<T> finish(TResult anotherResult){

        //Prevent a Mistake (Maybe an Infinite Loop)
        if (anotherResult.equals(this))
            return this;

        this.mCode= anotherResult.getmCode();
        try{
            //if (anotherResult.getResultObject().getClass().isAssignableFrom(Class<T>))
            this.setResultObject((T) anotherResult.getResultObject());
        }catch (Exception e){}

        this.stopBenchmarking();
        return this;
    }

    public TResult<T> finish(TResult anotherResult,  T resultObject){
        this.finish(anotherResult);
        //ToDo: From There Will Not Counted In Time Benchmark, Is This Matter ?
        this.setResultObject(resultObject);
        return this;
    }


        public TResult<T> finish(){

        this.stopBenchmarking();
        return this;
    }


    public TResult<T> succeed(T resultObject){

        this.setmCode(TBaseMCode.SUCCESS);
        this.setResultObject(resultObject);
        this.stopBenchmarking();
        return this;
    }

    public TResult<T> fail(TMCode errorCode, TMessageDetails details){

        this.setmCode(errorCode);
        this.setResultObject(null);
        this.stopBenchmarking();
        return this;
    }

    public TResult<T> fail(TMCode errorCode){

        this.setmCode(errorCode);
        this.setResultObject(null);
        this.stopBenchmarking();
        return this;
    }

    public TResult<T> fail(TException exception){

        this.setmCode(exception.getmCode());
        this.e= exception;
        this.setResultObject(null);
        this.stopBenchmarking();
        return this;
    }

    public TResult<T> exception(TException exception){

        this.setmCode(exception.getmCode());
        this.e= exception;
        return this;
    }




    //Benchmarking Methods
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    public void startBenchmarking(){

        this.benchmarkingStartTime = System.currentTimeMillis();
    }

    public long stopBenchmarking(){

        this.benchmarkingStopTime =  System.currentTimeMillis();
        this.benchmarkingTime = this.benchmarkingStopTime - this.benchmarkingStartTime;
        return this.benchmarkingTime;
    }

    public long getBenchmarkingTime() {
        return benchmarkingTime;
    }

    public void setBenchmarkingTime(long time){
        this.benchmarkingTime= time;
    }
    //------------------------------------------------------------------------------------------------------------------


    /*public boolean tryGetResultObject(Object _obj, Class clazz){
        try {
            if (getResultObject().getClass().getName().equals(clazz.getName())) {
                _obj = this.resultObject; //this.getResultObject();
                return true;
            } else
                return false;
        }catch (exception e){
            return false;
        }
    }

    public boolean tryGetResultObject(Object obj){
        try {
            Object tempOBJ= obj;
            boolean result= tryGetResultObject(obj, obj.getClass());
            return result;
        }catch (exception e){
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

        if (this.e != null)
            return false;

        try {

            if (this.mCode == null)
                return false;

            if (this.getmCode().getBaseCode().getCodeFamily().equals(TBaseMCodeFamily.SUCCESS_CODE_FAMILY))
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

    public void setResultObject(T resultObject) {

        this.resultObject = resultObject;
    }

    //Simple Getter & Setter Methods
    //------------------------------
    public TMCode getmCode() {
        return mCode;
    }

    public void setmCode(TMCode mCode) {
        this.mCode = mCode;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {

        this.code = code;

        //TMCodeRegistry.

    }

    public String getMessage() {

        return this.mCode.getRawMessage();
    }

    //----


    @Override
    public String toString() {

        String s= "";
        if (this.methodName != null)
            s= this.methodName.toUpperCase() + " : ";

        if (this.getmCode() != null)
            s= s + this.getmCode() + " | ";

        if (this.getMessage() != null)
            s= s + "Msg: " + this.getMessage() + " | ";

        //if (this.getBenchmarkingTime() != null)
            s= s + "ExecTime: " + this.getBenchmarkingTime() + " | ";

        if (this.getResultObject() != null)
            s= s + this.getResultObject();

        return s;
    }








    @Override
    public String onAfterSerialize(Object object, String s) throws Exception {
        return null;
    }

    @Override
    public void onAfterDeserialize(String s, Class<TResult> mainClass, Class[] genericParams, TResult object) throws Exception {

    }
}
