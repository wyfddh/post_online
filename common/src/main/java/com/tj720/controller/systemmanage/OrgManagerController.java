package com.tj720.controller.systemmanage;

import com.alibaba.fastjson.JSON;
import com.tj720.controller.base.controller.BaseController;
import com.tj720.model.common.system.org.MipOrganization;
import com.tj720.service.MipOrganizationService;
import com.tj720.service.MipUserService;
import com.tj720.utils.DateUtil;
import com.tj720.utils.Page;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/9/18.
 */
@RestController
@RequestMapping("/orgManager")
public class OrgManagerController extends BaseController {

    @Autowired
    private MipOrganizationService mipOrganizationService;

    @Autowired
    private MipUserService mipUserService;

    @RequestMapping("/getOrgInfoList")
    public JSONObject getOrgInfoList(String key, String orgTypeId, String platformId, @RequestParam(defaultValue = "10") int size,
                                     @RequestParam(defaultValue = "1") int currentPage) {

        JSONObject jsonObject = new JSONObject();
        Page page = new Page();
        page.setSize(size);
        page.setCurrentPage(currentPage);
//        MipUser mipUser = mipUserService.get(Tools.getUserId());
//        String orgId = "";
//        if (mipUser != null) {
//            orgId = mipUser.getOrgId();
//        } else {
//            jsonObject.put("code", 1);
//            jsonObject.put("msg", "登录异常");
//            jsonObject.put("count", 0);
//            jsonObject.put("data", null);
//            return jsonObject;
//        }
        Map<String,Object> map = new HashMap<String,Object>();
        if (StringUtils.isNotBlank(key)) {
            map.put("key",key);
        }
        if (StringUtils.isNotBlank(orgTypeId)) {
            map.put("orgTypeId",orgTypeId);
        }
        //查询符合条件的组织总数
        int count = mipOrganizationService.countOrgList(map);
        page.setAllRow(count);
        Integer start = page.getStart();
        Integer end = start + page.getSize();
        map.put("start",start);
        map.put("end",end - start);
        //查询分页数据
        List<MipOrganization> orgList = mipOrganizationService.getOrgInfoList(map);
        for (MipOrganization mipOrganization : orgList) {
            String s = DateUtil.DateToString(mipOrganization.getCreatedTime(), "yyyy-MM-dd HH:mm:ss");
            mipOrganization.setCreatedTimeName(s);
        }
        String jsonString = JSON.toJSONString(orgList);
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", page.getAllRow());
        jsonObject.put("data", jsonString);
        return jsonObject;
    }
}
