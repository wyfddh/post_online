package com.tj720.dao;

import com.tj720.model.common.Attachment;
import com.tj720.model.common.system.user.SysUser;
import com.tj720.model.common.video.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface PostVideoMapper {
//    int countByExample(PostVideoExample example);

//    int deleteByExample(PostVideoExample example);

    int deleteByPrimaryKey(String id);

    int insert(PostVideo record);

    int insertSelective(PostVideo record);

//    List<PostVideo> selectByExample(PostVideoExample example);

    PostVideo selectByPrimaryKey(String id);

//    int updateByExampleSelective(@Param("record") PostVideo record, @Param("example") PostVideoExample example);

//    int updateByExample(@Param("record") PostVideo record, @Param("example") PostVideoExample example);

    int updateByPrimaryKeySelective(PostVideo record);

    int updateByPrimaryKey(PostVideo record);

    /**
     * 查询影视资料列表（非影视部）
     * @param condition 关键词
     * @return
     */
    List<PostVideo> getPostVideoList(HashMap<String,Object> condition);

    /**
     * 统计影视资料列表（非影视部）
     * @param condition 查询条件
     * @return
     */
    Integer countPostVideoList(HashMap<String,Object> condition);

    /**
     * 查询影视资料列表（影视部）
     * @param condition 关键词
     * @return
     */
    List<PostVideo> getPostVideoListPlus(HashMap<String,Object> condition);

    /**
     * 统计影视资料列表（影视部）
     * @param condition 查询条件
     * @return
     */
    Integer countPostVideoListPlus(HashMap<String,Object> condition);

    /**
     * 根据id查询影视资料对象
     * @param id 影视资料id
     * @return
     */
    PostVideo getPostVideo(@Param("id") String id);

    /**
     * 批量设置权限
     * @param ids
     * @param authSetting
     */
    void bacthUpdateAuthSetting(@Param("list") List<String> ids,@Param("authSetting") String authSetting);

    /**
     * 查询上传审批列表
     * @param condition 条件
     * @return
     */
    List<UploadApprovalDto> getUploadApprovalList(HashMap<String,Object> condition);


    /**
     * 统计上传审批列表
     * @param condition 条件
     * @return
     */
    Integer countUploadApprovalList(HashMap<String,Object> condition);

    /**
     * 资料查询列表
     * @param condition
     * @return
     */
    List<PostVideo> getPostVideoQueryList(HashMap<String,Object> condition);

    /**
     * 资料查询列表(影视部)
     * @param condition
     * @return
     */
    List<PostVideo> getPostVideoQueryListPlus(HashMap<String,Object> condition);
    /**
     * 统计资料查询列表
     * @param condition
     * @return
     */
    Integer countPostVideoQueryList(HashMap<String,Object> condition);

    /**
     * 统计资料查询列表(影视部)
     * @param condition
     * @return
     */
    Integer countPostVideoQueryListPlus(HashMap<String,Object> condition);

    /**
     * 统计查询申请
     * @param condition
     * @return
     */
    Integer countQueryVideoList(HashMap<String,Object> condition);

    /**
     * 查询查询申请列表
     * @param condition
     * @return
     */
    List<QueryVideoDto> getQueryVideoList(HashMap<String,Object> condition);

    /**
     * 统计查询申请
     * @param condition
     * @return
     */
    Integer countQueryApprovalVideoList(HashMap<String,Object> condition);

    /**
     * 查询查询申请列表
     * @param condition
     * @return
     */
    List<QueryVideoDto> getQueryApprovalVideoList(HashMap<String,Object> condition);


    Integer countKeywordsList();

    List<HashMap<String,Object>> queryKeywordsList(HashMap<String,Object> condition);

    /**
     * 查询影视采集统计（交叉表）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCjTable(HashMap<String,Object> condition);
    /**
     * 查询影视采集统计（饼图）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCjPie(HashMap<String,Object> condition);
    /**
     * 查询影视采集统计（折线图）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCjLine(HashMap<String,Object> condition);
    /**
     * 查询影视采集统计（柱状图）(部门名称固定实现方法)
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCjBar(HashMap<String,Object> condition);
    /**
     * 查询影视采集统计（柱状图）（查询部门前5）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCjBarPlus(HashMap<String,Object> condition);

    /**
     * 查询影视查询统计（交叉表）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCxTable(HashMap<String,Object> condition);
    /**
     * 查询影视查询统计（饼图）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCxPie(HashMap<String,Object> condition);
    /**
     * 查询影视查询统计（折线图）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCxLine(HashMap<String,Object> condition);
    /**
     * 查询影视查询统计（柱状图）(用户前十)
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCxBarTop10(HashMap<String,Object> condition);
    /**
     * 查询影视查询统计（柱状图）（查询部门前5）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCxBarPlus(HashMap<String,Object> condition);

    /**
     * 查询待办
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getUndoTask(HashMap<String,Object> condition);

    /**
     * 查询已办
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getDoneTask(HashMap<String,Object> condition);

    /**
     * 查询已办结
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getFinishTask(HashMap<String,Object> condition);

    /**
     * 影视采集统计
     * @return
     */
    HashMap<String,Object> getVideoCjCount(HashMap<String,Object> condition);

    /**
     * 公开影视资料
     * @return
     */
    Integer getVideoOpenCount(HashMap<String,Object> condition);

    /**
     * 累计申请
     * @return
     */
    Integer getVideoCxApply(HashMap<String,Object> condition);

    /**
     * 累计通过
     * @return
     */
    Integer getVideoCxApproval(HashMap<String,Object> condition);

    /**
     * 判断用户是否属于某个部门
     * @param userId 用户id(不传则表示查询某个部门下所有用户)
     * @param orgCode 部门编码
     * @return
     */
    List<SysUser> checkUserOrg(@Param("userId") String userId,@Param("orgCode") String orgCode);

    /**
     * 查询上级部门id
     * @param userId
     * @return
     */
    String getOrgPidByUser(@Param("userId") String userId);

    /**
     * 查询附件列表
     * @param postVideoId
     * @return
     */
   List<Attachment> getPostVideoAttachment(@Param("postVideoId") String postVideoId);

    /**
     * 查询流程状态
     * @param userId
     * @param postVideoId
     * @return
     */
   String getProcessUserStatus(@Param("userId") String userId,@Param("postVideoId") String postVideoId
           ,@Param("type") String type);

    /**
     * 查询展陈页面影视资料
     * @param postVideo
     * @return
     */
    List<PostVideo> getPostVideoForExhib(PostVideo postVideo);

    /**
     * 查询影视资料列表根据关键词
     * @param keyId
     * @return
     */
    List<PostVideo> getVideoListByKeywords(@Param("keyId") String keyId);

    /**
     * 查询综合查询影视资料列表总数
     * @param param
     * @return
     */
    Integer countVedilListForCompreQuery(Map<String, Object> param);

    /**
     * 查询综合查询影视资料列表
     * @param param
     * @return
     */
    List<PostVideo> queryVedilListForCompreQuery(Map<String, Object> param);

    List<PostVideo> selectByMap(HashMap<String, Object> map);
}