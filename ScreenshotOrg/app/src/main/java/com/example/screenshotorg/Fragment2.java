package com.example.screenshotorg;


import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class Fragment2 extends Fragment {

    private GridLayoutManager lLayout;
       private static ArrayList<String> images2;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.twofragment, container, false);

        Toolbar topToolBar = (Toolbar)view.findViewById(R.id.toolbar);
        //setSupportActionBar(topToolBar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(topToolBar);
        //topToolBar.setLogo(R.drawable.logo);
        //topToolBar.setLogoDescription(getResources().getString(R.string.logo_desc));

        List<Item> rowListItem = getAllItemList();
        lLayout = new GridLayoutManager(requireContext(), 3);//category spancount

        RecyclerView rView = (RecyclerView)view.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(lLayout);


        RecyclerViewAdapter rcAdapter = new RecyclerViewAdapter(requireContext(), rowListItem);
        rView.setAdapter(rcAdapter);
        return view;
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
       // return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_refresh){
            Toast.makeText(requireContext(), "Refresh App", Toast.LENGTH_LONG).show();
        }
        if(id == R.id.action_new){
            Toast.makeText(requireContext(), "Create Text", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Item> getAllItemList(){

        List<Item> allItems = new ArrayList<Item>();

//        allItems.add(new Item("United States", R.drawable.one));
//        allItems.add(new Item("Canada", R.drawable.two));
//        allItems.add(new Item("United Kingdom", R.drawable.three));
//        allItems.add(new Item("Germany", R.drawable.four));
//        allItems.add(new Item("Sweden", R.drawable.five));
//        allItems.add(new Item("United Kingdom", R.drawable.six));
//        allItems.add(new Item("Germany", R.drawable.seven));
//        allItems.add(new Item("Sweden", R.drawable.eight));
        //modified
        images2 = getAllShownImagesPath(requireContext());
        String[] categories = {"", "Shopping", "Food", "Places", "Cosmetic", "Fashion","Recipe","Celebrities","Messages","Etc"};
        for (int i=1; i<10;i++){ //images에는 원하는 사진의 절대경로를 넣으면 됨 //images2.size()로 바꾸기!
            //allItems.add(new Item("#추천 tag를 달아주세요", BitmapFactory.decodeFile(images2.get(i))));
            allItems.add(new Item(categories[i], BitmapFactory.decodeFile(images2.get(i))));
        }



        //modified

        return allItems;
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
}
