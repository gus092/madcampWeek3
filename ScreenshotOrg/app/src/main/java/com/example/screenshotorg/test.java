//package com.example.screenshotorg;
//
//import android.app.Activity;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.Nullable;
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProviders;
//
//import com.bumptech.glide.Glide;
//import com.example.screenshotorg.R;
//import com.example.screenshotorg.ui.main.PageViewModel;
//
//import java.util.ArrayList;
//import java.util.Collections;
//
///**
// * A placeholder fragment containing a simple view.
// */
//public class test extends Fragment {
//    private ArrayList<String> images;
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
//
//
//    private class ImageAdapter extends BaseAdapter {
//
//        /** The context. */
//        private Activity context;
//
//        /**
//         * Instantiates a new image adapter.
//         *
//         * @param localContext
//         *            the local context
//         */
//        public ImageAdapter(Activity localContext) {
//            context = localContext;
//            images = getAllShownImagesPath(context);
//        }
//
//        public int getCount() {
//            return images.size();
//        }
//
//        public Object getItem(int position) {
//            return position;
//        }
//
//        public long getItemId(int position) {
//            return position;
//        }
//
//        public View getView(final int position, View convertView,
//                            ViewGroup parent) {
//            ImageView picturesView;
//            if (convertView == null) {
//                picturesView = new ImageView(context);
//                picturesView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                picturesView
//                        .setLayoutParams(new GridView.LayoutParams(270, 270));
//
//            } else {
//                picturesView = (ImageView) convertView;
//            }
//
//            Glide.with(context).load(images.get(position))
//                    .placeholder(R.drawable.ic_launcher_background).centerCrop()
//                    .into(picturesView);
//
//            return picturesView;
//        }
//
//        /**
//         * Getting All Images Path.
//         *
//         * @param activity
//         *            the activity
//         * @return ArrayList with images Path
//         */
//        private ArrayList<String> getAllShownImagesPath(Activity activity) {
//            Uri uri;
//            Cursor cursor;
//            int column_index_data, column_index_folder_name;
//            ArrayList<String> listOfAllImages = new ArrayList<String>();
//            String absolutePathOfImage = null;
//            uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//
//            String[] projection = { MediaStore.MediaColumns.DATA,
//                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
//
//            //FOR ACCESSING THE SCREENSHOTS FOLDER
//            cursor = activity.getContentResolver().query(
//                    MediaStore.Files.getContentUri("external"),
//                    null,
//                    MediaStore.Images.Media.DATA + " like ? ",
//                    new String[] {"%Screenshots%"},
//                    null
//            );
//            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//            column_index_folder_name = cursor
//                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
//            while (cursor.moveToNext()) {
//                absolutePathOfImage = cursor.getString(column_index_data);
//
//                listOfAllImages.add(absolutePathOfImage);
//            }
//
//            Collections.reverse(listOfAllImages);
//            return listOfAllImages;
//        }
//    }
//}