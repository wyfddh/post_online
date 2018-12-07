package com.tj720.service;

import com.tj720.model.common.Attachment;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Created by Administrator on 2018/9/25.
 */
@Service
public interface PictureService{

    /**
     * @Author wyf
     * @Description  获取完整url的图片集合
     * @Date  2018/9/29 9:54
     * @Param
     * @return java.util.List<com.tj720.model.common.MipAttachment>
     */
    List<Attachment> getPicList(String picIds);

    /**
     * @author wyf
     * @description  设置主图
     * @date  2018/10/9 16:20
     * @param picids  图片ids 逗号隔开
     * @param isMain  主图id
     * @param delpicids 删除的图片ids逗号隔开
     * @return java.lang.Boolean
     */
    Boolean setMain(String picids, String isMain, String delpicids) throws Exception;
}
