package com.example.screenshotorg;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.screenshotorg.R;
import com.example.screenshotorg.ui.main.PageViewModel;

import java.util.ArrayList;

public class Fragment1 extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;
    private static ArrayList<String> images;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.onefragment, container, false);
        mRecyclerView = (RecyclerView)root.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(requireContext());
        ((LinearLayoutManager) mLayoutManager).setReverseLayout(true);
        ((LinearLayoutManager) mLayoutManager).setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLayoutManager);



        // specify an adapter (see also next example)
        myDataset = new ArrayList<>();
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);


//        myDataset.add(new MyData("#InsideOut", R.drawable.search));
//        myDataset.add(new MyData("#Mini", R.drawable.search));
//        myDataset.add(new MyData("#ToyStroy", R.drawable.search));

        //modified

        images = getAllShownImagesPath(requireContext());

        for (int i=0; i<images.size();i++){ //images에는 원하는 사진의 절대경로를 넣으면 됨
            myDataset.add(new MyData("#추천 tag를 달아주세요",BitmapFactory.decodeFile(images.get(i))));
        }

//        final TextView textView = root.findViewById(R.id.section_label);
//        pageViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
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

///**
// * A placeholder fragment containing a simple view.
// */
//public class Fragment1 extends Fragment {
//
//    private static final String ARG_SECTION_NUMBER = "section_number";
//
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
////        int index = 1;
////        if (getArguments() != null) {
////            index = getArguments().getInt(ARG_SECTION_NUMBER);
////        }
////        pageViewModel.setIndex(index);
//    }
//
//    @Override
//    public View onCreateView(
//            @NonNull LayoutInflater inflater, ViewGroup container,
//            Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.onefragment, container, false);
////        final TextView textView = root.findViewById(R.id.section_label);
////        pageViewModel.getText().observe(this, new Observer<String>() {
////            @Override
////            public void onChanged(@Nullable String s) {
////                textView.setText(s);
////            }
////        });
//        return root;
//    }
//}
