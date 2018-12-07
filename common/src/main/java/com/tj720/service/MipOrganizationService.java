package com.tj720.service;


import com.tj720.controller.base.service.BaseService;
import com.tj720.model.common.system.org.MipOrganization;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface MipOrganizationService extends BaseService<MipOrganization> {

    int countOrgList(Map<String, Object> map);

    List<MipOrganization> getOrgInfoList(Map<String, Object> map);
}
