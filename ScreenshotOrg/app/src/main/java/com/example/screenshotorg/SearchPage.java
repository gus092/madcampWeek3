//검색어 구현

//package com.example.screenshotorg;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.SearchView;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.bumptech.glide.Glide;
//
//import org.w3c.dom.Text;
//
//import java.io.File;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class SearchPage extends AppCompatActivity {
//    private List<String> items = Arrays.asList("어벤져스","베트맨" ,"배트맨2","배구","슈퍼맨");
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.searchpage);
//
//        final SearchView searchView = findViewById(R.id.searchview);
//        final TextView resultTextView = findViewById(R.id.textView);
//        resultTextView.setText(getResult());
//
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
//        @Override
//        public boolean onQueryTextSubmit(String s) {
//            return false; }
//
//        @Override
//        public boolean onQueryTextChange(String s) {
//            resultTextView.setText(search(s));
//            return true;
//        }
//    });
//    }
//    private String search (String query){
//        StringBuilder sb = new StringBuilder();
//        for(int i=0;i<items.size();i++){
//            String item = items.get(i);
//            if(item.toLowerCase().contains(query.toLowerCase())){
//                sb.append(item);
//                if(i != items.size()-1){
//                    sb.append("\n");
//            }
//            }
//        }
//        return sb.toString();
//    }
//
//    private String getResult(){
//        StringBuilder sb = new StringBuilder();
//        for(int i=0;i<items.size();i++){
//            String item = items.get(i);
//            if(i ==items.size()-1){
//                sb.append(item);
//            }else{
//                sb.append("\n");
//            }
//        }
//        return sb.toString();
//    }
//
////    public void OnClick(View view) {
////    System.out.println("OOOOOOOOOOOOOOOCCCCCCCCCCCCCCCLLLICKKKKKKKKKKK");
////    }
//}
//
//
////package com.example.screenshotorg;
////
////import android.content.Intent;
////import android.os.Bundle;
////import android.text.Editable;
////import android.text.TextWatcher;
////import android.util.Log;
////import android.view.View;
////import android.widget.ArrayAdapter;
////import android.widget.Button;
////import android.widget.EditText;
////import android.widget.ImageView;
////import android.widget.ListView;
////
////import androidx.appcompat.app.AppCompatActivity;
////
////import com.bumptech.glide.Glide;
////
////import java.io.File;
////import java.lang.reflect.Array;
////import java.util.ArrayList;
////
////public class SearchPage extends AppCompatActivity {
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.searchpage);
////
////        Intent i = getIntent();
////
////        String tag = i.getStringExtra("search");
////
////        ListView list = (ListView)findViewById(R.id.theList);
////        EditText theFilter = (EditText)findViewById(R.id.searchFilter);
////
////        ArrayList<String> names = new ArrayList<>();
////        names.add("Mitch");
////        names.add("Black");
////        names.add("Shelly");
////        names.add("steve");
////        names.add("Jess");
////        names.add("Moolly");
////        names.add("Stich");
////        names.add("LALAL");
////        names.add("KKKKK");
////        names.add("OHOH");
////        names.add("Happy");
////
////        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item_layout,names);
////
////        theFilter.addTextChangedListener(new TextWatcher() {
////            @Override
////            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////
////            }
////
////            @Override
////            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
////
////            }
////
////            @Override
////            public void afterTextChanged(Editable editable) {
////
////            }
////        });
////
////
////
////        View.OnClickListener closebtn_listener = new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                finish();
////            }
////        };
////        Button closeLogin = (Button) findViewById(R.id.searchClose);
////        closeLogin.setOnClickListener(closebtn_listener);
////    }
////
////
////
////}

//<?xml version="1.0" encoding="utf-8"?>
//<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
//        xmlns:app="http://schemas.android.com/apk/res-auto"
//        xmlns:tools="http://schemas.android.com/tools"
//        android:layout_width="match_parent"
//        android:layout_height="match_parent"
//        tools:context=".MainActivity">
//
//<TextView
//        android:id="@+id/textView"
//                android:layout_width="415dp"
//                android:layout_height="155dp"
//                android:layout_marginTop="60dp"
//                android:layout_weight="1"
//                android:onClick="OnClick"
//                android:text="TextView"
//                app:layout_constraintEnd_toEndOf="parent"
//                app:layout_constraintHorizontal_bias="0.0"
//                app:layout_constraintStart_toStartOf="parent"
//                app:layout_constraintTop_toTopOf="parent" />
//
//<SearchView
//        android:id="@+id/searchview"
//                android:layout_width="match_parent"
//                android:layout_height="46dp"
//                android:layout_marginStart="8dp"
//                android:layout_marginTop="16dp"
//                android:layout_marginEnd="8dp"
//                android:layout_marginBottom="8dp"
//                app:layout_constraintBottom_toTopOf="@+id/textView"
//                app:layout_constraintEnd_toEndOf="parent"
//                app:layout_constraintHorizontal_bias="0.0"
//                app:layout_constraintStart_toStartOf="parent"
//                app:layout_constraintTop_toTopOf="parent"
//                tools:text="어벤져스\n배트맨\n배트맨2">
//
//</SearchView>
//</androidx.constraintlayout.widget.ConstraintLayout>