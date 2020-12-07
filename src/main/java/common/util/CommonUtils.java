package common.util;

import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**********************************************************
 *
 * <pre>
 * @file		:	CommonUtils.java
 * @Date		:	2020.01.
 * @author 		:	CKJ
 * @version		:	1.0
 * @Desc		:	공통 유틸 파일	
 * </pre>
 * 
 **********************************************************/
public class CommonUtils {


    /**********************************************************
     *
     * <pre>
     * @Method Name	:	isNullOrEmpty
     * @Date		:	2020.01.
     * @author 		:	CKJ
     * @version		:	1.0
     * @Desc		:	Null 여부 반환
     * </pre>
     * 
     * @param s
     * @return
     **********************************************************/
    public static boolean isNullOrEmpty(Object s){
        return (s == null);
    }

        
    /**********************************************************
     *
     * <pre>
     * @Method Name	:	isNullOrEmpty
     * @Date		:	2020.01.
     * @author 		:	CKJ
     * @version		:	1.0
     * @Desc		:	Null 여부 반환
     * </pre>
     * 
     * @param s
     * @return
     **********************************************************/
    public static boolean isNullOrEmpty(String s){
        return ( (s == null) || s.trim().equals("") );
    }
    
    
    /**********************************************************
     *
     * <pre>
     * @Method Name	:	replaceNull
     * @Date		:	2020.01.
     * @author 		:	CKJ
     * @version		:	1.0
     * @Desc		:	origin Null 일시 Default 값 반화
     * </pre>
     * 
     * @param origin
     * @param iDefault
     * @return
     **********************************************************/
    public static int replaceNull(Object origin, int iDefault){
        if( origin == null )
            return iDefault;
        else
            return replaceNull(origin.toString(), iDefault);
    }
    
    
    public static String replaceNull(Object origin, String strDefault){
        if( origin == null )
            return strDefault;
        else
            return replaceNull(origin.toString(), strDefault);
    }


    public static String replaceNull(String strOrg, String strDefault){
        return CommonUtils.isNullOrEmpty(strOrg) ? strDefault : strOrg;
    }
    
    
        

    public static int replaceNull(String strOrg, int iDefault){
        return ( !CommonUtils.isNullOrEmpty(strOrg) && CommonUtils.isNumeric(strOrg) ) ? Integer.parseInt(strOrg) : iDefault;
    }
    
    
    public static Long replaceNull(String strOrg, Long lDefault){
    	return ( !CommonUtils.isNullOrEmpty(strOrg) && CommonUtils.isNumeric(strOrg) ) ? Long.parseLong(strOrg): lDefault; 
    }
        
    
    /**********************************************************
     *
     * <pre>
     * @Method Name	:	isNumeric
     * @Date		:	2020.01.
     * @author 		:	CKJ
     * @version		:	1.0
     * @Desc		:	숫자 여부 체크
     * </pre>
     * 
     * @param s
     * @return
     **********************************************************/
    public static boolean isNumeric(String s){
        char[] chars = s.toCharArray();
        
        for(int index=0; index < chars.length; index++){
            if( ((int)chars[index] < 48) || ((int)chars[index] > 57) ){
                return false;
            }
        }
        
        return true;
    }
    
    
    /**********************************************************
     *
     * <pre>
     * @Method Name	:	isAlphabet
     * @Date		:	2020.01.
     * @author 		:	CKJ
     * @version		:	1.0
     * @Desc		:	영문(대,소문자) 체크
     * </pre>
     * 
     * @param s
     * @return
     **********************************************************/
    public static boolean isAlphabet(String s){
        char[] chars = s.toCharArray();
        
        for(int index=0; index < chars.length; index++){
            if( ((int)chars[index] < 65) && ((int)chars[index] > 90) || 
                    ((int)chars[index] < 97) && ((int)chars[index] > 122) ){
                return false;
            }
        }
        
        return true;
    }
    

    

    /****************************************************************************************
     * ※ readTextFile : 파일 내용을 읽어서          String으로 반환. ※
     *
     * @input:
     * String dir : path
     * 
     * @output:
     * String : 파일 내용
     ****************************************************************************************/ 
    public static String readTextFile(String path){
        StringBuffer sb = new StringBuffer();

        if( CommonUtils.isExistsFile(path) ){
            FileReader reader = null;
            
            try{
                // 파일리더 객체
                reader = new FileReader(path);
                int temp = 0;
                while(true){
                    temp = reader.read();
                    if( temp == -1 ){
                        break;
                    } else{
                        sb.append( (char)temp );
                    }
                }
                
            } catch(Exception e){
                System.out.println(e);
            }

        } else{
            System.out.println("FileUtils.readTextFile(String path) : \"" + path + "\"에 파일이 없습니다!");
        }
        
        
        return sb.toString();
    }
    
    
    
    /****************************************************************************************
     * ※ isExistsFile : 파일이 존재하는지 여부를 반환. ※
     *
     * @input:
     * String path : 파일 경로
     * 
     * @output:
     * boolean : 파일이 존재하는지 여부
     ****************************************************************************************/ 
    public static boolean isExistsFile(String path){
        if( CommonUtils.isNullOrEmpty(path) ){
            return false;
        
        } else{
            File file = new File(path);
            
            return file.exists();
        }
    }

    /**********************************************************
    *
    * <pre>
    * @Method Name	:	stripTags
    * @author 		:	CKJ
    * @version		:	1.0
    * @Desc		:	태그 변환 
    * </pre>
    * 
    * @param strOrg : 원본 문자열
    * @return String : 태그 변환된 문자열
    **********************************************************/
    public static String stripTags(String strOrg){
        String str = CommonUtils.replaceNull(strOrg, "");
        
        
        str = CommonUtils.eregi_replace(" union", " u nion", str);
        str = CommonUtils.eregi_replace(" select", " s elect", str);
        str = CommonUtils.eregi_replace(" update", " u pdate", str);
        str = CommonUtils.eregi_replace(" delete", " d elete", str);
        str = CommonUtils.eregi_replace(" insert", " i nsert", str);
        str = CommonUtils.eregi_replace(" drop", " d rop", str);
        
        
        str = CommonUtils.eregi_replace("<(/?)([^<>]*)?>", "&lt;$1$2&gt;", str);            // 태그 치환..!
        str = CommonUtils.eregi_replace("(javascript|vbscript)+", "_$1_", str);                // 스크립트 치환..!
        str = CommonUtils.eregi_replace("(<(/?)(script|style)([^<>]*)>)+", "_$1_", str);    // 스크립트 치환..!
        str = CommonUtils.eregi_replace("'", "`", str);
        str = CommonUtils.eregi_replace("=", "&#61", str);
        
        
        // 자바스크립트 명령어 치환..!
        //str = CommonUtils.eregi_replace("(onload|location|cookie|alert|window|open|onmouse|onkey|onclick)+", " ", str);        
        str = CommonUtils.eregi_replace("(onreset|onmove|onstop|onpaste|onstart|onresize|onrowexit|onselect|onmousewheel|ondataavailable|onafterprint|onafterupdate|onmousedown|onbeforeactivate|onbeforecopy|ondatasetchanged|onbeforedeactivate|onbeforeeditfocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onpropertychange|ondatasetcomplete|oncellchange|onlayoutcomplete|onmousemove|oncontextmenu|oncontrolselect|onreadystatechange|onselectionchange|onrowsinserted|onactivae|oncopy|oncut|onclick|onchange|onbeforecut|ondblclick|ondeactivate|ondrag|ondragend|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror|onerrorupdate|onfilterchange|onfinish|onfocus|onresizestart|onunload|onselectstart|onfocusin|onfocusout|onhelp|onkeydown|onkeypress|onkeyup|onrowsdelete|onload|onlosecapture|onload|onlosecapture|onbounce|onmouseenter|onmouseleave|onbefore|onmouseout|onmouseover|onmouseup|onresizeend|onabort|onmoveend|onmovestart|onrowenter|onsubmit|onblur)+", " ", str);
        return str;
    }

    /**********************************************************
    *
    * <pre>
    * @Method Name	:	eregi_replace
    * @author 		:	CKJ
    * @version		:	1.0
    * @Desc		:	정규식을 이용해 치환된 문자열 반환(대소문자 무시)
    * </pre>
    * 
    * @param pattern : 정규식 패턴
    * @param strReplace : 치환될 문자열
    * @param strOrg : 원본 문자열
    * @return String : 정규식을 이용해 치환된 문자열
    **********************************************************/
    public static String eregi_replace(String pattern, String strReplace, String strOrg){
        String str = CommonUtils.replaceNull(strOrg, "");
        
        Pattern p = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(str);
        
        return m.replaceAll(strReplace);
    }
        
    
    
    public static String maskingPhone(String strOrg) {
    	String str = CommonUtils.replaceNull(strOrg, "");
    	if(!CommonUtils.isNullOrEmpty(str)) {

        	if(str.indexOf("-") < 0) {
        		if(strOrg.length() == 11) {
        			str = str.substring(0, 3)+"-****-"+str.substring(7, 11);
        		}else {
        			str = str.substring(0, 3)+"-***-"+str.substring(6, 10);
        		}
        	}else {
        		String[] phoneArray = str.split("-");
        		str = phoneArray[0]+"-****-"+phoneArray[2];
        	}
    	}
    	return str;
    }
    public static String maskingName(String strOrg) {
    	String str = CommonUtils.replaceNull(strOrg, "");
    	if(!CommonUtils.isNullOrEmpty(str)) {
    		if(str.length() == 2) {
    			str = str.substring(0, 1)+"*";
    		}else {
    			String str2 = str.substring(0, 1);
    			for(int i = 0 ; i<str.length()-2;i++) {
    				str2 += "*";
    			}
    			str2 += str.substring(str.length()-1, str.length());
    			str = str2;
    		}	
    	}
    	return str;
    }
    public static String maskingEmail(String strOrg) {
    	String str = CommonUtils.replaceNull(strOrg, "");
    	if(!CommonUtils.isNullOrEmpty(str)) {
    		String[] emailArray = str.split("@");
    		if(emailArray[0].length() > 4) {
    			str = emailArray[0].substring(0, emailArray[0].length()-4)+"****@";
    		} else {    			
    			str = emailArray[0].substring(0, 1) + "****@";
    			
    		}
    		
    		str += emailArray[1];
    	}
    	return str;
    }
}
