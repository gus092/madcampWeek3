package com.example.screenshotorg;

import android.graphics.Bitmap;

public class Item {
    private  String name;
    private Bitmap photo;

    public Item(String name, Bitmap photo){
        this.name = name;
        this.photo = photo;
    }
    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name= name;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo){
        this.photo = photo;
    }


}

