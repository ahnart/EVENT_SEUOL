package common.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *  Class Name  : StringUtils.java
 *  Description : String Util
 *  @author CKJ
 *  @created 2016.08.01
 *  @version 1.0
*/
public class StringUtils extends org.springframework.util.StringUtils {
    
    /**
     * 생성자
     */
    private StringUtils() {}
    
    /**
     * Bean to String (로그용)
     * @param object 객체
     * @return 로그문자열
     * @author CKJ
     * @created 2016.08.01
     */
    public static String beanToString(Object object) {
        return ReflectionToStringBuilder.toString(object, ToStringStyle.MULTI_LINE_STYLE);
    }
    
    /**
     * 뉴라인 $lt;br$gt; 로 변경 (jstl 용)
     * @param string 문자열
     * @return 변환된 문자열
     * @author CKJ
     * @created 2016.08.01
     */
    public static String nl2br(String string) {
        return (string != null) ? string.replace("\n", "<br/>") : "";
    }
    
    /**
     * html 태그 제거 (jstl용)
     * @param string 문자열
     * @return 태그제거된 문자열
     * @author CKJ
     * @created 2016.08.01
     */
    public static String html2text(String string) {
        return string.replaceAll("\\<.*?>","");
    }
    
    
    /**
     * URL encoding
     * @param value 문자열
     * @return 인코딩된 문자열
     * @throws UnsupportedEncodingException
     * @author CKJ
     * @created 2016.08.01
     */
    public static String urlEncode(String value) throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8");
    }
    
    /**
     * Empty 채크
     * @param obj 객체
     * @return 결과
     * @author CKJ
     * @created 2016.08.01
     */
    public static boolean isEmpty(Object obj){
        if( obj instanceof String ) {
            return obj==null || "".equals(obj.toString().trim());
        } else if( obj instanceof List ) {
            return obj==null || ((List<?>)obj).isEmpty();
        } else if( obj instanceof Map ) {
            return obj==null || ((Map<?,?>)obj).isEmpty();
        } else if( obj instanceof Object[] ) {
            return obj==null || Array.getLength(obj)==0;
        } else {
            return obj==null;
        }
    }    
    
    public static String replaceAll(String string, String pattern, String replacement) {
        return string.replaceAll(pattern, replacement);
    }
    
}
