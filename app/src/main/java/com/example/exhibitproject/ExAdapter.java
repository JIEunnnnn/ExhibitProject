package com.example.exhibitproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ExAdapter extends RecyclerView.Adapter<ExAdapter.ViewHolder> {

    static private List<ExItem> mExTempArray;

    public ExAdapter(List<ExItem> ExList){
        mExTempArray = ExList;
    }

    OnItemClickListener listener;
    public static interface OnItemClickListener{
        public void onItemClick(ViewHolder holder, View view, int position);
    }

    static public ExItem getItem(int position){
        return mExTempArray.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tTime.setText("예상 시간: "+mExTempArray.get(position).getTime());
        holder.tNum.setText("예상 인원: "+mExTempArray.get(position).getNum());
        holder.rImage.setImageBitmap(mExTempArray.get(position).getImage());
        holder.setOnItemClickListener(listener);
    }



    public int getItemCount(){
        return mExTempArray.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tName, tTime ,tNum;
        public RoundedImageView rImage;
        public CardView cv;

        OnItemClickListener listener;

        public ViewHolder(final View itemView){
            super(itemView);
            tName = (TextView) itemView.findViewById(R.id.tView_name);
            tTime = (TextView) itemView.findViewById(R.id.tView_time);
            tNum = (TextView) itemView.findViewById(R.id.tView_num);
            rImage = (RoundedImageView) itemView.findViewById(R.id.ex_image);
            cv = (CardView) itemView.findViewById(R.id.ex_card);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!=null){
                        listener.onItemClick(ViewHolder.this, itemView, position);
                    }
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
    }
}

