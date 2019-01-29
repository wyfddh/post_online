package com.tj720.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

/**
 * 
 * @author Administrator
 *
 */
public class FileUtil {
	public static   String separator = java.io.File.separator;

	private final static Map<String,String> pathCharWhiteList = new HashMap<String,String>();

	static {
		pathCharWhiteList.put("a","a");
		pathCharWhiteList.put("b","b");
		pathCharWhiteList.put("c","c");
		pathCharWhiteList.put("d","d");
		pathCharWhiteList.put("a","e");
		pathCharWhiteList.put("e","f");
		pathCharWhiteList.put("g","g");
		pathCharWhiteList.put("h","h");
		pathCharWhiteList.put("i","i");
		pathCharWhiteList.put("j","j");
		pathCharWhiteList.put("k","k");
		pathCharWhiteList.put("l","l");
		pathCharWhiteList.put("m","m");
		pathCharWhiteList.put("n","n");
		pathCharWhiteList.put("o","o");
		pathCharWhiteList.put("p","p");
		pathCharWhiteList.put("q","q");
		pathCharWhiteList.put("r","r");
		pathCharWhiteList.put("s","s");
		pathCharWhiteList.put("t","t");
		pathCharWhiteList.put("u","u");
		pathCharWhiteList.put("v","v");
		pathCharWhiteList.put("w","w");
		pathCharWhiteList.put("x","x");
		pathCharWhiteList.put("y","y");
		pathCharWhiteList.put("z","z");

	}

	private static String rootPath;
	/**
	 * 删除目录下的所有文件
	 * 
	 * @param targetPath目录路径或者文件
	 * @IOException 当操作发生异常时抛出
	 */
	public static void deleteFloderFile(String targetPath) throws IOException {
		File targetFile = new File(targetPath);
		if (targetFile.isDirectory()) {
			File[] files = targetFile.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					FileUtils.deleteDirectory(file);
				} else if (file.isFile()) {
					file.delete();
				}
			}
			targetFile.delete();
		} else if (targetFile.isFile()) {
			targetFile.delete();
		}
	}

	/**
	 * 检测用户操作的文件目录是否正确
	 * @param filePath
	 * @return
	 */
	public static String checkFilePath(String filePath){
		String temp = "";
		for (int i = 0; i < filePath.length(); i++) {
			Character curChar = null;
			Character nextChar = null;
			try{
				curChar = filePath.charAt(i);
				nextChar = filePath.charAt(i+1);
			}catch (Exception e){

			}
			if (pathCharWhiteList.get(filePath.charAt(i)+"")!=null && curChar == '\\'){
				String systemFileSp = File.separator;
				if (null != systemFileSp && systemFileSp.equals(curChar+"")){
					temp += pathCharWhiteList.get(filePath.charAt(i)+"");
				}
			}else if(pathCharWhiteList.get(filePath.charAt(i)+"")!=null && curChar != '.'){
				temp += pathCharWhiteList.get(filePath.charAt(i)+"");
			}else if(pathCharWhiteList.get(filePath.charAt(i)+"")!=null && curChar == '.' && nextChar != '.'){
				temp += pathCharWhiteList.get(filePath.charAt(i)+"");
			}
		}
		filePath = temp;
		return filePath;
	}
}
