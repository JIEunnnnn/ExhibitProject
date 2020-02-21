package com.example.exhibitproject;

import android.graphics.Bitmap;

public class GalItem {
    private String description;
    private Bitmap picture;
    private String[] title;

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getPicture(){
        return picture;
    }

    public void setPicture(Bitmap picture){
        this.picture = picture;
    }
}
