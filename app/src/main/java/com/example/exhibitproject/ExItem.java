package com.example.exhibitproject;

import android.graphics.Bitmap;

public class ExItem {
    private int num, time;
    private String name ;
    private Bitmap image ;

    public int getNum(){
        return num;
    }

    public void setNum(int num){
        this.num = num;
    }

    public int getTime(){
        return time;
    }

    public void setTime(int time){
        this.time = time;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Bitmap getImage(){
        return image;
    }

    public void setImage(Bitmap image){
        this.image = image;
    }
}
