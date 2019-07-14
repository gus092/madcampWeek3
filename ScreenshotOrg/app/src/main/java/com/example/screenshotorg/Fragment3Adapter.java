package com.example.screenshotorg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Fragment3Adapter extends RecyclerView.Adapter<Fragment3Adapter.Holderview>{

    private List<Item> productlist;
    private Context context;


    public Fragment3Adapter(List<Item>productlist,Context context){
        this.productlist = productlist;
        this.context = context;
    }
    @Override
    public Holderview onCreateViewHolder(ViewGroup parent, int viewType){
        View layout= LayoutInflater.from(parent.getContext()).inflate(R.layout.customitem,parent,false);
        return new Holderview(layout);
    }



    @Override
    public void onBindViewHolder(@NonNull Holderview holder, final int position) {
        holder.v_name.setText(productlist.get(position).getName());
        //holder.v_image.setImageResource(productlist.get(position).getPhoto());
        holder.v_image.setImageBitmap(productlist.get(position).getPhoto());



        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Toast.makeText(context,"click on"+productlist.get(position).getName(),Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(view.getContext(), SearchActivity.class);
//                intent.putExtra("dirname",productlist.get(position).getName());
//                view.getContext().startActivity(intent);


            }


        });

    }

    @Override
    public int getItemCount() {
        return productlist.size();
    }
    public void setfilter(List<Item> listitem){
        productlist = new ArrayList<>();
        productlist.addAll(listitem);
        notifyDataSetChanged();

    }
    class Holderview extends RecyclerView.ViewHolder
    {
        ImageView v_image;
        TextView v_name;

        Holderview(View itemview)
        {
            super(itemview);
            v_image=(ImageView)itemview.findViewById(R.id.product_image);
            v_name=(TextView) itemview.findViewById(R.id.product_title);
        }
    }



}
