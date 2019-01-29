package com.tj720.service;

import com.tj720.controller.framework.JsonResult;
import com.tj720.controller.framework.MyException;
import com.tj720.model.common.Attachment;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author cm
 * @date 2018年7月2日15:34:24
 */
public interface AttachmentService{
	/**
	 * 单个附件上传
	 * @param file
	 * @return
	 * 		errorCode:1、上传成功，0、上传失败
	 * 		msg:提示信息
	 * 		Object:当文件上传成功时，返回当前文件对象
	 */
	public JsonResult saveAttachment(CommonsMultipartFile file, String tableName, String tableid, String source);

	/**
	 * 多个附件上传
	 * @param files
	 * @return
	 * 		errorCode:1、上传成功，0、上传失败
	 * 		msg:提示信息
	 * 		Object:当文件上传成功时，返回当前文件对象
	 */
	public JsonResult saveAttachment(CommonsMultipartFile[] files, String tableName, String tableid,
											  String source);

	/**
	 * 上传图片(无裁剪)
	 * @param
	 */
	public JsonResult uploadEditPic(CommonsMultipartFile file, String projectName);

	/**
	 * 裁剪并上传图片
	 * @param
	 */
	public JsonResult uploadImg(CommonsMultipartFile file, String projectName, HttpServletRequest request);

	/**
	 * 上传视频
	 * @param file，projectName，request
	 * @return
	 * 		errorCode:1、上传成功，0、上传失败
	 * 		msg:提示信息
	 * 		Object:当文件上传成功时，返回当前文件对象
	 */
	public JsonResult uploadVideo(CommonsMultipartFile file, String projectName, HttpServletRequest request) throws MyException,IOException;
	/**
	 * 保存数据库
	 * @param  //Attachment
	 */
	public void save(Attachment mipAttachment);

	/**
	 * 修改数据库
	 * @param  //Attachment
	 */
	public int update(Attachment mipAttachment);
	
	/**
	 * 批量保存到数据库
	 * @param attachment
	 */
	public void batchSave(List<Attachment> attachment);
	
	/**
	 * 根据主表名称和主表id查找相关附件
	 * @param attType		主表名
	 * @param attfkid		主表id
	 * @return
	 */
	public List<Attachment> getListByFkid(String attType, String attfkid);
	
	/**
	 * 根据id删除，同时删除服务器文件
	 * @param attchid		id
	 * @return
	 */
	public int deleteFile(String attchid);

	/**
	 * @Author wyf
	 * @Description 根据picids查询pic对象集合
	 * @Date  2018/9/29 9:45
	 * @Param picids picid字符串，逗号隔开的
	 * @return java.util.List<com.tj720.model.common.Attachment>
	 */
	List<Attachment> getListByIds(String picids);

	/**
	 * 批量修改
	 * @param picList
	 * @return
	 */
	void batchUpdate(List<Attachment> picList);

	/**
	 * 批量删除
	 * @param  delpicids
	 * @return
	 */
	void batchDelete(String delpicids);

	List<Attachment> getAttachmentsByFkId(String fkId);

	Attachment getAttachmentsById(String attId);

	int deleteAttachment(String attId);

//	void downloadFile(String filePath, OutputStream out);

	/**
	 * 获取单个附件（映射后）
	 * @param attId
	 * @return
	 */
	Attachment getFileTransPath(String attId);


	String getFileTransPathByPath(String path);

	/**
	 * 获取主图（映射后）
	 * @param attId
	 * @return
	 */
	List<Attachment> getMainFileTransPath(String attId);

	/**
	 * 获取附件列表
	 * @param attId 附件id
	 * @return
	 */
	List<Attachment> getFileTransPathList(String attId);

}
