package com.tj720.controller.attachment;


import com.tj720.common.enumeration.TrueOrFalse;
import com.tj720.config.FileUploadConfig;
import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.MyException;
import com.tj720.controller.springbeans.Config;
import com.tj720.model.common.Attachment;
import com.tj720.service.AttachmentService;
import com.tj720.utils.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/attach")
public class AttachmentController{

	@Autowired
	private Config config;

	@Autowired
	private AttachmentService attachmentService;
	
	private FileUploadConfig fileUploadConfig;

	/**
	 * @author wyf
	 * @description  单文件上传
	 * @date  2018/10/23 12:16
	 * @param file  文件
	 * @param tableName   表名
	 * @param tableId     对应表id，可不传
	 * @param source      没什么用，可不传
	 * @param request     请求
	 * @return com.tj720.controller.framework.JsonResult
	 */
	@RequestMapping(value = "/upload")
	@ResponseBody
	public JsonResult upload(@RequestParam(value = "file", required = false) CommonsMultipartFile file,
                             String tableName, String tableId, String source, HttpServletRequest request) {
		JsonResult saveAttachment = attachmentService.saveAttachment(file, tableName, tableId, source);
		return saveAttachment;
	}


	@RequestMapping("/uploadEditPic")
	@ResponseBody
	public JsonResult uploadEditPic(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request, String projectName) {
		JsonResult result = attachmentService.uploadEditPic(file,projectName);
		return result;
	}

	@RequestMapping("/cancelFile.do")
	@ResponseBody
	public JsonResult cancelFile(String resultPath) {

		String path = config.getFtpRootPath() + config.getRootPath() + resultPath;
		try {
			//以前版本：删除本地文件
//			FileUtil.delFile(path);
			fileUploadConfig = new FileUploadConfig(config);
			boolean delFtpFile = fileUploadConfig.deleteFile(path);
			return new JsonResult(1);
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonResult(2);
		}

	}

	@RequestMapping(value="/downFile.do")
	public void downFile(String path, String fileName, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");//设定请求字符编码
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String downPath = config.getFtpRootPath() + config.getRootPath() + path;

		try {
			response.setContentType("application/x-msdownload;charset=utf-8");
			response.setHeader("Content-disposition", "attachment; filename=" +  URLEncoder.encode(fileName, "UTF-8"));
			fileUploadConfig = new FileUploadConfig(config);
			fileUploadConfig.downloadFile(downPath, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 裁剪并上传图片
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/cutPicture.do")
	@ResponseBody
	public JsonResult cutPicture(@RequestParam(value = "file", required = false) CommonsMultipartFile file, HttpServletRequest request, String projectName) {
		JsonResult result = attachmentService.uploadImg(file, projectName, request);
		return result;
	}


	/**
	 * 上传图片(不裁剪)
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "/Picture.do")
	@ResponseBody
	public JsonResult uploadEditPic(@RequestParam(value = "file", required = false) CommonsMultipartFile file, String projectName) {
		JsonResult result = attachmentService.uploadEditPic(file,projectName);
		return result;
	}



	/**
	 * 多附件上传
	 */
	@SuppressWarnings("all")
	@RequestMapping(value = "/uploads.do")
	@ResponseBody
	public JsonResult uploadAttachs(@RequestParam(value = "files", required = false) CommonsMultipartFile[] files,
									String tableName, String tableid, String source) {
		JsonResult jsonResult = attachmentService.saveAttachment(files, tableName, tableid, source);
		return new JsonResult(1,jsonResult);
	}

	// 上传视频
	@RequestMapping(value = "/uploadVideo.do")
	@ResponseBody
	public JsonResult uploadVideo(@RequestParam(value = "file", required = false) CommonsMultipartFile file, HttpServletRequest request, String projectName) throws MyException,IOException {

		JsonResult result = attachmentService.uploadVideo(file,projectName,request);
		return result;
	}

	/**
	 * 根据文件批号查询附件列表
	 * @param fkId 文件批号
	 * @return
	 */
	@RequestMapping(value = "/getAttachmentsByFkId.do")
	@ResponseBody
	public JsonResult getAttachmentsByFkId(@RequestParam String fkId){
		try {
			List<Attachment> attachments= attachmentService.getAttachmentsByFkId(fkId);
			return  new JsonResult(1,attachments);
		}catch (Exception e){
			e.printStackTrace();
			return new JsonResult(0,"查询失败");
		}

	}

	@RequestMapping(value = "/getAttachmentsById.do")
	@ResponseBody
	public JsonResult getAttachmentsById(String attId){
		try {
			Attachment attachments= attachmentService.getAttachmentsById(attId);
			return  new JsonResult(1,attachments);
		}catch (Exception e){
			e.printStackTrace();
			return new JsonResult(0,"查询失败");
		}

	}

	@RequestMapping(value = "/deleteAttachment.do")
	@ResponseBody
	public JsonResult deleteAttachment(@RequestParam String attId){
		try {
			int result = attachmentService.deleteAttachment(attId);
			return  new JsonResult(1,result);
		}catch (Exception e){
			e.printStackTrace();
			return new JsonResult(0,"删除失败");
		}

	}
}
