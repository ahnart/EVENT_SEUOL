package common.util;
 

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


public class ImageUtils {

    /****************************************************************************************
     * ※ getImageSize : 이미지 파일의 가로세로 크기를 구한다. ※
     *
     * @input
     * String path : 이미지파일의 경로
     *
     * @output
     * int[1] : 이미지파일의 가로세로 크기
     ****************************************************************************************/
    public static int[] getImageSize(String path){
        Image img = new ImageIcon(path).getImage();
        
        int[] size = new int[2];
        
        size[0] = img.getWidth(null);
        size[1] = img.getHeight(null);
        
        return size;
    }
    
    
    
    /**
     * 리사이징 처리된 이미지 생성
     * @Method Name	: makeThumbnailImage
     * @param source : 원본  이미지 경로
     * @param target : 생성할 이미지 경로
     * @param sizeX : 최대 가로 사이즈
     * @param sizeY : 최대 세로 사이즈
     * @return
     * @Description	:
     */
    public static boolean makeThumbnailImage(String source, String target, int sizeX, int sizeY){
        return makeThumbnailImage(source, target, sizeX, sizeY, false);
    }
    
    
    
    /**
     * 리사이징 처리된 이미지 생성
     * @Method Name	: makeThumbnailImage
     * @param source : 원본  이미지 경로
     * @param target : 생성할 이미지 경로
     * @param sizeX : 최대 가로 사이즈
     * @param sizeY : 최대 세로 사이즈
     * @param scaleFixed : 최대 사이즈 유지 여부
     * @return
     * @Description	:
     */
    public static boolean makeThumbnailImage(String source, String target, int sizeX, int sizeY, boolean scaleFixed){
        return makeThumbnailImage(source, target, sizeX, sizeY, scaleFixed, Color.BLACK);
    }
    
    
    
    /**
     * 리사이징 처리된 이미지 생성
     * @Method Name	: makeThumbnailImage
     * @param source : 원본  이미지 경로
     * @param target : 생성할 이미지 경로
     * @param sizeX : 최대 가로 사이즈
     * @param sizeY : 최대 세로 사이즈
     * @param scaleFixed : 최대 사이즈 유지 여부
     * @param backgroundColor : 배경 색상
     * @return
     * @Description	:
     */
    public static boolean makeThumbnailImage(String source, String target, int sizeX, int sizeY, boolean scaleFixed, Color backgroundColor){
        
        try{
            BufferedImage sourceImage = ImageIO.read(new File(source));
            
            
    
            int oldWidth = sourceImage.getWidth(); 
            int oldHeight = sourceImage.getHeight(); 
            
            int width = sizeX; 
            int height = (sizeX * oldHeight) / oldWidth;
            
            if( height > sizeY ){
                width = (sizeY * width) / height;
                height = sizeY;
            }
            
            int startX = (!scaleFixed ? 0 : (int)Math.ceil(((sizeX - width) / 2)));
            int startY = (!scaleFixed ? 0 : (int)Math.ceil(((sizeY - height) / 2)));
            
            
            BufferedImage thumbImage = new BufferedImage((scaleFixed ? sizeX : width), (scaleFixed ? sizeY : height), BufferedImage.TYPE_INT_BGR);
            
            Graphics2D graphics = thumbImage.createGraphics();
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            graphics.setColor(backgroundColor);
            graphics.setBackground(backgroundColor);
            
            graphics.fillRect(0, 0, sizeX, sizeY);
            graphics.drawImage(sourceImage, startX, startY, width, height, null);
            
            ImageIO.write(thumbImage, "jpg", new File(target));
            
            return true;

        } catch(Exception ex){
            System.out.println(ex);
            
            return false;
        }
            
        
        /*
        
        Image imgSource = new ImageIcon(source).getImage(); 

        int oldWidth = imgSource.getWidth(null); 
        int oldHeight = imgSource.getHeight(null); 
        
        int newWidth = sizeX; 
        int newHeight = sizeY;
        
        if( !scaleFixed ){
            newWidth = sizeX; 
            newHeight = (sizeX * oldHeight) / oldWidth;
            
            if( newHeight > sizeY ){
                newWidth = (sizeY * newWidth) / newHeight;
                newHeight = sizeY;
            }
        }
        
        Image imgTarget = imgSource.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH); 
        
        try{
            int pixels[] = new int[newWidth * newHeight]; 
            
            int startX = (int)Math.ceil(((sizeX - newWidth) / 2));
            int startY = (int)Math.ceil(((sizeY - newHeight) / 2));
            
    
            PixelGrabber pg = new PixelGrabber(imgTarget, 0, 0, newWidth, newHeight, pixels, 0, newWidth); 
            pg.grabPixels(); 
    
            BufferedImage bi = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB); 
            bi.setRGB(startX, startY, newWidth, newHeight, pixels, 0, newWidth); 
    
            FileOutputStream fos = new FileOutputStream(target); 
    
            JPEGImageEncoder jpeg = JPEGCodec.createJPEGEncoder(fos); 
            JPEGEncodeParam jep = jpeg.getDefaultJPEGEncodeParam(bi); 

            jep.setQuality(1, false); 
            jpeg.encode(bi, jep); 
    
            fos.close();
            
            return true;

        } catch(Exception e){
            System.out.println("ImageUtils.saveThumbnailImage(String source, String target, int sizeX, int sizeY) : " + e);
            
            return false;
        }*/
        
    }
}
