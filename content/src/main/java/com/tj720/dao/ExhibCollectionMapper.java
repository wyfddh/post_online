package com.tj720.dao;

import com.tj720.model.common.exhibition.ExhibCollection;
import com.tj720.model.common.exhibition.ExhibCollectionExample;
import com.tj720.model.common.interfacecollect.InterfaceCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibCollectionMapper {
//    int countByExample(ExhibCollectionExample example);

//    int deleteByExample(ExhibCollectionExample example);

    int deleteByPrimaryKey(String id);

    int insert(ExhibCollection record);

    int insertSelective(ExhibCollection record);

    int insertByForeachCollection(@Param("list") List<InterfaceCollect> list);

//    List<ExhibCollection> selectByExample(ExhibCollectionExample example);

    List<InterfaceCollect> selectForExhib(String exhibId);

    ExhibCollection selectByPrimaryKey(String id);

//    int updateByExampleSelective(@Param("record") ExhibCollection record, @Param("example") ExhibCollectionExample example);

//    int updateByExample(@Param("record") ExhibCollection record, @Param("example") ExhibCollectionExample example);

    int updateByPrimaryKeySelective(ExhibCollection record);

    int updateByPrimaryKey(ExhibCollection record);

    /**
     * 根据陈展id删除藏品
     * @param exhibId
     * @return
     */
    Integer deleteByExhibId(String exhibId);
}