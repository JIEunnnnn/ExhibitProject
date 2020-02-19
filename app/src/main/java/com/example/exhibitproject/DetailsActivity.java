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
import static java.sql.Types.NULL;

// 내일할일 서버합치고 데이터맵핑 확인도하고
//
// 내일 => 혼잡도색깔나타내고 경로그리고
// 업데이트시키는거 ㅇㅇ !
// db변경하고
//
// 목요일날 : 맵액티비티, 상세페이지 정보화면ㅁ만 나타내는거 ㅇㅇ!
public class DetailsActivity extends AppCompatActivity {
    private ViewPager2 vpPager;
    FragmentPagerAdapter adapterViewPager;
    private WormDotsIndicator indicator;

    private static final String TAG = "OKHTTP 테스트";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String url = "http://172.30.1.46:3000/";
   //  name nownum size

    String result ; // 잘받아왔는지 전체값출력
    String name, detaildata, firstex, secondex, thirdex, fourthex, fifthex ;  // 전시회이름, 전시회설명, 전시회내 관내용
    String Exname; // 서버로 post보낼때 필요한 ....
    int nownum, size ; // 현재인원, 관크기
    //firstex secondex, thirdex, fourtex nownum size name detaildata
    //// 이름 현재인원수 전시회자세한설명 관크기 전시회 내의 관별 설명 3/4

   // 여기 수정하기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



        Intent intent = getIntent();
        Exname = intent.getStringExtra("NAME");
        // String Exname = intent.getStringExtra("EXNAME");
         Log.d(TAG, "DetailActivityy서버텟트11111" + Exname);
        TextView dName = findViewById(R.id.tView_detail_name);
        dName.setText(Exname);

        ViewPager vpPager = (ViewPager) findViewById(R.id.vPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        indicator = (WormDotsIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);

        final HttpConnection connectServ = new HttpConnection();
        connectServ.requestGet(url);
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
            // String str = Exname;

            Log.d(TAG, "DetailActivityy서버텟트제이쓴222Exname" + Exname );
            try{

                JSONObject js = new JSONObject();
                js.put("msg", Exname);
                body = RequestBody.create(JSON, js.toString()) ;

                Log.d(TAG, "DetailActivityy서버텟트제이쓴222body" + body );


                final Request req = new Request.Builder()
                            .url(url)
                            .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                            .post(body)
                            .build();
                // post형식


                Log.d(TAG, "DetailActivityy서버텟트2222요청객체" + req.toString() );


                clnt.newCall(req)
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.d(TAG, "DetailActivityy서버텟트33333실패" );
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                                Log.d(TAG, "DetailActivity서버텟트33333rsponse"+response );
                                result =  response.body().string(); // tostring()아님! string()써야함ㅇㅇㅇㅇ
                                Log.d(TAG, "DetailActivity서버텟트33333result"+result );

                                jsonGet(result);
                            }
                        });


            }catch(Exception e){
                e.printStackTrace();;
                Log.d(TAG, "DetailActivityy서버텟트444실패"+e.getMessage() );
            }

        }


    }


    public void jsonGet(String res){



        //firstex secondex, thirdex, fourtex nownum size name detaildata
        //// 이름 현재인원수 전시회자세한설명 관크기 전시회 내의 관별 설명 3/4

        try{
            JSONArray jsonar = new JSONArray(res);
            JSONObject jsonob =  new JSONObject();
            Log.d(TAG, "DetailActivity서버텟트33333"+jsonar );

            for(int k=0; k<jsonar.length(); k++) {
                jsonob = jsonar.getJSONObject(k);
            }
                    name = jsonob.getString("name");
                   detaildata = jsonob.getString("detaildata");
                    firstex = jsonob.getString("firstex");
                    secondex = jsonob.getString("secondex");
                    thirdex = jsonob.getString("thirdex");
                    fourthex = jsonob.getString("fourthex");


                  nownum = jsonob.getInt("nownum");
                  size = jsonob.getInt("size");
                  Log.d(TAG, "DetailActivity서버텟트33333JSONNNNNㅅ;ㅣ비ㅣㅣㅣㅣ");



                    // if fifth 없으면....그냥넘어가도록수행.....



         //   Log.d(TAG, "DetailActivity서버텟트33333JSONNNNN"+Exname+data+detaildata+expecnum+expectime);

        }catch (JSONException e){
            Log.d(TAG, "DetailActivity서버텟트33333에러났냐...."+e.getMessage());
            e.printStackTrace();
        }
    }
}
