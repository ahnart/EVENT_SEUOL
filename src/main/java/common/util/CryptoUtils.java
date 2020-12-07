package common.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.management.RuntimeErrorException;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.google.common.io.BaseEncoding;



/**********************************************************
 * <pre>
 * @file		:	CryptoUtils.java
 * @Date		:	2020.01.
 * @author 		:	CKJ
 * @version		:	1.0
 * @Desc		:		암호화 유틸
 * </pre>
 **********************************************************/
public class CryptoUtils {

		private static volatile CryptoUtils INSTANCE;
		
		// 키
		// private final static String KEY = "ABCDEFGHIJKLM0123456789NOPQRSTUVWXYZ";
		private final static String KEY = "EPOPKONSCRACHA!EVENTEPOPKONENMADA@";
		// 128bit (16자리)
		private final static String KEY_128 = KEY.substring(0, 128/8);	 
		// 256bit (32자리)
		private final static String KEY_256 = KEY.substring(0, 256/8);	 


		/**********************************************************
		 * <pre>
		 * @Name		:	encryptAES128
		 * @Date			:	2020.01.
		 * @author 		:	CKJ
		 * @version		:	1.0
		 * @Desc			:	AES 128 암호화
		 * </pre>
		 * @param str
		 * @return str
		 **********************************************************/
		public static String encryptAES128(String str) {
			String returnStr = "";
			if(!CommonUtils.isNullOrEmpty(str)){
				try {
					byte[] keyData = KEY_128.getBytes(CharEncoding.UTF_8);
					// 운용모드 CBC, 패딩은 PKCS5Padding
					Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				
					cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyData, "AES"), new IvParameterSpec(keyData));
					
					// AES 암호화
					byte[] encrypted = cipher.doFinal(str.getBytes(CharEncoding.UTF_8));
					byte[] base64Encoded = Base64.encodeBase64(encrypted);
					returnStr = new String(base64Encoded, CharEncoding.UTF_8);
				 }catch (Exception e) {}
			}
			return returnStr;
		 }

		/**********************************************************
		 * <pre>
		 * @Name		:	decryptAES128
		 * @Date			:	2020.01.
		 * @author 		:	CKJ
		 * @version		:	1.0
		 * @Desc			:	AES 128복호화
		 * </pre>
		 * @param str
		 * @return str
		 **********************************************************/
		public static String decryptAES128(String str) {
			String returnStr = "";
			if(!CommonUtils.isNullOrEmpty(str)){
				try {
					byte[] keyData = KEY_128.getBytes(CharEncoding.UTF_8);
				
					// 운용모드 CBC, 패딩은 PKCS5Padding
					Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				
					cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyData, "AES"), new IvParameterSpec(keyData));			
					byte[] base64Decoded = Base64.decodeBase64(str.getBytes(CharEncoding.UTF_8));				
					byte[] decrypted = cipher.doFinal(base64Decoded);
					returnStr = new String(decrypted, CharEncoding.UTF_8);				
				 }catch (Exception e) {}
			}
			return returnStr;
		 }
		 
		 

		/**********************************************************
		 * <pre>
		 * @Name		:	encryptAES256
		 * @Date			:	2020.01.
		 * @author 		:	CKJ
		 * @version		:	1.0
		 * @Desc			:	AES 256 암호화
		 * </pre>
		 * @param str
		 * @return str
		 **********************************************************/
		public static String encryptAES256(String str) {
			String returnStr = "";
			
			if(!CommonUtils.isNullOrEmpty(str)){
				try{
					byte[] key256Data = KEY_256.getBytes(CharEncoding.UTF_8);
					byte[] key128Data = KEY_128.getBytes(CharEncoding.UTF_8);
					
					Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
					
					//Patch 필요
				    cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key256Data, "AES"), new IvParameterSpec(key128Data));
					 
					byte[] encrypted = cipher.doFinal(str.getBytes(CharEncoding.UTF_8));
					byte[] base64Encoded = Base64.encodeBase64(encrypted);
					returnStr = new String(base64Encoded, CharEncoding.UTF_8);
				 }catch (Exception e) {}
			}
			 return returnStr;
		 }


		/**********************************************************
		 * <pre>
		 * @Name		:	decryptAES256
		 * @Date			:	2020.01.
		 * @author 		:	CKJ
		 * @version		:	1.0
		 * @Desc			:	AES 256복호화
		 * </pre>
		 * @param str
		 * @return str
		 **********************************************************/
		public static String decryptAES256(String str) {
			String returnStr = "";
			if(!CommonUtils.isNullOrEmpty(str)){
				try{
					byte[] key256Data = KEY_256.getBytes(CharEncoding.UTF_8);
					byte[] key128Data = KEY_128.getBytes(CharEncoding.UTF_8);
					
					Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
					cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key256Data, "AES"), new IvParameterSpec(key128Data));
					
					byte[] base64Decoded = Base64.decodeBase64(str.getBytes(CharEncoding.UTF_8));				
					byte[] decrypted = cipher.doFinal(base64Decoded);
					returnStr = new String(decrypted, CharEncoding.UTF_8);
				 }catch (Exception e) {}
			}
			 return returnStr;
		 }

		
		
		
		
		/**********************************************************
		 * <pre>
		 * @Name		:	encryptSHA256
		 * @Date			:	2020.01.
		 * @author 		:	CKJ
		 * @version		:	1.0
		 * @Desc			:	SHA256 암호화 단방향
		 * </pre>
		 * @param str
		 * @return str
		 **********************************************************/
		public static String encryptSHA256(String str) {
			 String returnStr = "";
			 if(!CommonUtils.isNullOrEmpty(str)){
				 try {			 		
					 byte[] stringBytes = str.getBytes();
					 int stringBytesLength = stringBytes.length;				 
					 byte[] dataBytes = new byte[1024];
					 for (int i = 0; i < stringBytesLength; i++) {
						 dataBytes[i] = stringBytes[i];
					 }
					 
					 MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
					 messageDigest.update(dataBytes, 0, stringBytesLength);
					 
					 byte[] encrypted = messageDigest.digest();
					 
					 StringBuffer sb = new StringBuffer();
					 // hex, 16진수				 
					 for (int i = 0; i < encrypted.length; i++) {
						 sb.append(Integer.toString((encrypted[i] & 0xff) + 0x100, 16).substring(1));
					 }
					 returnStr = sb.toString();
					 

				 }catch (Exception e) {} 
			 }
			 return returnStr;
			
		 }
		 

		 
		/**********************************************************
		 * <pre>
		 * @Name		:	encryptSHA512
		 * @Date			:	2020.01.
		 * @author 		:	CKJ
		 * @version		:	1.0
		 * @Desc			:	SHA512 암호화 단방향
		 * </pre>
		 * @param str
		 * @return str
		 **********************************************************/
		public static String encryptSHA512(String str) {
			 String returnStr = "";
			 if(!CommonUtils.isNullOrEmpty(str)){
				 try {
				 		
					 byte[] stringBytes = str.getBytes();
					 int stringBytesLength = stringBytes.length;
					 
					 byte[] dataBytes = new byte[1024];
					 for (int i = 0; i < stringBytesLength; i++) {
						 dataBytes[i] = stringBytes[i];
					 }
					 
					 MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
					 messageDigest.update(dataBytes, 0, stringBytesLength);
					 //messageDigest.update(dataBytes);
				
					 byte[] encrypted = messageDigest.digest();		
					 
					 // base64 인코딩
					 byte[] base64Encoded = Base64.encodeBase64(encrypted);
					 // 결과
					 returnStr = new String(base64Encoded, CharEncoding.UTF_8);
					 
				 }catch (Exception e) {	}	 
			 }
			 return returnStr;
		 }
		 
		 
		 
		/**********************************************************
		 * <pre>
		 * @Name		:	encodeBase64
		 * @Date			:	2020.01.
		 * @author 		:	CKJ
		 * @version		:	1.0
		 * @Desc			:	BASE64_ENCODE
		 * </pre>
		 * @param str
		 * @return str
		 **********************************************************/
		public static String encodeBase64(String str){
			 String returnStr = "";
			 if(!CommonUtils.isNullOrEmpty(str)){
				 try{
					 returnStr = Base64.encodeBase64String(str.getBytes());		        
				 } catch(Exception e){}
			 }		
			 return returnStr;
		 }
		 
		 
		/**********************************************************
		 * <pre>
		 * @Name		:	decodeBase64
		 * @Date			:	2020.01.
		 * @author 		:	CKJ
		 * @version		:	1.0
		 * @Desc			:	BASE64_DECODE
		 * </pre>
		 * @param str
		 * @return
		 **********************************************************/
		public static String decodeBase64(String str){
			 String returnStr = "";	        
			 if(!CommonUtils.isNullOrEmpty(str)){
				 try{
					 returnStr = new String(Base64.decodeBase64(str.getBytes()));
				 } catch(Exception e){}
			 }
			 return returnStr;
		 }

		

		// 거래소 (COIN ONE PAYLOAD / SHA512 )		
		private static final String DEFAULT_ENCODING = "UTF-8";
		private static final String HMAC_SHA256= "HmacSHA256";
		private static final String HMAC_SHA512 = "HmacSHA512";
		 
		public static byte[] hmacSha512(String value, String key){
		    try {
		        SecretKeySpec keySpec = new SecretKeySpec(
		                key.getBytes(DEFAULT_ENCODING),
		                HMAC_SHA512);
		 
		        Mac mac = Mac.getInstance(HMAC_SHA512);
		        mac.init(keySpec);
		        return mac.doFinal(value.getBytes(DEFAULT_ENCODING));
		 
		    } catch (NoSuchAlgorithmException e) {
		        throw new RuntimeException(e);
		    } catch (InvalidKeyException e) {
		        throw new RuntimeException(e);
		    } catch (UnsupportedEncodingException e) {
		        throw new RuntimeException(e);
		    }
		}

		public static byte[] hmacSha256(String value, String key){
		    try {
		        SecretKeySpec keySpec = new SecretKeySpec(
		                key.getBytes(DEFAULT_ENCODING),
		                HMAC_SHA256);
		 
		        Mac mac = Mac.getInstance(HMAC_SHA256);
		        mac.init(keySpec);
		        return mac.doFinal(value.getBytes(DEFAULT_ENCODING));
		 
		    } catch (NoSuchAlgorithmException e) {
		        throw new RuntimeException(e);
		    } catch (InvalidKeyException e) {
		        throw new RuntimeException(e);
		    } catch (UnsupportedEncodingException e) {
		        throw new RuntimeException(e);
		    }
		}

		public static String asHex(byte[] bytes){
		    return BaseEncoding.base16().lowerCase().encode(bytes);
		}
		
		
}
