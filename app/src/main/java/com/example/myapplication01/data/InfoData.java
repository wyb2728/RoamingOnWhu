package com.example.myapplication01.data;

import com.amap.api.maps2d.model.LatLng;

public class InfoData {
    private String title;
    private int imageResId;
    private String description;
    private LatLng position;

    public InfoData(String title, int imageResId, String description,LatLng position) {
        this.title = title;
        this.imageResId = imageResId;
        this.description = description;
        this.position = position;
    }

    public String getTitle() {
        return title;
    }
    public int getImageResId() {return imageResId;}
    public String getDescription() {
        return description;
    }
    public LatLng getPosition(){return position;}
}
