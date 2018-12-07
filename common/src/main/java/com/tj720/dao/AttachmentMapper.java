package com.tj720.dao;


import com.tj720.model.common.Attachment;
import com.tj720.model.common.AttachmentExample;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Repository
public interface AttachmentMapper{


    /**
     * 附件数据计数
     */
//    int countByExample(AttachmentExample example);


    /**
     * 删除附件信息
     */
//    int deleteByExample(AttachmentExample example);


    /**
     * 根据di删除附件信息
     */
    int deleteByPrimaryKey(String attId);


    /**
     * 添加附件信息(所有)
     */
    int insert(Attachment record);


    /**
     * 添加附件信息(部分)
     */
    int insertSelective(Attachment record);


    /**
     * 附件数据集合
     */
//    List<Attachment> selectByExample(AttachmentExample example);

    /**
     * 根据id查询附件信息
     */
    Attachment selectByPrimaryKey(String attId);

    /**
     * 修改附件信息
     */
//    int updateByExampleSelective(@Param("record") Attachment record, @Param("example") AttachmentExample example);

    /**
     * 修改附件信息
     */
//    int updateByExample(@Param("record") Attachment record, @Param("example") AttachmentExample example);

    /**
     * 修改附件信息(部分)
     */
    int updateByPrimaryKeySelective(Attachment record);

    /**
     * 修改附件信息(所有)
     */
    int updateByPrimaryKey(Attachment record);


    /**
     * 获取附件信息集合
     */
    List<HashMap> getAttachmentById(@Param("attFkId") String attFkId, @Param("attId") String attId,
                                    @Param("startRow") int startRow, @Param("pageSize") Integer pageSize);

    /**
     * 根据id集合获取附件信息条数
     */
    Integer countAttachmentById(@Param("attFkId") String attFkId, @Param("attId") String attId);

    /**
     * 根据id集合获取附件信息集合
     */
    List<Attachment> getListByIds(List<String> list);

    /**
     * 批量修改
     */
    void batchUpdate(List<Attachment> picList);

    /**
     * 批量删除
     */
    void batchDelete(List<String> list);

    List<Attachment> getAttachmentsByFkId(@Param("fkId") String fkId);

    Attachment getAttachmentsById(@Param("attId") String attId);


    List<Attachment> selectByMap(Map<String, Object> map);
}