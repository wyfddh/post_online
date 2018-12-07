package com.tj720.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tj720.controller.base.dao.BaseDao;
import com.tj720.controller.base.service.BaseServiceImpl;
import com.tj720.dao.MipMenuMapper;
import com.tj720.model.common.system.menu.MenuNode;
import com.tj720.model.common.system.menu.MenuZtreeNode;
import com.tj720.model.common.system.menu.MipMenu;
import com.tj720.model.common.system.menu.MipMenuExample;
import com.tj720.service.MipMenuService;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
*
* @author:cm
* @date:2018年6月27日10:20:17
*/
@Service("MipMenuService")
public class MipMenuServiceImpl extends BaseServiceImpl<MipMenu> implements MipMenuService {

	@Autowired
	private MipMenuMapper mipMenuMapper;

	@Override
	public BaseDao<MipMenu> getBaseDao() {
		return mipMenuMapper;
	}

	@Override
	public List<MenuNode> getAllMenuNodeList() {
		List<MenuNode> menuList = new ArrayList<>();
		
		//查询所有菜单
//		MipMenuExample example = new MipMenuExample();
//		MipMenuExample.Criteria criteria = example.createCriteria();
//		criteria.andStatusEqualTo((byte) 1);
//		criteria.andParentidEqualTo("0");
//		example.setOrderByClause("sequence");
		//查询第一级菜单
//		List<MipMenu> menuList1 = mipMenuMapper.selectByExample(example);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("status",(byte) 1);
		map.put("parentId","0");
		List<MipMenu> menuList1 = mipMenuMapper.selectMenuByMap(map);
		for (MipMenu mipMenu : menuList1) {
			MenuNode menuNode = new MenuNode();
			try {
				BeanUtils.copyProperties(menuNode, mipMenu);
				menuNode.setName(mipMenu.getMenuname());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<MenuNode> chileList = this.getAllMenuNodeListByPid(mipMenu.getId());
			menuNode.setChildren(chileList);
			menuList.add(menuNode);
		}
		return menuList;
	}
	
	/**
	 * 根据父id返回下级菜单列表
	 * @param parentId
	 * @return
	 */
	private List<MenuNode> getAllMenuNodeListByPid(String parentId){
		List<MenuNode> chileMenuList = new ArrayList<>();
//		MipMenuExample example = new MipMenuExample();
//		MipMenuExample.Criteria criteria = example.createCriteria();
//		criteria.andStatusEqualTo((byte) 1);
//		criteria.andParentidEqualTo(parentId);
//		example.setOrderByClause("sequence");
//		List<MipMenu> menuList = mipMenuMapper.selectByExample(example);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("status",(byte) 1);
		map.put("parentId",parentId);
		List<MipMenu> menuList = mipMenuMapper.selectMenuByMap(map);
		if(menuList != null && menuList.size() > 0) {
			for (MipMenu mipMenu : menuList) {
				MenuNode menuNode = new MenuNode();
				try {
					BeanUtils.copyProperties(menuNode, mipMenu);
					menuNode.setName(mipMenu.getMenuname());
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<MenuNode> childList = getAllMenuNodeListByPid(mipMenu.getId());
				menuNode.setChildren(childList);
				chileMenuList.add(menuNode);
			}
		}
		return chileMenuList;
	}

	@Override
	public void updateBySelect(MipMenu mipMenu) {
//		MipMenuExample example = new MipMenuExample();
//		MipMenuExample.Criteria criteria = example.createCriteria();
//		criteria.andIdEqualTo(mipMenu.getId());
//		mipMenuMapper.updateByExampleSelective(mipMenu, example);
		mipMenuMapper.updateByPrimaryKeySelective(mipMenu);
	}

	@Override
	public List<MipMenu> getMenuListByParentId(String parentId) {
//		MipMenuExample example = new MipMenuExample();
//		MipMenuExample.Criteria criteria = example.createCriteria();
//		criteria.andParentidEqualTo(parentId);
//		criteria.andStatusEqualTo((byte) 1);
//		return mipMenuMapper.selectByExample(example);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("status",(byte) 1);
		map.put("parentId",parentId);
		List<MipMenu> menuList = mipMenuMapper.selectMenuByMap(map);
		return menuList;
	}

	@Override
	public List<MenuZtreeNode> getAllMenuZtreeList(String type) {
		List<MenuZtreeNode> list = new ArrayList<>();
//		MipMenuExample example = new MipMenuExample();
//		MipMenuExample.Criteria criteria = example.createCriteria();
//		criteria.andStatusEqualTo((byte) 1);
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("status",(byte) 1);
		if(!StringUtil.isBlank(type)) {
			if("1".equals(type)) {
//				criteria.andTypeEqualTo(type);
				map.put("type",type);
			}
		}
//		example.setOrderByClause("sequence");
//		List<MipMenu> allMenu = mipMenuMapper.selectByExample(example);
		List<MipMenu> allMenu = mipMenuMapper.selectMenuByMap(map);
		for (MipMenu mipMenu : allMenu) {
			MenuZtreeNode node = new MenuZtreeNode();
			node.setId(mipMenu.getId());
			node.setName(mipMenu.getMenuname());
			node.setOpen(true);
			node.setpId(mipMenu.getParentid());
			node.setSequence(mipMenu.getSequence());
			node.setType(mipMenu.getType());
			list.add(node);
		}
		return list;
	}

	@Override
	public List<MipMenu> getMenuListByRoleIds(String[] roleIds) {
		
		List<MipMenu>roleHasMenus = mipMenuMapper.selectMenusByRoleIds(roleIds);
		return roleHasMenus;
	}

	@Override
	public boolean checkOnlyName(String roleids, String parentid, String id) {
//		MipMenuExample example = new MipMenuExample();
//		MipMenuExample.Criteria criteria = example.createCriteria();
//		criteria.andRoleidsEqualTo(roleids);
//		criteria.andParentidEqualTo(parentid);
//		criteria.andStatusEqualTo((byte) 1);
//		if(!StringUtils.isBlank(id)) {
//			criteria.andIdNotEqualTo(id);
//		}
//		List<MipMenu> selectByExample = mipMenuMapper.selectByExample(example);
//        return selectByExample == null || selectByExample.size() <= 0;

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("status",(byte) 1);
		map.put("parentId",parentid);
		map.put("roleids",roleids);
		if (StringUtils.isNotBlank(id)) {
			map.put("id",id);
		}
		List<MipMenu> allMenu = mipMenuMapper.selectMenuByMap(map);

		return allMenu == null || allMenu.size() <= 0;
	}
}
