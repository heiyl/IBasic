package com.heiyl.basic.models;

import java.io.Serializable;

/**
 * 首页条目数据
 * llxue2.0
 * 2016/1/21.
 * yulong
 */
public class IndexMessageItemDto implements Serializable {
    public String contentId;//文章的id
    public String contentTitle;//文章的标题
    public String content;//正文（做了25个字符切割）
    public String contentCreateTime;//创建时间
    public String contentCreateName;//创建人
    public String channelName;//所属栏目名字
    public String channelId;//所属栏目id
    public String contentImage;//配图
    public int type;//：未知；1：专栏；2：话题；
    public int mediaType;//0：未知；1：音频；2：视频


}
