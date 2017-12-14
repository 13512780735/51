package com.likeit.as51scholarship.media.view;

import com.likeit.as51scholarship.media.model.Video;

import java.io.Serializable;
import java.util.List;


/**
 * Author  wangchenchen
 * Description
 */
public class VideoListData implements Serializable {

    private List<Video> list;

    public List<Video> getList() {
        return list;
    }

    public void setList(List<Video> list) {
        this.list = list;
    }
}
