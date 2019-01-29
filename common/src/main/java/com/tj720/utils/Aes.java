package com.tj720.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
 
public class Aes
{
	private static String handlerKey(String apiKey) {
		String tempkey = "HDALd)9dkA*&1kS$CKSJ}{|A";
		if (apiKey.length() > 16) {
			apiKey = apiKey.substring(0, 16);
		} else if (apiKey.length() < 16) {
			apiKey = apiKey + tempkey.substring(0, 16 - apiKey.length());
		}
		return apiKey;
	}
	 public static final String IV="CRAPG_@W8#_19#10";
	 public static String encrypt(String data){
		 //String PWD = "mipApiKey";
         String mm = "mipApiKey";
         mm = handlerKey(mm);
         try {
             Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
             int blockSize = cipher.getBlockSize();
 
             byte[] dataBytes = data.getBytes();
             int plaintextLength = dataBytes.length;
             if (plaintextLength % blockSize != 0) {
                 plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
             }
 
             byte[] plaintext = new byte[plaintextLength];
             System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
              
             SecretKeySpec keyspec = new SecretKeySpec(mm.getBytes(), "AES");
             IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
 
             cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
             byte[] encrypted = cipher.doFinal(plaintext);
 
             return  parseByte2HexStr(encrypted);
 
         } catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }
 
     public static String desEncrypt(String data){
		 //String PWD = "mipApiKey";
         String mm = "mipApiKey";
//		 if(setting!=null)
//			 mm = setting.getValue();
         mm = handlerKey(mm);
         try
         {
        	 if(data==null||"".equals(data)){
        		 return "";
        	 }
        	 data=data.trim();
             byte[] encrypted1 = parseHexStr2Byte(data);
              
             Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
             SecretKeySpec keyspec = new SecretKeySpec(mm.getBytes(), "AES");
             IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes());
              
             cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
 
             byte[] original = cipher.doFinal(encrypted1);
             String originalString = new String(original);
             return originalString.trim();
         }
         catch (Exception e) {
             e.printStackTrace();
             return null;
         }
     }  
     /**将二进制转换成16进制
      * @param buf
      * @return
      */
     public static String parseByte2HexStr(byte buf[]) {
             StringBuffer sb = new StringBuffer();
             for (int i = 0; i < buf.length; i++) {
                     String hex = Integer.toHexString(buf[i] & 0xFF);
                     if (hex.length() == 1) {
                             hex = '0' + hex;
                     }
                     sb.append(hex.toUpperCase());
             }
             return sb.toString();
     }
     /**将16进制转换为二进制
      * @param hexStr
      * @return
      */
     public static byte[] parseHexStr2Byte(String hexStr) {
             if (hexStr.length() < 1) {
                 return null;
             }
             byte[] result = new byte[hexStr.length()/2];
             for (int i = 0;i< hexStr.length()/2; i++) {
                     int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
                     int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
                     result[i] = (byte) (high * 16 + low);
             }
             return result;
     }
     
     
     
     
     
     public static void main(String args[]){
    	 String content = "<img src='kjasbdfkjba'>	<p>撒旦法上档次v德胜东村V型操作<iframe class='ueditor_baidumap' src='http://127.0.0.1:8080/mip/back/lib/ueditor/1.4.3/dialogs/map/show.html#center=116.409704,39.873565&zoom=17&width=530&height=340&markers=116.404,39.915&markerStyles=l,A' height='344' frameborder='0' width='534'></iframe></p>";
    	 String txtcontent = content.replaceAll("</?(?!img|/?br|h\\d)[^>]+>", ""); //剔出<html>的标签  
         txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符  
         System.out.println(txtcontent); 
     }
 }