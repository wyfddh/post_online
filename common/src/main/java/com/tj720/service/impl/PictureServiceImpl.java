package com.tj720.service.impl;/**
 * Created by Administrator on 2018/9/25.
 */

import com.tj720.controller.springbeans.Config;
import com.tj720.model.common.Attachment;
import com.tj720.service.AttachmentService;
import com.tj720.service.PictureService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author wyf
 * @Description //TODO $
 * @Date $ $
 * @Param $
 * @return $
 **/
@Service
public class PictureServiceImpl implements PictureService{

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private Config config;
    /**
     * @Author wyf
     * @Description  获取完整url的图片集合
     * @Date  2018/9/29 9:54
     * @Param
     * @return java.util.List<com.tj720.model.common.MipAttachment>
     */
    @Override
    public List<Attachment> getPicList(String picIds) {
        List<Attachment> picList = attachmentService.getListByIds(picIds);
        if (picList.size() > 0) {
            for (Attachment attachment : picList){
                String attPath = attachment.getAttPath();
                attachment.setAttPath(config.getRootUrl() + attPath);
            }
        }
        return picList;
    }

    /**
     * 设置主图和删除图片
     * @param picids
     * @param isMain
     * @param delpicids
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Boolean setMain(String picids,String isMain,String delpicids) throws Exception{
        Boolean flag = true;
        try {
            //  设置主图
            List<Attachment> picList = attachmentService.getListByIds(picids);
            if (picList.size() > 0) {
                for (Attachment mipAttachment : picList) {
                    if (mipAttachment.getAttId().equals(isMain)) {
                        mipAttachment.setIsMain("1");
                    } else {
                        mipAttachment.setIsMain("0");
                    }
                }
                attachmentService.batchUpdate(picList);
            }
            //删除图片
            if (StringUtils.isNotBlank(delpicids)) {
                attachmentService.batchDelete(delpicids);
            }
        }catch(Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }
}
