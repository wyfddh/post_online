package com.tj720.service;/**
 * Created by MyPC on 2018/10/24.
 */

import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.bookingmanage.PostBookingManage;
import com.tj720.model.common.collect.Collect;
import com.tj720.model.common.publiccurator.PostPublicCuratorDto;
import com.tj720.model.common.volunteerapply.PostVolunteerApply;
import com.tj720.utils.Page;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

/**
 * @ClassName: WebInterfaceService
 * @Description: Web端接口
 * @Author: xa
 * @Date: 2018/11/3
 * @Version: 1.0
 **/
@Service
public interface WebInterfaceService{



    /**
     * 查列表藏品(典藏精选)
     * @return
     */
    public JSONObject selectListIndexByWebCollect(String name, String typeId, String sonTypeId, String orderBy, String
            userId);



    /**
     * 查列表藏品
     * @return
     */
    public JSONObject selectListByWebCollect(String name, String typeId, String sonTypeId, String orderBy, String userId,Integer currentPage,
                                     Integer size);

    /**
     * 查列表藏品
     * @return
     */
    public JSONObject selectListByWebCollectAndUser( String typeId,String userId, Integer currentPage,
                                             Integer size);







    /**
     * 查一个藏品
     * @param id 藏品id
     * @return
     */
    public JsonResult getOneWebCollect(String id);

    /**
     * 查一个藏品
     * @param id 藏品id
     * @return
     */
    public JsonResult getOneWebCollectAndUser(String id,String userId);



    /**
     * 添加预约管理
     */
    JsonResult  insertSelectiveWeb(PostBookingManage record);



    /**
     * 查询主题展览集合
     */
    JsonResult  themeshowWebList(String themeName,String themeSource,String userId,String id,Integer currentPage,
                                 Integer size) throws Exception;




    /**
     * 查询主题展览集合(无分页)
     */
    JsonResult  themeshowWebNoPages(String themeName,String themeSource) throws Exception;


    JsonResult  themeshowWebNoPages(String themeName,String themeSource,String userId) throws Exception;

    JsonResult  themeshowWebNoPagesPlus(String id,String userId) throws Exception;


      /**
     * 根据id查询主题展览(根据主题id关联查询出藏品)
     */
    JsonResult selectWebByPrimaryKey(String id) throws Exception;




    /**
     * 查询主题展览集合(精选专题)
     */
    JsonResult  themeshowWebztList(String themeName,String themeSource,String id,Integer currentPage,Integer size)
            throws Exception;


    JsonResult  themeshowWebztList(String themeName,String themeSource,String id,String userId,Integer currentPage,Integer size)
            throws Exception;

    /**
     * 查询精选专题
     * @param userId 用户id
     * @param page 分页
     * @return
     */
    JsonResult getJxztList(String userId,Page page);


    /**
     * 查询主题展览集合(首页)
     */
    JsonResult  themeshowsyWebztList(String themeName,String themeSource,Integer currentPage,Integer size) throws
            Exception;




    /**
     * 查询资讯管理集合
     */
    JsonResult  getInformationWebList(String informationType,String createTime,String orderBy, Page page) throws Exception;






    /**
     * 根据id查询资讯管理
     */
    JsonResult selectWebInfoByPrimaryKey(String id) throws Exception;



    /**
     * 查询邮票故事集合
     */
    JsonResult  getStampStoryWebList(String storyType,String createTime, Page page) throws Exception;



    /**
     * 根据id查询邮票故事
     */
    JsonResult selectWebStoyrByPrimaryKey(String id) throws Exception;



    /**
     * 查一个学术研究
     */
    JsonResult  selectWebReserarchByKey(String id);



    /**
     * 查列表学术研究
     */
    JSONObject  selectListWebByResearch(String startTime, String endTime, String orderBy, Integer
            currentPage, Integer size);


    /**
     * 查一个教育活动
     */
    JsonResult  selectWebEduByKey(String id);

    /**
     * 查列表教育活动
     */
    JSONObject  selectListWebByEducation(String title, String startTime, String endTime, String orderBy, Integer
            currentPage, Integer size);



    /**
     * 查询集邮之家集合
     */
    JsonResult  getHomeWebList(String collectHomeTheme, String createTime, Page page) throws Exception;



    /**
     * 根据id查询集邮之家
     */
    JsonResult  selectByWebHome(String id) throws Exception;


    /**
     * 查询学术研究之家(上一页、下一页)
     */
    JsonResult getOnePageWebResearch(Integer currentPage);



    /**
     * 查询资讯信息(上一页、下一页)
     */
    JsonResult getOnePageWebInfo(Integer currentPage,String informationType);



    /**
     * 查询邮票故事信息(上一页、下一页)
     */
    JsonResult  getDetailStoryById(Integer currentPage,String  storyType);



    /**
     * 查询教育信息(上一页、下一页)
     */
    JsonResult  getDetailEducationById(Integer currentPage);



    /**
     * 查询集邮之家(上一页、下一页)
     */
    JsonResult  getDetailHomeById(Integer  currentPage);


    /**
     * 志愿者申请(上一页、下一页)
     * @param page
     * @return
     */
    JsonResult getVolunteerActivitiesList(Page page);

    JsonResult getWebActivitiesList(Page page);

    JsonResult getVolunteerActivitiesListByUser(String userId,Page page);

    //首页典藏精选
    JsonResult  selectIndexCollectList();

    /**
     * 申请志愿者活动
     * @param postVolunteerApply
     * @return
     */
    JsonResult addApplyVolunteer(PostVolunteerApply postVolunteerApply);

    /**
     * 添加在线策展
     * @param dto
     * @return
     */
    JsonResult addPublicCurator(PostPublicCuratorDto dto);

    /**
     * 查询公共策展列表
     * @param userId
     * @param status
     * @param page
     * @return
     */
    JsonResult getPublicCuratorList(String userId, Integer status,Page page);

    /**
     * 根据id获取公共策展详情
     * @param id
     * @return
     */
    JsonResult getPublicCuratorById(String id);

    /**
     * 根据ids查询藏品列表
     * @param ids 字符串格式id集合 如：1,2,3
     * @param typeId
     * @param sonTypeId 若为全部则传all
     * @return
     */
    JsonResult selectCollectListByIds(String ids, String typeId, String sonTypeId);

    /**
     * 根据一级、二级分类查询藏品列表
     * @param collect
     * @return
     */
    JsonResult getSelectCollectByTypeAndSonType(Collect collect);


    JsonResult getAppThemeById(String id,String userId);
}
