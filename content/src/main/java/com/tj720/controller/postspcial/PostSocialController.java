package com.tj720.controller.postspcial;




import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.social.PostSocial;

import com.tj720.service.PostSocialService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

/**
 * @ClassName: PostSocialController
 * @Description: 社教控制层
 * @Author: xiaoao
 * @Date: 2018/10/15
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/postsocial")
public class PostSocialController {

      @Autowired
      private PostSocialService postSocialService;


    /**
     * 删除社教信息
     * @param id
     * @return
     * @throws Exception
     */
    @ControllerAop(action= "删除社教信息")
    @RequestMapping("/deleteSocial")
    @ResponseBody
    public JsonResult deleteSocial(String id) throws Exception{
        return postSocialService.deleteByPrimaryKey(id);
    }


    /**
     * @Description 查询社教列表
     * @param   //分页参数
     * @return
     */
    @ControllerAop(action = "查询社教列表")
    @RequestMapping("/getSocialList")
    @ResponseBody
    public JSONObject getSocialList(String keywords, @RequestParam(defaultValue = "1") String orderBy,@RequestParam
            (defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception{
        return  postSocialService.getSocials(keywords,orderBy,currentPage,size);
    }


    /**
     * 新增社教信息
     * @param  record
     * @return
     * @throws Exception
     */
    @ControllerAop(action = "新增社教信息")
    @RequestMapping("/saveSocial")
    @ResponseBody
    public JsonResult saveSocial(PostSocial record) throws Exception{
        return postSocialService.insert(record);
    }


    /**
     * 根据id获取社教信息（社教id）
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "根据id获取社教信息")
    @RequestMapping("/getSocialById")
    @ResponseBody
    public JsonResult selectByPrimaryKey(String id) throws Exception{
        return  postSocialService.selectByPrimaryKey(id);
    }


    /**
     * 修改社教信息
     * @param record
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "修改社教信息")
    @RequestMapping("/updateSocial")
    @ResponseBody
    public JsonResult updateByPrimaryKeySelective(PostSocial record) throws Exception{
        return postSocialService.updateByPrimaryKeySelective(record);
    }

    /**
     * 批量删除社教信息
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "批量删除社教信息")
    @RequestMapping("/batchRemove")
    @ResponseBody
    public JsonResult batchRemove(@RequestBody List<PostSocial> postSocials) throws Exception{
        return  postSocialService.batchRemove(postSocials);
    }


    /**
     * 批量删除社教信息
     * @param ids
     * @return
     * @throws Exception
     */
    @ControllerAop(action = "批量删除社教信息")
    @RequestMapping("/batchRemoves")
    @ResponseBody
    public JsonResult batchRemove(String[] ids) throws Exception{
        return  postSocialService.batchRemoves(ids);
    }



}
