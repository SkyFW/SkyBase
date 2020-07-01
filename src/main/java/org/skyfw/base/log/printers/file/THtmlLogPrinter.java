package org.skyfw.base.log.printers.file;

import org.skyfw.base.log.printers.TLogPrinterColorSet;
import org.skyfw.base.log.printers.TLogPrinter_Interface;
import org.skyfw.base.log.TLogRecord;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;

public class THtmlLogPrinter implements TLogPrinter_Interface {

    private TLogPrinterColorSet<String> logPrinterColorSet;

    private OutputStream outputStream;
    private OutputStreamWriter outputStreamWriter;

    static {
    }

    public THtmlLogPrinter(OutputStream outputStream) {
        this.outputStream= outputStream;
        this.outputStreamWriter= new OutputStreamWriter(this.outputStream);

        logPrinterColorSet= new TLogPrinterColorSet();

        logPrinterColorSet.DEBUG_LOG_COLOR =   "White";
        logPrinterColorSet.TRACE_LOG_COLOR =   "White";
        logPrinterColorSet.INFO_LOG_COLOR =    "Cyan";
        logPrinterColorSet.WARNING_LOG_COLOR = "Yellow";
        logPrinterColorSet.ERROR_LOG_COLOR =   "Maroon";
        logPrinterColorSet.FATAL_LOG_COLOR =   "Maroon";


        /*
        if (TDebugger.IsDebuggerPresent()) {
        }
        else{
        }*/
    }

    @Override
    public void print(TLogRecord logRecord) {

        //Var
        String mainTable;
        String details;

        //Path p = Paths.get("C:\\Hello\\AnotherFolder\\The file Name.PDF");
        //String file = p.getFileName().toString();

        StringBuilder headerStringBuilder = new StringBuilder();
        headerStringBuilder.append("<table style=\"width:100%; text-align:left; \">");
        headerStringBuilder.append("<tr>");

        headerStringBuilder.append("<th ");
        headerStringBuilder.append("width=\"25%\" >");
        headerStringBuilder.append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(logRecord.getTimeStamp()));
        headerStringBuilder.append("</th>");

        headerStringBuilder.append("<th ");
        headerStringBuilder.append("width=\"15%\" >");
        headerStringBuilder.append(logRecord.getType().toString());
        headerStringBuilder.append("</th>");

        headerStringBuilder.append("<th ");
        headerStringBuilder.append("width=\"25%\" >");
        headerStringBuilder.append(logRecord.getClassName().substring(logRecord.getClassName().lastIndexOf('.') + 1));
        headerStringBuilder.append("</th>");

        headerStringBuilder.append("<th ");
        headerStringBuilder.append("width=\"25%\" >");
        headerStringBuilder.append(logRecord.getMethodName()+"()");
        headerStringBuilder.append("</th>");

        headerStringBuilder.append("<th ");
        headerStringBuilder.append("width=\"10%\" >");
        headerStringBuilder.append("E: ");
        headerStringBuilder.append(new Boolean(logRecord.isException()).toString());
        headerStringBuilder.append("</th>");
        headerStringBuilder.append("</tr>");
        headerStringBuilder.append("</table>");



        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table bgcolor=\"Gray\" dir=\"ltr\"  border=\"3\" bordercolor=\"Gray\" style=\"width:100%; text-align:left; ");
        stringBuilder.append("color: ");
        stringBuilder.append(this.logPrinterColorSet.getColorByLogLevel(logRecord.getType()));
        stringBuilder.append(";\">");

        stringBuilder.append("<tr>");
            stringBuilder.append("<th>");
                stringBuilder.append(headerStringBuilder.toString());
            stringBuilder.append("</th>");
        stringBuilder.append("</tr>");


        stringBuilder.append("<tr>");
            stringBuilder.append("<th>");
                //Replacing The New Line Char With HTML Line Break Tag
                stringBuilder.append(logRecord.getDescription().replaceAll("\n", "<br>"));
            stringBuilder.append("</th>");
        stringBuilder.append("</tr>");

        stringBuilder.append("</table>");
        stringBuilder.append("<hr style=\" margin:5px \">");
        stringBuilder.append( "\n");



        //mainTable= mainTable.replace("<DetailTableColor>",TTerminalTextColor.RED.toString());
        //mainTable= mainTable.replace("</DetailTableColor>",TTerminalTextColor.CYAN.toString());
        //mainTable= mainTable.replace("╔══",TTerminalTextColor.RED.toString() + "╔══");
        //mainTable= mainTable.replace("══╝","══╝" + detailsTable+TTerminalTextColor.CYAN.toString());


        try {
            this.outputStreamWriter.write(stringBuilder.toString());
            this.outputStreamWriter.flush();
            this.outputStream.flush();
        }catch (Exception e){
        }

    }


}
