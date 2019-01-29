package com.tj720.controller.postLiterature;

import com.tj720.config.FileUploadConfig;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.auth.ControllerAop;
import com.tj720.controller.springbeans.Config;
import com.tj720.model.common.Attachment;
import com.tj720.model.literature.PostLiterature;
import com.tj720.model.literature.PostLiteratureWithBLOBs;
import com.tj720.service.PostLiteratureSerialNumberService;
import com.tj720.service.PostLiteratureService;
import com.tj720.utils.ExportExcelUtil;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;

/**
 * @author wyf
 * @date 2018/10/16 10:38
 **/
@RequestMapping("/postLiterature")
@RestController
public class PostLiteratureController {
    @Autowired
    private PostLiteratureService postLiteratureService;

    @Autowired
    private PostLiteratureSerialNumberService postLiteratureSerialNumberService;

    @Autowired
    private Config config;

    private FileUploadConfig fileUploadConfig;

    /**
     * @author wyf
     * @description  获取列表数据
     * @date  2018/10/16 11:20
     * @param key   搜索条件
     * @param dataType  文献类型
     * @param status    文献状态
     * @param orderBy   排序
     * @param currentPage   当前页
     * @param size         每页显示数
     * @param open         不为空就是只查公开数据
     * @return com.tj720.controller.framework.JsonResult
     */
    @ControllerAop(action = "获取列表数据")
    @RequestMapping("postLiteratureList")
    public JSONObject postLiteratureList(String key,String dataType,String status,String inventoryState,String orderBy,String open,
            @RequestParam String module,
            @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) {

        JSONObject jsonObject = postLiteratureService.postLiteratureList(key,dataType,status,inventoryState,orderBy,open,currentPage,size,module);

        return jsonObject;
    }

    /**
     * @author wyf
     * @description   保存
     * @date  2018/10/17 10:19
     * @param
     * @return com.tj720.controller.framework.JsonResult
     */
    @ControllerAop(action = "保存文献")
    @RequestMapping("postLiteratureSave")
    public JsonResult postLiteratureSave(PostLiteratureWithBLOBs postLiteratureWithBLOBs) {

        JsonResult jsonResult = postLiteratureService.postLiteratureSave(postLiteratureWithBLOBs);

        return jsonResult;
    }

    /**
     * @author wyf
     * @description   删除发布撤回设置权限
     * @date  2018/10/17 10:32
     * @param id    id
     * @param status  需要修改的状态
     * @param setting 需要修改设置
     * @return com.tj720.controller.framework.JsonResult
     */
    @ControllerAop(action = "删除发布撤回设置权限")
    @RequestMapping("modifyState")
    public JsonResult modifyState(String id,String status,String setting) {

        JsonResult jsonResult = postLiteratureService.modifyState(id,status,setting);

        return jsonResult;
    }

    /**
     * @author wyf
     * @description  批量导出文献数据
     * @date  2018/10/18 11:25
     * @param  arr  id的数组
     * @return com.tj720.controller.framework.JsonResult
     */
    @ControllerAop(action = "批量导出文献数据")
    @RequestMapping("batchExport")
    public JsonResult batchExport(@RequestParam(value = "arr[]") String[] arr) {

        JsonResult jsonResult = postLiteratureService.batchExport(arr);

        return jsonResult;
    }
    /**
     * @author wyf
     * @description  批量导入文献数据
     * @date  2018/10/18 15:53
     * @param request
     * @param file
     * @return com.tj720.controller.framework.JsonResult
     */
    @ControllerAop(action = "批量导入文献数据")
    @RequestMapping("batchImport")
    public JsonResult batchImport(HttpServletRequest request,@RequestParam("file") MultipartFile file) {

        JsonResult jsonResult = postLiteratureService.batchImport(request,file);

        return jsonResult;
    }

    /**
     * @author wyf
     * @description  下载文献导出模板
     * @date  2018/10/18 15:53
     * @param type
     * @param request
     * @param response
     * @return org.springframework.http.ResponseEntity<byte[]>
     */
    @ControllerAop(action = "下载文献导出模板")
    @RequestMapping("downDemo")
    public ResponseEntity<byte[]> downDemo(String type,HttpServletRequest request,HttpServletResponse response) throws IOException {
        String fileName = "";
        if ("1".equals(type)) {
            fileName = "文献导入模板.xlsx";
        } else if ("2".equals(type)) {
            fileName = "文献分类.xlsx";
        } else {
            return null;
        }

        postLiteratureService.downDemo(fileName,request,response);

        return null;
    }

    /**
     * @author wyf
     * @description  批量设置权限
     * @date  2018/10/22 11:19
     * @param  arr  id数组
     * @return com.tj720.controller.framework.JsonResult
     */
    @ControllerAop(action = "批量设置权限")
    @RequestMapping("batchSetting")
    public JsonResult batchSetting(@RequestParam(value = "arr[]") String[] arr,String setting) {
        if (arr == null || arr.length <= 0) {
            return new JsonResult(0);
        }
        JsonResult jsonResult = postLiteratureService.batchSetting(arr,setting);

        return jsonResult;
    }

    /**
     * @author wyf
     * @description  批量撤回
     * @date  2018/10/22 15:22
     * @param  arr id数组
     * @return com.tj720.controller.framework.JsonResult
     */
    @ControllerAop(action = "批量撤回")
    @RequestMapping("batchRecall")
    public JsonResult batchRecall(@RequestParam(value = "arr[]") String[] arr) {
        if (arr == null || arr.length <= 0) {
            return new JsonResult(0);
        }
        JsonResult jsonResult = postLiteratureService.batchRecall(arr);

        return jsonResult;
    }

    /**
     * @author wyf
     * @description  获取文献新增页面下拉数据
     * @date  2018/10/24 11:41
     * @param request
     * @param response
     * @return void
     */
    @ControllerAop(action = "获取文献新增页面下拉数据")
    @RequestMapping("getSelectData")
    public JsonResult getSelectData(HttpServletRequest request,HttpServletResponse response) {
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("text/html;charset=UTF-8");
//        response.setHeader("Access-Control-Allow-Origin", "*");

//        Map<String,Object> map = postLiteratureService.getSelectData();
//        JSONArray jsonArray = JSONArray.fromObject(map);
//        String result = jsonArray.toString();
//        String callback = request.getParameter("callback");
//        result = callback + "(" + result + ")";
//        try {
//            response.getWriter().write(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        JsonResult jsonResult = postLiteratureService.getSelectData();

        return jsonResult;
    }

    /**
     * 查询文献类型下拉
     * @return
     */
    @ControllerAop(action = "查询文献类型下拉")
    @RequestMapping("/getLiteratureType")
    public JsonResult getLiteratureType() {
        try {
            return postLiteratureService.getLiteratureType();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }

    /**
     * 新增文件
     * @param literature
     * @return
     */
    @ControllerAop(action = "新增文献分类")
    @RequestMapping("/saveLiteratureType")
    public JsonResult saveLiteratureType(PostLiterature literature) {
        try {
            return postLiteratureService.saveLiteratureType(literature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null , "20000003");
    }
    @ControllerAop(action = "获取文献报表1")
    @RequestMapping("/getLiteratureReport1")
    public JsonResult getLiteratureReport1(String status,String startTime,String endTime,String type){
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("status",status);
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);
        JsonResult jsonResult = postLiteratureService.getLiteratureCjReport1(condition,type);
        return jsonResult;
    }
    @ControllerAop(action = "获取文献报表2")
    @RequestMapping("/getLiteratureReport2")
    public JsonResult getLiteratureReport2(String status,String startTime,String endTime,String type){
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("status",status);
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);
        JsonResult jsonResult = postLiteratureService.getLiteratureCjReport2(condition,type);
        return jsonResult;
    }
    @ControllerAop(action = "导出文献报表1")
    @RequestMapping("/exportLiteratureReport1")
    public void exportLiteratureReport1(String status,String startTime,String endTime,String type,
                                     HttpServletRequest request, HttpServletResponse response)throws IOException{
        String zipName = "文献转化分析.zip";
        //交叉表数据
        String[] tableRow = new String[]{"类型","纸质版本(次)","电子版本(次)","档案版本(次)"};
        if ("2".equals(type)){
            tableRow = new String[]{"流程状态","纸质版本","档案版本"};
        }
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("status",status);
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);
        JsonResult videoCjTable = postLiteratureService.getVideoCjTable(condition);
        List<Object[]> tableData = new ArrayList<Object[]>();
        if ("2".equals(type)){
            videoCjTable = postLiteratureService.getVideoCxTable(condition);
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjTable.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[3];
                temp[0] = map.get("type").toString();
                temp[1] = map.get("ZHIZHI").toString();
                temp[2] = map.get("DANGAN").toString();
                tableData.add(temp);
            }
        }else {
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjTable.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[4];
                temp[0] = map.get("type").toString();
                temp[1] = map.get("ZHIZHI").toString();
                temp[2] = map.get("DIANZI").toString();
                temp[3] = map.get("DANGAN").toString();
                tableData.add(temp);
            }
        }

        //饼图数据
        String[] pieRow = new String[]{"文献类型","总数"};
        if ("2".equals(type)){
            pieRow = new String[]{"申请类型","总数"};
        }
        JsonResult videoCjPie = postLiteratureService.getVideoCjPie(condition);
        List<Object[]> pieData = new ArrayList<Object[]>();
        if ("2".equals(type)){
            videoCjPie = postLiteratureService.getVideoCxPie(condition);
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjPie.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[2];
                temp[0] = map.get("name").toString();
                temp[1] = map.get("value").toString();
                pieData.add(temp);
            }
        }else{
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjPie.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[2];
                temp[0] = map.get("name").toString();
                temp[1] = map.get("value").toString();
                pieData.add(temp);
            }
        }

        //折线图数据
        String[] lineRow = new String[]{"操作日期","纸质版本","电子版本","档案版本"};
        if ("2".equals(type)){
            lineRow = new String[]{"操作日期","申请","借阅","归还"};
        }
        JsonResult videoCjLine = postLiteratureService.getVideoCjLine(condition);
        List<Object[]> linedata = new ArrayList<Object[]>();
        if ("2".equals(type)){
            videoCjLine = postLiteratureService.getVideoCxLine(condition);
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjLine.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[4];
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String updateTime = simpleDateFormat.format((Date) map.get("updateTime"));
                temp[0] = updateTime;
                temp[1] = map.get("申请").toString();
                temp[2] = map.get("借阅").toString();
                temp[3] = map.get("归还").toString();
                linedata.add(temp);
            }
        }else {
            List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjLine.getData();
            for (HashMap<String, Object> map : list) {
                Object[] temp = new String[4];
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String updateTime = simpleDateFormat.format((Date) map.get("updateTime"));
                temp[0] = updateTime;
                temp[1] = map.get("档案版本").toString();
                temp[2] = map.get("电子版本").toString();
                temp[3] = map.get("纸质版本").toString();
                linedata.add(temp);
            }
        }
        String execelName1 = "文献转化分析交叉表统计";
        String execelName2 = "文献转化分析饼图统计";
        String execelName3 = "文献转化分析折线图统计";
        if ("2".equals(type)){
            execelName1 = "文献借阅统计交叉表统计";
            execelName2 = "文献借阅统计饼图统计";
            execelName3 = "文献借阅统计折线图统计";
        }
        List<HashMap<String,Object>> excelInfo = new ArrayList<HashMap<String,Object>>();

        HashMap ext1 = new HashMap();
        ext1.put("title",execelName1);
        ext1.put("rowName",tableRow);
        ext1.put("dataList",tableData);
        excelInfo.add(ext1);

        HashMap ext2 = new HashMap();
        ext2.put("title",execelName2);
        ext2.put("rowName",pieRow);
        ext2.put("dataList",pieData);
        excelInfo.add(ext2);

        HashMap ext3 = new HashMap();
        ext3.put("title",execelName3);
        ext3.put("rowName",lineRow);
        ext3.put("dataList",linedata);
        excelInfo.add(ext3);

        ExportExcelUtil exportExcelUtil = new ExportExcelUtil();
        exportExcelUtil.exportMultiSheet(response,excelInfo);


    }
    @ControllerAop(action = "导出文献报表2")
    @RequestMapping("/exportLiteratureReport2")
    public void exportLiteratureReport2(String status,String startTime,String endTime,String type,
                                     HttpServletRequest request, HttpServletResponse response)throws IOException{
        String zipName = "文献分类统计.zip";
        HashMap<String,Object> condition = new HashMap<String,Object>();
        condition.put("status",status);
        condition.put("startTime",startTime);
        condition.put("endTime",endTime);
        //折线图数据
        String[] barRow = new String[]{"文献分类","采集","发布"};
        JsonResult videoCjBar = postLiteratureService.getVideoCjBarPlus(condition);
        List<HashMap<String,Object>> list = (List<HashMap<String,Object>>)videoCjBar.getData();
        List<Object[]> barData = new ArrayList<Object[]>();
        for (HashMap<String, Object> map : list) {
            Object[] temp = new String[3];
            temp[0] = map.get("orgName").toString();
            temp[1] = map.get("采集").toString();
            temp[2] = map.get("发布").toString();
            barData.add(temp);
        }

        ExportExcelUtil exportExcelUtil = new ExportExcelUtil("文献分类统计",barRow,barData);
        exportExcelUtil.export(response);
    }

    /**
     * 查询文献采集统计
     * @return
     */
    @ControllerAop(action = "查询文献采集统计")
    @RequestMapping("/getVideoCjCount")
    public JsonResult getVideoCjCount(@RequestParam String module){
        JsonResult count = postLiteratureService.getVideoCjCount(module);
        return count;
    }
    /**
     * 查询文献查询统计
     * @return
     */
    @ControllerAop(action = "查询文献查询统计")
    @RequestMapping("/getVideoCxCount")
    public JsonResult getVideoCxCount(@RequestParam String module){
        JsonResult count = postLiteratureService.getVideoCxCount(module);
        return count;
    }


    /**
     * 查询待办
     * @param currentUserId 用户id
     * @return
     */
    @ControllerAop(action = "查询待办")
    @RequestMapping("/getUndoTask")
    public JsonResult getUndoTask(@RequestParam String currentUserId){
        JsonResult undoTask = postLiteratureService.getUndoTask(currentUserId);
        return undoTask;
    }
    /**
     * 查询已办
     * @param currentUserId 用户id
     * @return
     */
    @ControllerAop(action = "查询已办")
    @RequestMapping("/getDoneTask")
    public JsonResult getDoneTask(@RequestParam String currentUserId){
        JsonResult doneTask = postLiteratureService.getDoneTask(currentUserId);
        return doneTask;
    }
    /**
     * 查询已办结
     * @param currentUserId 用户id
     * @return
     */
    @ControllerAop(action = "查询已完结")
    @RequestMapping("/getFinishTask")
    public JsonResult getFinishTask(@RequestParam String currentUserId){
        JsonResult finishTask = postLiteratureService.getFinishTask(currentUserId);
        return finishTask;
    }
    @ControllerAop(action = "获取单个文献")
    @RequestMapping("getLiteratureById")
    public JsonResult getLiteratureById(@RequestParam String id){
        JsonResult jsonResult = postLiteratureService.getLiteratureById(id);
        return jsonResult;
    }

    /**
     * 生成索书号
     * @param dataName 资料名称
     * @param selectID 文献分类
     * @return
     */
    @ControllerAop(action = "生成索书号")
    @RequestMapping("/getSerialNumber")
    public JsonResult getSerialNumber(String dataName, String selectID) {
        try {
            return postLiteratureSerialNumberService.getSerialNumber(dataName, selectID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000003");
    }
    @ControllerAop(action = "获取单个文献")
    @RequestMapping("/getLiteratureBLOBSById")
    public JsonResult getLiteratureBLOBSById(String id) {
        try {
            return postLiteratureService.selectByPrimaryKey(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JsonResult(0, null, "20000003");
    }

    /**
     * @author wyf
     * @description  下载电子文献
     * @date  2018/12/3 15:05
     * @param postLiteratureId
     * @param request
     * @param response
     * @return void
     */
    @ControllerAop(action = "下载电子文献")
    @RequestMapping("/downloadLiteratureFile")
    public void  downloadLiteratureFile(String postLiteratureId, HttpServletRequest request, HttpServletResponse response)throws IOException{
        JsonResult jsonResult = postLiteratureService.selectByPrimaryKey(postLiteratureId);
        PostLiteratureWithBLOBs literature = (PostLiteratureWithBLOBs)jsonResult.getData();
        String zipName = literature.getDataName()+".zip";
        List<Attachment> fileList = postLiteratureService.getPostLiteratureAttachment(postLiteratureId);//查询数据库中记录
        for (Attachment attachment : fileList) {
            String downPath = config.getFtpRootPath() + config.getRootPath() + attachment.getAttPath();
            attachment.setAttPath(downPath);
        }
        ZipOutputStream out = new ZipOutputStream(response.getOutputStream());

        try {
            request.setCharacterEncoding("UTF-8");//设定请求字符编码
            response.setContentType("application/x-msdownload;charset=utf-8");
            String regex = "[`~!@#$%^&*()\\+\\=\\{}|:\"?><【】\\/r\\/n]";

            Pattern pa = Pattern.compile(regex);
            Matcher ma = pa.matcher(zipName);
            if(ma.find()){
                zipName = ma.replaceAll("");
            }
            response.setHeader("Content-disposition", "attachment; filename=" +  zipName);
            fileUploadConfig = new FileUploadConfig(config);
            fileUploadConfig.downloadFileToZip(fileList,out,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
