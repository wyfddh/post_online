package com.tj720.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.tj720.controller.framework.JsonResult;
import com.tj720.dao.PostLiteratureMapper;
import com.tj720.dao.PostLiteratureProcessDetailMapper;
import com.tj720.dao.PostLiteratureProcessMapper;
import com.tj720.dao.PostLiteratureTypeMapper;
import com.tj720.dao.PostVideoMapper;
import com.tj720.dao.SysRoleAuthMapper;
import com.tj720.dao.SysUserMapper;
import com.tj720.model.common.Attachment;
import com.tj720.model.common.dict.SysDict;
import com.tj720.model.common.system.department.SysDepartment;
import com.tj720.model.common.system.user.SysUser;
import com.tj720.model.literature.PostLiterature;
import com.tj720.model.literature.PostLiteratureProcess;
import com.tj720.model.literature.PostLiteratureProcessDetail;
import com.tj720.model.literature.PostLiteratureType;
import com.tj720.model.literature.PostLiteratureWithBLOBs;
import com.tj720.service.PostLiteratureSerialNumberService;
import com.tj720.service.PostLiteratureService;
import com.tj720.service.SysDictSevice;
import com.tj720.service.SysNoticeService;
import com.tj720.service.jedis.JedisService;
import com.tj720.utils.ExportExcelUtil;
import com.tj720.utils.Page;
import com.tj720.utils.Tools;
import com.tj720.utils.common.IdUtils;
import com.tj720.utils.common.POIUtil;
import com.tj720.utils.common.Utils;
import java.text.SimpleDateFormat;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author wyf
 * @date 2018/10/16 10:16
 **/
@Transactional
@Service
public class PostLiteratureServiceImpl implements PostLiteratureService {

    @Autowired
    private PostLiteratureMapper postLiteratureMapper;
    @Autowired
    private PostLiteratureProcessMapper postLiteratureProcessMapper;
    @Autowired
    private PostLiteratureProcessDetailMapper postLiteratureProcessDetailMapper;
    @Autowired
    private SysDictSevice sysDictSevice;
    @Autowired
    private SysRoleAuthMapper sysRoleAuthMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private PostLiteratureTypeMapper postLiteratureTypeMapper;
    @Autowired
    private SysNoticeService sysNoticeService;
    @Autowired
    private PostLiteratureSerialNumberService postLiteratureSerialNumberService;
    @Autowired
    private PostVideoMapper postVideoMapper;

    private HttpServletResponse response;

    /** 文献类型Redis key */
    public static final String LITERATURE_TYPE_REDIS_KEY = "literature_type_redis_key";

    @Override
    public JSONObject postLiteratureList(String key, String dataType, String status,String inventoryState, String orderBy, String open,
                                         Integer currentPage, Integer size) {
        //分页对象
        Page page = new Page();
        page.setCurrentPage(currentPage);
        page.setSize(size);

        Map<String, Object> map = new HashMap<String, Object>();

        if (StringUtils.isNotBlank(key)) {
            map.put("key", key);
        }
        if (StringUtils.isNotBlank(dataType)) {
            map.put("dataType", dataType);
        }
        //open有值就查公开数据
        if (StringUtils.isNotBlank(open)) {
            map.put("status", "2");
            map.put("open","1");
        } else if (StringUtils.isNotBlank(status)) {
            map.put("status", status);
        }
        if (StringUtils.isNotBlank(inventoryState)) {
            map.put("inventoryState", inventoryState);
        }
        if (StringUtils.isNotBlank(orderBy)) {
            map.put("orderBy", orderBy);
        } else {
            //默认1
            map.put("orderBy", 1);
        }
        //符合检索条件的数量
        Integer count = postLiteratureMapper.countPostLiteratureList(map);

        page.setAllRow(count);
        Integer start = page.getStart();
        map.put("start", start);
        map.put("end", size);
        //获取分页后数据集合
        List<PostLiteratureWithBLOBs> list = postLiteratureMapper.getPostLiteratureList(map);
        String jsonString = null;
        if (list != null && list.size() > 0) {
            List<PostLiteratureWithBLOBs> postLiteratureList = handleData(list);
            jsonString = JSON.toJSONString(postLiteratureList);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", 0);
        jsonObject.put("msg", "");
        jsonObject.put("count", page.getAllRow());
        jsonObject.put("data", jsonString);

        return jsonObject;
    }

    @Override
    public JsonResult postLiteratureSave(PostLiteratureWithBLOBs postLiteratureWithBLOBs) {
        JsonResult jsonResult = null;
        String userId  = Tools.getUserId();
        //根据保存类型设置保存草稿或者直接发布
        try {
            if ("1".equals(postLiteratureWithBLOBs.getSubmitType())) {
                postLiteratureWithBLOBs.setStatus("1");
            } else if ("2".equals(postLiteratureWithBLOBs.getSubmitType())) {
                postLiteratureWithBLOBs.setStatus("2");
            }
            postLiteratureWithBLOBs.setUpdater(userId);
            postLiteratureWithBLOBs.setUpdateTime(new Date());
            postLiteratureWithBLOBs.setInventoryState(postLiteratureWithBLOBs.getNumber().toString());
            //新增
            if (StringUtils.isBlank(postLiteratureWithBLOBs.getId())) {
                String increaseIdByNanoTime = IdUtils.getIncreaseIdByNanoTime();

                postLiteratureWithBLOBs.setId(increaseIdByNanoTime);
                postLiteratureWithBLOBs.setCreator(userId);
                postLiteratureWithBLOBs.setCreateTime(new Date());
                postLiteratureWithBLOBs.setSequence(50);
                postLiteratureMapper.insertSelective(postLiteratureWithBLOBs);
                //修改
            } else {
                postLiteratureMapper.updateByPrimaryKeySelective(postLiteratureWithBLOBs);
            }
            jsonResult = new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = new JsonResult("111116");
        }


        return jsonResult;
    }

    @Override
    @Transactional
    public JsonResult modifyState(String id, String status, String setting) {
        JsonResult jsonResult = null;
        if (StringUtils.isNotBlank(id)){
            try {
                String userId  = Tools.getUserId();
                String userName = Tools.getUserName();
                PostLiteratureWithBLOBs postLiterature = postLiteratureMapper.selectByPrimaryKey(id);
                postLiterature.setUpdateTime(new Date());
                postLiterature.setUpdater(userId);
                //删除发布撤回
                if (StringUtils.isNotBlank(status)) {
                    //撤回时默认驳回所有该文献的待申请
                    if ("3".equals(status)) {
                        //撤回默认设置权限设置为不公开
                        postLiterature.setPermissionsSettings("1");

                        //查询该文献待审核的申请
                        List<PostLiteratureProcess> processIdList = postLiteratureProcessMapper.getProcessByLiteratureId(id);
                        for (PostLiteratureProcess postLiteratureProcess : processIdList) {

                            //新增一条审批记录为已办
                            PostLiteratureProcessDetail processDetail = new PostLiteratureProcessDetail();
                            String processDetailId = IdUtils.getIncreaseIdByNanoTime();
                            processDetail.setId(processDetailId);
                            processDetail.setProcessId(postLiteratureProcess.getId());
                            processDetail.setProcessStatus("3");
                            processDetail.setProcessOperation("2");
                            processDetail.setProcessUserId(userId);
                            processDetail.setProcessUserName(userName);
                            processDetail.setProcessRemark("该文献目前未公开！");
                            processDetail.setCreateTime(new Date());
                            //查审批人部门
                            SysDepartment dept = postLiteratureProcessDetailMapper.getDaptByUserId(postLiteratureProcess.getApproveId());
                            processDetail.setProcessOrgName(dept.getDepartmentName());

                            postLiteratureProcessDetailMapper.insertSelective(processDetail);
                            //批量更新所有该流程成员状态为已完成
                            postLiteratureProcessDetailMapper.batchUpdateProcessStatus(postLiteratureProcess.getId(),"3");
                            //发送通知

                            String content = getNowDate()+";您的"+postLiterature.getDataName()+"申请已被驳回";
                            sysNoticeService.sendNoticePlus(postLiteratureProcess.getApplicant(),"2","确定",userId,
                                    content,postLiteratureProcess.getId(),userId);

                        }
                        Map<String,Object> map = new HashMap<String,Object>();
                        map.put("literatureId",id);
                        map.put("approveId",userId);
                        map.put("updateTime",new Date());
                        map.put("approveStatus","3");
                        //更新
                        postLiteratureProcessMapper.updateByMap(map);


                    }
                    postLiterature.setStatus(status);

                    //权限设置
                } else if (StringUtils.isNotBlank(setting)) {
                    postLiterature.setPermissionsSettings(setting);
                }
                postLiteratureMapper.updateByPrimaryKeySelective(postLiterature);
                jsonResult = new JsonResult(1);
            } catch (Exception e) {
                e.printStackTrace();
                jsonResult = new JsonResult("111116");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); //手动开启事务回滚
            }
        } else {
            jsonResult = new JsonResult("111115");
        }
        return jsonResult;
    }

    /**
     * 获取当前时间
     * @return
     */
    private String getNowDate(){
        Date date= new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        return format;
    }


    /**
     * @param list
     * @return java.util.List<com.tj720.model.literature.PostLiteratureWithBLOBs>
     * @author wyf
     * @description 处理集合数据，添加数据字典描述
     * @date 2018/10/18 14:42
     */
    private List<PostLiteratureWithBLOBs> handleData(List<PostLiteratureWithBLOBs> list) {

        //获取文献类型字典数据
        List<SysDict> literatureTypeList = sysDictSevice.getDictListByKey("literature_type");
        //获取库存状态字典数据
        List<SysDict> inventoryStateList = sysDictSevice.getDictListByKey("inventory_state");
        //获取提交状态字典数据
        List<SysDict> submitStateList = sysDictSevice.getDictListByKey("submit_state");
        //获取权限设置字典数据
        List<SysDict> permissionsSettingsList = sysDictSevice.getDictListByKey("permissions_settings");
        //入库人
//        List<SysUser> userList = sysUserMapper.getUserByDepartmentName("资料室");
        List<SysUser> userList = postVideoMapper.checkUserOrg(null, "zls");

        //还差文献分类，分类索书号


        for (PostLiteratureWithBLOBs postLiterature : list) {

            for (SysDict sysDict : literatureTypeList) {
                if (StringUtils.isNotBlank(postLiterature.getDataType()) && sysDict.getDictCode().equals(postLiterature.getDataType())) {
                    postLiterature.setDataTypeName(sysDict.getDictName());
                    break;
                }
            }
            for (SysDict sysDict : inventoryStateList) {
                if (StringUtils.isBlank(postLiterature.getInventoryState())) {
                    //电子版本
                    if ("2".equals(postLiterature.getDataType())) {
                        postLiterature.setInventoryStateName("/");
                        break;
                    }
                } else if (sysDict.getDictCode().equals(postLiterature.getInventoryState())) {
                    postLiterature.setInventoryStateName(sysDict.getDictName());
                    break;
                }

            }
            for (SysDict sysDict : submitStateList) {
                if (StringUtils.isNotBlank(postLiterature.getStatus()) && sysDict.getDictCode().equals(postLiterature.getStatus())) {
                    postLiterature.setStatusName(sysDict.getDictName());
                    break;
                }
            }
            for (SysDict sysDict : permissionsSettingsList) {
                if (StringUtils.isNotBlank(postLiterature.getPermissionsSettings()) && sysDict.getDictCode().equals(postLiterature.getPermissionsSettings())) {
                    postLiterature.setPermissionsSettingsName(sysDict.getDictName());
                    break;
                }
            }
            if (userList != null && userList.size() > 0) {
                for (SysUser sysUser : userList) {
                    if (StringUtils.isNotBlank(postLiterature.getWarehouses()) && sysUser.getId().equals(postLiterature.getWarehouses())) {
                        postLiterature.setWarehousesName(sysUser.getName());
                        break;
                    }
                }
            }
        }
        return list;
    }

    @Override
    public JsonResult batchExport(String[] arr) {
        JsonResult jsonResult = null;
        List<String> ids = Arrays.asList(arr);
        List<PostLiteratureWithBLOBs> list = postLiteratureMapper.getListByIds(ids);
        if (list != null && list.size() > 0) {
            List<PostLiteratureWithBLOBs> postLiteratureList = handleData(list);
            String title = "文献资料";
            String[] rowName = {"序号", "文献名称", "文献类型", "数量", "单价", "出版社", "出版时间", "入库人", "文献分类", "分类索书号", "库存状态", "状态",
                    "权限设置"};
            List<Object[]> dataList = new ArrayList<Object[]>();
            Object[] objs = null;
            for (int i = 0; i < postLiteratureList.size(); i++) {
                objs = new Object[rowName.length];
                objs[0] = i + 1;
                objs[1] = postLiteratureList.get(i).getDataName();
                objs[2] = postLiteratureList.get(i).getDataTypeName();
                objs[3] = postLiteratureList.get(i).getNumber();
                objs[4] = postLiteratureList.get(i).getPrice();
                objs[5] = postLiteratureList.get(i).getPress();
                objs[6] = postLiteratureList.get(i).getPublishingTime();
                objs[7] = postLiteratureList.get(i).getWarehousesName();
                objs[8] = postLiteratureList.get(i).getLiteratureTypeName();
                objs[9] = postLiteratureList.get(i).getLiteratureTypeIndex();
                objs[10] = postLiteratureList.get(i).getInventoryStateName();
                objs[11] = postLiteratureList.get(i).getStatusName();
                objs[12] = postLiteratureList.get(i).getPermissionsSettingsName();

                dataList.add(objs);
            }

            try {
                ExportExcelUtil ex = new ExportExcelUtil(title, rowName, dataList);
                ex.export();
                jsonResult = new JsonResult(1);
            } catch (Exception e) {
                e.printStackTrace();
                jsonResult = new JsonResult("111116");
            }
        }
        return jsonResult;
    }

    @Override
    public JsonResult batchImport(HttpServletRequest request, MultipartFile file) {
        JsonResult jsonResult = null;
        try {
            String userId  = Tools.getUserId();
            List<String[]> readExcel = POIUtil.readExcel(file);

            List<List<String[]>> splitList = Utils.split(readExcel, 1000);
            for (int i = 0; i < splitList.size(); i++) {
                List<PostLiteratureWithBLOBs> li = new ArrayList<PostLiteratureWithBLOBs>();
                for (int j = 0; j < splitList.get(i).size(); j++) {
                    String[] strings = splitList.get(i).get(j);
                    int len = strings.length;
                    if ((StringUtils.isNotBlank(strings[0]) && Utils.isNum(strings[0]))) {
                        if (StringUtils.isBlank(strings[1]) || StringUtils.isBlank(strings[2]) || StringUtils.isBlank(strings[3]) || StringUtils.isBlank(strings[6]) || StringUtils.isBlank(strings[10]) || StringUtils.isBlank(strings[18])) {
                            continue;
                        }
                        PostLiteratureWithBLOBs postLiterature = new PostLiteratureWithBLOBs();
                        String nextId = IdUtils.getIncreaseIdByNanoTime();
                        postLiterature.setId(nextId);
                        postLiterature.setDataName(strings[1].trim());
                        if (strings[2].contains("纸质") || "1".equals(strings[2].trim())) {
                            postLiterature.setDataType("1");
                        } else if (strings[2].contains("电子") || "2".equals(strings[2].trim())) {
                            postLiterature.setDataType("2");
                        } else if (strings[2].contains("档案") || "3".equals(strings[2].trim())) {
                            postLiterature.setDataType("3");
                        }
                        postLiterature.setIsbnNum(strings[3].trim());
                        postLiterature.setRetrievalWords(strings[4].trim());
                        postLiterature.setSummary(strings[5].trim());
                        if (Utils.isNum(strings[6].trim())) {
                            if ("2".equals(postLiterature.getDataType())) {
                                postLiterature.setNumber(1);
                                postLiterature.setInventoryState("1");
                            } else {
                                postLiterature.setNumber(Integer.parseInt(strings[6].trim()));
                                postLiterature.setInventoryState(strings[6].trim());
                            }
                        }
                        if (Utils.isDecimal(strings[7].trim())) {
                            postLiterature.setPrice(new BigDecimal(strings[7].trim()));
                        }
                        postLiterature.setPress(strings[8].trim());

                        postLiterature.setPublishingTime(strings[9].trim());
//                        if (StringUtils.isNotBlank(strings[10])) {
//                            List<SysUser> userList = sysRoleAuthMapper.getUserListByName(strings[10], "0");
//                            if (userList != null && userList.size() > 0) {
//                                postLiterature.setWarehouses(userList.get(0).getId());
//                            }
//                        }
                        postLiterature.setWarehouses(userId);
                        postLiterature.setWarehousingTime(strings[10].trim());

                        postLiterature.setAuthor(strings[11].trim());
                        if (Utils.isNum(strings[12].trim())) {
                            postLiterature.setPaginalNumber(Integer.parseInt(strings[12].trim()));
                        }
                        if (strings[13].contains("大型") || "1".equals(strings[13].trim())) {
                            postLiterature.setFormat("1");
                        } else if (strings[13].contains("中型") || "2".equals(strings[13].trim())) {
                            postLiterature.setFormat("2");
                        } else if (strings[13].contains("小型") || "3".equals(strings[13].trim())) {
                            postLiterature.setFormat("3");
                        }
                        if (strings[14].contains("第一版") || strings[14].contains("第1版") || "1".equals(strings[14].trim())) {
                            postLiterature.setEdition("1");
                        } else if (strings[14].contains("第二版") || strings[14].contains("第2版") || "2".equals(strings[14].trim())) {
                            postLiterature.setEdition("2");
                        } else if (strings[14].contains("第三版") || strings[14].contains("第3版") || "3".equals(strings[14].trim())) {
                            postLiterature.setEdition("3");
                        } else if (strings[14].contains("第四版") || strings[14].contains("第4版") || "4".equals(strings[14].trim())) {
                            postLiterature.setEdition("4");
                        } else if (strings[14].contains("第五版") || strings[14].contains("第5版") || "5".equals(strings[14].trim())) {
                            postLiterature.setEdition("5");
                        } else if (strings[14].contains("其他") || "6".equals(strings[14].trim())) {
                            postLiterature.setEdition("6");
                        }
                        postLiterature.setCongbianming(strings[15].trim());
                        postLiterature.setCongbianzhe(strings[16].trim());
                        postLiterature.setRemark(strings[17].trim());
                        //文献分类
                        PostLiteratureType postLiteratureType = postLiteratureTypeMapper.getTypeById(strings[18].trim());
                        if (postLiteratureType != null) {
                            postLiterature.setLiteratureTypeOne(postLiteratureType.getId());
                            postLiterature.setLiteratureTypeTwo(postLiteratureType.getTypeName());

                            //分类索书号,电子版本没有
                            if ("2".equals(postLiterature.getDataType())) {

                            } else {
                                JsonResult serialNumber = postLiteratureSerialNumberService.getSerialNumber(postLiterature.getDataName(),postLiterature.getLiteratureTypeOne());
                                Map<String, String> map = (Map<String, String>) serialNumber.getData();
                                String callNo = map.get("callNo");
                                String searchValue = map.get("searchValue");
                                postLiterature.setSearchValue(searchValue);
                                postLiterature.setCallNo(callNo);
                            }
                        } else {
                            continue;
                        }
                        postLiterature.setPermissionsSettings("1");
                        postLiterature.setCreateTime(new Date());
                        postLiterature.setCreator(userId);
                        postLiterature.setUpdateTime(new Date());
                        postLiterature.setUpdater(userId);
                        postLiterature.setStatus("1");
                        postLiterature.setSequence(50);
                        li.add(postLiterature);
                    }
                }
                if (li.size() > 0) {
                    postLiteratureMapper.batchInsert(li);
                    jsonResult = new JsonResult(1);
                } else {
                    jsonResult = new JsonResult("111117");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            jsonResult = new JsonResult("111116");
        }
        return jsonResult;
    }

    /**
     * @author wyf
     * @description  根据属性名和object对象获取属性值
     * @date  2018/10/19 12:06
     * @param fieldName  属性名
     * @param o  对象
     * @return java.lang.Object
     */
    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void downDemo(String fileName, HttpServletRequest request, HttpServletResponse response) {
        //        response.setContentType("application/vnd.ms-excel;charset=utf-8"); /*设定相应类型 编码*/
        try {
            request.setCharacterEncoding("UTF-8");//设定请求字符编码
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedInputStream bis = null;//创建输入输出流
        BufferedOutputStream bos = null;

        //        String ctxPath = request.getSession().getServletContext().getRealPath("/") + "upload/";//获取文件真实路径
        String ctxPath = request.getSession().getServletContext().getRealPath("/template/");
        System.out.println(ctxPath);
        String downLoadPath = ctxPath + fileName;
        System.out.println(downLoadPath);
        try {
            long fileLength = new File(downLoadPath).length();//获取文件长度
            response.setContentType("application/x-msdownload;");//下面这三行是固定形式
            //            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

            response.setHeader("Content-Length", String.valueOf(fileLength));
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));//创建输入输出流实例
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];//创建字节缓冲大小
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();//关闭输入流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();//关闭输出流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public JsonResult batchSetting(String[] arr, String setting) {
        JsonResult jsonResult = null;
        try {
            List<String> keys = Arrays.asList(arr);
            postLiteratureMapper.batchSetting(keys,setting);
            jsonResult = new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = new JsonResult("111116");
        }
        return jsonResult;
    }

    @Override
    public JsonResult batchRecall(String[] arr) {
        JsonResult jsonResult = null;
        try {
            List<String> keys = Arrays.asList(arr);
            //撤回默认设置权限为不公开
            postLiteratureMapper.batchRecall(keys);
            jsonResult = new JsonResult(1);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = new JsonResult("111116");
        }
        return jsonResult;
    }

    @Override
    public JsonResult getSelectData() {
        JsonResult jsonResult = null;

        Map<String,Object> map = new HashMap<String,Object>();
        //查询入库成员列表
        try {
//            List<SysUser> userList = sysUserMapper.getUserByDepartmentName("资料室");
            List<SysUser> userList = postVideoMapper.checkUserOrg(null, "zls");
            map.put("userList",userList);
            jsonResult = new JsonResult(1,map);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult = new JsonResult("111116");
        }

        return jsonResult;
    }

    @Override
    public JsonResult getLiteratureType() {
        String yao = PostLiteratureServiceImpl.LITERATURE_TYPE_REDIS_KEY;
        if (JedisService.isExist(yao)) {
            String type = JedisService.get(yao);
            return new JsonResult(1, JSONUtils.parse(type));
        }
        List<Map<String, Object>> tree = this.getLiteratureTypeTree(null);
        String treeStr = JSONUtils.toJSONString(tree);
        JedisService.set(yao, treeStr);
        return new JsonResult(1, tree);
    }

    private List<Map<String, Object>> getLiteratureTypeTree(String pid) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = null;
        // 获取当前级所有类型
        List<PostLiteratureType> literatures = postLiteratureTypeMapper.getLiteratureTypeByPid(pid);
        for (PostLiteratureType type : literatures) {
            map = new HashMap<String, Object>(3);
            map.put("name", type.getTypeName());
            map.put("id", type.getId());
            map.put("children", this.getLiteratureTypeTree(type.getId()));
            result.add(map);
        }
        return result;
    }

    @Override
    public JsonResult saveLiteratureType(PostLiterature literature) {
        return null;
    }

    @Override
    public List<PostLiterature> getLiteratureName() {
        List<PostLiterature> list = null;
        try {
            list = postLiteratureMapper.getLiteratureName();
        } catch (Exception e) {
            e.printStackTrace();
            list = new ArrayList<PostLiterature>();
        }

        return list;
    }

    /**
     * 查询图表1
     *
     * @param condition 查询条件
     * @param type      查询类型(1：采集:/2：查询)
     * @return
     */
    @Override
    public JsonResult getLiteratureCjReport1(HashMap<String, Object> condition, String type) {
        try {
            HashMap<String,Object> data = new HashMap<String,Object>();
            if (type.equals("1")){
                List<HashMap<String,Object>> table = postLiteratureMapper.getVideoCjTable(condition);
                List<HashMap<String,Object>> pie = postLiteratureMapper.getVideoCjPie(condition);
                List<HashMap<String,Object>> line = postLiteratureMapper.getVideoCjLine(condition);
                data.put("table",table);
                HashMap<String,Object> pieData = new HashMap<String,Object>();
                pieData.put("data",pie);
                String[] legend = new String[]{"纸质版本","电子版本","档案版本"};
                pieData.put("legend",legend);
                data.put("pie",pieData);
                HashMap<String,Object> lineData = new HashMap<String,Object>();
                List<HashMap<String,Object>> yAxis = new ArrayList<HashMap<String,Object>>();
                List<String> xAxis = new ArrayList<String>();
                for (HashMap<String, Object> map : line) {
                    xAxis.add(map.get("updateTime").toString());
                }
                for (int i=0;i<legend.length;i++){
                    HashMap<String,Object> temp = new HashMap<String,Object>();
                    temp.put("name",legend[i]);
                    List<Object> values = new ArrayList<Object>();
                    for (HashMap<String, Object> map : line) {
                        values.add(map.get(legend[i]));
                    }
                    temp.put("data",values);
                    temp.put("type","line");
                    temp.put("stack","总量");
                    yAxis.add(temp);
                }
                lineData.put("data",yAxis);
                lineData.put("legend",legend);
                lineData.put("xAxis",xAxis);
                data.put("line",lineData);
            }else {
                List<HashMap<String,Object>> table = postLiteratureMapper.getVideoCxTable(condition);
                List<HashMap<String,Object>> pie = postLiteratureMapper.getVideoCxPie(condition);
                List<HashMap<String,Object>> line = postLiteratureMapper.getVideoCxLine(condition);
                data.put("table",table);
                String[] legend1 = new String[]{"纸质申请","PC端"};
                String[] legend = new String[]{"申请","借阅","归还"};
                HashMap<String,Object> pieData = new HashMap<String,Object>();
                pieData.put("data",pie);
                pieData.put("legend",legend1);
                data.put("pie",pieData);
                HashMap<String,Object> lineData = new HashMap<String,Object>();
                List<HashMap<String,Object>> yAxis = new ArrayList<HashMap<String,Object>>();
                List<String> xAxis = new ArrayList<String>();
                for (HashMap<String, Object> map : line) {
                    xAxis.add(map.get("updateTime").toString());
                }
                for (int i=0;i<legend.length;i++){
                    HashMap<String,Object> temp = new HashMap<String,Object>();
                    temp.put("name",legend[i]);
                    List<Object> values = new ArrayList<Object>();
                    for (HashMap<String, Object> map : line) {
                        values.add(map.get(legend[i]));
                    }
                    temp.put("data",values);
                    temp.put("type","line");
                    temp.put("stack","总量");
                    yAxis.add(temp);
                }
                lineData.put("data",yAxis);
                lineData.put("legend",legend);
                lineData.put("xAxis",xAxis);
                data.put("line",lineData);
            }
            return new JsonResult(1,data);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询图表2
     *
     * @param condition 查询条件
     * @param type      查询类型(1：采集:/2：查询)
     * @return
     */
    @Override
    public JsonResult getLiteratureCjReport2(HashMap<String, Object> condition, String type) {
        try {
            HashMap<String,Object> data = new HashMap<String,Object>();
            List<HashMap<String,Object>> bar = postLiteratureMapper.getVideoCjBarPlus(condition);
            HashMap<String,Object> barData = new HashMap<String,Object>();
            String[] legend = new String[]{"采集","发布"};
            List<HashMap<String,Object>> yAxis = new ArrayList<HashMap<String,Object>>();
            List<String> xAxis = new ArrayList<String>();
            for (HashMap<String, Object> map : bar) {
                xAxis.add(map.get("orgName").toString());
            }
            for (int i=0;i<legend.length;i++){
                HashMap<String,Object> temp = new HashMap<String,Object>();
                temp.put("name",legend[i]);
                List<Object> values = new ArrayList<Object>();
                for (HashMap<String, Object> map : bar) {
                    values.add(map.get(legend[i]));
                }
                temp.put("data",values);
                temp.put("type","bar");
                temp.put("barGap",0);
                yAxis.add(temp);
            }
            barData.put("data",yAxis);
            barData.put("legend",legend);
            barData.put("xAxis",xAxis);
            data.put("bar",barData);
            return new JsonResult(1,data);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询文献采集统计（交叉表）
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCjTable(HashMap<String, Object> condition) {
        try{
            List<HashMap<String, Object>> videoCjTable = postLiteratureMapper.getVideoCjTable(condition);
            return new JsonResult(1,videoCjTable);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询文献采集统计（饼图）
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCjPie(HashMap<String, Object> condition) {
        try{
            List<HashMap<String, Object>> videoCjTable = postLiteratureMapper.getVideoCjPie(condition);
            return new JsonResult(1,videoCjTable);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询文献采集统计（折线图）
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCjLine(HashMap<String, Object> condition) {
        try{
            List<HashMap<String, Object>> videoCjTable = postLiteratureMapper.getVideoCjLine(condition);
            return new JsonResult(1,videoCjTable);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 文献分类统计
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCjBarPlus(HashMap<String, Object> condition) {
        try{
            List<HashMap<String, Object>> videoCjTable = postLiteratureMapper.getVideoCjBarPlus(condition);
            return new JsonResult(1,videoCjTable);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询文献查询统计（交叉表）
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCxTable(HashMap<String, Object> condition) {
        try{
            List<HashMap<String, Object>> videoCjTable = postLiteratureMapper.getVideoCxTable(condition);
            return new JsonResult(1,videoCjTable);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询文献查询统计（饼图）
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCxPie(HashMap<String, Object> condition) {
        try{
            List<HashMap<String, Object>> videoCjTable = postLiteratureMapper.getVideoCxPie(condition);
            return new JsonResult(1,videoCjTable);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询文献查询统计（折线图）
     *
     * @param condition
     * @return
     */
    @Override
    public JsonResult getVideoCxLine(HashMap<String, Object> condition) {
        try{
            List<HashMap<String, Object>> videoCjTable = postLiteratureMapper.getVideoCxLine(condition);
            return new JsonResult(1,videoCjTable);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }


    /**
     * 查询影视采集统计
     *
     * @return
     */
    @Override
    public JsonResult getVideoCjCount() {
        try {
            HashMap<String,Object> count =  postLiteratureMapper.getVideoCjCount();
            return new JsonResult(1,count);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询影视查询统计
     *
     * @return
     */
    @Override
    public JsonResult getVideoCxCount() {
        try {
            HashMap<String,Integer> count =  new HashMap<>(3);
            Integer videoOpenCount = postLiteratureMapper.getVideoOpenCount();
            Integer videoCxApply = postLiteratureMapper.getVideoCxApply();
            Integer videoCxApproval = postLiteratureMapper.getVideoCxApproval();
            count.put("videoOpenCount",videoOpenCount);
            count.put("videoCxApply",videoCxApply);
            count.put("videoCxApproval",videoCxApproval);
            return new JsonResult(1,count);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询待办
     *
     * @param currentUserId 用户id
     * @return
     */
    @Override
    public JsonResult getUndoTask(String currentUserId) {
        try {
            HashMap<String,Object> condition = new HashMap<String, Object>(5);
            condition.put("currentUserId",currentUserId);
            List<HashMap<String, Object>> undoTask = postLiteratureMapper.getUndoTask(condition);
            return new JsonResult(1,undoTask);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询已办
     *
     * @param currentUserId 用户id
     * @return
     */
    @Override
    public JsonResult getDoneTask(String currentUserId) {
        try {
            HashMap<String,Object> condition = new HashMap<String, Object>(5);
            condition.put("currentUserId",currentUserId);
            List<HashMap<String, Object>> doneTask = postLiteratureMapper.getDoneTask(condition);
            return new JsonResult(1,doneTask);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 查询已办结
     *
     * @param currentUserId 用户id
     * @return
     */
    @Override
    public JsonResult getFinishTask(String currentUserId) {
        try {
            HashMap<String,Object> condition = new HashMap<String, Object>(5);
            condition.put("currentUserId",currentUserId);
            List<HashMap<String, Object>> finishTask = postLiteratureMapper.getFinishTask(condition);
            return new JsonResult(1,finishTask);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    /**
     * 根据id查询文献详情
     *
     * @param id
     * @return
     */
    @Override
    public JsonResult getLiteratureById(String id) {
        try {
            PostLiterature postLiterature = postLiteratureMapper.getLiteratureById(id);
            return new JsonResult(1,postLiterature);
        }catch (Exception e){
            e.printStackTrace();
            return new JsonResult("111116");
        }
    }

    @Override
    public JsonResult selectByPrimaryKey(String id) {
        PostLiteratureWithBLOBs postLiteratureWithBLOBs = postLiteratureMapper.selectByPrimaryKey(id);
        return new JsonResult(1, postLiteratureWithBLOBs);
    }

    @Override
    public void update(PostLiteratureWithBLOBs postLiterature) {
        postLiteratureMapper.updateByPrimaryKeySelective(postLiterature);
    }

    @Override
    public List<PostLiterature> getLiteratureByIdList(List<String> keys) {
        return postLiteratureMapper.getLiteratureByIdList(keys);
    }

    @Override
    public List<Attachment> getPostLiteratureAttachment(String postLiteratureId){
        return postLiteratureMapper.getPostLiteratureAttachment(postLiteratureId);
    }
}
