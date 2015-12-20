package com.xoredge.tariqjameelbayans.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ahmad Ali on 11/14/2015.
 */
public class VideoApi {
    public String id;
    public String title;
    public String channel;
    public String owner;
    public String duration;
    public String record_start_time;
    public String views_total;
    public String Description;
    public boolean isFavourite;
    public VideoApi(){

    }
    public VideoApi(String vid){
        this.id =vid;
    }
    public VideoApi(String vid, String title){
        this.id =vid;
        this.title=title;
    }
}
