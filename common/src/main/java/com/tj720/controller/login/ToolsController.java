package com.tj720.controller.login;

import com.tj720.service.ICacheService;
import com.tj720.utils.Const;
import com.tj720.utils.MyCookie;
import com.tj720.utils.ValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 程荣凯
 * @Date: 2018/10/16 12:05
 */
@Controller
@RequestMapping("/Tools")
public class ToolsController {
    @Autowired
    HttpServletResponse response;
    @Autowired
    HttpServletRequest request;
    @Autowired
    ICacheService cacheService;

    @RequestMapping("getImgCode")
    public void getImgvcode() throws IOException {
        // 设置response，输出图片客户端不缓存
        response.setDateHeader("Expires", 0);
        response.addHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
        response.setContentType("image/jpeg");
        ServletOutputStream out = response.getOutputStream();
        ValidateCodeService vservice = new ValidateCodeService();
        String uuid = MyCookie.getCookie(Const.COOKIE_UUID, false, request);
        cacheService.setStr(Const.CACHE_IMGCODE + uuid, vservice.getCode() , 10 * 60);
        cacheService.setStr(Const.CACHE_IMGCODE_TIMES + uuid, "0" , 10 * 60);
        try {
            vservice.write(out);
            out.flush();
        } finally {
            out.close();
        }
    }
}
