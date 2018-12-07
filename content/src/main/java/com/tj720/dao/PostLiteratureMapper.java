package com.tj720.dao;

import com.tj720.model.common.Attachment;
import com.tj720.model.literature.PostLiterature;
import com.tj720.model.literature.PostLiteratureWithBLOBs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface PostLiteratureMapper {
    int deleteByPrimaryKey(String id);

    int insert(PostLiteratureWithBLOBs record);

    int insertSelective(PostLiteratureWithBLOBs record);

    PostLiteratureWithBLOBs selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(PostLiteratureWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(PostLiteratureWithBLOBs record);

    int updateByPrimaryKey(PostLiterature record);

    /**
     * @author wyf
     * @description  获取符合检索条件的数据数量
     * @date  2018/10/16 11:36
     * @param  map   检索条件
     * @return java.lang.Integer
     */
    Integer countPostLiteratureList(Map<String, Object> map);

    /**
     * @author wyf
     * @description  获取符合检索条件的数据集合
     * @date  2018/10/16 11:59
     * @param  map   检索条件
     * @return java.util.List<com.tj720.model.literature.PostLiteratureWithBLOBs>
     */
    List<PostLiteratureWithBLOBs> getPostLiteratureList(Map<String, Object> map);

    /**
     * @author wyf
     * @description  根据idlist获取对象list
     * @date  2018/10/18 10:54
     * @param ids list集合
     * @return java.util.List<com.tj720.model.literature.PostLiteratureWithBLOBs>
     */
    List<PostLiteratureWithBLOBs> getListByIds(List<String> ids);

    void batchRecall(List<String> keys);

    void batchSetting(@Param("list") List<String> keys,@Param("setting") String setting);

    void batchInsert(List<PostLiteratureWithBLOBs> li);

    /**
     * 根据起始书名搜索是否存在
     * @param bookNameStart
     * @return
     */
    Integer selectByFirstName(String bookNameStart);

    /**
     * 根据书名获取书籍信息
     * @param dataName
     * @return
     */
    List<PostLiterature> getPostLiteratureByName(String dataName);

    /**
     * 根据书名模糊匹配
     * @param beforeName
     * @return
     */
    List<PostLiterature> getPostLiteratureListByName(String beforeName);

    /**
     * 获取基本类型使用数量
     */
    int getBaseTypeCount(String baseType);

    /**
     * @author wyf
     * @description  获取文献名集合
     * @date  2018/11/16 11:37
     * @return java.util.List<com.tj720.model.literature.PostLiterature>
     */
    List<PostLiterature> getLiteratureName();


    /**
     * 查询文献采集统计（交叉表）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCjTable(HashMap<String,Object> condition);
    /**
     * 查询文献采集统计（饼图）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCjPie(HashMap<String,Object> condition);
    /**
     * 查询文献采集统计（折线图）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCjLine(HashMap<String,Object> condition);
    /**
     * 查询文献采集统计（柱状图）（查询部门前5）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCjBarPlus(HashMap<String,Object> condition);

    /**
     * 查询文献查询统计（交叉表）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCxTable(HashMap<String,Object> condition);
    /**
     * 查询文献查询统计（饼图）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCxPie(HashMap<String,Object> condition);
    /**
     * 查询文献查询统计（折线图）
     * @param condition
     * @return
     */
    List<HashMap<String,Object>> getVideoCxLine(HashMap<String,Object> condition);


    /**
     * 影视采集统计
     * @return
     */
    HashMap<String,Object> getVideoCjCount();

    /**
     * 公开影视资料
     * @return
     */
    Integer getVideoOpenCount();

    /**
     * 累计申请
     * @return
     */
    Integer getVideoCxApply();

    /**
     * 累计通过
     * @return
     */
    Integer getVideoCxApproval();

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


    PostLiterature getLiteratureById(@Param("id") String id);

    PostLiterature getById(String id);

    PostLiterature  getLiteratureByBaseType (String baseType);

    List<PostLiterature> getLiteratureByIdList(List<String> keys);

    List<Attachment> getPostLiteratureAttachment(String postLiteratureId);

}