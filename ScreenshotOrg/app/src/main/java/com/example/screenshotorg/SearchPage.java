package com.example.screenshotorg;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.screenshotorg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchPage extends AppCompatActivity {
    private List<String> items = new ArrayList<String>(); //Arrays.asList("어벤져스","배트맨" ,"배트맨2","배구","슈퍼맨","개미","달팽이","새우");
    private int wannabe = 0;
    private String goalPath = "";
    private ArrayList<String> patharray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchpage);

        final SearchView searchView = findViewById(R.id.searchview);
        final TextView resultTextView = findViewById(R.id.textView);
        resultTextView.setText(getResult());

        items = new ArrayList<String>();
        patharray = new ArrayList<String>();

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        try {
            JSONObject json = new JSONObject((dbHandler.findHandler(0).getStudentName()));
            JSONArray totaljson = json.getJSONArray("total");
            for (int i = 0; i < totaljson.length(); i ++){
                JSONObject currobj = totaljson.getJSONObject(i);
                items.add(currobj.get("text").toString());
                patharray.add(currobj.get("path").toString());

            }

        } catch (JSONException e){
            e.printStackTrace();
        }



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false; }

            @Override
            public boolean onQueryTextChange(String s) {
                resultTextView.setText(search(s));
                return true;
            }
        });
    }
    private String search (String query){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<items.size();i++){
            String item = items.get(i);
            if(item.toLowerCase().contains(query.toLowerCase())){
                sb.append(shorten(item));
                goalPath = patharray.get(i);
                if(i != items.size()-1){
                    sb.append("\n");
                 }
            }
        }
        return sb.toString();
    }

    private String shorten(String orig){
        int len = orig.length();
        if (len < 40){
            return orig;
        }

        if (len <120 ){
            return orig.substring((len - 40) /2, (len-40)/2 +39);
        }
        return orig.substring (len / 3, len/3 + 40);

    }

    private String getResult(){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<items.size();i++){
            String item = items.get(i);
            if(i ==items.size()-1){
                sb.append(shorten(item));
            }else{
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public void OnClick(View view) {
        Toast.makeText(this, "Path: " + goalPath, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(view.getContext(), FullImageActivity.class);
        intent.putExtra("fullsize",goalPath);
        view.getContext().startActivity(intent);

        System.out.println("OOOOOOOOOOOOOOOCCCCCCCCCCCCCCCLLLICKKKKKKKKKKK");
    }
}


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
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.bumptech.glide.Glide;
//
//import java.io.File;
//import java.lang.reflect.Array;
//import java.util.ArrayList;
//
//public class SearchPage extends AppCompatActivity {
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.searchpage);
//
//        Intent i = getIntent();
//
//        String tag = i.getStringExtra("search");
//
//        ListView list = (ListView)findViewById(R.id.theList);
//        EditText theFilter = (EditText)findViewById(R.id.searchFilter);
//
//        ArrayList<String> names = new ArrayList<>();
//        names.add("Mitch");
//        names.add("Black");
//        names.add("Shelly");
//        names.add("steve");
//        names.add("Jess");
//        names.add("Moolly");
//        names.add("Stich");
//        names.add("LALAL");
//        names.add("KKKKK");
//        names.add("OHOH");
//        names.add("Happy");
//
//        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item_layout,names);
//
//        theFilter.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//
//
//        View.OnClickListener closebtn_listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        };
//        Button closeLogin = (Button) findViewById(R.id.searchClose);
//        closeLogin.setOnClickListener(closebtn_listener);
//    }
//
//
//
//}

