package com.cnstock.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取访问者的IP
 * @author lzd
 * @date 2019年4月23日
 * @version
 */
public class IpUtils {
	 public static String getIpAddr(HttpServletRequest request) {
	        String ipAddress = null;
	        try {
	            ipAddress = request.getHeader("x-forwarded-for");
	            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	                ipAddress = request.getHeader("Proxy-Client-IP");
	            }
	            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	                ipAddress = request.getHeader("WL-Proxy-Client-IP");
	            }
	            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
	                ipAddress = request.getRemoteAddr();
	                if (ipAddress.equals("127.0.0.1")) {
	                    // 根据网卡取本机配置的IP
	                    InetAddress inet = null;
	                    try {
	                        inet = InetAddress.getLocalHost();
	                        ipAddress = inet.getHostAddress();
	                    } catch (UnknownHostException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }
	            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
	            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
	                                                                // = 15
	                if (ipAddress.indexOf(",") > 0) {
	                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
	                }
	            }
	        } catch (Exception e) {
	            ipAddress="";
	        }
	        // ipAddress = this.getRequest().getRemoteAddr();
	        
	        return ipAddress;
	    }
}