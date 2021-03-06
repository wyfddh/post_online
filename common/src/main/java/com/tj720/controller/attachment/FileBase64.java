package com.tj720.controller.attachment;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.net.URLConnection;
import java.util.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 版权所有：2016 项目名称：ImgeBase64
 *
 * 类描述：将图片转化为Base64字符串
 * 类名称：cn.sanishan.util.Base64Img
 * 创建人：
 * 创建时间：2016年10月27日
 * 下午3:25:49
 * 修改人：
 * 修改时间：2016年10月27日 下午3:25:49
 * 修改备注：
 *
 * @version V1.0
 */
public class FileBase64 {

    /**
     * @param imgURL 网络资源位置
     * @return Base64字符串
     * @Title: GetImageStrFromUrl
     * @Description: TODO(将一张网络图片转化成Base64字符串)
     */
    public static String GetImageStrFromUrl(String imgURL) {
        String str = "";
        try {
            // 创建URL
            URL url = new URL(imgURL);
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //            URLConnection urlConnection = url.openConnection();
            //            int contentLength = urlConnection.getContentLength();


            InputStream inputStream = conn.getInputStream();
            byte[] data = new byte[conn.getContentLength()];
            byte[] temp = new byte[2048];
            int total = 0;
            int len = 0;
            while ((len = inputStream.read(temp)) > 0) {
                System.arraycopy(temp, 0, data, total, len);
                total += len;
            }
            inputStream.close();

            str = new String(new BASE64Encoder().encode(data));

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        //            BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return "data:image/png;base64,"+ str;
    }

    /**
     * @param imgPath
     * @return
     * @Title: GetImageStrFromPath
     * @Description: TODO(将一张本地图片转化成Base64字符串)
     */

    public static String GetImageStrFromPath(String imgPath) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        return encoder.encode(data);
    }

    /**
     * @param imgStr
     * @return
     * @Title: GenerateImage
     * @Description: TODO(base64字符串转化成图片)
     */
    public static boolean GenerateImage(String imgStr) {
        if (imgStr == null) // 图像数据为空
        {
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            String imgFilePath = "d://222.jpg";
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
