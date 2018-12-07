package com.tj720.service;

import com.tj720.model.common.dict.SysDict;
import java.util.List;
import java.util.Map;

import com.tj720.utils.Page;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/9/20.
 */
@Service
public interface SysDictSevice {

    /**
     *
     * @param keys 字典类型
     * @return 返回符合条件字典集合
     */
    List<SysDict> getDictListByKeys(List<String> keys);

    /**
     *
     * @param key  单个字典类型
     * @param dictCode  字典code
     * @param dictName  字典名
     * @return 返回符合条件字典集合
     */
    List<SysDict> getDictListByKey(String key, String dictCode, String dictName);

    /**
     *
     * @param key 单个字典类型
     * @return 返回符合条件字典集合
     */
    List<SysDict> getDictListByKey(String key);


    Map<String,Object> getDictListByArr(String[] arr);

    /**
     * 查询业务字典列表
     * @param dictCode
     * @param dictName
     * @param dictType
     * @return
     */
    List<SysDict> getSysDictList(String dictCode,String dictName,String dictType,Page page);

    /**
     * 根据父id查询业务字典列表
     * @param pid 父id
     * @return
     */
    List<SysDict> getSysDictListByPid(String pid);

    /**
     * 根据父id查询业务字典列表
     * @param pid 父id
     * @return
     */
    List<SysDict> getDictListByPid(String pid);

    /**
     * 根据业务字典类型查询业务字典及子集
     * @param dictType 业务字典类型
     * @return
     */
    List<SysDict> getSysDictListAndChild(String dictType);

    /**
     * 新增业务字典
     * @param sysDict
     * @return
     */
    int addSysDict(SysDict sysDict);

    /**
     * 修改业务字典
     * @param sysDict
     * @return
     */
    int updateSysDict(SysDict sysDict);

    /**
     * 删除业务字典
     * @param dictId
     * @return
     */
    int deleteSysDict(String dictId);

    SysDict getDictById(String dictId);

    SysDict getDictByDictCode(String dictCode);

    List<SysDict> getSysDictListAll();

     List<SysDict> getSysDictListByType(String dictType);
}
