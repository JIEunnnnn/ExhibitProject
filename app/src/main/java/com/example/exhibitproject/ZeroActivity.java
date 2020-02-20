package com.example.exhibitproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;


public class ZeroActivity extends Fragment {
    private String title;
    private int page;

    public static ZeroActivity newInstance(int page, String title) {
        ZeroActivity fragment = new ZeroActivity();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

    }

    TextView gTitle, g_t_d, g_d_d, g_d_d1, g_des_d;
    ImageView gView;
    InputStream exhibit1;
    String imageNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.zero, container, false);

        String title = ((DetailsActivity)getActivity()).title;
        String data = ((DetailsActivity)getActivity()).data;
        String[] guide = ((DetailsActivity)getActivity()).gs;
        String detailData = ((DetailsActivity)getActivity()).detaildata;
        imageNum = ((DetailsActivity)getActivity()).imageNum;

        gTitle = view.findViewById(R.id.gTitle);
        g_t_d = view.findViewById(R.id.g_t_d);
        g_d_d = view.findViewById(R.id.g_d_d);
        g_d_d1 = view.findViewById(R.id.g_d_d1);
        g_des_d = view.findViewById(R.id.g_des_d);
        gView = view.findViewById(R.id.gView);

        try {
            exhibit1 = getResources().getAssets().open(imageNum + "-1.jpg");
            Bitmap bm = BitmapFactory.decodeStream(exhibit1);
            gView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            gView.setImageBitmap(bm);
        } catch (Exception e) {
            e.printStackTrace();
        }

        gTitle.setText(title);
        g_t_d.setText(data);
        g_d_d.setText(guide[0]);
        g_d_d1.setText(guide[1]);
        g_des_d.setText(detailData);

        return view;
    }
}