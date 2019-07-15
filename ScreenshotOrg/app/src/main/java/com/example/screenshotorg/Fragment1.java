package com.example.screenshotorg;
//이거는 날리는 방지용입니다.
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.screenshotorg.R;
import com.example.screenshotorg.ui.main.PageViewModel;
import com.google.android.gms.common.api.Batch;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;

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


        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(requireContext(),mRecyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(requireContext(),"Edit 하려면 길게 누르세요.",Toast.LENGTH_SHORT).show();
                        //Toast.makeText(requireContext(),position+"번 째 아이템 클릭",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Toast.makeText(requireContext(),"Edit 모드로 들어갑니다.",Toast.LENGTH_SHORT).show();

                        Intent intent4 = new Intent(getActivity(), EditActivity.class);
                        intent4.putExtra("edit", images.get(position));
                        startActivity(intent4);
                    }
                })); //modified

//        myDataset.add(new MyData("#InsideOut", R.drawable.search));
//        myDataset.add(new MyData("#Mini", R.drawable.search));
//        myDataset.add(new MyData("#ToyStroy", R.drawable.search));

        //modified

        images = getAllShownImagesPath(requireContext());

        for (int i=1; i<images.size();i++){ //images에는 원하는 사진의 절대경로를 넣으면 됨
            myDataset.add(new MyData("#추천 tag를 달아주세요",BitmapFactory.decodeFile(images.get(i-1))));
        }


//        final TextView textView = root.findViewById(R.id.section_label);
//        pageViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        return view;
    }


    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            void onItemClick(View view, int position);

            void onLongItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mListener != null) {
                        Log.d("long","press");
                        mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

        @Override public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
    }  //modified







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
