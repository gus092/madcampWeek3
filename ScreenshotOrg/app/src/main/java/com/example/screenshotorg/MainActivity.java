package com.example.screenshotorg;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.screenshotorg.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public MenuItem searchItem;

   // public SearchView searchView;
    ArrayAdapter<String> adapter;
    String tag;
//    ArrayList<String> dInfoArrayList = new ArrayList<>();//정보가 들어있는 리스트
//    ArrayList<String> foodInfoArrayList = new ArrayList<>(); //지금 검색되는 list


    String[] permission_list = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton searchLogin = (FloatingActionButton)findViewById(R.id.fab);
        searchLogin.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                tag="this is a test";
                Intent intent3 = new Intent(getApplicationContext(),SearchPage.class);
                intent3.putExtra("search",tag);
                startActivity(intent3);
            }
        });



//        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED) {
//            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
//
//            } else {
//                ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.READ_CONTACTS}, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
//            }
//        } else {
//
//        }
//
//        try {
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
//            } else {
//                ActivityCompat.requestPermissions(MainActivity.this,
//                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
////                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
////                startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        checkPermission();
    }


    public void initialize(){
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getBaseContext(), getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }

    public void checkPermission(){
        //현재 안드로이드 버전이 6.0미만이면 메서드를 종료한다.
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return;
        List<String> PermissionList = new ArrayList<>();

        for(String permission : permission_list){
            //권한 허용 여부를 확인한다.
            int chk = checkCallingOrSelfPermission(permission);

            if(chk == PackageManager.PERMISSION_DENIED){
                //권한 허용을여부를 확인하는 창을 띄운다
                PermissionList.add(permission);
            }
        }
        if(!PermissionList.isEmpty()){
            requestPermissions( PermissionList.toArray(new String[PermissionList.size()]),0);
        }
        else{
            initialize();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==0)
        {
            if(grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    //허용됬다면
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {

                    } else {
                        Toast.makeText(getApplicationContext(), "앱권한설정하세요", Toast.LENGTH_LONG).show();
                        finish();
                        return;
                    }
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "앱권한설정하세요", Toast.LENGTH_LONG).show();
                finish();
                return;
            }

            initialize();

        }
    }


//    public void onClick(View view) {
//        Intent i = new Intent(this.getBaseContext(),SearchView.class);
//                            i.putExtra("search", "100");
//                            startActivity(i);
//
//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//
//        getMenuInflater().inflate(R.menu.menu,menu);
//        final MenuItem myActionMenuItem=menu.findItem(R.id.app_search);
//        searchView=(SearchView)myActionMenuItem.getActionView();
//        ((EditText)searchView.findViewById(androidx.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.colorAccent));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if(!searchView.isIconified()){
//                    searchView.setIconified(true);
//                }
//                myActionMenuItem.collapseActionView();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
//        return true;
//    }
//    private List<Item> filter(List<Item> pl,String query){
//        query=query.toLowerCase();
//        final List<Item> filteredModeList= new ArrayList<>();
//        for (Item model:pl){
//            final String text = model.getName().toLowerCase();
//            if(text.startsWith(query)){
//                filteredModeList.add(model);
//            }
//        }
//        return filteredModeList;
//    }
}