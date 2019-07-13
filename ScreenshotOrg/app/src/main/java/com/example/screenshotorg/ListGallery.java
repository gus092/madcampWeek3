//package com.example.screenshotorg;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.SearchView;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class ListGallery extends Activity {
//
//    ArrayAdapter<String> adapter;
//    String tag;
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Intent i = getIntent();
//        tag = i.getStringExtra("path");
//
//
//        setContentView(R.layout.listgallery);
//
//
//        ListView iv = (ListView)findViewById(R.id.ListViewCountry);
//        ArrayList<String> arrayCountry = new ArrayList<>();
//        arrayCountry.addAll(Arrays.asList(getResources().getStringArray(R.array.array_country)));
//
//        adapter = new ArrayAdapter<>(
//                ListGallery.this,
//                android.R.layout.simple_list_item_1,
//                arrayCountry);
//        iv.setAdapter(adapter);
//}
//         //@Override
//        public boolean OnCreateOptionsMenu(Menu menu){
//                MenuInflater inflater = getMenuInflater();
//                inflater.inflate(R.menu.menu,menu);
//                MenuItem item = menu.findItem(R.id.app_search);
//                SearchView searchView = (SearchView)item.getActionView();
//
//                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                    @Override
//                    public boolean onQueryTextSubmit(String s) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onQueryTextChange(String s) {
//                        adapter.getFilter().filter(s);
//                        //하고싶은 동작 적기
//                        return false;
//                    }
//                });
//
//                return super.onCreateOptionsMenu(menu);
//
//
//        }
//
//}
//
//
//
//
//
////
////    String tag;
////    ListView search_tag;
////    ArrayAdapter<String> adapter;
////
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.listgallery);
////
////        Intent i = getIntent();
////        tag = i.getStringExtra("tag");
////
////        search_tag = (ListView) findViewById(R.id.search_tag);
////        ArrayList<String> arrayTag = new ArrayList<>();
////        arrayTag.addAll(Arrays.asList(getResources().getStringArray(R.array.my_tags)));
////
////        adapter = new ArrayAdapter<String>(
////                getBaseContext(),
////                android.R.layout.simple_list_item_1,
////                arrayTag
////        );
////        search_tag.setAdapter(adapter);
////    }
////        @Override
////        public boolean OnCreateOptionsMenu(Menu menu){
////                MenuInflater inflater = getMenuInflater();
////                inflater.inflate(R.menu.searchmenu,menu);
////                MenuItem item = menu.findItem(R.id.search_tag);
////                SearchView searchView = (SearchView)item.getActionView();
////                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
////                    @Override
////                    public boolean onQueryTextSubmit(String s) {
////                        return false;
////                    }
////
////                    @Override
////                    public boolean onQueryTextChange(String s) {
////                        adapter.getFilter().filter(s);
////                        return false;
////                    }
////                });
////
////                return super.onCreateOptionsMenu(menu);
////
////
////
////        }
////}