package com.asaproject.eventnotifier;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Date;

//The class used to upload data to database
public class EventItems
{
    private String title;

    private String desc;
    private String imageUrl;
    private String imageName;
    private  String username;
    private long dateOfEvent;

    public long getDateOfEvent() {
        return dateOfEvent;
    }

    public void setDateOfEvent(long dateOfEvent) {
        this.dateOfEvent = dateOfEvent;
    }

    public EventItems()
    {

    }

    public EventItems(String title,String desc,long date,String imageUrl,String imageName,String username)
    {
        this.title=title;
        this.desc=desc;
        this.imageUrl=imageUrl;
        this.imageName = imageName;
        this.username=username;
        this.dateOfEvent=date;
    }

    public String getUsername() {
        return username;
    }
    public String getTitle() {
        return title;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getImageName() {return imageName; }
    public String getDesc() {
        return desc;
    }


    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setUsername(String username) {
        this.username = username;
    }






    public void setTitle(String title) {
        this.title = title;
    }
    public void setImageUrl(String image) {
        this.imageUrl = image;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }



}
