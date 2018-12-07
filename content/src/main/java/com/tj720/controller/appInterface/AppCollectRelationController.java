package com.tj720.controller.appInterface;

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.bookingmanage.PostBookingManage;
import com.tj720.model.common.volunteerapply.PostVolunteerApply;
import com.tj720.service.*;
import com.tj720.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author xa
 * @Date: 2018/11/7
 */
@RestController
@RequestMapping("/appCollectionrelation")
public class AppCollectRelationController {

    @Autowired
    private PubUserService  pubUserService;


    @Autowired
    private  CollectService  collectService;

    @Autowired
    private WebInterfaceService webCollectService;

    @Autowired
    private PostWebCollectService postWebCollectService;
    @Autowired
    private PostBookingService postBookingService;
    /**
     * 根据用户获取藏品信息（userId）
     * @return
     * @throws  Exception
     */
    @RequestMapping("/getCollectsByUserId")
    public JsonResult getCollectsByUserId(@RequestParam String userId,@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception{
        return  pubUserService.getCollectsByUserId(userId,"2",currentPage,size);

    }

    /**
     * 根据用户获取主题信息（userId）
     * @return
     * @throws  Exception
     */
    @RequestMapping("/getThemeByUserId")
    public JsonResult getThemeByUserId(@RequestParam String userId,@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) throws Exception{
        return  pubUserService.getThemeByUserId(userId,"1",currentPage,size);
    }


    /**
     * //根据用户id查询是否收藏（userId）
     * @return
     * @throws  Exception
     */
    @RequestMapping("/getCollectdcyUid")
    public JsonResult getCollectdcyUid(@RequestParam String userId, String type,@RequestParam
            (defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "3") Integer size){
        return   collectService.getCollectdcByUid(userId,type,currentPage,size);
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
            JsonResult jsonResult = webCollectService.getPublicCuratorList(userId, status, page);
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
     * 添加收藏和取消收藏（userId、cid、type）
     * @return   根据传入hasCollected判断(1为收藏  0为取消收藏)
     * @throws  Exception
     */
    @RequestMapping("/insertWebCollect")
    public JsonResult insertWebCollect(@RequestParam String userId,@RequestParam String cid,@RequestParam String type
            ,String hasCollected) throws Exception{
        return  postWebCollectService.updateCollectionStatus(userId,cid,type,hasCollected);
    }

    /**
      *添加收藏和取消收藏（userId、cid、type）
      * 根据传入hasCollected判断(1为收藏  0为取消收藏)
     **/
    @RequestMapping("/updateCollectionStatus")
    public JsonResult updateCollectionStatus(@RequestParam String userId,@RequestParam String cid
            ,@RequestParam String type,@RequestParam String hasCollected){
        return  postWebCollectService.updateCollectionStatus(userId,cid,type,hasCollected);
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
     * @Description  查询预约管理数据
     * @return
     */
    @RequestMapping("/getWebBookingList")
    public JsonResult getBookingList( @RequestParam(defaultValue = "1") Integer currentPage
            , @RequestParam(defaultValue = "10") Integer size,@RequestParam String userId) throws Exception{
        Page pageInfo = new Page();
        pageInfo.setCurrentPage(currentPage);
        pageInfo.setSize(size);
        JsonResult homeList = postBookingService.getBookingList(userId,pageInfo);
        return  homeList;

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
            JsonResult jsonResult = webCollectService.getWebActivitiesList(page);
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
}
