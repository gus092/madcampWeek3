package com.example.screenshotorg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity {

    RecyclerView listshowrcy;
    List<Item> productlists = new ArrayList<>();
    public androidx.appcompat.widget.SearchView searchView;
    Fragment2Adapter adapter3;
    private static ArrayList<String> images3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.searchactivity);
        Intent intent2 = getIntent();
        int dirname = intent2.getIntExtra("dirname",-1);

        String mycatname = "total";
        switch (dirname){
            case 0:
                mycatname = "shopping";
                break;
            case 1:
                mycatname = "food";
                break;
            case 2:
                mycatname = "places";
                break;
            case 3:
                mycatname = "cosmetic";
                break;
            case 4:
                mycatname = "fashion";
                break;
            case 5:
                mycatname = "text";
                break;
            case 6:
                mycatname = "celebrities";
                break;
            case 7:
                mycatname = "etc";
                break;
            default:
                mycatname = "total";
                break;
        }
        ArrayList<String> myimagelist = new ArrayList<String>();
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        try {
            JSONObject json = new JSONObject((dbHandler.findHandler(0).getStudentName()));
            JSONArray myType = json.getJSONArray(mycatname);
            for (int i = 0; i < myType.length(); i ++){
                myimagelist.add(myType.get(i).toString());
            }

        } catch (JSONException e){
            e.printStackTrace();
        }






        //modified

//
//        productlists.add(new Item("1111 dog one",R.drawable.dog1));
//        productlists.add(new Item("222 dog two",R.drawable.dog2));
//        productlists.add(new Item("3333 dog three",R.drawable.dog3));
//        productlists.add(new Item("4444 dog four",R.drawable.dog4));
//        productlists.add(new Item("5555 doug five",R.drawable.dog5));
//        productlists.add(new Item("six",R.drawable.dog6));
//        productlists.add(new Item("seven",R.drawable.dog7));
//        productlists.add(new Item("eight",R.drawable.dog8));
//        productlists.add(new Item("nine dog 9999 ",R.drawable.dog9));

        images3 = myimagelist;//getAllShownImagesPath(this);

        for (int i=0; i<images3.size();i++){ //images에는 원하는 사진의 절대경로를 넣으면 됨
            productlists.add(new Item("#추천 tag를 달아주세요", BitmapFactory.decodeFile(images3.get(i))));
        }

        //modified

        listshowrcy =(RecyclerView)findViewById(R.id.listshow);
        listshowrcy.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listshowrcy.setLayoutManager(linearLayoutManager);
        adapter3 = new Fragment2Adapter(productlists,this);
        listshowrcy.setAdapter(adapter3);

    }
    private ArrayList<String> getAllShownImagesPath(Context activity) {
        // grouping 된 첫사진만
        Uri uri;
        Cursor cursor;
        int data,album;
        int check=0;
        int column_index_data, column_index_folder_name;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // 수정할부분
        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(
                MediaStore.Files.getContentUri("external"),
                null,
                MediaStore.Images.Media.DATA + " like ? ",
                new String[] {"%Screenshots%"},
                null
        );
        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        column_index_folder_name = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
        while (cursor.moveToNext()) {

            absolutePathOfImage = cursor.getString(column_index_data);
            listOfAllImages.add(absolutePathOfImage);

        }
        return listOfAllImages;
    }//modified


    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu,menu);
        final MenuItem myActionMenuItem=menu.findItem(R.id.app_search);
        searchView=(androidx.appcompat.widget.SearchView)myActionMenuItem.getActionView();
        ((EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.colorAccent));
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!searchView.isIconified()){
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }
    private List<Item> filter(List<Item> pl,String query){
        query=query.toLowerCase();
        final List<Item> filteredModeList= new ArrayList<>();
        for (Item model:pl){
            final String text = model.getName().toLowerCase();
            if(text.startsWith(query)){
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }

}
