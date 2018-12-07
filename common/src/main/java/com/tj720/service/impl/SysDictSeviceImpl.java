package com.tj720.service.impl;

import com.tj720.dao.SysDictMapper;
import com.tj720.model.common.dict.SysDict;
import com.tj720.model.common.dict.SysDictExample;
import com.tj720.service.SysDictSevice;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tj720.utils.Page;
import com.tj720.utils.common.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2018/9/20.
 */
@Service
public class SysDictSeviceImpl implements SysDictSevice {

    @Autowired
    private SysDictMapper sysDictMapper;

    @Override
    public List<SysDict> getDictListByKeys(List<String> keys) {
        if (keys != null) {
            if (keys.size() > 0) {
                return sysDictMapper.getDictListByKeys(keys);
            }
        }
        return null;
    }

    @Override
    public List<SysDict> getDictListByKey(String key, String dictCode, String dictName) {

        return sysDictMapper.getDictListByKey(key,dictCode,dictName);
    }

    @Override
    public List<SysDict> getDictListByKey(String key) {
        List<SysDict> dictListByKey = getDictListByKey(key, null, null);
        return dictListByKey;
    }

    @Override
    public Map<String, Object> getDictListByArr(String[] arr) {
        List<String> keys = Arrays.asList(arr);
        Map<String, Object> map = new HashMap<String, Object>();
        for (int i = 0; i < keys.size(); i++) {
            List<SysDict> list = getDictListByKey(keys.get(i));
            map.put(keys.get(i), list);
        }
        return map;
    }

    /**
     * 查询业务字典列表
     *
     * @param dictCode
     * @param dictName
     * @param dictType
     * @return
     */
    @Override
    public List<SysDict> getSysDictList(String dictCode, String dictName, String dictType,Page page) {
        Integer count = sysDictMapper.countDictList(dictType,dictCode,dictName);
        page.setAllRow(count);
        List<SysDict> sysDictList = sysDictMapper.getDictList(dictType,dictCode,dictName,page.getStart(),page.getSize());
        return sysDictList;
    }

    /**
     * 根据父id查询业务字典列表
     *
     * @param pid 父id
     * @return
     */
    @Override
    public List<SysDict> getSysDictListByPid(String pid) {
        List<SysDict> dictByPid = sysDictMapper.getDictByPid(pid);
        if (!StringUtils.isEmpty(dictByPid)){
            for (SysDict dict : dictByPid) {
                List<SysDict> sysDictListByPid = getSysDictListByPid(dict.getDictCode());
                if (!StringUtils.isEmpty(sysDictListByPid)){
                    dictByPid.addAll(sysDictListByPid);
                }else {
                    continue;
                }
            }
        }
        return dictByPid;
    }

    @Override
    public List<SysDict> getDictListByPid(String pid) {
        return sysDictMapper.getDictByPid(pid);
    }

    /**
     * 根据业务字典类型查询业务字典及子集
     *
     * @param dictType 业务字典类型
     * @return
     */
    @Override
    public List<SysDict> getSysDictListAndChild(String dictType) {
        List<SysDict> dictListByKey = getDictListByKey(dictType);
        for (SysDict dict : dictListByKey) {
            List<SysDict> sysDictListByPid = getSysDictListByPid(dict.getDictCode());
            if (!StringUtils.isEmpty(sysDictListByPid)){
                dictListByKey.addAll(sysDictListByPid);
            }
        }
        return dictListByKey;
    }

    /**
     * 新增业务字典
     *
     * @param sysDict
     * @return
     */
    @Override
    public int addSysDict(SysDict sysDict) {
        sysDict.setId(IdUtils.getIncreaseIdByNanoTime());
        int count = sysDictMapper.insertSelective(sysDict);
        return count;
    }

    /**
     * 修改业务字典
     *
     * @param sysDict
     * @return
     */
    @Override
    public int updateSysDict(SysDict sysDict) {
        int count = sysDictMapper.updateByPrimaryKeySelective(sysDict);
        return count;
    }

    /**
     * 删除业务字典
     *
     * @param dictId
     * @return
     */
    @Override
    public int deleteSysDict(String dictId) {
        int result = sysDictMapper.deleteByPrimaryKey(dictId);
        return result;
    }

    @Override
    public SysDict getDictById(String dictId) {
        SysDict dict = sysDictMapper.getDictById(dictId);
        return dict;
    }

    @Override
    public SysDict getDictByDictCode(String dictCode) {
        SysDict dict = sysDictMapper.getDictByDictCode(dictCode);
        return dict;
    }

    @Override
    public List<SysDict> getSysDictListAll() {
        List<SysDict> list = sysDictMapper.getSysDictListAll();
        return list;
    }

    /**
     * 查询业务字典列表
     * @param dictType
     * @return
     */
    @Override
    public List<SysDict> getSysDictListByType(String dictType) {
        List<SysDict> sysDictList = sysDictMapper.getSysDictListByType(dictType);
        return sysDictList;
    }

}
