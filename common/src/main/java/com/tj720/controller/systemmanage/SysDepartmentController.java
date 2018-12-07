package com.tj720.controller.systemmanage;



import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.LayUiTableJson;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.model.common.system.department.SysDepartment;
import com.tj720.model.common.system.menu.MenuTreeDto;
import com.tj720.service.SysDepartmentService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @ClassName: SysDepartmentController
 * @Description: 部门控制层
 * @Author: xiaoao
 * @Date: 2018/10/9 0009
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/sysdepartment")
public class SysDepartmentController{

      @Autowired
      private  SysDepartmentService  sysDepartmentService;


    /**
     * 删除部门信息
     * @param departmentId
     * @return
     * @throws Exception
     */
    @ControllerAop(action= "删除部门信息")
    @RequestMapping("/deleteDepartment")
    @ResponseBody
    public JsonResult deleteDepart(String departmentId) throws Exception{
        return sysDepartmentService.deleteByPrimaryKey(departmentId);
    }


    /**
     * 根据departmentId获取部门信息（departmentId）
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "根据departmentId获取部门信息")
    @RequestMapping("/getDeptByDeptId")
    @ResponseBody
    public JsonResult selectByPrimaryKey(String departmentId) throws Exception{
        return  sysDepartmentService.selectByPrimaryKey(departmentId);
    }


    /**
     * @Description 查询部门列表
     * @param   //分页参数
     * @return
     */
    @ControllerAop(action = "查询部门列表")
    @RequestMapping("/getDepartList")
    @ResponseBody
    public JSONObject getDepartList(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue =
            "10") Integer size,@RequestParam(defaultValue = "1") String orderBy) throws Exception{

        return  sysDepartmentService.getDepartList(currentPage,size,orderBy);
    }


    /**
     * @Description 编辑部门列表
     * @param
     * @return
     */
    @ControllerAop(action = "编辑部门")
    @RequestMapping("/getDepartEdit")
    public JsonResult gotoDeptEdit(String departmentId, String parentId) throws Exception{
        return  sysDepartmentService.selectByPrimaryKey(departmentId);
    }



    /**
     * @Description 获取所有树形结构 部门节点信息
     * @param
     * @return
     */
    @ControllerAop(action = "获取部门树形结构")
    @RequestMapping("/getParentDeptTreeData")
    @ResponseBody
    public LayUiTableJson  getParentDeptTreeData() throws Exception{
        JsonResult jsonResult = sysDepartmentService.getAllDeptList();
        List<MenuTreeDto> list = (List<MenuTreeDto>)jsonResult.getData();
        List<HashMap> data = new ArrayList<HashMap>();
        for (MenuTreeDto menuTreeDto : list) {
            HashMap map = new HashMap();
            map.put("id",menuTreeDto.getId());
            map.put("name",menuTreeDto.getFunctionname());
            map.put("pId",menuTreeDto.getPid());
            map.put("open",null);
            map.put("chkDisabled","false");
            data.add(map);
        }

        return new LayUiTableJson(0,null,data.size(),data);
    }




    /**
     * 新增或者修改部门信息
     * @param record
     * @return
     * @throws Exception
     */
    @ControllerAop(action = "新增或者修改部门信息")
    @RequestMapping("/saveDept")
    @ResponseBody
    public JsonResult saveDept(SysDepartment  record) throws Exception{
        return sysDepartmentService.insertSelective(record);
    }


    /**
     * 修改部门状态
     * @param status
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "修改部门状态")
    @RequestMapping("/modifyDeptStatus")
    @ResponseBody
    public JsonResult updateDeptStatus(String status,String departmentId) throws Exception{
        return sysDepartmentService.updateDeptStatus(status,departmentId);
    }

    /**
     * 获取部门集合（id和name）
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "获取部门集合（id和name）")
    @RequestMapping("/getDeptOptions")
    @ResponseBody
    public JSONObject getDeptOptions() throws Exception{
        return  sysDepartmentService.getDeptOptions();
    }


    /**
     * 根据userId获取部门信息（userId）
     * @return
     * @throws  Exception
     */
    @ControllerAop(action = "根据userId获取部门信息")
    @RequestMapping("/getDeptByUid")
    @ResponseBody
    public JsonResult getDeptById(String userId) throws Exception{
        return  sysDepartmentService.getDeptById(userId);
    }

    @ControllerAop(action= "获取所有部门信息")
    @RequestMapping("/getAllDeptList")
    @ResponseBody
    public JsonResult getAllDeptList(){
        try {
            JsonResult allDeptList = sysDepartmentService.getAllDeptList();
            return allDeptList;
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult(0,"系统异常");
        }

    }


  }
