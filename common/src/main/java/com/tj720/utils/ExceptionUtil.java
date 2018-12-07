package com.tj720.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Exception工具类
 * @author: caiming
 * @date: 2018年9月2日
 */
public class ExceptionUtil {

	/**
	 * 返回错误信息字符串
	 * 
	 * @param ex
	 *            Exception
	 * @return 错误信息字符串
	 */
	public static String getExceptionMessage(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}

}
