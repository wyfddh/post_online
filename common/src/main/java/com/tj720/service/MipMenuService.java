package com.tj720.service;

import com.tj720.controller.base.service.BaseService;
import com.tj720.model.common.system.menu.MenuNode;
import com.tj720.model.common.system.menu.MenuZtreeNode;
import com.tj720.model.common.system.menu.MipMenu;

import java.util.List;



/**
 * 
 * @author: caiming
 * @date: 2018年9月2日
 */
public interface MipMenuService extends BaseService<MipMenu> {
	
	/**
	 * 查询当前所有菜单
	 * @return
	 */
    List<MenuNode> getAllMenuNodeList();

	/**
	 * 查询当前所有菜单(下拉树专用)
	 * @param type 如果type=1，则只查询type=1的数据
	 * 			type=1：菜单类型
	 * 			type=2: 权限类型
	 * @return
	 */
    List<MenuZtreeNode> getAllMenuZtreeList(String type);
	
	/**
	 * 字段中不为空的字段进行更新
	 * @param mipMenu
	 */
    void updateBySelect(MipMenu mipMenu);

	/**
	 * 根据父id查询子级菜单
	 * @return
	 */
    List<MipMenu> getMenuListByParentId(String parentId);
	
	/**
	 * 根据角色查询该角色拥有的菜单
	 * @param roleIds
	 * @return
	 */
    List<MipMenu> getMenuListByRoleIds(String roleIds[]);
	
	/**
	 * 根据传入信息，判断唯一名称在当前父id下，是否已经存在
	 * @param roleids
	 * @param parentid
	 * @param id
	 * @return
	 */
	boolean checkOnlyName(String roleids, String parentid, String id);


	
}
