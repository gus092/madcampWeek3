//package com.example.screenshotorg;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.Menu;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class Fragment3 extends Fragment {
//    RecyclerView listshowrcy;
//    List<Item> productlists = new ArrayList<>();
//
//    Fragment3Adapter adapter3;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    public static ArrayList<String> images;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        View view = inflater.inflate(R.layout.searchactivity, container, false);
//        productlists.add(new Item("1111 dog one",R.drawable.dog1));
//        productlists.add(new Item("222 dog two",R.drawable.dog2));
//        productlists.add(new Item("3333 dog three",R.drawable.dog3));
//        productlists.add(new Item("4444 dog four",R.drawable.dog4));
//        productlists.add(new Item("5555 doug five",R.drawable.dog5));
//        productlists.add(new Item("six",R.drawable.dog6));
//        productlists.add(new Item("seven",R.drawable.dog7));
//        productlists.add(new Item("eight",R.drawable.dog8));
//        productlists.add(new Item("nine dog 9999 ",R.drawable.dog9));
//
//
//
//        listshowrcy=(RecyclerView)getActivity().findViewById(R.id.listshow);
//        listshowrcy.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireContext());
//        listshowrcy.setLayoutManager(linearLayoutManager);
//        adapter3 = new Fragment3Adapter(productlists,requireContext());
//        listshowrcy.setAdapter(adapter3);
//
//        return view;
//    }
//
//
//}
