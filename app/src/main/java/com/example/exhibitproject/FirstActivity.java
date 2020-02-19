package com.example.exhibitproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class FirstActivity extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static FirstActivity newInstance(int page, String title) {
        FirstActivity fragment = new FirstActivity();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first, container, false);
        /*EditText tvLabel = (EditText) view.findViewById(R.id.editText);
        tvLabel.setText(page + " -- " + title);*/

        //get data from details activity
        String firstex = ((DetailsActivity)getActivity()).firstex;
        String secondex = ((DetailsActivity)getActivity()).secondex;
        String thirdex = ((DetailsActivity)getActivity()).thirdex;
        String fourthex = ((DetailsActivity)getActivity()).fourthex;
        int nownum = ((DetailsActivity)getActivity()).nownum;
        int size = ((DetailsActivity)getActivity()).size;
        int mapNum = ((DetailsActivity)getActivity()).mapNum;
        int[][] pointer = {{265, 500}, {476,268}, {80, 310}, {355, 85}};
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        float[][] pointer2 = {{width*0.43f,height*0.52f},{width*0.8f, height*0.283f},{width*0.147f, height*0.332f},{width*0.6f, height*0.092f}};
        ImageView mapView = view.findViewById(R.id.mapView);
        Bitmap bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        BitmapDrawable bd = null;
        bd = (BitmapDrawable) getResources().getDrawable(R.drawable.map4,null);
        Bitmap bit = Bitmap.createScaledBitmap(bd.getBitmap(), width, height, true);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(bit,0,0,null);
        Paint paint = new Paint();
//first~fourth: 현재 사람수, 관 크기
        paint.setAntiAlias(true);

        paint.setColor(Color.YELLOW);

        for(int i=0; i<pointer.length; i++){
            //혼잡도 계산
            /*double rate = nownum[i]/size[i];
        String stat = "";
        if(rate==0 || (rate>0)&&rate<30)
            stat = "A";
        else if(rate==30 ||(rate>30&&rate<60))
            stat = "B";
        else
            stat = "C";

        switch (stat){
            case "A" :
            paint.setColor(Color.GREEN);
                break;
            case "B" :
            paint.setColor(Color.YELLOW);
                break;
            case "C":
            paint.setColor(Color.RED);
                break;
            default:
            paint.setColor(Color.YELLOW);
                break;
        }*/
            paint.setAlpha(150);
            canvas.drawCircle(pointer2[i][0], pointer2[i][1], 80, paint);
        }
        mapView.setImageBitmap(bitmap);

        return view;
    }
}