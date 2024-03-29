package com.example.screenshotorg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class EditActivity extends AppCompatActivity {

    String value;
    public static String[] categories = {"Shopping", "Food", "Places", "Cosmetic", "Fashion","Text","Celebrities","Etc"};
    public static int checknumber=1000;
    public static CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editactivity);

        Intent i = getIntent();

        String path = i.getStringExtra("edit");

        ImageView GalleryPreviewImg = (ImageView) findViewById(R.id.full_image_view2);

        GalleryPreviewImg.bringToFront();

        Glide.with(this).asBitmap().load(new File(path)).into(GalleryPreviewImg);

        //added with checkedtext view
        // initiate a ListView
        ListView listView = (ListView) findViewById(R.id.listView);

        // set the adapter to fill the data in ListView
//        if(checknumber==1000)
//        {customAdapter = new CustomAdapter(getApplicationContext(), categories);}
//        else
            customAdapter = new CustomAdapter(getApplicationContext(), categories);
        listView.setAdapter(customAdapter);
        //added



        View.OnClickListener closebtn_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checknumber=1000;
                finish();
            }
        };
        Button closeLogin = (Button) findViewById(R.id.close_button);
        closeLogin.setOnClickListener(closebtn_listener);


    }
}

class CustomAdapter extends BaseAdapter {
    String[] names;
    Context context;
    LayoutInflater inflter;
    String value;



    public CustomAdapter(Context context, String[] names) {
        this.context = context;
        this.names = names;
        inflter = (LayoutInflater.from(context));

    }



    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        view = inflter.inflate(R.layout.checked_list_items, null);

        final ImageView Checked = (ImageView)view.findViewById(R.id.checkImage);

        final CheckedTextView simpleCheckedTextView = (CheckedTextView) view.findViewById(R.id.simpleCheckedTextView);
        simpleCheckedTextView.setText(names[position]);

        if(position==EditActivity.checknumber)
            Checked.setImageResource(R.drawable.checked);
        else
           // Checked.setVisibility(View.INVISIBLE);

// perform on Click Event Listener on CheckedTextView
        simpleCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (simpleCheckedTextView.isChecked()) {
// set cheek mark drawable and set checked property to false
                    value = "un-Checked";
                    //Checked.setImageResource(R.draw);


                    Checked.setVisibility(View.GONE);
                    EditActivity.checknumber=1000;
                    simpleCheckedTextView.setChecked(false);

                } else {
// set cheek mark drawable and set checked property to true
                    value = "Checked";
                    Checked.setImageResource(R.drawable.checked);

                    EditActivity.checknumber=position;
                    EditActivity.customAdapter = new CustomAdapter(context, EditActivity.categories);

//                    simpleCheckedTextView.setCheckMarkDrawable(R.drawable.checked);
                    simpleCheckedTextView.setChecked(true);
                }

                    Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }



}
