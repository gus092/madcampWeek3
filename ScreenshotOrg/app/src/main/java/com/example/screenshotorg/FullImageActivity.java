package com.example.screenshotorg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;

public class FullImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image_view);

        Intent i = getIntent();

        String path = i.getStringExtra("fullsize");

        ImageView GalleryPreviewImg = (ImageView) findViewById(R.id.full_image_view2);
        GalleryPreviewImg.bringToFront();

       Glide.with(this).asBitmap().load(new File(path)).into(GalleryPreviewImg);

        View.OnClickListener closebtn_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        Button closeLogin = (Button) findViewById(R.id.close_button);
        closeLogin.setOnClickListener(closebtn_listener);

    }
}
