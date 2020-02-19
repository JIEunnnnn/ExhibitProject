package com.example.exhibitproject;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second, container, false);
        /*EditText tvLabel = (EditText) view.findViewById(R.id.editText);
        tvLabel.setText(page + " -- " + title);*/
        RecyclerView rDetail = view.findViewById(R.id.rDetail);
        mGalArray = new ArrayList<GalItem>();

        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rDetail.setLayoutManager(layoutManager);

        String name = ((DetailsActivity)getActivity()).name;
        String detaildata = ((DetailsActivity)getActivity()).detaildata;
        //TODO:베열로 받아와야 할것 같은데..?


        //------repeat------

        GalItem item = new GalItem();
        item.setTitle(name);
        item.setDescription(detaildata);
        mGalArray.add(item);

        //------------------

        mGalAdapter = new GalAdapter(mGalArray);
        rDetail.setAdapter(mGalAdapter);
        rDetail.setItemAnimator(new DefaultItemAnimator());


        return view;
    }
}