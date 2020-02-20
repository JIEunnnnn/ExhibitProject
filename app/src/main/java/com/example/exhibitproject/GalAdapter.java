package com.example.exhibitproject;

import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

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
        //holder.iGal.setImageResource(mGalTempArray.get(position).getImage());//TODO:SET IMAGE RESOURCE
        holder.tTitle.setText(mGalTempArray.get(position).getTitle());
        holder.tDescription.setText(mGalTempArray.get(position).getDescription());
        holder.setOnItemClickListener(listener);
    }



    public int getItemCount(){
        return mGalTempArray.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tTitle, tDescription;
        public CardView cv;

        OnItemClickListener listener;

        public ViewHolder(final View itemView){
            super(itemView);
            tTitle = (TextView) itemView.findViewById(R.id.tView_gallery_title);
            tDescription = (TextView) itemView.findViewById(R.id.tView_gallery_description);
            cv = (CardView) itemView.findViewById(R.id.gal_card);
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
    }
}

