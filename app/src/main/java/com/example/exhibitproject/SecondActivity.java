package com.example.exhibitproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class SecondActivity extends Fragment {
    private String title;
    private int page;
    private GalAdapter mGalAdapter;
    private List<GalItem> mGalArray;
    private LinearLayoutManager layoutManager;

    public static SecondActivity newInstance(int page, String title) {
        SecondActivity fragment = new SecondActivity();
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

    String[] name, name1, detail, pic;
    InputStream exhibit1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*Context contextTheme = new ContextThemeWrapper(getActivity(),R.style.AppTheme);
        LayoutInflater localInflater = getActivity().getLayoutInflater().cloneInContext(contextTheme);*/

        View view = inflater.inflate(R.layout.second, container, false);
        RecyclerView rDetail = view.findViewById(R.id.rDetail);
        mGalArray = new ArrayList<GalItem>();

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rDetail.setLayoutManager(layoutManager);

        name = ((DetailsActivity)getActivity()).ts;
        detail = ((DetailsActivity)getActivity()).fs2;
        pic = ((DetailsActivity)getActivity()).ps;



        //------repeat------
        for(int i=0; i< name.length;i++)
        {

            name1 = name[i].split("/");
            GalItem item = new GalItem();
            item.setTitle(name1);
            item.setDescription(detail[i]);
            if(pic[i]!=null) {
                try {
                    exhibit1 = getResources().getAssets().open(pic[i] + ".png");
                    Bitmap bm = BitmapFactory.decodeStream(exhibit1);
                    item.setPicture(bm);

                } catch (IOException e) {

                }
            }

            mGalArray.add(item);
        }
        //------------------

        mGalAdapter = new GalAdapter(mGalArray);
        rDetail.setAdapter(mGalAdapter);
        rDetail.setItemAnimator(new DefaultItemAnimator());


        return view;
    }
}
