package com.example.exhibitproject;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    String[] name, detail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Context contextTheme = new ContextThemeWrapper(getActivity(),R.style.AppTheme);
        LayoutInflater localInflater = getActivity().getLayoutInflater().cloneInContext(contextTheme);

        View view = localInflater.inflate(R.layout.second, container, false);
        RecyclerView rDetail = view.findViewById(R.id.rDetail);
        mGalArray = new ArrayList<GalItem>();

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rDetail.setLayoutManager(layoutManager);

        name = ((DetailsActivity)getActivity()).ts;
        detail = ((DetailsActivity)getActivity()).fs2;



        //------repeat------
        for(int i=0; i< name.length;i++)
        {
            GalItem item = new GalItem();
            item.setTitle(name[i]);
            item.setDescription(detail[i]);
            mGalArray.add(item);
        }
        //------------------

        mGalAdapter = new GalAdapter(mGalArray);
        rDetail.setAdapter(mGalAdapter);
        rDetail.setItemAnimator(new DefaultItemAnimator());


        return view;
    }
}