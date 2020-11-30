package common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import java.util.GregorianCalendar;
import java.util.Date;


/**********************************************************
 * <pre>
 * @file		:	DateUtils.java
 * @Date		:	2020.01.
 * @author 		:	CKJ
 * @version		:	1.0
 * @Desc		:		날짜 관련 UTIL
 * </pre>
 **********************************************************/
public class DateUtils {
	 /**
     * 캘린더 객체를 yyyy-MM-dd HH:mm:ss 형태의 문자열로 변환합니다.
     * 
     * @param cal 캘린더 객체
     * @return 변환된 문자열
     */
    public static String StringFromCalendar(Calendar cal){
        // 날짜를 통신용 문자열로 변경
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(cal.getTime());
    }
     
    /**
     * 캘린더 객체를 yyyy-MM-dd형태의 문자열로 변환합니다.
     * 
     * @param cal 캘린더 객체
     * @return 변환된 문자열
     */
    public static String StringSimpleFromCalendar(Calendar cal) {
        // 날짜를 통신용 문자열로 변경
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(cal.getTime());
    }
     
    /**
     * yyyy-MM-dd HH:mm:ss 형태의 문자열을 캘린더 객체로 변환합니다.
     * 만약 변환에 실패할 경우 오늘 날짜를 반환합니다.
     * 
     * @param date 날짜를 나타내는 문자열
     * @return 변환된 캘린더 객체
     */
    public static Calendar CalendarFromString(String date) {
        Calendar cal = Calendar.getInstance();
         
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(formatter.parse(date));
        }
        catch(ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }
     
    /**
     * yyyy-MM-dd 형태의 문자열을 캘린더 객체로 변환합니다.
     * 만약 변환에 실패할 경우 오늘 날짜를 반환합니다.
     * 
     * @param date 날짜를 나타내는 문자열
     * @return 변환된 캘린더 객체
     */
    public static Calendar CalendarFromStringSimple(String date) {
        Calendar cal = Calendar.getInstance();
         
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            cal.setTime(formatter.parse(date));
        } catch(ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }
    
    
    
    /**********************************************************
     * <pre>
     * @Name		:	MonthDiff
     * @Date			:	2020.01.
     * @author 		:	CKJ
     * @version		:	1.0
     * @Desc			:	startDate, endDate  간의 개월차이	
     * </pre>
     * @param startDate
     * @param endDate
     * @return 
     **********************************************************/
    public static int MonthDiff(String startDate, String endDate){
    	int diff = 0;
    	try{
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        	Calendar startCalendar = Calendar.getInstance();
        	startCalendar.setTime(formatter.parse(startDate));
        	Calendar endCalendar = Calendar.getInstance();
        	endCalendar.setTime(formatter.parse(endDate));

			int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
			diff = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
    	}catch(ParseException e){
    		e.printStackTrace();
    	}
        

    	return diff;
    }
    

    public static int DayDiff(String targetDate){
    	int diff = 0;
    	try{
    		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    		Calendar cal = Calendar.getInstance();
        	String today = formatter.format(cal.getTime());
        	Date startDate = formatter.parse(targetDate);
        	Date endDate = formatter.parse(today);
        	
        	long diffDay = endDate.getTime() - startDate.getTime();

        	diff = Integer.parseInt( String.valueOf(diffDay / (24 * 60 * 60 * 1000)));
    	}catch(ParseException e){
    		e.printStackTrace();
    	}
    	return diff;
    }
}
