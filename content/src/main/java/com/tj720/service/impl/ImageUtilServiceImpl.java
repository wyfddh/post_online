package com.tj720.service.impl;

import com.tj720.model.common.Attachment;
import com.tj720.service.AttachmentService;
import com.tj720.service.ImageUtiilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: 程荣凯
 * @Date: 2019/1/10 18:59
 */
@Service
public class ImageUtilServiceImpl implements ImageUtiilService{
    @Autowired
    private  AttachmentService attachmentService;
    @Autowired
    HttpServletRequest request;
    /**
     * 获取编辑器内容转化
     *
     * @param content 编辑器内容
     * @param type    类型（1，转化，0，不转化）
     * @return
     */
    @Override
    public String getContent(String content, String type) {
        if (null == content){
            return content;
        }
        if (type.equals("1")){
            String newStr = replaceHtmlTag(content, "img", "src", "'");
            return newStr;
        }else {
            return content;
        }
    }

    public static void main(String[] args) {
        String content = "<p align=\"center\"><img alt=\"\" src=\"http://bj.chinapost.com.cn/res/branch/1807/1807470264.jpg\" data-bd-imgshare-binded=\"1\">　　</p><p align=\"center\" style=\"text-align: left; \"><span style=\"text-align: justify;\">近日，在史家小学一年级学生入队仪式上，东城区邮政分公司与史家小学养正邮局开展了“放飞梦想、寄语未来——写给未来的自己”主题活动信件交接仪式。仪式中，一年级的学生们将写给未来自己的信交给投递员，邮政工作人员加盖养正邮局日戳同时现场封装邮袋。信件承载着孩子们的希望与梦想，开启了“慢递”旅程。6年后，学生们小学毕业时，信件将被寄到他们的手中。此次活动让学生们</span><span style=\"text-align: justify;\">受了传统书信文化的魅力。</span></p>";
        ImageUtilServiceImpl imageUtilService = new ImageUtilServiceImpl();
//        System.out.println(imageUtilService.getContent(content,"1"));

//        String newStr = replaceHtmlTag(content, "img", "src", "\"");
//        System.out.println(newStr);
    }

    public  String replaceHtmlTag(String str, String beforeTag,
                                          String tagAttrib, String endTag) {
        String regxpForTag = "<\\s*" + "img" + "\\s+([^>]*)\\s*" ;
        String regxpForTagAttrib = "src" + "=\\s*\"([^\"]+)\"" ;
        Pattern patternForTag = Pattern.compile (regxpForTag,Pattern. CASE_INSENSITIVE );
        Pattern patternForAttrib = Pattern.compile (regxpForTagAttrib,Pattern. CASE_INSENSITIVE );
        Matcher matcherForTag = patternForTag.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result = matcherForTag.find();
        // 循环找出每个 img 标签
         while (result) {
         StringBuffer sbreplace = new StringBuffer( "<img " );
         Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag
                             .group(1));
         if (matcherForAttrib.find()) {
             String fileTransPath = request.getContextPath()+"/attach/getFileTransPath.do?id="+matcherForAttrib.group(1);
             if (fileTransPath != null){
                 matcherForAttrib.appendReplacement(sbreplace, "src='"+fileTransPath
                         + endTag);
             }

         }
         matcherForAttrib.appendTail(sbreplace);
         matcherForTag.appendReplacement(sb, sbreplace.toString());
         result = matcherForTag.find();
         }
         matcherForTag.appendTail(sb);
         return sb.toString();

         }

    public  String backReplaceHtmlTag(String str, String beforeTag,
                                  String tagAttrib, String endTag) {
        String regxpForTag = "<\\s*" + "img" + "\\s+([^>]*)\\s*" ;
        String regxpForTagAttrib = "src" + "=\\s*\"([^\"]+)\"" ;
        String regxpForTagAttribError = "errorPath" + "=\\s*\"([^\"]+)\"" ;
        Pattern patternForTag = Pattern.compile (regxpForTag,Pattern. CASE_INSENSITIVE );
        Pattern patternForAttrib = Pattern.compile (regxpForTagAttrib,Pattern. CASE_INSENSITIVE );
        Pattern patternForAttribError = Pattern.compile (regxpForTagAttribError,Pattern. CASE_INSENSITIVE );
        Matcher matcherForTag = patternForTag.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result = matcherForTag.find();
        // 循环找出每个 img 标签
        while (result) {
            StringBuffer sbreplace = new StringBuffer( "<img " );
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag
                    .group(1));
            if (matcherForAttrib.find()) {
                Matcher matcherForAttribError = patternForAttribError.matcher(matcherForTag
                        .group(1));
                if (matcherForAttribError.find()){
                    matcherForAttrib.appendReplacement(sbreplace, "src=\""+matcherForAttribError.group(1)
                            + endTag);
//                    matcherForAttribError.appendReplacement(sbreplace, "");
                }
            }
            matcherForAttrib.appendTail(sbreplace);
            matcherForTag.appendReplacement(sb, sbreplace.toString());
            result = matcherForTag.find();
        }
        matcherForTag.appendTail(sb);
        return sb.toString();

    }

    /**
     * 拿到转换前的数据
     *
     * @param content
     * @return
     */
    @Override
    public String backContent(String content) {
        String newStr = backReplaceHtmlTag(content, "img", "src", "\"");
        return newStr;
    }
}
