package com.example.exhibitproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ExAdapter mExAdapter;
    private List<ExItem> mExArray;
    private LinearLayoutManager layoutManager;

    public static final String url = "http://192.168.0.2:3000/";
    // 192.168.0.2

    static RecyclerView exList;
    private static final String TAG = "OKHTTP 테스트";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    String result;
    String name, data, detaildata; // 전시회이름, 전시회설명, 전시회내 관내용
    int expecnum, expectime; // 예상인원, 예상시간

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        final HttpConnection connectServ = new HttpConnection();

        exList = findViewById(R.id.ex_list);
        mExArray = new ArrayList<ExItem>();

        connectServ.requestGet(url);
        // connectServ.jsonGet(url);

        layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        exList.setLayoutManager(layoutManager);

        /*for(int i=0;i<5;i++) {
            ExItem item = new ExItem();
            item.setNum(0); // 예상인원
            item.setTime(0); // 예상시간
            item.setName(result);
            //item.setImage();
            mExArray.add(item);
        }*/


    }

    class HttpConnection {

        OkHttpClient clnt = new OkHttpClient(); // OK객체 생성






        public void requestGet(String url){  // 메소드를 생성하자.

            try{


                // body가 필요없는이유 클라에서 서버 전송할 값엇ㅄ으니까!
                final Request req = new Request.Builder()
                        .url(url)
                        .build(); // post추가안하면 get형식으로 서버에서 값 받아오기

                Log.d(TAG, "서버텟트2222" + req.toString() ); // method=GET, url=http://172.30.1.46:3000/


                clnt.newCall(req)
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.d(TAG, "서버텟트33333실패" );
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {


                                Log.d(TAG, "서버텟트33333"+response );
                                result =  response.body().string(); // tostring()아님! string()써야함ㅇㅇㅇㅇ



                                Log.d(TAG, "서버텟트33333"+result);
                               //  Log.d(TAG, "서버텟트33333"+name);
                                jsonGet(result);

                                ExItem item = new ExItem();
                                item.setName(result);
                                mExArray.add(item);
                                mExAdapter = new ExAdapter(mExArray);






                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {

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
                                });


                            }
                        });

            }catch (Exception e){
                e.printStackTrace();
                Log.d(TAG, "서버텟트444실패" );
            }
        }


        public void jsonGet(String result){
                    // JsonArray 추가하고 json object 로 받아서 하기 ㅇㅇㅇ.ㅇ..!
                    //Value of type org.json.JSONArray cannot be converted to JSONObject

            try{
                JSONArray jsonar = new JSONArray(result);
                JSONObject jsonob =  new JSONObject();

                Log.d(TAG, "서버텟트33333JSONNNNN"+jsonar);
                // jsonob = new JSONObject(response.body().toString());

                // Iterator i = jsonob.keys();

            for(int k=0; k<jsonar.length(); k++){
               jsonob = jsonar.getJSONObject(k);
            }

                name = jsonob.getString("name");
                data = jsonob.getString("data");
                detaildata = jsonob.getString("detaildata");
                expecnum = jsonob.getInt("expecnum");
                expectime = jsonob.getInt("expectime");

                Log.d(TAG, "서버텟트33333JSONNNNN"+name);


            }catch (JSONException e){
                Log.d(TAG, "서버텟트33333에러났냐...."+e.getMessage());
                e.printStackTrace();
            }




        }
    }
}
