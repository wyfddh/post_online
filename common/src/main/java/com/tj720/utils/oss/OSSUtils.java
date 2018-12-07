package com.tj720.utils.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import com.tj720.controller.springbeans.Config;
import com.tj720.model.common.Attachment;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.lang.System.out;

/**
 * @Auther: caiming
 * @Date: 2018/11/22 19:49
 * @Description:
 */
public class OSSUtils {

    @Autowired
    private Config sysConfig;
    private static OSSConfig config = null;

    public OSSUtils(Config config) {
        sysConfig = config;
    }

    /**
     * 功能描述: 单个文件上传
     *
     * @param: [path, fileName, file]
     * @return: java.lang.String
     * @auther: caiming
     * @date: 2018/11/22 21:57
     */
    public String uploadFile(String path, String fileName, InputStream file) {
        config = config == null ? new OSSConfig(sysConfig) : config;
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
        return putObject(path, file, fileType, fileName);
    }


    /**
     * @param fileUrl 需要删除的文件url
     * @return boolean 是否删除成功
     * @MethodName: deleteFile
     * @Description: 单文件删除
     */
    public boolean deleteFile(String fileUrl) {
        config = config == null ? new OSSConfig(sysConfig) : config;

        String bucketName = OSSUtils.getBucketName(fileUrl);       //根据url获取bucketName
        String fileName = OSSUtils.getFileName(fileUrl);           //根据url获取fileName
        if (bucketName == null || fileName == null) return false;
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            GenericRequest request = new DeleteObjectsRequest(bucketName).withKey(fileName);
            ossClient.deleteObject(request);
        } catch (Exception oe) {
            oe.printStackTrace();
            return false;
        } finally {
            ossClient.shutdown();
        }
        return true;
    }

    /**
     * @param fileUrls 需要删除的文件url集合
     * @return int 成功删除的个数
     * @MethodName: batchDeleteFiles
     * @Description: 批量文件删除(较快)：适用于相同endPoint和BucketName
     */
    public int deleteFile(List<String> fileUrls) {
        int deleteCount = 0;    //成功删除的个数
        String bucketName = OSSUtils.getBucketName(fileUrls.get(0));       //根据url获取bucketName
        List<String> fileNames = OSSUtils.getFileName(fileUrls);         //根据url获取fileName
        if (bucketName == null || fileNames.size() <= 0) return 0;
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName).withKeys(fileNames);
            DeleteObjectsResult result = ossClient.deleteObjects(request);
            deleteCount = result.getDeletedObjects().size();
        } catch (OSSException oe) {
            oe.printStackTrace();
            throw new RuntimeException("OSS服务异常:", oe);
        } catch (ClientException ce) {
            ce.printStackTrace();
            throw new RuntimeException("OSS客户端异常:", ce);
        } finally {
            ossClient.shutdown();
        }
        return deleteCount;

    }

    /**
     * @param file
     * @param fileType
     * @param fileName
     * @return String
     * @MethodName: putObject
     * @Description: 上传文件
     */
    private String putObject(String path, InputStream file, String fileType, String fileName) {
        config = config == null ? new OSSConfig(sysConfig) : config;
        String url = null;      //默认null
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
            InputStream input = file;
            ObjectMetadata meta = new ObjectMetadata();             // 创建上传Object的Metadata
            meta.setContentType(OSSUtils.contentType(fileType));       // 设置上传内容类型
            meta.setCacheControl("no-cache");                   // 被下载时网页的缓存行为
            PutObjectRequest request = new PutObjectRequest(config.getBucketName(), "files/"+path.split(sysConfig.getRootUrl())[1].toString()+fileName, input, meta);           //创建上传请求
            ossClient.putObject(request);
            url = config.getEndpoint().replaceFirst("http://", "http://" + config.getBucketName() + ".") + "/" + fileName;
        } catch (OSSException oe) {
            oe.printStackTrace();
            return null;
        } catch (ClientException ce) {
            ce.printStackTrace();
            return null;
        } finally {
            ossClient.shutdown();
        }
        return url;
    }

    /**
     *
     * 功能描述: oss下载
     *
     * @param: [path, out]
     * @return: void
     * @auther: caiming
     * @date: 2018/11/23 8:53
     */
    public void downloadOssFile(String path, OutputStream out) {
        config = config == null ? new OSSConfig(sysConfig) : config;
        OSSClient ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        String key = path.toString();
        key = key.replaceAll("\\\\", "/");
        OSSObject object = ossClient.getObject(config.getBucketName(), key);
        BufferedInputStream input = new BufferedInputStream(object.getObjectContent());
        byte[] buffBytes = new byte[1024];
        OutputStream outputStream = out;
        int read = 0;
        try {
            while ((read = input.read(buffBytes)) != -1) {
                outputStream.write(buffBytes, 0, read);
            }
            outputStream.flush();
            outputStream.close();
            input.close();
            ossClient.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从OSS下载文件流到outputStream中
     *
     * @param files
     *            ftp存放路径
     * @param out
     *            文件输出流
     * @return true成功，false失败
     */
    public boolean downOssFileToZip(List<Attachment> files, ZipOutputStream out, HttpServletResponse response) {
        config = config == null ? new OSSConfig(sysConfig) : config;
        OSSClient ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        boolean success = false;
        try {
            List<InputStream> list = new ArrayList<InputStream>();
            for (Attachment file : files) {
                String path = file.getAttPath();
                path = path.replace(sysConfig.getRootUrl(),"files/");
                path = path.replace(sysConfig.getFtpRootPath(),"");
                path = path.replace("\\", "/");
                String fileName = path.substring(path.lastIndexOf("/") + 1);
                String key = path.toString();
                key = key.replaceAll("\\\\", "/");
                OSSObject object = ossClient.getObject(config.getBucketName(), path);
//                ObjectMetadata object = ossClient.getObject(new GetObjectRequest(config.getBucketName(), key), new File(fileName));
                InputStream input = object.getObjectContent();
                doCompress(input,file.getAttName(),out);
                //多个文件下载时，此句必加
                response.flushBuffer();
                input.close();
            }
            success = true;
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null){
                try {out.close();} catch (IOException e) {}
            }
            ossClient.shutdown();
        }
        return success;
    }






    public boolean downOssFileToZipPlus(List<HashMap<String,Object>> files, ZipOutputStream out, HttpServletResponse response) {
        config = config == null ? new OSSConfig(sysConfig) : config;
        OSSClient ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        boolean success = false;
        try {
            for (HashMap<String, Object> file : files) {
                String zipName = file.get("fileName").toString();
                List<Attachment> attachments = (List<Attachment>)file.get("attachment");
                for (Attachment attachment : attachments) {
                    String path = attachment.getAttPath();
                    path = path.replace(sysConfig.getRootUrl(),"files/");
                    path = path.replace(sysConfig.getFtpRootPath(),"");
                    path = path.replace("\\", "/");
                    String fileName = path.substring(path.lastIndexOf("/") + 1);
                    String key = path.toString();
                    key = key.replaceAll("\\\\", "/");
                    OSSObject object = ossClient.getObject(config.getBucketName(), path);
                    InputStream input = object.getObjectContent();
                    doCompress(input,attachment.getAttName(),out,zipName);
                    //多个文件下载时，此句必加
                    response.flushBuffer();
                    input.close();
                }
            }
            success = true;
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out != null){
                try {out.close();} catch (IOException e) {}
            }
            ossClient.shutdown();
        }
        return success;
    }

    /**
     * @param fileType
     * @return String
     * @MethodName: contentType
     * @Description: 获取文件类型
     */
    private static String contentType(String fileType) {
        fileType = fileType.toLowerCase();
        String contentType = "";
        switch (fileType) {
            case "bmp":
                contentType = "image/bmp";
                break;
            case "gif":
                contentType = "image/gif";
                break;
            case "png":
            case "jpeg":
            case "jpg":
                contentType = "image/jpeg";
                break;
            case "html":
                contentType = "text/html";
                break;
            case "txt":
                contentType = "text/plain";
                break;
            case "vsd":
                contentType = "application/vnd.visio";
                break;
            case "ppt":
            case "pptx":
                contentType = "application/vnd.ms-powerpoint";
                break;
            case "doc":
            case "docx":
                contentType = "application/msword";
                break;
            case "xml":
                contentType = "text/xml";
                break;
            case "mp4":
                contentType = "video/mp4";
                break;
            default:
                contentType = "application/octet-stream";
                break;
        }
        return contentType;
    }

    /**
     * @param fileUrl 文件url
     * @return String bucketName
     * @MethodName: getBucketName
     * @Description: 根据url获取bucketName
     */
    private static String getBucketName(String fileUrl) {
        String http = "http://";
        String https = "https://";
        int httpIndex = fileUrl.indexOf(http);
        int httpsIndex = fileUrl.indexOf(https);
        int startIndex = 0;
        if (httpIndex == -1) {
            if (httpsIndex == -1) {
                return null;
            } else {
                startIndex = httpsIndex + https.length();
            }
        } else {
            startIndex = httpIndex + http.length();
        }
        int endIndex = fileUrl.indexOf(".oss-");
        return fileUrl.substring(startIndex, endIndex);
    }

    /**
     * @param fileUrl 文件url
     * @return String fileName
     * @MethodName: getFileName
     * @Description: 根据url获取fileName
     */
    private static String getFileName(String fileUrl) {
        String str = "aliyuncs.com/";
        int beginIndex = fileUrl.indexOf(str);
        if (beginIndex == -1) return null;
        return fileUrl.substring(beginIndex + str.length());
    }

    /**
     * @param fileUrls 文件url
     * @return List<String>  fileName集合
     * @MethodName: getFileName
     * @Description: 根据url获取fileNames集合
     */
    private static List<String> getFileName(List<String> fileUrls) {
        List<String> names = new ArrayList<>();
        for (String url : fileUrls) {
            names.add(getFileName(url));
        }
        return names;
    }

    /**
     * 压缩到zip
     * @param fis
     * @param fileName
     * @param out
     * @throws IOException
     */
    public void doCompress(InputStream fis, String fileName, ZipOutputStream out)throws IOException{
        byte[] buffer = new byte[1024];
        out.putNextEntry(new ZipEntry(fileName));
        int len = 0;
        while ((len=fis.read(buffer))>0){
            out.write(buffer,0,len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }

    public void doCompress(InputStream fis, String fileName, ZipOutputStream out,String dir)throws IOException{
        byte[] buffer = new byte[1024];
        out.putNextEntry(new ZipEntry(dir+"/"+fileName));
        int len = 0;
        while ((len=fis.read(buffer))>0){
            out.write(buffer,0,len);
            out.flush();
        }
        out.closeEntry();
        fis.close();
    }

    private  String downloadOSS( List<String> objectNames){
        String basePath="D:/files";
        config = config == null ? new OSSConfig(sysConfig) : config;
        OSSClient ossClient = new OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
        try{
            for (String objectName:objectNames){
                //指定文件保存路径
                String filePath = basePath+"/"+objectName.substring(0,objectName.lastIndexOf("/"));
                //判断文件目录是否存在，不存在则创建
                File file = new File(filePath);
                if (!file.exists()){
                    file.mkdirs();
                }
                //判断保存文件名是否加后缀
                if (objectName.contains(".")){
                    //指定文件保存名称
                    filePath = filePath+"/"+objectName.substring(objectName.lastIndexOf("/")+1);
                }
                //获取OSS文件并保存到本地指定路径中，此文件路径一定要存在，若保存目录不存在则报错，若保存文件名已存在则覆盖本地文件
                ossClient.getObject(new GetObjectRequest(config.getBucketName(),objectName),new File(filePath));
            }
        }catch (Exception e){
            out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }finally {
            //关闭oss连接
            if (ossClient != null){
                ossClient.shutdown();
            }
        }
        return basePath;
    }






    public static void main(String[] arg, HttpServletResponse response) {
        //downloadOssFile("http://postal.oss-cn-hangzhou.aliyuncs.com/post_video/audio/2018-11-23/02dc467b83794da283f05032ebfe4dc5.mp3",  out);
        String fileName = "1111111.jpg";
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        fileType = fileType.toLowerCase();
        out.println(fileType);
    }
}
