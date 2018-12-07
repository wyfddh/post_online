package com.tj720.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.springbeans.Config;
import com.tj720.model.common.interfacecollect.InterfaceCollect;
import com.tj720.model.common.interfacecollect.InterfaceCollectType;
import com.tj720.service.InterfaceCollectService;
import com.tj720.utils.HttpPostGet;
import com.tj720.utils.Tools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 杜昶
 * @Date: 2018/11/16 10:51
 */
@Service
public class InterfaceCollectServiceImpl implements InterfaceCollectService {

    @Autowired
    private Config config;

    @Override
    public JsonResult getCollctTypeList() {
        Map<String, String> header = new HashMap<String, String>(1);
        header.put("CONTENT_TYPE", HttpPostGet.ACCEPT_JSON);
        try {
            String res = HttpPostGet.get(config.getInterfaceCollectPath() + "/icmsApi/icmsRestController/getShareCulType?typegroupid=A0211", null, header);
            JSONObject jsonObject = JSONObject.parseObject(res);
            Integer status = jsonObject.getInteger("status");
            if (null != status) {
                if (status != 0) {
                    return new JsonResult(0, null, "150000001");
                } else {
                    List<InterfaceCollectType> types = JSONArray.parseArray(jsonObject.getString("data"), InterfaceCollectType.class);
                    return new JsonResult(1, types);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "150000002");
    }

    @Override
    public JsonResult getCollectByTypeAndName(String culCategory, String culName) {
        Map<String, String> header = new HashMap<String, String>(1);
        header.put("CONTENT_TYPE", HttpPostGet.ACCEPT_JSON);
        try {
            StringBuilder url = new StringBuilder(config.getInterfaceCollectPath()).append("/icmsApi/icmsRestController/getShareCulList?pageIndex=1&pageSize=200");
//            if (StringUtils.isNotBlank(culCategory)) {
//                url.append("&culCategory=").append(culCategory);
//            }
//            if (StringUtils.isNotBlank(culName)) {
//                url.append("&culName=").append(culName);
//            }
            String res = HttpPostGet.get(url.toString(), null, header);
            JSONObject jsonObject = JSONObject.parseObject(res);
            Integer status = jsonObject.getInteger("status");
            if (null != status) {
                if (status != 0) {
                    return new JsonResult(0, null, "150000001");
                } else {
                    List<InterfaceCollect> collects = JSONArray.parseArray(jsonObject.getString("data"), InterfaceCollect.class);
                    List<InterfaceCollect> resultList = new ArrayList<InterfaceCollect>();
                    for (InterfaceCollect collect : collects) {
                        if (!StringUtils.isEmpty(culCategory)){
                            if (!StringUtils.isEmpty(culName)){
                                if (culName.equals(collect.getCulName())){
                                    resultList.add(collect);
                                }
                            }else{
                                if (culCategory.equals(collect.getCulCategory())){
                                    resultList.add(collect);
                                }
                            }
                        }else{
                            if (!StringUtils.isEmpty(culName)){
                                if (culName.equals(collect.getCulName())){
                                    resultList.add(collect);
                                }
                            }else {
                                resultList.add(collect);
                            }
                        }

                    }
                    JsonResult result = new JsonResult(1, resultList);
                    result.setCode(0);
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "150000002");
    }

    @Override
    public JsonResult getCollectByCulId(String culId) {
        Map<String, String> header = new HashMap<String, String>(1);
        header.put("CONTENT_TYPE", HttpPostGet.ACCEPT_JSON);
        try {
            String res = HttpPostGet.get(config.getInterfaceCollectPath() + "/icmsApi/icmsRestController/getShareCulDetail?id=" + culId, null, header);
            JSONObject jsonObject = JSONObject.parseObject(res);
            Integer status = jsonObject.getInteger("status");
            if (null != status) {
                if (status != 0) {
                    return new JsonResult(0, null, "150000001");
                } else {
                    List<InterfaceCollect> collects = JSONObject.parseArray(jsonObject.getString("data"), InterfaceCollect.class);

                    return new JsonResult(1, collects.get(0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "150000002");
    }

    public Map<String, Object> getShareCulFieldAndDataList(String keyWord, Integer pageIndex, Integer pageSize) {
        Map<String, Object> result = new HashMap<String, Object>(4);
        result.put("code", 1);
        Map<String, String> header = new HashMap<String, String>(1);
        header.put("CONTENT_TYPE", HttpPostGet.ACCEPT_JSON);
        try {
            StringBuilder url = new StringBuilder(config.getInterfaceCollectPath()).append("/icmsApi/icmsRestController/getShareCulFieldAndDataList");
            url.append("?pageIndex=").append(pageIndex).append("&pageSize=").append(pageSize).append("&shareType=").append("0");
            if (StringUtils.isNotEmpty(keyWord)) {
                url.append("&keyWord=").append(keyWord);
            }
            String res = HttpPostGet.get(url.toString(), null, header);
            JSONObject jsonObject = JSONObject.parseObject(res);
            Integer status = jsonObject.getInteger("status");
            if (null != status) {
                if (status != 0) {
                    result.put("msg", jsonObject.getString("message"));
                    return result;
                } else {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    JSONObject head = jsonArray.getJSONObject(0);
                    jsonArray.remove(0);
                    result.put("head", head);
                    result.put("code", 0);
                    result.put("count", jsonObject.get("count"));
                    result.put("data", jsonArray);
                    return result;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("msg", "网络异常");
        return result;
    }
}
