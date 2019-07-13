package com.example.screenshotorg;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.screenshotorg.R;
import com.example.screenshotorg.ui.main.PageViewModel;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment2 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
//        int index = 1;
//        if (getArguments() != null) {
//            index = getArguments().getInt(ARG_SECTION_NUMBER);
//        }
//        pageViewModel.setIndex(index);
    }

    /**
     * The images.
     */
    public static ArrayList<String> images;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.twofragment, container, false);


        GridView gallery = (GridView) view.findViewById(R.id.galleryGridView);

        gallery.setAdapter(new ImageAdapter(requireContext()));
        Context context1 = requireContext();

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> arg0, final View arg1,
                                    final int position, long arg3) {
//                positionGlobal = position;
                if (null != images && !images.isEmpty()) { // 해당 그리드를 선택하면

                    //해당 그리드를 선택하면 하는 동작

                    Intent i = new Intent(getActivity(), FullImageActivity.class);
                    i.putExtra("path", images.get(position));
                    startActivity(i);

                    //String tag= tag;
//                    String root = Environment.getExternalStorageDirectory().toString();
//                    System.out.println("==================================================================        "+root);
//                    File myDir = new File(root + "/DCIM/"+"333");
//                    myDir.mkdirs();


                }


            }

        });
        return view;
    }


    /**
     * The Class ImageAdapter.
     */
    public static class ImageAdapter extends BaseAdapter {

        /** The context. */
        public static Context context;


        /**
         * Instantiates a new image adapter.
         *
         * @param localContext
         *            the local context
         */
        public ImageAdapter(Context localContext) {
            context = localContext;
            images = getAllShownImagesPath(context);
        }



        public int getCount() {
            return images.size();
        }


        public String getImage (int position) {
            return images.get(position);
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ImageView picturesView;
            if (convertView == null) {
                picturesView = new ImageView(context);
                picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                picturesView
                        .setLayoutParams(new GridView.LayoutParams(700, 700));

            } else {
                picturesView = (ImageView) convertView;
            }

            Glide.with(context).load(images.get(position))
                    //.placeholder(R.drawable.ic_launcher).centerCrop()
                    .into(picturesView);

            return picturesView;
        }

        /**
         * Getting All Images Path.
         *
         * @param activity
         *            the activity
         * @return ArrayList with images Path
         */
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


           // cursor = activity.getContentResolver().query(uri, projection, null, null, null);


            ///////<< 앨범으로 만들기
    //            cursor = activity.getContentResolver().query(uri, projection, "GROUP BY (BUCKET_DISPLAY_NAME",
    //                    null, null);
    //            while(cursor.moveToNext()){
    //
    //            }
    //                data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
    //                album = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME);
    //
    //                viewholder{
    //                    image view = set(data);
    //                    image view onclik{
    //
    //                    }
    //                    text view = set(album);
    //
    //                }
    //
    //
    //
    //             cursor=   activity.getContentResolver().query(uri, projection, "BUCKET_DISTPLAY_NAME ==" +"'" +album+"'" ,
    //                     null, null);
            //<<---- 2번째 view
            //////<< 앨범으로 만들기


            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            column_index_folder_name = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
            while (cursor.moveToNext()) {

                absolutePathOfImage = cursor.getString(column_index_data);
                listOfAllImages.add(absolutePathOfImage);

            }
            return listOfAllImages;
        }
    }
    }


