package org.skyfw.base.exception;


import org.skyfw.base.datamodel.TDataModel;
import org.skyfw.base.datamodel.annotation.IgnoredField;
import org.skyfw.base.datamodel.annotation.KeyField;
import org.skyfw.base.log.TLogger;
import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCode;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.serializing.TSerializable;


public abstract class TException/*<M extends TMCode, ED extends TMessageDetails>*/ extends Exception
        implements TSerializable<TException>, TDataModel {

    @IgnoredField
    private static transient TLogger logger= TLogger.getLogger();


    protected TMCode mCode;
    private TMCodeSeverity severity;
    private String exceptionClassName;
    //private String message;


    //Default fields
    @KeyField
    Long creationTimeStamp;
    @IgnoredField
    Long lastAccessTimeStamp;
    @IgnoredField
    Long lastUpdateTimeStamp;
    @IgnoredField
    Long deletionTimeStamp;


    //Constructor Methods
    //~~~~~~~~~~~~~~~~~~~
    protected TException(TMCode mCode, TMCodeSeverity severity, Throwable cause){

        super(generateMessage(mCode, severity), cause);

        this.exceptionClassName= this.getClass().getName();

        if (mCode == null)
            this.mCode= TBaseMCode.UNKNOWN_MESSAGE_CODE;
        else
            this.mCode = mCode;

        this.severity= severity;
    }


    protected TException(TMCode mCode, TException cause){

        super(generateMessage(mCode, cause.severity), cause);

        this.exceptionClassName= this.getClass().getName();

        if (mCode == null)
            this.mCode= cause.mCode;
        else
            this.mCode = mCode;

        this.severity= cause.severity;
    }

    protected TException(TException cause){
        this(null, cause);
    }



    private static String generateMessage(TMCode mCode, TMCodeSeverity severity){
        //ToDo: Local functionality should be implemented.
        String message= "";

        if (mCode == null)
            return message;

        if (mCode.getModuleName() != null)
            message=  mCode.getModuleName() + ": ";

        message= message + mCode /*+ "(" + mCode.getCode() + ")"*/
                + "\nLevel: " + mCode.getSeverity();
        return message;
    }


    //Creator Methods
    //~~~~~~~~~~~~~~~

    //public static <E extends Enum<E>>  TException create(TMCode errorCode, E errorCode) {
    /*public static TException create(TMCode errorCode) {

        return new TException(errorCode, null, null);
    }

    public static TException create(TMCode errorCode, TMCodeSeverity severity) {

        return new TException(errorCode, severity, null);
    }

    public static TException create(TMCode errorCode, Throwable cause) {

        return new TException(errorCode, null, cause);
    }

    public static TException create(TMCode errorCode, TMCodeSeverity severity, Throwable cause) {

        return new TException(errorCode, severity, cause);
    }
    //~~~~~~~~~~~~~~~~~~
*/


    //Simple Getter & Setter Methods
    //------------------------------
    public TMCode getmCode() {
        return mCode;
    }

    public TMCodeSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(TMCodeSeverity severity) {
        this.severity = severity;
    }

    /* public String getRawMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }*/

    //----


    @Override
    public String toString() {

        String s = "";
        String methodName= null;

        if ((this.getStackTrace() != null)
        && (this.getStackTrace().length > 0))
            methodName= this.getStackTrace()[0].getMethodName();


        if (methodName != null)
            s = methodName.toUpperCase() + " : ";

        if (this.getmCode() != null)
            s = s + this.getmCode().toString() + " | ";

        if (this.getMessage() != null)
            s = s + "Msg: " + this.getMessage() + " | ";

        return s;
    }






    public TException log(){

        logger.doLog(this.severity, this);
        return this;
    }

    /*public TException logDebug(){
        logger.debug(this);
        return this;
    }

    public TException logTrace(){
        logger.trace(this);
        return this;
    }

    public TException logInfo(){
        logger.info(this);
        return this;
    }

    public TException logWarn(){
        logger.warn(this);
        return this;
    }

    public TException logError(){
        logger.error(this);
        return this;
    }

    public TException logFatal(){
        logger.fatal(this);
        return this;
    }*/


    @Override
    public Long getCreationTimeStamp() {
        return creationTimeStamp;
    }

    @Override
    public void setCreationTimeStamp(Long creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    @Override
    public Long getLastAccessTimeStamp() {
        return lastAccessTimeStamp;
    }

    @Override
    public void setLastAccessTimeStamp(Long lastAccessTimeStamp) {
        this.lastAccessTimeStamp = lastAccessTimeStamp;
    }

    @Override
    public Long getLastUpdateTimeStamp() {
        return lastUpdateTimeStamp;
    }

    @Override
    public void setLastUpdateTimeStamp(Long lastUpdateTimeStamp) {
        this.lastUpdateTimeStamp = lastUpdateTimeStamp;
    }

    @Override
    public Long getDeletionTimeStamp() {
        return deletionTimeStamp;
    }

    @Override
    public void setDeletionTimeStamp(Long deletionTimeStamp) {
        this.deletionTimeStamp = deletionTimeStamp;
    }
}
