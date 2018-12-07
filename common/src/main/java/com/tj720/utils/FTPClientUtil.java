package com.tj720.utils;

import com.tj720.utils.common.IdUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * 
 * @author LC
 *
 */
public class FTPClientUtil {

	private final static Logger logger = LoggerFactory.getLogger(FTPClientUtil.class);

	/**
	 * Description: 向FTP服务器上传文件 7个参数不修改文件名
	 *
	 * @param url      FTP服务器hostname
	 * @param port     FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param path     FTP服务器保存目录
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input    输入流
	 * @return 成功返回true, 否则返回false
	 */
	public static boolean uploadFile(String url, int port, String username, String password, String path,
									 String filename, InputStream input) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);// 连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.error("FTP登陆异常,错误码={}", reply);
				return success;
			}
			ftp.changeWorkingDirectory(path);
			ftp.storeFile(filename, input);
			input.close();
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	/**
	 * Description: 从FTP服务器下载文件
	 *
	 * @param url        FTP服务器hostname
	 * @param port       FTP服务器端口
	 * @param username   FTP登录账号
	 * @param password   FTP登录密码
	 * @param remotePath FTP服务器上的相对路径
	 * @param fileName   要下载的文件名
	 * @param localPath  下载后保存到本地的路径
	 * @return
	 */
	public static boolean downFile(String url, int port, String username, String password, String remotePath,
								   String fileName, String localPath) {
		boolean success = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(url, port);
			// 如果采用默认端口,可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				logger.error("FTP登陆异常,错误码={}", reply);
				return success;
			}
			ftp.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			FTPFile[] fs = ftp.listFiles();
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					File localFile = new File(localPath + "/" + ff.getName());
					OutputStream is = new FileOutputStream(localFile);
					ftp.retrieveFile(ff.getName(), is);
					is.close();
				}
			}
			ftp.logout();
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return success;
	}

	/**
	 * Description: 向FTP服务器上传文件 8个参数 修改文件名的
	 *
	 * @param host     FTP服务器hostname
	 * @param port     FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param basePath FTP服务器基础目录
	 * @param filePath FTP服务器文件存放路径。例如分日期存放:/2015/01/01。文件的路径为basePath+filePath
	 * @param filename 上传到FTP服务器上的文件名
	 * @param input    输入流
	 * @return 成功返回true, 否则返回false
	 */
	public static boolean uploadFileFixFielname(String host, int port, String username, String password,
												String basePath, String filePath, String filename, InputStream input, String localCharset) {
		boolean result = false;
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			ftp.connect(host, port);// 连接FTP服务器
			// 如果采用默认端口,可以使用ftp.connect(host)的方式直接连接FTP服务器
			ftp.login(username, password);// 登录
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				return result;
			}
			// 开启服务器对UTF-8的支持,如果服务器支持就用UTF-8编码,否则就使用本地编码(GBK).
			if (FTPReply.isPositiveCompletion(ftp.sendCommand("OPTS UTF8", "ON"))) {
				localCharset = "UTF-8";
			}
			ftp.setControlEncoding(localCharset);
			ftp.enterLocalPassiveMode();// 设置被动模式
			// 切换到上传目录
			if (!ftp.changeWorkingDirectory(basePath + filePath)) {
				// 如果目录不存在创建目录
				String[] dirs = filePath.split("/");
				String tempPath = basePath;
				for (String dir : dirs) {
					if (null == dir || "".equals(dir))
						continue;
					tempPath += "/" + dir;
					if (!ftp.changeWorkingDirectory(tempPath)) {
						if (!ftp.makeDirectory(tempPath)) {
							return result;
						} else {
							ftp.changeWorkingDirectory(tempPath);
						}
					}
				}
			}
			// 设置上传文件的类型为二进制类型
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			// 上传文件
			if (!ftp.storeFile(filename, input)) {
				return result;
			}
			input.close();
			ftp.logout();
			result = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}

	/**
	 * 返回二进制数据 为web页面使用
	 *
	 * @param fileName
	 * @param url
	 * @param port
	 * @param username
	 * @param password
	 * @param remotePath
	 * @return
	 */
	public static byte[] downFileByte(String fileName, String url, int port, String username, String password,
									  String remotePath, String localCharset) {
		byte[] return_arraybyte = null;
		FTPClient ftpClient = new FTPClient();
		try {
			ftpClient.connect(url, port);
			ftpClient.login(username, password);// 登录
			ftpClient.changeWorkingDirectory(remotePath);// 转移到FTP服务器目录
			// 判断连接是否异常
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				logger.error("FTP登陆异常,错误码={}", reply);
				return null;
			}
			// 开启服务器对UTF-8的支持,如果服务器支持就用UTF-8编码,否则就使用本地编码(GBK).
			if (FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"))) {
				localCharset = "UTF-8";
			}
			ftpClient.setControlEncoding(localCharset);
			ftpClient.enterLocalPassiveMode();// 设置被动模式
			// ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置传输的模式
			FTPFile[] files = ftpClient.listFiles();
			for (FTPFile file : files) {
				if (file.getName().equals(fileName)) {
					ftpClient.enterLocalPassiveMode();
					InputStream ins = ftpClient.retrieveFileStream(file.getName());
					ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
					byte[] buf = new byte[204800];
					int bufsize = 0;
					while ((bufsize = ins.read(buf, 0, buf.length)) != -1) {
						byteOut.write(buf, 0, bufsize);
					}
					return_arraybyte = byteOut.toByteArray();
					byteOut.close();
					ins.close();
					break;
				}
			}
			ftpClient.logout();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭FTP
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return return_arraybyte;
	}

	/**
	 * 文件名装换 10位uuid+日期("/yyyy/MM/dd")
	 *
	 * @param uploadFile
	 * @return
	 */
	public static String fileNameConvert(MultipartFile uploadFile) {
		if (uploadFile == null) {
			return null;
		}
		String oldName = uploadFile.getOriginalFilename();
		String newName = IdUtils.getIncreaseIdByCurrentTimeMillis();
		return newName + oldName.substring(oldName.lastIndexOf("."));
	}
}

