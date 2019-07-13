//package com.example.screenshotorg;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.GridView;
//import android.widget.ImageView;
//import android.widget.Toast;
//
//import androidx.annotation.RequiresApi;
//
//import com.bumptech.glide.Glide;
//
//import java.util.ArrayList;
//
//public class TagGallery extends Activity {
//    public static ArrayList<String> images;
//    String tag;
//    public GridView gallery;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Intent i = getIntent();
//        tag = i.getStringExtra("tag");
//
//
//
//        gallery = (GridView)findViewById(R.id.tagGridView);
//
//        gallery.setAdapter(new ImageAdapter2(this));
//
//        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onItemClick(AdapterView<?> arg0, final View arg1,
//                                    final int position, long arg3) {
////                positionGlobal = position;
//                if (null != images && !images.isEmpty()) { // 해당 그리드를 선택하면
//                    Intent i = new Intent(getBaseContext(), FullImageActivity.class);  // getbasecontext??
//                    i.putExtra("path", images.get(position));
//                    startActivity(i);
//
//                }
//
//
//            }
//
//        });
//    }
//
//
//    /**
//     * The Class ImageAdapter.
//     */
//    public static class ImageAdapter2 extends BaseAdapter {
//
//        /** The context. */
//        public static Context context;
//
////       public static List photoRemove = new ArrayList();
//
//        /**
//         * Instantiates a new image adapter.
//         *
//         * @param localContext
//         *            the local context
//         */
//        public ImageAdapter2(Context localContext) {
//            context = localContext;
//            images = getAllShownImagesPath(context);
//        }
//
//        public ImageAdapter2(Context localContext,int i) {
//            context = localContext;
//            images = getAllShownImagesPath(context);
//
//
//        }
//
//
//        public int getCount() {
//            return images.size();
//        }
//
//
//        public String getImage (int position) {
//            return images.get(position);
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
//                        .setLayoutParams(new GridView.LayoutParams(700, 700));
//
//            } else {
//                picturesView = (ImageView) convertView;
//            }
//
//            Glide.with(context).load(images.get(position))
//                    //.placeholder(R.drawable.ic_launcher).centerCrop()
//                    .into(picturesView);
//
//            return picturesView;
//        }
//
//
//
//        /**
//         * Getting All Images Path.
//         *
//         * @param activity
//         *            the activity
//         * @return ArrayList with images Path
//         */
//        private ArrayList<String> getAllShownImagesPath(Context activity) {
//            Uri uri;
//            Cursor cursor;
//            int data,album;
//
//
//            int column_index_data, column_index_folder_name;
//            ArrayList<String> listOfAllImages = new ArrayList<String>();
//            String absolutePathOfImage = null;
//            uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//
//            String[] projection = { MediaStore.MediaColumns.DATA,
//                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME };
//
//            cursor = activity.getContentResolver().query(uri, projection, null, null, null);
//
////            /////<< 앨범으로 만들기
////                        cursor = activity.getContentResolver().query(uri, projection, "GROUP BY (BUCKET_DISPLAY_NAME",
////                                null, null);
////                        while(cursor.moveToNext()){
////
////                        }
////                            data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
////                            album = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.BUCKET_DISPLAY_NAME);
////
////                            gallery
////
////                            viewholder{
////                                image view = set(data);
////                                image view onclik{
////
////                                }
////                                text view = set(album);
////
////                            }
////
////
////
////                         cursor=   activity.getContentResolver().query(uri, projection, "BUCKET_DISTPLAY_NAME ==" +"'" +album+"'" ,
////                                 null, null);
////            <<---- 2번째 view
////            ////<< 앨범으로 만들기
//
//
//
//
//
//            column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
//            column_index_folder_name = cursor
//                    .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
//            while (cursor.moveToNext()) {
//
//                absolutePathOfImage = cursor.getString(column_index_data);
//                listOfAllImages.add(absolutePathOfImage);
//
//            }
//            return listOfAllImages;
//        }
//    }
//
//
//}
