package com.example.exhibitproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static com.example.exhibitproject.MainActivity.JSON;
import static com.example.exhibitproject.MainActivity.url;

public class DetailsActivity extends AppCompatActivity {
    private ViewPager2 vpPager;
    FragmentPagerAdapter adapterViewPager;
    private WormDotsIndicator indicator;

    private static final String TAG = "OKHTTP 테스트";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final String url = "http://192.168.0.2:3000/";

    String result ;
    String name, data, detaildata; // 전시회이름, 전시회설명, 전시회내 관내용
    int expecnum, expectime; // 예상인원, 예상시간

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final HttpConnection connectServ = new HttpConnection();
        connectServ.requestGet(url);

        Intent intent = getIntent();
        String name = intent.getStringExtra("NAME");
        TextView dName = findViewById(R.id.tView_detail_name);
        dName.setText(name);

        ViewPager vpPager = (ViewPager) findViewById(R.id.vPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        indicator = (WormDotsIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return FirstActivity.newInstance(0, "Page # 1");
                case 1:
                    return SecondActivity.newInstance(1, "Page # 2");
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

    }



    class HttpConnection {


        OkHttpClient clnt = new OkHttpClient(); // OK객체 생성


        public void requestGet(String url){
            final RequestBody body ;
            try{
                body = RequestBody.create(JSON, "Detail" ) ;

                final Request req = new Request.Builder()
                            .url(url)
                            .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                            .post(body)
                            .build();


                Log.d(TAG, "DetailActivityy서버텟트2222" + req.toString() );


                clnt.newCall(req)
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.d(TAG, "DetailActivityy서버텟트33333실패" );
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                                Log.d(TAG, "DetailActivity서버텟트33333"+response );
                                result =  response.body().string(); // tostring()아님! string()써야함ㅇㅇㅇㅇ
                                Log.d(TAG, "DetailActivity서버텟트33333"+result );

                                jsonGet(result);
                            }
                        });


            }catch(Exception e){
                e.printStackTrace();;
                Log.d(TAG, "DetailActivityy서버텟트444실패" );
            }

        }


    }


    public void jsonGet(String res){

        try{
            JSONArray jsonar = new JSONArray(res);
            JSONObject jsonob =  new JSONObject();
            Log.d(TAG, "DetailActivity서버텟트33333"+jsonar );

            for(int k=0; k<jsonar.length(); k++){
                jsonob = jsonar.getJSONObject(k);
            }
            name = jsonob.getString("name");
            data = jsonob.getString("data");
            detaildata = jsonob.getString("detaildata");
            expecnum = jsonob.getInt("expecnum");
            expectime = jsonob.getInt("expectime");

            Log.d(TAG, "DetailActivity서버텟트33333JSONNNNN"+name);


        }catch (JSONException e){
            Log.d(TAG, "DetailActivity서버텟트33333에러났냐...."+e.getMessage());
            e.printStackTrace();
        }
    }
}
