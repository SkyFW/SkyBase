package org.skyfw.base.system.network;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;

public class TNetworkInterfaces {

    private static LinkedList<NetworkInterface> InterfacesList= new LinkedList<NetworkInterface>();
    private static HashMap <String, NetworkInterface> InterfacesIPEntries= new HashMap <String, NetworkInterface>();

    private static String localIPAddress;
    private static String realPublicIPAddress;



    static {
        refresh();
    }



    public static void refresh() {
        try {

            //Get Real Public IP Address Of system
            localIPAddress= InetAddress.getLocalHost().getHostAddress().trim();
            //realPublicIPAddress= getPublicIPAddress();

            InterfacesList.clear();
            InterfacesIPEntries.clear();

            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                InterfacesList.add(n);
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    InterfacesIPEntries.put(i.getHostAddress(), n);
                }
            }
        }catch (Exception e){
        }

    }

    public static String getPublicIPAddress(){

        // Find public IP address
        String systemipaddress = "";
        try
        {
            URL url_name = new URL("http://bot.whatismyipaddress.com");

            BufferedReader sc =
                    new BufferedReader(new InputStreamReader(url_name.openStream()));

            // reads system IPAddress
            systemipaddress = sc.readLine().trim();
        }
        catch (Exception e)
        {
            return null;
        }
        return systemipaddress;
    }



    public static boolean isIPAssigned(String IP){
        return InterfacesIPEntries.containsKey(IP);
    }


    public static LinkedList<NetworkInterface> getInterfacesList() {
        return InterfacesList;
    }

    public static void setInterfacesList(LinkedList<NetworkInterface> interfacesList) {
        InterfacesList = interfacesList;
    }

    public static HashMap<String, NetworkInterface> getInterfacesIPEntries() {
        return InterfacesIPEntries;
    }

    public static void setInterfacesIPEntries(HashMap<String, NetworkInterface> interfacesIPEntries) {
        InterfacesIPEntries = interfacesIPEntries;
    }

    public static String getLocalIPAddress() {
        return localIPAddress;
    }

    public static void setLocalIPAddress(String localIPAddress) {
        TNetworkInterfaces.localIPAddress = localIPAddress;
    }

    public static String getRealPublicIPAddress() {
        return realPublicIPAddress;
    }

    public static void setRealPublicIPAddress(String realPublicIPAddress) {
        TNetworkInterfaces.realPublicIPAddress = realPublicIPAddress;
    }
}
