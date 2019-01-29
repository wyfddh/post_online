package com.tj720.controller.webInterface;



import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.model.common.bookingmanage.PostBookingManage;
import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.collect.CollectDto;
import com.tj720.model.common.dict.SysDict;
import com.tj720.model.common.publiccurator.PostPublicCuratorDto;
import com.tj720.model.common.themeshow.PostThemeShow;
import com.tj720.model.common.volunteerapply.PostVolunteerApply;
import com.tj720.service.SysDictSevice;
import com.tj720.service.WebInterfaceService;
import com.tj720.utils.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *   Web端调用接口
 * @Author:  xa
 * @Date: 2018/11/3
 */
@RestController
@RequestMapping("/webInterface")
public class WebInterfaceController{

    @Autowired
    private WebInterfaceService  webCollectService;
    @Autowired
    private SysDictSevice sysDictSevice;



    /**
     * 查询首页列表(主题、典藏精选、资讯)
     *
     * @param
     * @return
     */
    @RequestMapping("/getIndexData")
    public JsonResult  getIndexData(String  userId)throws Exception{
        Map<String, Object> data = new HashMap<>();
        Page page = new Page();
        page.setSize(6);

        //首页推荐主题展
        JsonResult jsonResult1 = webCollectService.themeshowWebList(null, null,userId,null, 1, 1);
        data.put("postThemeShow",jsonResult1.getData());


        //典藏精选
        JSONObject jsonObject1 = webCollectService.selectListIndexByWebCollect(null, null, null,null, userId);
        List<Collect> collectList = (List<Collect>) jsonObject1.get("data");
        data.put("collectList",collectList);


        //资讯动态
        JsonResult informationWebList = webCollectService.getInformationWebList(null, null, null,page);
        //List<PostInformationManage> data1 = (List<PostInformationManage>) informationWebList.getData();
        data.put("informationWebList",informationWebList);
        JsonResult jsonResult = new JsonResult(1, data);
        return jsonResult;
    }




    /**
     * @Author  xa
     * @Description 查询主题展数据(含首页推荐)
     * @return
     */
    @RequestMapping("/getthemeshowWebList")
    public JsonResult getthemeshowWebList(String themeName, String themeSource,@RequestParam("userId") String userId,
          @RequestParam("id")  String id, @RequestParam(defaultValue = "1") Integer
            currentPage, @RequestParam(defaultValue = "6") Integer size) throws Exception{
        Map<String, Object> data = new HashMap<String, Object>();
        //首页推荐主题展
        //JsonResult jsonResult1 = webCollectService.themeshowWebList(null, null,userId,null, 1, 1);
        //data.put("postThemeShow",jsonResult1.getData());
        JsonResult jsonResult1 = webCollectService.themeshowWebRelationList(null, null,userId,id, 1, 1);
        data.put("postThemeShow",jsonResult1.getData());




        JsonResult themeList = webCollectService.themeshowWebNoPages(null, null,userId);
        List<CollectDto> lists = (List<CollectDto>) themeList.getData();
        data.put("themeList",lists);

        JsonResult collectList1 = webCollectService.themeshowWebNoPagesPlus(id,userId);
        List<CollectDto> collectList = (List<CollectDto>) collectList1.getData();
        data.put("collectList",collectList);

        //精选专题
        JsonResult ztlist = webCollectService.getJxztList(userId,null);
        List<PostThemeShow> ztlists = (List<PostThemeShow>) ztlist.getData();
        data.put("ztlists",ztlists);

        JsonResult jsonResult = new JsonResult(1, data);
        return  jsonResult;
    }





    /**
     * 根据id获取主题展信息（id）
     * @return
     * @throws  Exception
     */
    @RequestMapping("/getWebShowById")
    public JsonResult selectWebByPrimaryKey(@RequestParam  String id) throws Exception{
        return  webCollectService.selectWebByPrimaryKey(id);

    }



    /**
     * @Author  xa
     * @Description 查询主题展数据(精选专题)
     * @return
     */
    @RequestMapping("/getthemeshowWebztList")
    public JsonResult getthemeshowWebztList(String themeName, String themeSource,String id,
    @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "4") Integer size) throws Exception{
        return  webCollectService.themeshowWebztList(themeName,themeSource,null,currentPage,size);

    }



    /**
     * @Author  xa
     * @Description  查询藏品分类数据(二级分类)
     * @return
     */
    @RequestMapping("/getCollectTypeWebList")
    public LayUiTableJson getCollectTypeWebList() throws Exception{
        try {
            List<SysDict> sysDictList = sysDictSevice.getSysDictListByType("post_collect_two");
            return new LayUiTableJson(1,null,0,sysDictList);
        }catch (Exception e){
            e.printStackTrace();
            return new LayUiTableJson(0,"查询失败",0,null);
        }
    }

    /**
     * 查询藏品列表
     *
     * @param
     * @return
     */
    @RequestMapping("/selectListByWebCollect")
    public JSONObject selectListByWebCollect(String typeId, String sonTypeId,@RequestParam("userId")String userId,
                 @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size){
        //if("1".equals(typeId)){
        //    typeId = "2717224097316052047740414293";
        //}else if("3".equals(typeId)){
        //    typeId = "2717772932861592047740477942";
        //}else if("2".equals(typeId)){
        //    typeId = "2717475987212012047740476094";
        //}
        return webCollectService.selectListByWebCollect(null,typeId,sonTypeId,null, userId, currentPage, size);
    }



    /**
     * 查询单个藏品
     *
     * @param id
     * @return
     */
    @RequestMapping("/getWebOneCollect")
    public JsonResult getWebOneCollect(@RequestParam("id") String id){
        return  webCollectService.getOneWebCollect(id);
    }



    /**
     * 首页典藏精选藏品
     *
     * @param
     * @return
     */
    @RequestMapping("/selectIndexCollectList")
    public JsonResult selectIndexCollectList(){
        return  webCollectService.selectIndexCollectList();
    }



    /**
     * @Author  xa
     * @Description  查询邮政故事分类数据
     * @return
     */
    @RequestMapping("/getStoryTypeWebList")
    public LayUiTableJson getStoryTypeWebList() throws Exception{
        try {
            List<SysDict> sysDictList = sysDictSevice.getSysDictListByType("stamp_story");
            return new LayUiTableJson(1,null,sysDictList.size(),sysDictList);
        }catch (Exception e){
            e.printStackTrace();
            return new LayUiTableJson(0,"查询失败",0,null);
        }
    }



    /**
     * 查询单个邮政故事(分页参数为1,上一页、下一页)
     *
     * @param
     * @return
     */
    @RequestMapping("/getOnePageWebStory")
    public JsonResult getOnePageWebStory(Integer rowNum,String  storyType){
        JsonResult result = webCollectService.getDetailStoryById(rowNum,storyType);
        return  new JsonResult(1,result);
    }




    /**
     * @Author  xa
     * @Description  查询邮政故事数据
     * @return
     */
    @RequestMapping("/getStampStoryWebList")
    public JsonResult  getStampStoryWebList(String storyType,@RequestParam(defaultValue = "1") Integer
            currentPage,@RequestParam(defaultValue = "10") Integer size) throws Exception{
        JsonResult stampStoryList = null;
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        if("0".equals(storyType)){
            stampStoryList =  webCollectService.getStampStoryWebList(null, null, pageInfo);
        }else{
            stampStoryList =  webCollectService.getStampStoryWebList(storyType, null, pageInfo);
        }
        return  new JsonResult(1,stampStoryList,pageInfo);
    }



    /**
     * 根据id获取邮政故事（id）
     * @return
     * @throws  Exception
     */
    @RequestMapping("/getStoryWebById")
    public JsonResult selectWebStoyrByPrimaryKey(@RequestParam  String id) throws Exception {
        return  webCollectService.selectWebStoyrByPrimaryKey(id);
    }




    /**
     * @Author  xa
     * @Description  查询资讯分类数据
     * @return
     */
    @RequestMapping("/getInfoTypeWebList")
    public LayUiTableJson  getInfoTypeWebList() throws Exception{
        try {
            List<SysDict> sysDictList = sysDictSevice.getSysDictListByType("info_manager");
            return new LayUiTableJson(1,null,0,sysDictList);
        }catch (Exception e){
            e.printStackTrace();
            return new LayUiTableJson(0,"查询失败",0,null);
        }
    }






    /**
     * @Author  xa
     * @Description  查询资讯管理数据
     * @return
     */
    @RequestMapping("/getInformationWebList")
    public JsonResult  getInformationWebList(String informationType,String createTime,@RequestParam(defaultValue = "1") String orderBy,
    @RequestParam(defaultValue = "1") Integer currentPage,@RequestParam(defaultValue = "10") Integer size) throws Exception{
        JsonResult informationList = null;
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        if("1".equals(informationType)){
            informationList =  webCollectService.getInformationWebList(null, createTime,orderBy, pageInfo);
        }else{
            informationList =  webCollectService.getInformationWebList(informationType, createTime,orderBy, pageInfo);
        }

        return  new JsonResult(1,informationList,pageInfo);
    }



    /**
     * 查询单个资讯(分页参数为1,上一页、下一页)
     *
     * @param
     * @return
     */
    @RequestMapping("/getOnePageWebInfo")
    public JsonResult getOnePageWebInfo(Integer rowNum,String informationType){
        JsonResult result = webCollectService.getOnePageWebInfo(rowNum,informationType);
        return  new JsonResult(1,result);
    }





    /**
     * 根据id获取资讯管理（id）
     * @return
     * @throws  Exception
     */
    @RequestMapping("/getInfoManagerById")
    public JsonResult selectWebInfoByPrimaryKey(@RequestParam  String id) throws Exception {
        return  webCollectService.selectWebInfoByPrimaryKey(id);
    }




    /**
     * 查询学术研究列表
     *
     * @param
     * @return
     */
    @RequestMapping("/getListWebResearch")
    public JsonResult getListWebResearch(String startTime, String endTime, String orderBy,
    @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "6") Integer size) {
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        JSONObject reserarchObject = webCollectService.selectListWebByResearch(startTime, endTime, orderBy,
                currentPage, size);
        return  new JsonResult(1,reserarchObject,pageInfo);
    }



    /**
     * 查询单个学术研究(分页参数为1,上一页、下一页)
     *
     * @param
     * @return
     */
    @RequestMapping("/getOnePageWebResearch")
    public JsonResult getOnePageWebResearch(Integer rowNum){
        JsonResult result = webCollectService.getOnePageWebResearch(rowNum);
        return  new JsonResult(1,result);
    }


    /**
     * 查询单个学术研究
     *
     * @param id
     * @return
     */
    @RequestMapping("/getOneWebResearch")
    public JsonResult getOneWebResearch(@RequestParam("id") String id) {
        return  webCollectService.selectWebReserarchByKey(id);
    }



    /**
     * 查询单个教育(分页参数为1,上一页、下一页)
     *
     * @param
     * @return
     */
    @RequestMapping("/getOnePageWebEdu")
    public JsonResult getOnePageWebEdu(Integer rowNum){
        JsonResult result = webCollectService.getDetailEducationById(rowNum);
        return  new JsonResult(1,result);
    }




    /**
     * 查询教育活动列表
     *
     * @param
     * @return
     */
    @RequestMapping("/getListWebEducation")
    public JsonResult  getListWebEducation(String title, String startTime, String endTime,@RequestParam(defaultValue = "1") String orderBy,
                                           @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) {
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        JSONObject educationObject = webCollectService.selectListWebByEducation(title, startTime, endTime, orderBy,
                currentPage, size);
        String page = educationObject.getString("page");
        return  new JsonResult(1,educationObject,page);
    }


    /**
     * 查询单个教育活动
     *
     * @param id
     * @return
     */
    @RequestMapping("/getOneWebEducation")
    public JsonResult  getOneWebEducation(@RequestParam("id") String id) {
        return  webCollectService.selectWebEduByKey(id);
    }



    /**
     * 查询单个集邮之家(分页参数为1,上一页、下一页)
     *
     * @param
     * @return
     */
    @RequestMapping("/getOnePageWebHome")
    public JsonResult getOnePageWebHome(Integer rowNum){
        JsonResult result = webCollectService.getDetailHomeById(rowNum);
        return  new JsonResult(1,result);
    }



    /**
     * @Author  xa
     * @Description  查询集邮之家数据
     * @return
     */
    @RequestMapping("/getCollectHomeList")
    public  JsonResult getHomeWebList(String collectHomeTheme,String createTime,@RequestParam(defaultValue = "1")
            Integer currentPage,@RequestParam(defaultValue = "6") Integer size) throws Exception{
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        JsonResult homeList = webCollectService.getHomeWebList(collectHomeTheme, createTime, pageInfo);
        return  new JsonResult(1,homeList,pageInfo);

    }




    /**
     * 根据id获取集邮之家（id）
     * @return
     * @throws  Exception
     */
    @RequestMapping("/getWebHomeById")
    public JsonResult  selectByWebHome(String id) throws Exception {
        return  webCollectService.selectByWebHome(id);
    }








    /**
     * 新增预约信息
     * @param  record
     * @return
     * @throws Exception
     */
    @RequestMapping("/saveWebCollectHome")
    public JsonResult saveWebCollectHome(PostBookingManage record) throws Exception{
        return  webCollectService.insertSelectiveWeb(record);
    }








    /**
     * @Author  xa
     * @Description 查询主题展数据(精选专题)
     * @return
     */
    @RequestMapping("/getZtthemeshowWebList")
    public JsonResult getZtthemeshowWebList(String themeName, String themeSource,String id, @RequestParam(defaultValue = "1")
            Integer currentPage, @RequestParam(defaultValue = "4") Integer size) throws Exception{
        return  webCollectService.themeshowWebztList(themeName,themeSource,null,currentPage,size);

    }

    /**
     * 获取志愿者活动列表
     * @param currentPage
     * @param size
     * @return
     */
    @RequestMapping("/getVolunteerActivitiesList")
    public JsonResult getVolunteerActivitiesList(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "4") Integer size) {
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setSize(size);
        try {
            JsonResult jsonResult = webCollectService.getVolunteerActivitiesList(page);
            if (jsonResult.getSuccess() == 1) {
                return new JsonResult(1, jsonResult, page);
            } else {
                return new JsonResult(0, null, jsonResult.getErrorCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }

    /**
     * 申请志愿者活动
     * @param postVolunteerApply
     * @return
     */
    @RequestMapping("/addApplyVolunteer")
    public JsonResult addApplyVolunteer(@RequestBody PostVolunteerApply postVolunteerApply) {
        try {
            return webCollectService.addApplyVolunteer(postVolunteerApply);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }

    /**
     * 添加策展
     * @param dto
     * @return
     */
    @RequestMapping("/addPublicCurator")
    public JsonResult addPublicCurator(PostPublicCuratorDto dto) {
        try {
            return webCollectService.addPublicCurator(dto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }

    /**
     * 查询公共策展列表
     * @param currentPage
     * @param size
     * @param status 状态 -1-已提交 0-全部 1-已采用
     * @param userId
     * @return
     */
    @RequestMapping("/getPublicCuratorList")
    public JsonResult getPublicCuratorList(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "4") Integer size
            , @RequestParam(defaultValue = "0") Integer status, String userId) {
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setSize(size);
        try {
            JsonResult jsonResult = webCollectService.getPublicCuratorList(userId,status, page);
            if (jsonResult.getSuccess() == 1) {
                return new JsonResult(1, jsonResult, page);
            } else {
                return new JsonResult(0, null, jsonResult.getErrorCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }

    /**
     * 根据id查询公共策展
     * @param id
     * @return
     */
    @RequestMapping("/getPublicCuratorById")
    public JsonResult getPublicCuratorById(String id) {
        try {
            return webCollectService.getPublicCuratorById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }

    /**
     * 根据ids查询藏品列表
     * @param ids 字符串格式id集合 如：1,2,3
     * @param typeId 若为全部则传all
     * @param sonTypeId 若为全部则传all
     * @return
     */
    @RequestMapping("/selectCollectListByIds")
    public JsonResult selectCollectListByIds(@RequestParam("ids")String ids,@RequestParam("typeId") String typeId, String sonTypeId) {
        try {
            return webCollectService.selectCollectListByIds(ids, typeId, sonTypeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000003");
    }

    /**
     * 获取藏品类型下拉
     * @param key
     * @return
     */
    @RequestMapping("/getSelectDataByKey")
    public JsonResult getSelectDataByKey(String key){
        try {
            List<SysDict> dictListByKey = sysDictSevice.getDictListByKey(key);
            return new JsonResult(1,dictListByKey);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"查询失败");
        }
    }

    /**
     * 获取藏品类型二级下拉
     * @param pid
     * @return
     */
    @RequestMapping("/getSelectDataByPid")
    public JsonResult getSelectDataByPid(String pid) {
        try {
            List<SysDict> dictListByKey = sysDictSevice.getDictListByPid(pid);
            return new JsonResult(1,dictListByKey);
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult(0,"查询失败");
        }
    }

    /**
     * 根据typeId 和 sonTypeId查询藏品列表
     * @param typeId
     * @param sonTypeId
     * @return
     */
    @RequestMapping("/getSelectCollectByTypeAndSonType")
    public JsonResult getSelectCollectByTypeAndSonType(String typeId, String sonTypeId) {
        try {
            Collect collect = new Collect();
            collect.setTypeId(typeId);
            collect.setSonTypeId(sonTypeId);
            return webCollectService.getSelectCollectByTypeAndSonType(collect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000003");
    }
}
