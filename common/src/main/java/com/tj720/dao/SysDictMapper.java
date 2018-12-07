package com.tj720.dao;

import com.tj720.model.common.dict.SysDict;
import com.tj720.model.common.dict.SysDictExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SysDictMapper {
//    int countByExample(SysDictExample example);

//    int deleteByExample(SysDictExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

//    List<SysDict> selectByExample(SysDictExample example);

    SysDict selectByPrimaryKey(String id);

//    int updateByExampleSelective(@Param("record") SysDict record, @Param("example") SysDictExample example);

//    int updateByExample(@Param("record") SysDict record, @Param("example") SysDictExample example);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);

    List<SysDict> getDictListByKeys(List<String> keys);

    List<SysDict> getDictListByKey(@Param("key") String key, @Param("dictCode") String dictCode,
                                   @Param("dictName") String dictName);

    SysDict getDictById(String dictId);

    List<SysDict> getDictByPid(String pid);

    Integer countDictList(@Param("key") String key, @Param("dictCode") String dictCode,
                               @Param("dictName") String dictName);

    List<SysDict> getDictList(@Param("key") String key, @Param("dictCode") String dictCode,
                               @Param("dictName") String dictName,@Param("currentPage") int currentPage,
                        @Param("size") int size);

    List<SysDict> getSysDictListAll();

    SysDict getDictByDictCode(@Param("dictCode") String dictCode);

    List<SysDict> getSysDictListByType(@Param("dictType") String dictType);
}