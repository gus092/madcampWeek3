package com.example.screenshotorg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class TagGallery extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();

        String path = i.getStringExtra("path");

    }
}
