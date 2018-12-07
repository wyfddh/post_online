package com.tj720.service;



import com.tj720.controller.framework.JsonResult;
import com.tj720.model.common.social.PostSocial;
import net.sf.json.JSONObject;

import java.util.List;

public interface PostSocialService {


	JsonResult deleteByPrimaryKey(String id) throws  Exception;

	JSONObject getSocials(String keywords,String orderBy, Integer currentPage, Integer size) throws Exception;

	JsonResult insert(PostSocial record) throws  Exception;

	JsonResult selectByPrimaryKey(String id) throws Exception;

	JsonResult  updateByPrimaryKeySelective(PostSocial record) throws Exception;

	JsonResult  updateByPrimaryKey(PostSocial record) throws Exception;

	JsonResult batchRemove(List<PostSocial> postSocials) throws Exception;

	JsonResult batchRemoves(String[] ids) throws Exception;

}
