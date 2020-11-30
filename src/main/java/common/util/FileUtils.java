package common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.springframework.web.multipart.MultipartFile;

/**
 *  Class Name  : FileUtils.java
 *  Description : 파일 유틸클래스
 *  @author CKJ
 *  @created 2016.08.01
 *  @version 1.0
*/
public class FileUtils {

    /**
     * 생성자
     */
    private FileUtils(){}
    
    /**
     * 확장자
     * @param fileName 파일이름
     * @return 확장자
     * @author CKJ
     * @created 2016.08.01
     */
    public static String getFileExtension(String filename) {
        return getFilenameAndExtension(filename)[1];
    }

    /**
     * 확장자
     * @param file 파일객체
     * @return 확장자
     * @author CKJ
     * @created 2016.08.01
     */
    public static String getFileExtension(File file) {
        return getFileExtension(file.getName());
    }

    /**
     * 확장자
     * @param file 파일객체
     * @return 확장자
     * @author CKJ
     * @created 2016.08.01
     */
    public static String getFileExtension(MultipartFile file) {
        return getFileExtension(file.getOriginalFilename());
    }
    
    /**
     * 파일명, 확장자 분리
     * @param filename 파일이름
     * @return 파일이름, 확장자 배열
     * @author CKJ
     * @created 2016.08.01
     */
    public static String[] getFilenameAndExtension(String filename) {
        String[] temp = new String[2];
        int index = filename.lastIndexOf(".");
        if (index == -1) {
            temp[0] = filename;
            temp[1] = "";
        } else {
            temp[0] = filename.substring(0, index);
            temp[1] = filename.substring(index + 1);
        }
        return temp;
    }
    
    public static boolean isExistsFile(String path){
        if( CommonUtils.isNullOrEmpty(path) ){
            return false;
        
        } else{
            File file = new File(path);            
            return file.exists();
        }
    }
    
    

    
    public static String readTextFile(String path){
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null; 
        try{
            //reader = new FileReader(path);   
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path),"UTF8"));
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
            //System.out.println(e);
        }
        return sb.toString();
    }
}
