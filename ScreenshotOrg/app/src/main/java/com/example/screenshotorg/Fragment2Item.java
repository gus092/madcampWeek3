package com.example.screenshotorg;

import android.graphics.Bitmap;

public class Fragment2Item {
    private  String name;
    private int photo;

    public Fragment2Item(String name, int photo){
        this.name = name;
        this.photo = photo;
    }
    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name= name;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo){
        this.photo = photo;
    }


}


