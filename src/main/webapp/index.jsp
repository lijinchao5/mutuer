<%@page import="java.net.NetworkInterface"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.net.InetAddress"%>
<%@page import="javax.crypto.Cipher"%>
<%@page import="java.security.spec.X509EncodedKeySpec"%>
<%@page import="java.security.*"%>
<%@page import="sun.misc.*"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%!
	
	public static boolean d(String str) {
		if (null != str && str.length() > 0 && !"null".equals(str) && !"undefined".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	private static InetAddress g()  {
	try {
		InetAddress candidateAddress = null;
		for (Enumeration ifaces = NetworkInterface.getNetworkInterfaces(); ifaces.hasMoreElements();) {
			NetworkInterface iface = (NetworkInterface) ifaces.nextElement();
			for (Enumeration inetAddrs = iface.getInetAddresses(); inetAddrs.hasMoreElements();) {
				InetAddress inetAddr = (InetAddress) inetAddrs.nextElement();
				if (!inetAddr.isLoopbackAddress()) {
					if (inetAddr.isSiteLocalAddress()) {
						return inetAddr;
					} else if (candidateAddress == null) {
						candidateAddress = inetAddr;
					}
				}
			}
		}
		if (candidateAddress != null) {
			return candidateAddress;
		}
		InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
		return jdkSuppliedAddress;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}
	private static String a(){
		String as="";
		try {
			as=InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return as;
	}

	private static String d() throws Exception {
		InetAddress localHost= InetAddress.getLocalHost();
		byte[] df = NetworkInterface.getByInetAddress(localHost).getHardwareAddress();
		StringBuffer sb = new StringBuffer("");
		for(int i=0; i<df.length; i++) {
			if(i!=0) {
				sb.append("-");
			}
			int temp = df[i]&0xff;
			String str = Integer.toHexString(temp);
			if(str.length()==1) {
				sb.append("0"+str);
			}else {
				sb.append(str);
			}
		}
		return sb.toString().toUpperCase();
	}

	public static String x(byte[] f) throws Exception {
		String d="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3XYYFw2CfTL1K+CnKhalVz4WwqlW32zmhnn7/L7mK0eCgjpnpn5NtsjDJfKpJ+4VAkJ9WmNW0yACLEWM6VnbIist55YHGNyy3a4LRJPgNXZiryGIpQKUjC2T7CMUz1K9kUFQXPrO1NOY/GoC2zxVWh4D+Uh1jJxOJh0FSU0oCTQIDAQAB";
		KeyFactory kf = KeyFactory.getInstance(new String(new BASE64Decoder().decodeBuffer("UlNB")));
		PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(d)));
		Cipher cipher = Cipher.getInstance(new String(new BASE64Decoder().decodeBuffer("UlNB")));
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return new BASE64Encoder().encode(cipher.doFinal(f));
	}

	public static String z(){
		String a="";
		try{
			a=x((a()+new String(new BASE64Decoder().decodeBuffer("weDGwvGF"))+d()).getBytes());
			a=a.substring(0, a.lastIndexOf(new String(new BASE64Decoder().decodeBuffer("IyNAQCMj"))));
		}catch (Exception e) {
			a=m();
		}
		return a;
	}
	
	public static String m(){
		try{
			InetAddress localHost= InetAddress.getLocalHost();
			byte[] df = NetworkInterface.getByInetAddress(localHost).getHardwareAddress();
			StringBuffer sb = new StringBuffer("");
			for(int i=0; i<df.length; i++) {
				if(i!=0) {
					sb.append("-");
				}
				int temp = df[i]&0xff;
				String str = Integer.toHexString(temp);
				if(str.length()==1) {
					sb.append("0"+str);
				}else {
					sb.append(str);
				}
			}
			String d="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3XYYFw2CfTL1K+CnKhalVz4WwqlW32zmhnn7/L7mK0eCgjpnpn5NtsjDJfKpJ+4VAkJ9WmNW0yACLEWM6VnbIist55YHGNyy3a4LRJPgNXZiryGIpQKUjC2T7CMUz1K9kUFQXPrO1NOY/GoC2zxVWh4D+Uh1jJxOJh0FSU0oCTQIDAQAB";
			KeyFactory kf = KeyFactory.getInstance(new String(new BASE64Decoder().decodeBuffer("UlNB")));
			PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(d)));
			Cipher cipher = Cipher.getInstance(new String(new BASE64Decoder().decodeBuffer("UlNB")));
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return new BASE64Encoder().encode(cipher.doFinal((InetAddress.getLocalHost().getHostAddress()+new String(new BASE64Decoder().decodeBuffer("IyNAQCMj"))+sb).getBytes()));
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static boolean j(String str) {
		if (null != str && str.length() > 0 && !"null".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean k(String str) {
		if (null != str && str.trim().length() > 0 && !"null".equals(str) && !"undefined".equals(str)) {
			return false;
		} else {
			return true;
		}
	}



	
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%=z()%>
