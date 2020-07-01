package org.skyfw.base.system.debugger.Standard;

import org.skyfw.base.system.debugger.TDebugger_Interface;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public class TStandardDebugger implements TDebugger_Interface {

    @Override
    public boolean IsDebuggerPresent(){
        try {

            RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
            List<String> args = runtime.getInputArguments();
            for (String arg : args) {
                if ("-Xdebug".equals(arg)) { // NOI18N
                    return true;
                } else if ("-agentlib:jdwp".equals(arg)) { // NOI18N
                    // The idea of checking -agentlib:jdwp
                    // is taken from org.netbeans.modules.sampler.InternalSampler
                    return true;
                } else if (arg.startsWith("-agentlib:jdwp=")) { // NOI18N
                    return true;
                }
            }
        } catch (SecurityException ex) {
        }
        return false;
    }

}
