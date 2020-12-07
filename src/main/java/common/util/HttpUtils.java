package common.util;

import javax.servlet.http.HttpServletRequest;

/**
 *  Class Name  : HttpUtils.java
 *  Description : HTTP 유틸
 *  @author CKJ
 *  @created 2016.08.01
 *  @version 1.0
*/
public class HttpUtils {

    /**
     * 생성자
     */
    private HttpUtils() {}
    
    /**
     * IP 구하기
     * waf -> elb -> apache -> tomcat
     * @param request HttpRequest
     * @return IP
     * @author CKJ
     * @created 2016.08.01
     */
    public static String getRemoteAddr(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || ip.toLowerCase().equals("unknown"))
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); //for iis arr

        if (ip == null || ip.length() == 0 || ip.toLowerCase().equals("unknown"))
            ip = request.getHeader("Proxy-Client-IP");

        if (ip == null || ip.length() == 0 || ip.toLowerCase().equals("unknown"))
            ip = request.getHeader("HTTP_X_FORWARDED_FOR"); //for iis arr

        if (ip == null || ip.length() == 0 || ip.toLowerCase().equals("unknown"))
            ip = request.getHeader("REMOTE_ADDR");

        if (ip == null || ip.length() == 0 || ip.toLowerCase().equals("unknown"))
            ip = request.getRemoteAddr();

        // 아이피 
        if (ip.indexOf(",") > -1) {
            ip = ip.substring(0, ip.indexOf(","));
        }

        return ip;
    }
}
