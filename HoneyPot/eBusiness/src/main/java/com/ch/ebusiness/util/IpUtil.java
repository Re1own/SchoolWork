package com.ch.ebusiness.util;



import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtil{
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
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
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

//
//    public static String getCityInfo(String ip){
//
//        //db
//        String dbPath = IpUtil.class.getResource("/ip2region.db").getPath();
//
//        File file = new File(dbPath);
//        if ( file.exists() == false ) {
//            System.out.println("Error: Invalid ip2region.db file");
//        }
//
//
//
//        //查询算法
//        int algorithm = DbSearcher.BTREE_ALGORITHM; //B-tree
//        //DbSearcher.BINARY_ALGORITHM //Binary
//        //DbSearcher.MEMORY_ALGORITYM //Memory
//        try {
//            DbConfig config = new DbConfig();
//            DbSearcher searcher = new DbSearcher(config, dbPath);
//
//            //define the method
//            Method method = null;
//            switch ( algorithm )
//            {
//                case DbSearcher.BTREE_ALGORITHM:
//                    method = searcher.getClass().getMethod("btreeSearch", String.class);
//                    break;
//                case DbSearcher.BINARY_ALGORITHM:
//                    method = searcher.getClass().getMethod("binarySearch", String.class);
//                    break;
//                case DbSearcher.MEMORY_ALGORITYM:
//                    method = searcher.getClass().getMethod("memorySearch", String.class);
//                    break;
//            }
//
//            DataBlock dataBlock = null;
//            if ( Util.isIpAddress(ip) == false ) {
//                System.out.println("Error: Invalid ip address");
//            }
//
//            dataBlock  = (DataBlock) method.invoke(searcher, ip);
//
//            return dataBlock.getRegion();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

}