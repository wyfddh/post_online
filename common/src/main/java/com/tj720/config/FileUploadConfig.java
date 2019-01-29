
package com.tj720.config;

import com.tj720.controller.springbeans.Config;
import com.tj720.model.common.Attachment;
import com.tj720.utils.FtpUtil;
import com.tj720.utils.oss.OSSUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipOutputStream;


/**
 * @Auther: caiming
 * @Date: 2018/11/22 21:14
 * @Description:
 */

import com.tj720.utils.FtpUtil;
import sun.misc.BASE64Encoder;

public class FileUploadConfig {

    private FtpUtil ftpUtil;
    private OSSUtils ossUtils;
    private Config config;
    private byte uploadType = 2;    //1-ftp    2-oss

    public FileUploadConfig(Config config){
        this.config = config;
    }


/**
     *
     * 功能描述: 上传文件
     *
     * @param: [path, fileName, file]
     * @return: boolean
     * @auther: caiming
     * @date: 2018/11/22 21:40
     */

    public boolean uploadFile(String path, String fileName, InputStream file){
        if(uploadType == 1){
            ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
            boolean uploadFtpFile = ftpUtil.uploadFtpFile(path, fileName, file);
            return uploadFtpFile;
        }else if(uploadType == 2){
            ossUtils = new OSSUtils(config);
            String fileUrl = ossUtils.uploadFile(path, fileName, file);
            if(StringUtils.isNotBlank(fileUrl)){
                return true;
            }
        }
        return false;
    }


/**
     *
     * 功能描述: 删除文件
     *
     * @param: [path]
     * @return: boolean
     * @auther: caiming
     * @date: 2018/11/22 21:40
     */

    public boolean deleteFile(String path){
        if(uploadType == 1){
            ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
            boolean delFtpFile = ftpUtil.delFtpFile(path);
            return delFtpFile;
        }else if(uploadType == 2){
            ossUtils = new OSSUtils(config);
            boolean delFtpFile = ossUtils.deleteFile(path);
            return delFtpFile;
        }
        return false;
    }


/**
     *
     * 功能描述: 下载文件
     *
     * @param: [path, out]
     * @return: void
     * @auther: caiming
     * @date: 2018/11/22 21:40
     */

    public void downloadFile(String path, OutputStream out){
        if(uploadType == 1){
            ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
            ftpUtil.downFtpFile(path, out);
        }else if(uploadType == 2){
            ossUtils = new OSSUtils(config);
            ossUtils.downloadOssFile(path, out);
        }
    }


    /**
     *
     * 功能描述: 下载文件toZip
     *
     * @param: [path, out]
     * @return: void
     * @auther: caiming
     * @date: 2018/11/22 21:40
     */
    public void downloadFileToZip(List< Attachment > files, ZipOutputStream out, HttpServletResponse response){
        if(uploadType == 1){
            ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
            ftpUtil.downFtpFiletoZip(files, out, response);
        }else if(uploadType == 2){
            ossUtils = new OSSUtils(config);
            ossUtils.downOssFileToZip(files, out, response);
        }
    }

    /**
     *
     * 功能描述: 下载文件toZip
     *
     * @param: [path, out]
     * @return: void
     * @auther: caiming
     * @date: 2018/11/22 21:40
     */
    public void downloadFileToZipPlus(List<HashMap<String,Object>> files, ZipOutputStream out, HttpServletResponse response){
        if(uploadType == 1){
            ftpUtil = new FtpUtil(config.getFtpUrl(), Integer.valueOf(config.getFtpPort()), config.getFtpUserName(), config.getFtpPassWord());
            ftpUtil.downFtpFiletoZipPlus(files, out, response);
        }else if(uploadType == 2){
            ossUtils = new OSSUtils(config);
            ossUtils.downOssFileToZipPlus(files, out, response);
        }
    }


    public String getFileStreamPath(String path){
//        ossUtils = new OSSUtils(CheckConfig);
        String newPath = "";
//        InputStream inputStream = ossUtils.getFileStreamPath(path);
        InputStream inputStream = null;
        try {
            URL url = new URL(path);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //            URLConnection urlConnection = url.openConnection();
            //            int contentLength = urlConnection.getContentLength();
            inputStream = conn.getInputStream();
            byte[] data = new byte[conn.getContentLength()];
            byte[] temp = new byte[2048];
            int total = 0;
            int len = 0;
            while ((len = inputStream.read(temp)) > 0) {
                System.arraycopy(temp, 0, data, total, len);
                total += len;
            }
            inputStream.close();
            newPath = new String(new BASE64Encoder().encode(data));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            }catch (Exception e){

            }

        }
        return "data:image/png;base64,"+ newPath;
    }

}

