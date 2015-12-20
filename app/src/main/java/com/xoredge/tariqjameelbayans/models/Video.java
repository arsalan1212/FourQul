package com.xoredge.tariqjameelbayans.models;


import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by Ahmad Ali on 11/14/2015.
 */
@Table(name = "Video")
public class Video extends Model {
    @Column(name = "vid", index = true)
    public String vid;
    @Column(name = "title")
    public String title;
    @Column(name = "channel")
    public String channel;
    @Column(name = "owner")
    public String owner;
    @Column(name = "duration")
    public String duration;
    @Column(name = "record_start_time")
    public String record_start_time;
    @Column(name = "views_total")
    public String views_total;
    @Column(name = "Description")
    public String Description;
    @Column(name = "isFavourite")
    public boolean isFavourite;
    public Video(){
        super();
    }
    public Video(VideoApi fromApi){
        this.vid=fromApi.id;
        this.title=fromApi.title;
        this.channel=fromApi.channel;
        this.owner = fromApi.owner;
        this.duration = fromApi.duration;
        this.Description = fromApi.Description;
        this.record_start_time=fromApi.record_start_time;
        this.views_total = fromApi.views_total;
        this.isFavourite = fromApi.isFavourite;
    }
    public Video(String vid){
        this.vid =vid;
    }
    public Video(String vid,String title){
        this.vid =vid;
        this.title=title;
    }
}
