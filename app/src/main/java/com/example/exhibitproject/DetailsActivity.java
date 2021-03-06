package com.example.exhibitproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;
import static com.example.exhibitproject.MainActivity.JSON;
import static com.example.exhibitproject.MainActivity.url;
import static java.sql.Types.NULL;

public class DetailsActivity extends AppCompatActivity {
    FragmentPagerAdapter adapterViewPager;
    private WormDotsIndicator indicator;

    private static final String TAG = "OKHTTP 테스트";
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String url = "http://192.168.0.5:3000/";//TODO:SET SERVER IP ADDR
   //  name nownum size

    String result ; // 잘받아왔는지 전체값출력
    String title, data, detaildata, g_name, g_size, g_people, g_des, g_guide, g_picture;  // 전시회이름, 전시회설명, 관내인원, 관내크기, 관이름, 관내자세한설명, 전시회 그림설명
    String Exname, imageNum; // 서버로 post보낼때 필요한 ....
    int  mapNum ; // intent넘기는거 구분하는 변수

    String[] fs = new String[4]; // 관내인원 분할
    String[] ss = new String[4]; // 관내크기 분할
    String[] ts = new String[4]; // 관이름 분할
    String[] fs2 = new String[4]; // 관내자세한설명 분할
    String[] gs = new String[2]; //가이드 정보
    String[] ps = new String[4] ; // 그림 정보

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        final HttpConnection connectServ = new HttpConnection();
        connectServ.requestGet(url);
    }

    // 여기 수정하기
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);



        Intent intent = getIntent();
        Exname = intent.getStringExtra("NAME");
        mapNum = intent.getIntExtra("MAP", 1);
        imageNum = intent.getStringExtra("IMAGENAME");
        // String Exname = intent.getStringExtra("EXNAME");
         Log.d(TAG, "DetailActivityy서버텟트11111" + Exname);



        final ViewPager vpPager = (ViewPager) findViewById(R.id.vPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());

        adapterViewPager.notifyDataSetChanged();
        vpPager.setAdapter(adapterViewPager);

        indicator = (WormDotsIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(vpPager);



        adapterViewPager.notifyDataSetChanged();
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

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

                    return ZeroActivity.newInstance(0, "Page # 1");
                case 1:
                    return FirstActivity.newInstance(1, "Page # 2");
                case 2:
                    return SecondActivity.newInstance(2, "Page # 3");
                default:
                    return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }

        public int getItemPosition(Object object){
            return POSITION_NONE;
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

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {


                                    }
                                });
                            }
                        });


            }catch(Exception e){
                e.printStackTrace();;
                Log.d(TAG, "DetailActivityy서버텟트444실패"+e.getMessage() );
            }

        }


    }


    public void jsonGet(String res){
        try{
            JSONArray jsonar = new JSONArray(res);
            JSONObject jsonob =  new JSONObject();
            Log.d(TAG, "DetailActivity서버텟트33333"+jsonar );
            Object js = jsonar.getJSONObject(0);
            Log.d(TAG, "테스트!!!!DetailActivity서버텟트33333JS)"+js);



            for(int k=0; k<jsonar.length(); k++) {
                jsonob = jsonar.getJSONObject(k);
            }
                    title = jsonob.getString("title");
                    data = jsonob.getString("data");
                   detaildata = jsonob.getString("detaildata");
                   // exhibitpeople exhibitsize exhibitname exhibitdetaildaa

                    g_people = jsonob.getString("exhibitpeople"); // 관내인원수
                    g_size = jsonob.getString("exhibitsize"); // 관크기
                    g_name = jsonob.getString("exhibitname"); // 관이름
                    g_des = jsonob.getString("exhibitdetaildata"); // 관내 자세한설명
                    g_guide = jsonob.getString("guidedata");
                    g_picture= jsonob.getString("exhibitpicture"); //  그림정보

            Log.d(TAG, "테스트!!!!DetailActivity서버텟트33333JS)"+g_people);
            Log.d(TAG, "테스트!!!!DetailActivity서버텟트33333JS)"+g_size);
            Log.d(TAG, "테스트!!!!DetailActivity서버텟트33333JS)"+g_name);
            Log.d(TAG, "테스트!!!!DetailActivity서버텟트33333JS)"+g_des);
            Log.d(TAG, "테스트!!!!DetailActivity서버텟트33333JS)"+g_guide);

            g_people = g_people.replace("[", "").replace("]","");
            fs = g_people.split(",");

            g_size = g_size.replace("[", "").replace("]","");
            ss = g_size.split(",");

            g_name = g_name.replace("[", "").replace("]","").replaceAll("\"","");
            ts =  g_name.split(",");

            g_des = g_des.replace("[","").replace("]","").replaceAll("\"","");
            fs2 =g_des.split(",");

            g_guide = g_guide.replace("[","").replace("]","").replaceAll("\"","");
            gs = g_guide.split(",");


            g_picture = g_picture.replace("[","").replace("]","").replaceAll("\"","");
            ps = g_picture.split(",");

            Log.d(TAG, "DetailActivity서버텟트33333JSONNNNNㅅ;ㅣ비ㅣㅣㅣㅣ");


        }catch (JSONException e){
            Log.d(TAG, "DetailActivity서버텟트33333에러났냐...."+e.getMessage());
            e.printStackTrace();
        }
    }
}
