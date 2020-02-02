package com.example.exhibitproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExAdapter mExAdapter;
    private List<ExItem> mExArray;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView exList = findViewById(R.id.ex_list);
        mExArray = new ArrayList<ExItem>();

        for(int i=0;i<5;i++){
            ExItem item = new ExItem();
            item.setNum(0);
            item.setTime(0);
            item.setName("Exhibition "+(i+1));
            //item.setImage();

            layoutManager = new LinearLayoutManager(MainActivity.this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            exList.setLayoutManager(layoutManager);
            mExArray.add(item);
        }

        mExAdapter = new ExAdapter(mExArray);

        exList.setAdapter(mExAdapter);
        exList.setItemAnimator(new DefaultItemAnimator());
        mExAdapter.setOnItemClickListener(new ExAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(ExAdapter.ViewHolder holder, View view, int position) {
                Toast.makeText(getApplicationContext(),"position = "+position,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                intent.putExtra("NAME", ExAdapter.getItem(position).getName());
                startActivity(intent);
            }
        });
    }
}
