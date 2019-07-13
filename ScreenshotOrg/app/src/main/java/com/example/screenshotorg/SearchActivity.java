package com.example.screenshotorg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity {

    RecyclerView listshowrcy;
    List<Item> productlists = new ArrayList<>();
    public androidx.appcompat.widget.SearchView searchView;
    Fragment3Adapter adapter3;
     public static ArrayList<String> images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchactivity);
        Intent intent2 = getIntent();

        String search = intent2.getStringExtra("search");


        productlists.add(new Item("1111 dog one",R.drawable.dog1));
        productlists.add(new Item("222 dog two",R.drawable.dog2));
        productlists.add(new Item("3333 dog three",R.drawable.dog3));
        productlists.add(new Item("4444 dog four",R.drawable.dog4));
        productlists.add(new Item("5555 doug five",R.drawable.dog5));
        productlists.add(new Item("six",R.drawable.dog6));
        productlists.add(new Item("seven",R.drawable.dog7));
        productlists.add(new Item("eight",R.drawable.dog8));
        productlists.add(new Item("nine dog 9999 ",R.drawable.dog9));



        listshowrcy =(RecyclerView)findViewById(R.id.listshow);
        listshowrcy.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        listshowrcy.setLayoutManager(linearLayoutManager);
        adapter3 = new Fragment3Adapter(productlists,this);
        listshowrcy.setAdapter(adapter3);

    }
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
