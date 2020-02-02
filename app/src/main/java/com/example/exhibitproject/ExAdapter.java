package com.example.exhibitproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

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
        holder.tName.setText(mExTempArray.get(position).getName());
        holder.tTime.setText("예상 시간: "+mExTempArray.get(position).getTime());
        holder.tNum.setText("예상 인원: "+mExTempArray.get(position).getNum());
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
        public CardView cv;

        OnItemClickListener listener;

        public ViewHolder(final View itemView){
            super(itemView);
            tName = (TextView) itemView.findViewById(R.id.tView_name);
            tTime = (TextView) itemView.findViewById(R.id.tView_time);
            tNum = (TextView) itemView.findViewById(R.id.tView_num);
            cv = (CardView) itemView.findViewById(R.id.ex_card);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!=null){
                        listener.onItemClick(ViewHolder.this, itemView, position);
                    }
                    //TODO:open details page
                }
            });
        }

        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
    }
}

