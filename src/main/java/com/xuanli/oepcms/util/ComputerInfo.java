package com.xuanli.oepcms.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ComputerInfo {
	
	private static String macAddressStr = null;
    private static String computerName = System.getenv().get("COMPUTERNAME");

    private static final String[] windowsCommand = { "ipconfig", "/all" };
    private static final String[] linuxCommand = { "/sbin/ifconfig", "-a" };
    private static final Pattern macPattern = Pattern.compile(".*((:?[0-9a-f]{2}[-:]){5}[0-9a-f]{2}).*",
            Pattern.CASE_INSENSITIVE);

    /**
     * 获取多个网卡地址
     * 
     * @return
     * @throws IOException
     */
    private final static List<String> getMacAddressList() throws IOException {
        final ArrayList<String> macAddressList = new ArrayList<String>();
        final String os = System.getProperty("os.name");
        final String command[];

        if (os.startsWith("Windows")) {
            command = windowsCommand;
        } else if (os.startsWith("Linux")) {
            command = linuxCommand;
        } else {
            throw new IOException("Unknow operating system:" + os);
        }
        // 执行命令
        final Process process = Runtime.getRuntime().exec(command);

        BufferedReader bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        for (String line = null; (line = bufReader.readLine()) != null;) {
            Matcher matcher = macPattern.matcher(line);
            if (matcher.matches()) {
                macAddressList.add(matcher.group(1));
                // macAddressList.add(matcher.group(1).replaceAll("[-:]",
                // ""));//去掉MAC中的“-”
            }
        }

        process.destroy();
        bufReader.close();
        return macAddressList;
    }

    /**
     * 获取一个网卡地址（多个网卡时从中获取一个）
     * 
     * @return
     */
    public static String getMacAddress() {
        if (macAddressStr == null || macAddressStr.equals("")) {
            StringBuffer sb = new StringBuffer(); // 存放多个网卡地址用，目前只取一个非0000000000E0隧道的值
            try {
                List<String> macList = getMacAddressList();
                for (Iterator<String> iter = macList.iterator(); iter.hasNext();) {
                    String amac = iter.next();
                    if (!amac.equals("0000000000E0")) {
                        sb.append(amac);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            macAddressStr = sb.toString();

        }

        return macAddressStr;
    }

    /**
     * 获取电脑名
     * 
     * @return
     */
    public static String getComputerName() {
        if (computerName == null || computerName.equals("")) {
            computerName = System.getenv().get("COMPUTERNAME");
        }
        return computerName;
    }

    /**
     * 获取客户端IP地址
     * 
     * @return
     */
    public static String getIpAddrAndName() throws IOException {
        return InetAddress.getLocalHost().toString();
    }

    /**
     * 获取客户端IP地址
     * 
     * @return
     */
    public static String getIpAddr() throws IOException {
        final String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
        	return InetAddress.getLocalHost().getHostAddress().toString();
        } else if (os.startsWith("Linux")) {
//        	Process process = null;  
//            List<String> processList = new ArrayList<String>();  
//            try {  
//                process = Runtime.getRuntime().exec("ps -aux");  
//                BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));  
//                String line = "";  
//                while ((line = input.readLine()) != null) {  
//                    processList.add(line);  
//                }  
//                input.close();  
//            } catch (IOException e) {  
//                e.printStackTrace();  
//            }  
        	
        	
        	String command1  = "/sbin/ifconfig | grep -Eo 'inet (addr:)?([0-9]*\\.){3}[0-9]*' | grep -Eo '([0-9]*\\.){3}[0-9]*' | grep -v '127.0.0.1'";
        	// 执行命令
            final Process process = Runtime.getRuntime().exec(command1);
            String ip="";
            BufferedReader bufReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            while ( (line = bufReader.readLine()) != null) {
            	System.out.println(line);
            	ip+=line;
            }
            return ip;
        } else {
            throw new IOException("Unknow operating system:" + os);
        }
    }

    /**
     * 获取电脑唯一标识
     * 
     * @return
     */
    public static String getComputerID() {
        String id = getMacAddress();
        if (id == null || id.equals("")) {
            try {
                id = getIpAddrAndName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return computerName;
    }

    /**
     * 限制创建实例
     */
    private ComputerInfo() {

    }

    public static void main(String[] args) throws IOException {
        System.out.println(ComputerInfo.getMacAddress());
        System.out.println(ComputerInfo.getComputerName());
        System.out.println(ComputerInfo.getIpAddr());
        System.out.println(ComputerInfo.getIpAddrAndName());
        System.out.println(ComputerInfo.getComputerID());
    }

}
