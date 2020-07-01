package org.skyfw.base.log.printers.terminal;

import com.jakewharton.fliptables.FlipTable;
import org.skyfw.base.log.printers.TLogPrinterColorSet;
import org.skyfw.base.log.printers.TLogPrinter_Interface;
import org.skyfw.base.mcodes.TMCodeSeverity;
import org.skyfw.base.log.TLogRecord;
import org.skyfw.base.system.debugger.TDebugger;
import org.skyfw.base.system.terminal.TTerminal;
import org.skyfw.base.system.terminal.TTerminalTextColor;
import org.skyfw.base.utils.TStringUtils;
import org.fusesource.jansi.AnsiConsole;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class TLogLinearPrettyPrinter implements TLogPrinter_Interface {

    static TLogPrinterColorSet<TTerminalTextColor> logPrinterColorSet;
    private static TLoadingAnimationThread loadingAnimationThread;

    static {

        logPrinterColorSet= new TLogPrinterColorSet();

        if (TDebugger.IsDebuggerPresent()) {
            logPrinterColorSet.DEBUG_LOG_COLOR =   TTerminalTextColor.WHITE;
            logPrinterColorSet.TRACE_LOG_COLOR =   TTerminalTextColor.WHITE;
            logPrinterColorSet.INFO_LOG_COLOR =    TTerminalTextColor.CYAN_BRIGHT;
            logPrinterColorSet.WARNING_LOG_COLOR = TTerminalTextColor.YELLOW_BRIGHT;
            logPrinterColorSet.ERROR_LOG_COLOR =   TTerminalTextColor.RED_BRIGHT;
            logPrinterColorSet.FATAL_LOG_COLOR =   TTerminalTextColor.RED;
        }
        else{
            logPrinterColorSet.DEBUG_LOG_COLOR =   TTerminalTextColor.WHITE;
            logPrinterColorSet.TRACE_LOG_COLOR =   TTerminalTextColor.WHITE;
            logPrinterColorSet.INFO_LOG_COLOR =    TTerminalTextColor.CYAN_BRIGHT;
            logPrinterColorSet.WARNING_LOG_COLOR = TTerminalTextColor.YELLOW_BRIGHT;
            logPrinterColorSet.ERROR_LOG_COLOR =   TTerminalTextColor.RED_BRIGHT;
            logPrinterColorSet.FATAL_LOG_COLOR =   TTerminalTextColor.RED;
        }

    }


    @Override
    public void print(TLogRecord logRecord) {

        if (this.loadingAnimationThread != null){
            this.loadingAnimationThread.terminateAndWait();
            this.loadingAnimationThread= null;
        }

        if (logRecord.getProgressStatus() != null) {
           this.loadingAnimationThread= new TLoadingAnimationThread(logRecord);
           return;
        }

        //Var
        String mainTable;
        String details;

        //Path p = Paths.get("C:\\Hello\\AnotherFolder\\The file Name.PDF");
        //String file = p.getFileName().toString();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(logRecord.getTimeStamp()));
        stringBuilder.append(" | ");
        stringBuilder.append(logRecord.getType().toString());
        stringBuilder.append(" | ");
        stringBuilder.append(logRecord.getClassName().substring(logRecord.getClassName().lastIndexOf('.') + 1));
        stringBuilder.append(" | ");
        stringBuilder.append(logRecord.getMethodName()+"()");
        stringBuilder.append(" |  E: ");
        stringBuilder.append(new Boolean(logRecord.isException()).toString());


        String[] headers = {TStringUtils.formatToLineWidth(stringBuilder.toString(), 75)};

        String[][] data = {
                {TStringUtils.formatToLineWidth(logRecord.getDescription(), 74)}
        };

        mainTable= FlipTable.of(headers, data);



        //mainTable= mainTable.replace("<DetailTableColor>",TTerminalTextColor.RED.toString());
        //mainTable= mainTable.replace("</DetailTableColor>",TTerminalTextColor.CYAN.toString());
        //mainTable= mainTable.replace("╔══",TTerminalTextColor.RED.toString() + "╔══");
        //mainTable= mainTable.replace("══╝","══╝" + detailsTable+TTerminalTextColor.CYAN.toString());

        TTerminal.reset();

        setTerminalColorByLogLevel(logRecord.getType());

        System.out.println(mainTable.trim());
        TTerminal.reset();
    }


    public static void setTerminalColorByLogLevel(TMCodeSeverity logLevel){
        switch (logLevel){
            case DEBUG:
                TTerminal.setTextColor(logPrinterColorSet.DEBUG_LOG_COLOR);
                break;
            case TRACE:
                TTerminal.setTextColor(logPrinterColorSet.TRACE_LOG_COLOR);
                break;
            case INFO:
                TTerminal.setTextColor(logPrinterColorSet.INFO_LOG_COLOR);
                break;
            case WARNING:
                TTerminal.setTextColor(logPrinterColorSet.WARNING_LOG_COLOR);
                break;
            case ERROR:
                TTerminal.setTextColor(logPrinterColorSet.ERROR_LOG_COLOR);
                break;
            case FATAL:
                TTerminal.setTextColor(logPrinterColorSet.FATAL_LOG_COLOR);
                break;
        }
    }



    public static class TLoadingAnimationThread extends Thread{

        private TLogRecord logRecord;
        private boolean terminated= false;

        public TLoadingAnimationThread(TLogRecord logRecord) {
            this.logRecord = logRecord;
            this.start();
        }

        public void terminateAndWait() {
            this.terminated = true;
            try {
                this.join();
            }catch (Exception e){}
        }

        public void run() {
            try {
                /*synchronized (system.out)*/ {

                    setTerminalColorByLogLevel(logRecord.getType());
                    AnsiConsole.systemUninstall();

                    String anim = "|/-\\";
                    int x = 0;
                    while ( ! this.terminated) {
                        x++;
                        String data = "\r" + anim.charAt(x % anim.length()) + " " + this.logRecord.getDescription();
                        System.out.write(data.getBytes());

                        //thread.sleep(100);
                        try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException ex) { }
                    }

                    AnsiConsole.systemInstall();
                    System.out.println("");
                    System.out.flush();
                    TTerminal.reset();
                }
            } catch (Exception e) {
            }
        }
    }


}
