package com.tj720.controller.postthemeshow;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.service.PostThemeShowService;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/themeshow")
public class PostThemeShowController{

    @Autowired
    private PostThemeShowService  postThemeShowService;


    /**
     * @Author  xa
     * @Description 查询主题展数据
     * @return
     */
    @ControllerAop(action = "查询主题展数据")
    @RequestMapping("/getThemeShowList")
    @ResponseBody
    public LayUiTableJson getThemeList(String themeName,String themeSource,@RequestParam(defaultValue = "1")
            String orderBy,@RequestParam(defaultValue = "1") Integer
            currentPage,@RequestParam(defaultValue = "10") Integer size) throws Exception{
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        JsonResult themeShowList = postThemeShowService.themeshowList(themeName, themeSource,orderBy,pageInfo);
        if (themeShowList != null && themeShowList.getSuccess() == 1){
            if (null != themeShowList){
                //List<PostThemeShow> themeShow = (List<PostThemeShow>)themeShowList.getData();
                return new LayUiTableJson(0,themeShowList.getMsg(),pageInfo.getAllRow(),(List)themeShowList.getData());
            }else {
                return new LayUiTableJson(0,themeShowList.getMsg(),0,null);
            }
        }else {
            return new LayUiTableJson(1,themeShowList.getMsg(),0,null);
        }

    }




    /**
     * @Author  xa
     * @Description 根据id删除主题展
     * @return
     */
    @ControllerAop(action = "根据id删除主题展")
    @RequestMapping("/deleteById")
    @ResponseBody
    public JsonResult deleteByPrimaryKey(String id) throws Exception{
        return postThemeShowService.deleteByPrimaryKey(id);
    }



    /**
     *  批量删除主题展 ids
     * @param
     * @return
     */
    @ControllerAop(action = "批量删除主题展")
    @RequestMapping("/updateThemebByIds")
    public JsonResult updateThemebByIds(@RequestParam(value="ids")String[] ids) {
        List<String> asList = Arrays.asList(ids);
        return  postThemeShowService.updateThemebByIds(asList);
    }



    /**
     * 新增主题展
     * @param  record
     * @return
     * @throws Exception
     */
    @ControllerAop(action = "新增主题展信息")
    @RequestMapping("/saveThemeShow")
    @ResponseBody
    public JsonResult saveThemeShow(PostThemeShow record,String picids) throws Exception{
        return postThemeShowService.insertSelective(record,picids);
    }


    /**
     * 根据id获取主题展信息（id）
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "根据id获取主题展信息")
    @RequestMapping("/getShowById")
    @ResponseBody
    public JsonResult selectByPrimaryKey(String id) throws Exception{
         return  postThemeShowService.selectByPrimaryKey(id);
    }

    /**
     * 修改主题展
     * @param record
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "修改主题展信息")
    @RequestMapping("/updateThemeShow")
    @ResponseBody
    public JsonResult updateByPrimaryKeySelective(PostThemeShow record,String picids) throws Exception{
        return  postThemeShowService.updateByPrimaryKeySelective(record,picids);
    }


    /**
     * 查询主题来源集合  （主题来源）
     * @param
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "修改主题展信息")
    @RequestMapping("/getSourceOptions")
    @ResponseBody
    public JsonResult getSourceOptions() throws Exception{
        return  postThemeShowService.getSourceOptions();
    }



    /**
     * 修改推荐 （主题来源）
     * @param
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "首页推荐和精选专题装填修改")
    @RequestMapping("modifyRecommend")
    @ResponseBody
    public JsonResult modifyRecommend(String recommendStatus, String recommendId, String recommendName) throws Exception{
        JsonResult jsonResult = null;
            if (StringUtils.isNotBlank(recommendStatus) && StringUtils.isNotBlank(recommendId) && StringUtils.isNotBlank(recommendName)) {
                PostThemeShow postThemeShow = (PostThemeShow)postThemeShowService.selectByPrimaryKey(recommendId).getData();
                if ("sytj".equals(recommendName)) {
                    if ("1".equals(recommendStatus)) {
                        //查询首页推荐数，限制1
                        Integer countSytj = postThemeShowService.CountSytj();
                        if (countSytj ==0 ) {
                            postThemeShow.setPageRecommend(recommendStatus);
                        } else {
                            return new JsonResult("50000012");
                        }
                    } else if ("0".equals(recommendStatus)) {
                        postThemeShow.setPageRecommend(recommendStatus);
                    }
                } else if ("jctj".equals(recommendName)) {
                    if ("1".equals(recommendStatus)) {
                        //查询精选专题总数，限制3
                        Integer countJczj = postThemeShowService.CountJczj();
                        if (countJczj < 3) {
                            postThemeShow.setSelectedTopics(recommendStatus);
                        } else {
                            return new JsonResult("50000013");
                        }
                    } else {
                        postThemeShow.setSelectedTopics(recommendStatus);
                    }
                }
                postThemeShow.setUpdateDate(new Date());
                postThemeShow.setUpdateBy(Tools.getUserId());
                try {
                    postThemeShowService.updateByPrimaryKeySelective(postThemeShow);
                    jsonResult = new JsonResult(1);
                } catch (Exception e) {
                    e.printStackTrace();
                    jsonResult = new JsonResult("111116");
                }
            } else {
                jsonResult = new JsonResult("111116");
            }


        return jsonResult;
    }





}
