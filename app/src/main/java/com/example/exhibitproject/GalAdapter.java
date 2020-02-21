package com.example.exhibitproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.List;

public class GalAdapter extends RecyclerView.Adapter<GalAdapter.ViewHolder> {

    static private List<GalItem> mGalTempArray;

    public GalAdapter(List<GalItem> GalList){
        mGalTempArray = GalList;
    }

    OnItemClickListener listener;
    public static interface OnItemClickListener{
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    static public GalItem getItem(int position){
        return mGalTempArray.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview2,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String[] title = mGalTempArray.get(position).getTitle().split("!");
        holder.tTitle.setText(title[1]);
        holder.tSection.setText(title[0]);
        holder.tEngTitle.setText(title[2]);
        holder.tDescription.setText(mGalTempArray.get(position).getDescription());
        holder.iGallery.setImageBitmap(mGalTempArray.get(position).getPicture());

        holder.setOnItemClickListener(listener);
    }



    public int getItemCount(){
        return mGalTempArray.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tTitle, tSection, tEngTitle, tDescription;
        public ImageView iGallery;
        public CardView cv;

        OnItemClickListener listener;

        public ViewHolder(final View itemView){
            super(itemView);
            tTitle = (TextView) itemView.findViewById(R.id.g_title);
            tSection = (TextView) itemView.findViewById(R.id.g_section);
            tEngTitle = (TextView) itemView.findViewById(R.id.g_eng_title);
            tDescription = (TextView) itemView.findViewById(R.id.g_description);
            iGallery = (ImageView) itemView.findViewById(R.id.g_image);
            cv = (CardView) itemView.findViewById(R.id.gal_card);
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
    }
}

