package org.skyfw.base.log;


import org.skyfw.base.classes.TObjects;
import org.skyfw.base.configuration.TDefaults;
import org.skyfw.base.exception.TException;
import org.skyfw.base.log.printers.file.THtmlLogPrinter;
import org.skyfw.base.log.printers.terminal.TLogLinearPrettyPrinter;
import org.skyfw.base.log.printers.TLogPrinter_Interface;
import org.skyfw.base.mcodes.TMCode;
import org.apache.commons.io.FilenameUtils;
import org.skyfw.base.mcodes.TMCodeSeverity;

import java.io.File;
import java.io.FileOutputStream;
//import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

public final class TLogger {

    // >>> Static Default Settings
    public static TMCodeSeverity defaultMinLogLevelForPrint= TMCodeSeverity.DEBUG;
    public static TMCodeSeverity defaultMinLogLevelForFile= TMCodeSeverity.WARNING;

    // >>> Defaults
    public static TLogPrinter_Interface defaultLogPrinter;
    public static TLogPrinter_Interface defaultLogFilePrinter;
    public static TLogManager_Interface defaultLogManager= null;


    //Logger Settings
    private TMCodeSeverity minLogLevelForPrint = defaultMinLogLevelForPrint;
    private TMCodeSeverity minLogLevelForFile= defaultMinLogLevelForFile;
    private TLoggerStackTraceType stackTraceType= TLoggerStackTraceType.AUTO_STACK_TRACE_IF_NO_MANUAL_DATA_PROVIDED;

    private String className= null;
    private String fileName= null;

    private File outputFile= null;
    private TLogPrinter_Interface logPrinter= null;
    private TLogPrinter_Interface logFilePrinter = null;
    private TLogManager_Interface logManager= null;




    //Static
    private static ConcurrentHashMap <String, TLogger> loggersList= null;

    //Static Initiator
    static {
        defaultLogPrinter= new TLogLinearPrettyPrinter();

        try{

            //Generating The Default log Path
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            Calendar calendar = Calendar.getInstance();
            final String defaultLogPath= FilenameUtils.separatorsToSystem(TDefaults.defaultDir
                    + "\\Logs"
                    + "\\" + calendar.get(Calendar.YEAR)
                    + "\\" + (calendar.get(Calendar.MONTH)+1) //LocalDate.now().getMonthValue()
                    + "\\" + calendar.get(Calendar.DAY_OF_MONTH)
                    + "\\" + calendar.get(Calendar.HOUR)
                    + "-" + calendar.get(Calendar.MINUTE)
                    + "-" + calendar.get(Calendar.SECOND)
                    + "___" + System.currentTimeMillis()
                    + ".html");

            //Force Create The Default log Path Directories
            new File (new File(defaultLogPath).getParentFile().getPath()).mkdirs();

            defaultLogFilePrinter = new THtmlLogPrinter(new FileOutputStream(new File(defaultLogPath)));
        }catch (Exception e){
            System.out.println(e);
        }
    }



    //public static logEvent()


    private TLogger(){
        this.init(null);
    }


    private void init(String className){


        /*if (className != null) {
            this.className = className;
            Class clazz= TSkyClassLoaderImpl1.loadByName(className);
            if (clazz != null){
                fileName= clazz.
            }
        }
        else*/ {
            this.className = Thread.currentThread().getStackTrace()[4].getClassName();
            this.fileName = Thread.currentThread().getStackTrace()[4].getFileName();
        }

        if (this.logFilePrinter == null)
            this.logFilePrinter = defaultLogFilePrinter;

        if (this.logPrinter == null)
            this.logPrinter= defaultLogPrinter;


    }

    public String getClassName() {
        return className;
    }

    /*Trace - Only when I would be "tracing" the code and trying to find one part of a function specifically.
    Debug - Information that is diagnostically helpful to people more than just developers (IT, sysadmins, etc.).
    Info - Generally useful information to log (service start/stop, configuration assumptions, etc). Info I want to always have available but usually don't care about under normal circumstances. This is my out-of-the-box config level.
    Warn - Anything that can potentially cause application oddities, but for which I am automatically recovering. (Such as switching from a primary to backup server, retrying an operation, missing secondary data, etc.)
    Error - Any error which is fatal to the operation, but not the service or application (can't open a required file, missing data, etc.). These errors will force user (administrator, or direct user) intervention. These are usually reserved (in my apps) for incorrect connection strings, missing services, etc.
            Fatal - Any error that is forcing a shutdown of the service or application to prevent data loss (or further data loss). I reserve these only for the most heinous errors and situations where there is guaranteed to have been data corruption or loss.
      */

    //******************************************************************************************************************

    public void debug(String logMessage, Throwable e){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.DEBUG.getValue())
            return;
        this.doLog(TMCodeSeverity.DEBUG, logMessage, null, -1, e);
    }

    public void debug(String logMessage){

        this.debug(logMessage, null, -1);
    }

    public void debug(TMCode mCode, String ...args){

        this.debug(mCode.compile(args));
    }

    public void debug(String logMessage, String methodName, int lineNumber){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.DEBUG.getValue())
            return;
        this.doLog(TMCodeSeverity.DEBUG, logMessage, methodName, lineNumber, null);
    }

    public void debug (TException e){

        doLog(TMCodeSeverity.DEBUG, e);
    }

    //******************************************************************************************************************

    public void trace(String logMessage, Throwable e){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.TRACE.getValue())
            return;
        this.doLog(TMCodeSeverity.TRACE, logMessage, null, -1, e);
    }

    public void trace(String logMessage){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.TRACE.getValue())
            return;
        this.trace(logMessage, null, -1);
    }

    public void trace(String logMessage, String methodName, int lineNumber){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.TRACE.getValue())
            return;
        this.doLog(TMCodeSeverity.TRACE, logMessage, methodName, lineNumber, null);
    }

    public void traceBegin(String logMessage){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.TRACE.getValue())
            return;
        this.doLog(TMCodeSeverity.TRACE, logMessage, null, -1, null
                 , TLogRecord.TProgressStatusLogType.BEGINING, (byte)0);
    }

    public void trace (TException e){

        doLog(TMCodeSeverity.TRACE, e);
    }
    //******************************************************************************************************************

    public void info(String logMessage, Throwable e){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.INFO.getValue())
            return;
        this.doLog(TMCodeSeverity.INFO, logMessage, null, -1, e);
    }

    public void info(String logMessage) {
        this.info(logMessage, null, -1);
    }

    public void info(String logMessage, String methodName, int lineNumber){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.INFO.getValue())
            return;
        this.doLog(TMCodeSeverity.INFO, logMessage, methodName, lineNumber, null);
    }

    public void infoBegin(String logMessage){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.INFO.getValue())
            return;
        this.doLog(TMCodeSeverity.INFO, logMessage, null, -1, null
                 , TLogRecord.TProgressStatusLogType.BEGINING, (byte)0);
    }

    public void info (TException e){

        doLog(TMCodeSeverity.INFO, e);
    }

    //******************************************************************************************************************

    public void warn(String logMessage, Throwable e){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.WARNING.getValue())
            return;
        this.doLog(TMCodeSeverity.WARNING, logMessage, null, -1, e);
    }

    public void warn(String logMessage){
        this.warn(logMessage, null, -1);
    }

    public void warn(String logMessage, String methodName, int lineNumber){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.WARNING.getValue())
            return;
        this.doLog(TMCodeSeverity.WARNING, logMessage, methodName, lineNumber, null);
    }

    public void warn (TException e){

        doLog(TMCodeSeverity.WARNING, e);
    }

    //******************************************************************************************************************

    public void error(String logMessage, Throwable e){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.ERROR.getValue())
            return;
        this.doLog(TMCodeSeverity.ERROR, logMessage, null, -1, e);
    }

    public void error(String logMessage){
        this.error(logMessage, null, -1);
    }


    public void error(String logMessage, String methodName, int lineNumber){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.ERROR.getValue())
            return;
        this.doLog(TMCodeSeverity.ERROR, logMessage, methodName, lineNumber, null);
    }

    public void error (TException e){

        doLog(TMCodeSeverity.ERROR, e);
    }

    //******************************************************************************************************************

    public void fatal(String logMessage, Throwable e){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.FATAL.getValue())
            return;
        this.doLog(TMCodeSeverity.FATAL, logMessage, null, -1, e);
    }

    public void fatal(String logMessage){
        this.fatal(logMessage, null, -1);
    }

    public void fatal(String logMessage, String methodName, int lineNumber){
        //Ignore The log If It's Level Is Less Than Min log Level
        if (minLogLevelForPrint.getValue() > TMCodeSeverity.FATAL.getValue())
            return;
        this.doLog(TMCodeSeverity.FATAL, logMessage, methodName, lineNumber, null);
    }

    public void fatal (TException e){

        doLog(TMCodeSeverity.FATAL, e);
    }

    //******************************************************************************************************************

    //ToDo: Performance
    public synchronized void doLog(TMCodeSeverity logLevel, TException e){

        doLog(logLevel, null, null, 0, e, null, (byte)-1);
    }

    private synchronized void doLog(TMCodeSeverity logLevel, String logDescription, String methodName, int lineNumber
            , Throwable e){

        doLog(logLevel, logDescription, methodName, lineNumber, e, null, (byte)-1);
    }

    private synchronized void doLog(TMCodeSeverity logLevel, String logDescription, String methodName, int lineNumber
            , Throwable e, TLogRecord.TProgressStatusLogType ProgressStatus, byte progress){

        if ((e != null) && (e.getCause() != null))
            doLog(logLevel, logDescription, methodName, lineNumber, e.getCause(), ProgressStatus, progress);


        //Var
        StackTraceElement[] stackTraceElements= null;
        boolean manualDataProvided= false;

        StringBuilder descriptionBuilder;
        if (logDescription == null)
            descriptionBuilder= new StringBuilder();
        else
            descriptionBuilder= new StringBuilder(logDescription);


        TLogRecord logRecord= new TLogRecord();

        if ( ! TObjects.nullOrEmpty(methodName) && (lineNumber > 0)){
            logRecord.setMethodName(methodName);
            logRecord.setLineNumber(lineNumber);
            manualDataProvided= true;
        }

        if (e != null)
        /*if ( e.getStackTrace().length > 4)*/ {
            //Copy Stack Trace Elements From index Number 4
            /*stackTraceElements= Arrays.copyOfRange(e.getStackTrace(), 4, e.getStackTrace().length);*/
            stackTraceElements= e.getStackTrace();
            manualDataProvided= true;
            if (TException.class.isInstance(e)){
                descriptionBuilder.append( ((TException)e).getmCode().toString() + "\n"
                        + "Exception Class: " + e.getClass().getSimpleName() + "\n"
                        + ((TException)e).getmCode().getRawMessage() );

                descriptionBuilder.append("\nParams: ");
                ((TException)e).entrySet().forEach(stringObjectEntry -> {
                    /*String fieldName= stringObjectEntry.getKey();*/
                    descriptionBuilder.append("\n" + stringObjectEntry.getKey() + ": " + stringObjectEntry.getValue());
                });

                descriptionBuilder.append("\n\nStack trace:");
                StackTraceElement[] stackTraceElements1=  e.getStackTrace();
                for (StackTraceElement stackTraceElement :stackTraceElements1){
                    descriptionBuilder.append("\n" + stackTraceElement.toString());
                }

            } else
                descriptionBuilder.append("\nException: " + e.toString());
        }

        if ((stackTraceType.equals(TLoggerStackTraceType.ALWAYS_AUTO_STACK_TRACE))
          ||(stackTraceType.equals(TLoggerStackTraceType.AUTO_STACK_TRACE_IF_NO_MANUAL_DATA_PROVIDED) && ! manualDataProvided)) {
            stackTraceElements=  Thread.currentThread().getStackTrace();
            for (int i= 1; i < stackTraceElements.length; i++){
                if ( ! stackTraceElements[i].getClassName().equals(TLogger.class.getName())){
                    stackTraceElements=  Arrays.copyOfRange(stackTraceElements, i, stackTraceElements.length);
                    break;
                }
            }
        }


        logRecord.setTimeStamp(System.currentTimeMillis());
        logRecord.setDescription(descriptionBuilder.toString());
        logRecord.setException((e != null));
        logRecord.setClassName(this.className);
        logRecord.setType(logLevel);
        logRecord.setProgressStatus(ProgressStatus);
        logRecord.setProgress(progress);

        if ((stackTraceElements != null) && (stackTraceElements.length > 0)) {
            logRecord.setMethodName(stackTraceElements[0].getMethodName());
            logRecord.setLineNumber(stackTraceElements[0].getLineNumber());
            logRecord.setFileName(stackTraceElements[0].getFileName());
        }

        if (!(stackTraceType.equals(TLoggerStackTraceType.ALWAYS_NO_STACK_TRACE)))
            logRecord.setStackTraceElements(stackTraceElements);


        this.logPrinter.print(logRecord);

        if (logLevel.getValue() >= this.minLogLevelForFile.getValue())
        if (this.logFilePrinter != null)
            this.logFilePrinter.print(logRecord);


    }




   private StackTraceElement[] getCallStackFromIndex(int index){

       StackTraceElement[] mainStackTraceElements;
       //mainStackTraceElements.

       /*String nameofCurrMethod = new Throwable()
               .getStackTrace()[0]
               .getMethodName();
        */

       /*String nameofCurrMethod = new exception()
               .getStackTrace()[0]
               .getMethodName();
               */

       /*String nameofCurrMethod = new Object() {}
               .getClass()
               .getEnclosingMethod()
               .getName();
                */

       return Thread.currentThread()
               .getStackTrace();

   }






   public static TLogger getLogger() {

       String currentClassName = null;
       try {
           currentClassName = Thread.currentThread().getStackTrace()[2].getClassName();
       } catch (Throwable e) {
           return null;
       }

       if (loggersList == null)
           loggersList = new ConcurrentHashMap<String, TLogger>();

       if (loggersList.contains(currentClassName))
           return loggersList.get(currentClassName);
       else {
           TLogger newLogger = new TLogger();
           loggersList.put(currentClassName, newLogger);
           return newLogger;
       }
   }


}
