package com.example.screenshotorg;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView countryName;
    public ImageView countryPhoto;

    public RecyclerViewHolders(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        countryName = (TextView)itemView.findViewById(R.id.country_name);
        countryPhoto = (ImageView)itemView.findViewById(R.id.country_photo);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Country Position = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
        Log.e("PPPPOS", "Layoutpos: " + getLayoutPosition()+ " getAdapterPosition: " + getAdapterPosition());

        Intent intent = new Intent(view.getContext(), SearchActivity.class);
        intent.putExtra("dirname", getLayoutPosition());
        view.getContext().startActivity(intent);

    }
}