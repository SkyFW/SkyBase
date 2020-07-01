package org.skyfw.base.init;

import org.skyfw.base.exception.TException;
import org.skyfw.base.log.TLogger;
import org.skyfw.base.mcodes.TBaseMCode;
import org.skyfw.base.mcodes.TMCodeRegistry;
import org.skyfw.base.system.debugger.TDebugger;
import org.skyfw.base.system.network.TNetworkInterfaces;
import org.skyfw.base.system.terminal.TTerminal;
import org.skyfw.base.system.terminal.TTerminalFXFont;
import org.skyfw.base.system.terminal.TTerminalTextColor;
import org.skyfw.base.utils.TStringUtils;


import java.io.*;
import java.util.concurrent.TimeUnit;
//import java.util.jar.Attributes;
//import java.util.jar.JarFile;
//import java.util.jar.Manifest;
//import java.util.logging.Logger;
//import static java.util.jar.Attributes.Name.IMPLEMENTATION_VERSION;

public class TBaseInitiator {

    public static void init() throws TException {
        TLogger logger = TLogger.getLogger();

        TMCodeRegistry.register(TBaseMCode.class);


        //Var
        String logoContent= "";
        String copyRightContent= "";
        int waitTime= 2000;
        InputStream is;

        is = ClassLoader.class.getResourceAsStream("/SkyFWLogo.txt");
        logoContent= TStringUtils.loadStringFromInputStream(is);
        //String theString = (new IOUtils).toString(file, StandardCharsets.UTF_8);

        is = ClassLoader.class.getResourceAsStream("/CopyRight.txt");
        copyRightContent= TStringUtils.loadStringFromInputStream(is);

        if (TDebugger.IsDebuggerPresent())
            waitTime= 10;

        TTerminal.clrscr();
        TTerminal.clearScreen();
        TTerminal.reset();

        /*
        system.out.println(" ═ ║ ╠ ╡ ╢ ╣ ╤ ╥ ╦ ╧ ╨ ╩ ╪ ╫ ╬");
        try { system.in.read(); }catch (exception e){ }
        */

        /*
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "chcp", "65001").inheritIO();
            Process p = pb.start();
            p.waitFor();
        }catch (exception e){}
        */

        //TTerminal.setBackgroundColor(TTerminalBGColor.BLUE_BACKGROUND);

        TTerminal.setTextColor(TTerminalTextColor.CYAN);
        System.out.println(logoContent);


        //TTerminal.reset();

        //TTerminal.setTextColor(TTerminalTextColor.CYAN_BRIGHT);
        try { TimeUnit.MILLISECONDS.sleep(waitTime); } catch (InterruptedException ex) { }
        TTerminal.writeFX("Sky", TTerminalFXFont.ASCII_3D);
        try { TimeUnit.MILLISECONDS.sleep(waitTime); } catch (InterruptedException ex) { }
        TTerminal.writeFX("FrameWork", TTerminalFXFont.LARRY_3D);
        try { TimeUnit.MILLISECONDS.sleep(waitTime); } catch (InterruptedException ex) { }
        //TTerminal.writeFX("FrameWork", TTerminalFXFont.SUB_ZERO);



        //Showing the base version
        System.out.println("Base Version: " + TBaseInitiator.class.getPackage().getImplementationVersion());
        System.out.println("");


        TTerminal.setTextColor(TTerminalTextColor.GREEN_BRIGHT);
        System.out.print("system Local Ip Address: ");
        TTerminal.setTextColor(TTerminalTextColor.GREEN_BOLD);
        System.out.println(TNetworkInterfaces.getLocalIPAddress());

        TTerminal.setTextColor(TTerminalTextColor.GREEN_BRIGHT);
        System.out.print("system Public Ip Address: ");
        TTerminal.setTextColor(TTerminalTextColor.GREEN_BOLD);
        String realPublicIPAddress= TNetworkInterfaces.getRealPublicIPAddress();
        if (realPublicIPAddress != null)
            System.out.println(realPublicIPAddress);
        else
            System.out.println("Undefined");

        /*TNetworkInterfaces.getInterfacesIPEntries().forEach( (ip, iface) -> {
            system.out.println(ip);
        });*/
        TTerminal.reset();
        System.out.println();

        //Showing Hardware Resources Brief Report
        logger.info(THardwareResourceChecker.getBriefReport(), "init", 0);




        /*
        //Could Be Some Animation Later
        for(int i= 0; i < 25; i++){
            TTerminal.goToXY(i, 5);
            system.out.print("*");
            try { TimeUnit.MILLISECONDS.sleep(100); } catch (InterruptedException ex) { }
        }
        try { TimeUnit.MILLISECONDS.sleep(waitTime); } catch (InterruptedException ex) { }*/

        TTerminal.setTextColor(TTerminalTextColor.WHITE);
        //system.out.println(TStringUtils.formatToLineWidth(copyRightContent, 80));
        logger.info(copyRightContent.replaceAll("\r\n", "\n"));


        TTerminal.reset();
    }

}
