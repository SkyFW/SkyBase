package org.skyfw.base.log.printers;

import org.skyfw.base.mcodes.TMCodeSeverity;

public class TLogPrinterColorSet<T> {

    public T DEBUG_LOG_COLOR;//= TTerminalTextColor.WHITE;
    public T TRACE_LOG_COLOR;//= TTerminalTextColor.WHITE_BRIGHT;
    public T INFO_LOG_COLOR;//= TTerminalTextColor.CYAN_BRIGHT;
    public T WARNING_LOG_COLOR;//= TTerminalTextColor.YELLOW_BRIGHT;
    public T ERROR_LOG_COLOR;//= TTerminalTextColor.RED;
    public T FATAL_LOG_COLOR;//= TTerminalTextColor.RED_BRIGHT;

    public T getColorByLogLevel(TMCodeSeverity logLevel) {
        switch (logLevel) {
            case DEBUG:
                return this.DEBUG_LOG_COLOR;

            case TRACE:
                return this.TRACE_LOG_COLOR;

            case INFO:
                return this.INFO_LOG_COLOR;

            case WARNING:
                return this.WARNING_LOG_COLOR;

            case ERROR:
                return this.ERROR_LOG_COLOR;

            case FATAL:
                return this.FATAL_LOG_COLOR;

            default:
                return this.DEBUG_LOG_COLOR;

        }
    }

}
