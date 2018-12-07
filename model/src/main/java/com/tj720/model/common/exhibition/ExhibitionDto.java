package com.tj720.model.common.exhibition;/**
 * Created by MyPC on 2018/10/15.
 */

import com.tj720.model.common.interfacecollect.InterfaceCollect;
import com.tj720.model.common.video.PostExhibVideo;

import java.util.List;


/**
 * @ClassName: ExhibitionDto
 * @Description: TODO
 * @Author: MyPC
 * @Date: 2018/10/15
 * @Version: 1.0
 **/
public class ExhibitionDto {

    private Exhibition exhibition;

    private List<ExhibRoom> listExhibRoom;

    private List<InterfaceCollect> listExhibCollection;

    private List<PostExhibVideo> listExhibVideo;

    public List<ExhibRoom> getListExhibRoom() {
        return listExhibRoom;
    }

    public void setListExhibRoom(List<ExhibRoom> listExhibRoom) {
        this.listExhibRoom = listExhibRoom;
    }

    public List<InterfaceCollect> getListExhibCollection() {
        return listExhibCollection;
    }

    public void setListExhibCollection(List<InterfaceCollect> listExhibCollection) {
        this.listExhibCollection = listExhibCollection;
    }

    public List<PostExhibVideo> getListExhibVideo() {
        return listExhibVideo;
    }

    public void setListExhibVideo(List<PostExhibVideo> listExhibVideo) {
        this.listExhibVideo = listExhibVideo;
    }


    public Exhibition getExhibition() {
        return exhibition;
    }

    public void setExhibition(Exhibition exhibition) {
        this.exhibition = exhibition;
    }
}
