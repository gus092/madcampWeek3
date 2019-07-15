package com.example.screenshotorg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class EditActivity extends AppCompatActivity {

    String value;
    public static String[] categories = {"Shopping", "Food", "Places", "Cosmetic", "Fashion","Text","Celebrities","Etc"};
    public static int checknumber=1000;
    public static int choice=-1;
    public static CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editactivity);

        Intent i = getIntent();

        final String path = i.getStringExtra("edit");
        final int cardviewnum = i.getIntExtra("cardviewNumber",-1);



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

        final MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        View.OnClickListener closebtn_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //choice, path
                try {
                    Log.e("pathpath", "You're here: cardviewnum: " + cardviewnum + " path: " + path);
                    JSONObject json = new JSONObject((dbHandler.findHandler(0).getStudentName()));
                    JSONArray totalarr = json.getJSONArray("total");
                    JSONObject imgjson = totalarr.getJSONObject(cardviewnum);
                    JSONArray currcategories = imgjson.getJSONArray("categories");
                    String cat = "";
                    if (imgjson.get("path").equals(path)){
                        Log.e("ENTER AHAHAH", "You're here: cardviewnum: " + cardviewnum);
                        switch( cardviewnum) {
                            case 0:
                                cat = "shopping";
                                break;
                            case 1:
                                cat = "food";
                                break;
                            case 2:
                                cat = "places";
                                break;
                            case 3:
                                cat = "cosmetic";
                                break;
                            case 4:
                                cat = "fashion";
                                break;
                            case 5:
                                cat = "text";
                                break;
                            case 6:
                                cat = "celebrities";
                                break;
                            case 7:
                                cat = "etc";
                                break;
                        }

                        if (!cat.equals("")){
//                            for (int t = 0; t < currcategories.length(); t++){
//                                int pos = -1;
//                                JSONArray currentlist = json.getJSONArray(currcategories.get(t).toString());
//                                for (int k = 0; k < currentlist.length(); k++){
//                                    if (currentlist.get(k).toString().equals(path)){
//                                        pos = k;
//                                        break;
//                                    }
//                                }
//                                if (pos >-1) {
//                                    currentlist.remove(pos);
//                                }
//                                json.put(currcategories.get(t).toString(), currentlist);
//                            }

                            JSONArray carray = new JSONArray();
                            carray.put(cat);
                            imgjson.put("categories",carray);
                            //totalarr.remove(cardviewnum);
                            totalarr.put(cardviewnum, imgjson);

                            JSONArray updatedcat = json.getJSONArray(cat);
                            updatedcat.put(path);
                            json.put(cat, updatedcat);
                            json.put("total", totalarr);
                            dbHandler.updateHandler(0, json.toString());


                        }

                    }
//                    for (int k = 0 ; k < totalarr.length(); k ++){
//                        //total
//                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }





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

                    EditActivity.checknumber = position;
                    EditActivity.choice = position;
                    EditActivity.customAdapter = new CustomAdapter(context, EditActivity.categories);
                    System.out.println("cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc               "+ position);

//                    simpleCheckedTextView.setCheckMarkDrawable(R.drawable.checked);
                    simpleCheckedTextView.setChecked(true);





                }

                    Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }



}
