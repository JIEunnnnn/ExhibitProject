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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


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

        //get data from details activity
        /*String[] nownum = ((DetailsActivity)getActivity()).fs; // 관내인원
        String[] size = ((DetailsActivity)getActivity()).ss; // 관내크기*/
        String[] nownum = {"10","3","6","9"};
        String[] size = {"12","300","60","900"};


        int mapNum = ((DetailsActivity)getActivity()).mapNum;
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int galNum = 0;

        List<String> route = new ArrayList<String>();
        List<String> A = new ArrayList<String>();
        List<String> B = new ArrayList<String>();
        List<String> C = new ArrayList<String>();
        float[][] pointer;

        ImageView mapView = view.findViewById(R.id.mapView);
        TextView tRoute = view.findViewById(R.id.t_route);
        BitmapDrawable bd = null;
        if(mapNum==1){
            bd = (BitmapDrawable) getResources().getDrawable(R.drawable.map3,null);
            height=width;
            float[][] pointer1 = {{width*0.76f,height*0.27f},{width*0.3f, height*0.27f},{width*0.915f, height*0.8f}};
            pointer = pointer1;
            galNum=3;
        } else{
            bd = (BitmapDrawable) getResources().getDrawable(R.drawable.map2,null);
            float[][] pointer0 = {{width*0.43f,height*0.52f},{width*0.8f, height*0.283f},{width*0.6f, height*0.0915f},{width*0.147f, height*0.332f}};
            pointer = pointer0;
            galNum=4;
        }
        Bitmap bitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        String[] galleries = new String[galNum];
        Bitmap bit = Bitmap.createScaledBitmap(bd.getBitmap(), width, height, true);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(bit,0,0,null);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        paint.setColor(Color.YELLOW);

        for(int i=0; i<pointer.length; i++){
            //혼잡도 계산
            double rate = Double.parseDouble(nownum[i])/Double.parseDouble(size[i])*100;
            Log.d("TAG",i+"관: "+rate+"   "+nownum[i]);
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
            case "C":
                paint.setColor(Color.RED);
                break;
            default:
                paint.setColor(Color.YELLOW);
                break;
        }
            paint.setAlpha(150);
            canvas.drawCircle(pointer[i][0], pointer[i][1], 80, paint);
            galleries[i] = stat;
        }
        mapView.setImageBitmap(bitmap);

        for(int i=0;i<galleries.length;i++){
            if(galleries[i].equals("A")){
                A.add((i+1)+"관"+galleries[i]);
            }else if(galleries[i].equals("B")){
                B.add((i+1)+"관"+galleries[i]);
            }else{
                C.add((i+1)+"관"+galleries[i]);
            }
        }//A,B,C 분류

        for(int i=0;i<A.size();i++){
            route.add(A.get(i).substring(0,2));
        }

        for(int i=0; i<B.size();i++){
            route.add(B.get(i).substring(0,2));
        }

        for(int i=0; i<C.size();i++){
            route.add(C.get(i).substring(0,2));
        }

        tRoute.setText("추천 경로: "+route.toString());

        return view;
    }

    public void mapRender(float[][] pointer, String mapNum){

    }
}