package com.example.capstone_1;

import static com.example.capstone_1.R.drawable.rd_et_background_line_subtitle;
import static com.example.capstone_1.R.drawable.rd_et_background_line_subtitle_nocheck;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.capstone_1.databinding.ActivityMainBinding;
import com.example.capstone_1.databinding.ActivityMainLoginBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity_login extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainLoginBinding binding_login;
    private TextView tv_login_id, tv_login_name, tv_login_company, tv_login_phone;
    private Button btn_tell;

    private TextView tv_water, tv_waterPersent, tv_tmp, tv_sky, tv_vec, tv_wsd, tv_humidity, tv_date, tv_time, tv_address, tv_address_login;
    private String address; // ???????????? ?????? ??????
    private ImageView img_weather;
    private TextView tv_slash1, tv_slash2, tv_slash3, tv_slash4, tv_slash5, tv_slash6;
    private View v_center_line;
    private LinearLayout box3, box4;

    //?????? + ??? + ?????? ?????? ?????????
    private String url_weather = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=";
    private String url_service_Key = "vYddcBhVO08I9DSvmVD6Mhs12hpr7JzPOK8VQcn80lKi07kH4clX2seo0VFMebG45VJTEUBC91nYSvUsXQzDpg%3D%3D";
    private int url_x, url_y, url_xx, url_yy;
    private String url_m;
    private String html;
    private String[] Array;

    //??????
    private double cur_lat;
    private double cur_lon;
    public static int TO_GRID = 0;

    //????????????
    private TextView tv_pm25Value, tv_coValue, tv_o3Value, tv_o3Grade, tv_pm10Value, tv_coGrade;
    private String url_dust = "http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?sidoName=";
    private String url_dust_service_Key = "vYddcBhVO08I9DSvmVD6Mhs12hpr7JzPOK8VQcn80lKi07kH4clX2seo0VFMebG45VJTEUBC91nYSvUsXQzDpg%3D%3D";
    private String address_dust_sido, html_dust, url_d, address_dust_station;
    private String[] Array_dust, dust_list;
    private int set_i = 0;

    //??????
    private TextView tv_water_search, tv_waterPersent_search, tv_tmp_search, tv_sky_search, tv_vec_search, tv_wsd_search, tv_humidity_search, tv_slash1_search, tv_slash2_search,
            tv_slash3_search, tv_address_search, tv_pm25Value_search, tv_coValue_search, tv_o3Value_search, tv_o3Grade_search, tv_pm10Value_search, tv_coGrade_search
            ,tv_slash4_search, tv_slash5_search, tv_slash6_search;
    private ImageView img_weather_search;
    private String html_search, url_m_search, address_search="", html_dust_search, url_d_search, Address_dust_sido_search="";
    private String[] Array_search, dust_list_search;
    private double search_lat, search_lon;

    //??????
    private HashMap<String, String> hashMap1 = new HashMap<>();
    private String url_news="https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=100", news_text; // news default
    private TextView tv_news;
    private final Document[] document = new Document[6];
    private final Elements[] elements = new Elements[6];
    private Integer number = 0;
    // sub title
    private TextView tv_title1, tv_title2, tv_title3, tv_title4, tv_title5, tv_title6;

    String userID = "";
    String userPassword = "";
    String userName = "";
    String userCompany = "";
    HashMap <String, String> hashMap = new HashMap<String, String>(){{
        put("???????????????","1566-7711"); put("??????AXA","1566-1566"); put("???????????????????????????","1588-4770");
        put("??????????????????","1566-8000"); put("AIG????????????","1544-0911"); put("????????????????????????","1588-0220");
        put("??????????????????","1588-3344"); put("ERGO??????","1544-2580"); put("kdb??????","1588-4040");
        put("??????????????????","1588-5959"); put("THE-K????????????","1566-3000"); put("????????????","1588-3131");
        put("????????????","1688-1688"); put("?????????????????????","1577-1001"); put("????????????","1577-1004");
        put("????????????","1566-8282"); put("????????????(???.????????????)","1588-6363"); put("????????????????????????","2144-2600");
        put("????????????","1588-5114"); put("????????????????????????","1588-6500"); put("??????????????????","1588-5580");
        put("????????????","1588-5656"); put("??????????????????","1588-3114"); put("PCA??????","1588-4300");
        put("LIG????????????","1544-0114"); put("????????????","1588-2288"); put("???????????????","080-779-7575");
        put("????????????","1588-0100"); put("????????????","1588-1001"); put("ING????????????","1588-5005");

        put("??????HSBC????????????","080-3488-7000");
        put("KB????????????","1588-9922");
        put("?????????????????????","1688-2004");
        put("?????????????????????","1577-3311");
        put("?????????????????????","1588-0058");
        put("AIA????????????","080-500-4949");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding_login = ActivityMainLoginBinding.inflate(getLayoutInflater());
        setContentView(binding_login.getRoot());

        setSupportActionBar(binding_login.appBarLoginMain.toolbarLogin);
        DrawerLayout drawer = binding_login.loginDrawerLayout;
        NavigationView navigationView = binding_login.navLoginView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_main_login, R.id.nav_function1_login, R.id.nav_function2_login, R.id.nav_function3_login, R.id.nav_function4_login)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_login);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_main_login:
                        Toast.makeText(getApplicationContext(), "??????1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function1_login:
                        Intent intent_function1 = new Intent(MainActivity_login.this, function1_Activity_login.class);
                        intent_function1.putExtra("userID",userID);
                        intent_function1.putExtra("userName", userName);
                        intent_function1.putExtra("userCompany",userCompany);
                        intent_function1.putExtra("address", address);
                        startActivity(intent_function1);
                        Toast.makeText(getApplicationContext(), "?????? ?????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function2_login:
                        Intent intent_function2 = new Intent(MainActivity_login.this, function2_Activity_login.class);
                        intent_function2.putExtra("userID",userID);
                        intent_function2.putExtra("userName", userName);
                        intent_function2.putExtra("userCompany",userCompany);
                        intent_function2.putExtra("address", address);
                        startActivity(intent_function2);
                        Toast.makeText(getApplicationContext(), "???????????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function3_login:
                        Intent intent_function3 = new Intent(MainActivity_login.this, function3_Activity_login.class);
                        intent_function3.putExtra("userID",userID);
                        intent_function3.putExtra("userName", userName);
                        intent_function3.putExtra("userCompany",userCompany);
                        intent_function3.putExtra("address", address);
                        startActivity(intent_function3);
                        Toast.makeText(getApplicationContext(), "????????? ???????????????", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_function4_login:
                        Intent intent_function4 = new Intent(MainActivity_login.this, function4_Activity_login.class);
                        intent_function4.putExtra("userID",userID);
                        intent_function4.putExtra("userName", userName);
                        intent_function4.putExtra("userCompany",userCompany);
                        intent_function4.putExtra("address", address);
                        startActivity(intent_function4);
                        Toast.makeText(getApplicationContext(), "??? ??????", Toast.LENGTH_SHORT).show();
                        break;
                }

                DrawerLayout drawer = findViewById(R.id.login_drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        hashMap1.put("??????", "https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=100");
        hashMap1.put("??????", "https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=101");
        hashMap1.put("??????", "https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=102");
        hashMap1.put("??????/??????", "https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=103");
        hashMap1.put("IT/??????", "https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=105");
        hashMap1.put("??????", "https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=104");

        tv_news = findViewById(R.id.tv_news);
        getData1 getData1 = new getData1();
        getData1.execute();

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // GPS ???????????? ????????? ????????? ??????????????? ????????? ????????????~!!!
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            checkDangerousPermissions();
            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // ????????? ???????????????
                100, // ??????????????? ?????? ???????????? (miliSecond)
                1, // ??????????????? ?????? ???????????? (m)
                mLocationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // ????????? ???????????????
                100, // ??????????????? ?????? ???????????? (miliSecond)
                1, // ??????????????? ?????? ???????????? (m)
                mLocationListener);

        tv_date = findViewById(R.id.tv_date);
        tv_time = findViewById(R.id.tv_time);
        tv_tmp = findViewById(R.id.tv_tmp);
        tv_sky = findViewById(R.id.tv_sky);
        tv_water = findViewById(R.id.tv_water);
        tv_waterPersent = findViewById(R.id.tv_waterPersent);
        tv_vec = findViewById(R.id.tv_vec);
        tv_wsd = findViewById(R.id.tv_wsd);
        tv_humidity = findViewById(R.id.tv_humidity);
        img_weather = findViewById(R.id.img_weather);

        tv_pm25Value = findViewById(R.id.tv_pm25Value);
        tv_coValue = findViewById(R.id.tv_coValue);
        tv_o3Value = findViewById(R.id.tv_o3Value);
        tv_o3Grade = findViewById(R.id.tv_o3Grade);
        tv_pm10Value = findViewById(R.id.tv_pm10Value);
        tv_coGrade = findViewById(R.id.tv_coGrade);

        tv_slash1 = findViewById(R.id.tv_slash1);
        tv_slash2 = findViewById(R.id.tv_slash2);
        tv_slash3 = findViewById(R.id.tv_slash3);
        tv_slash4 = findViewById(R.id.tv_slash4);
        tv_slash5 = findViewById(R.id.tv_slash5);
        tv_slash6 = findViewById(R.id.tv_slash6);

        tv_tmp_search = findViewById(R.id.tv_tmp_search);
        tv_sky_search = findViewById(R.id.tv_sky_search);
        tv_water_search = findViewById(R.id.tv_water_search);
        tv_waterPersent_search = findViewById(R.id.tv_waterPersent_search);
        tv_vec_search = findViewById(R.id.tv_vec_search);
        tv_wsd_search = findViewById(R.id.tv_wsd_search);
        tv_humidity_search = findViewById(R.id.tv_humidity_search);
        img_weather_search = findViewById(R.id.img_weather_search);

        tv_pm25Value_search = findViewById(R.id.tv_pm25Value_search);
        tv_coValue_search = findViewById(R.id.tv_coValue_search);
        tv_o3Value_search = findViewById(R.id.tv_o3Value_search);
        tv_o3Grade_search = findViewById(R.id.tv_o3Grade_search);
        tv_pm10Value_search = findViewById(R.id.tv_pm10Value_search);
        tv_coGrade_search = findViewById(R.id.tv_coGrade_search);

        tv_slash1_search = findViewById(R.id.tv_slash1_search);
        tv_slash2_search = findViewById(R.id.tv_slash2_search);
        tv_slash3_search = findViewById(R.id.tv_slash3_search);
        tv_slash4_search = findViewById(R.id.tv_slash4_search);
        tv_slash5_search = findViewById(R.id.tv_slash5_search);
        tv_slash6_search = findViewById(R.id.tv_slash6_search);
        tv_address_search = findViewById(R.id.tv_address_search);

        v_center_line = findViewById(R.id.v_center_line);
        box3 = findViewById(R.id.box3);
        box4 = findViewById(R.id.box4);

        tv_title1 = findViewById(R.id.tv_title1);
        tv_title2 = findViewById(R.id.tv_title2);
        tv_title3 = findViewById(R.id.tv_title3);
        tv_title4 = findViewById(R.id.tv_title4);
        tv_title5 = findViewById(R.id.tv_title5);
        tv_title6 = findViewById(R.id.tv_title6);

        tv_title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData1 getData1 = new getData1();
                reset_title();

                tv_title1.setBackgroundResource(rd_et_background_line_subtitle);
                tv_title1.setTextColor(Color.parseColor("#5D02FF"));
                number = 0;
                getData1.execute();
            }
        });

        tv_title2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData1 getData1 = new getData1();
                reset_title();

                tv_title2.setBackgroundResource(rd_et_background_line_subtitle);
                tv_title2.setTextColor(Color.parseColor("#5D02FF"));
                number = 1;
                getData1.execute();
            }
        });

        tv_title3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData1 getData1 = new getData1();
                reset_title();

                tv_title3.setBackgroundResource(rd_et_background_line_subtitle);
                tv_title3.setTextColor(Color.parseColor("#5D02FF"));
                number = 2;
                getData1.execute();
            }
        });

        tv_title4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData1 getData1 = new getData1();
                reset_title();

                tv_title4.setBackgroundResource(rd_et_background_line_subtitle);
                tv_title4.setTextColor(Color.parseColor("#5D02FF"));
                number = 3;
                getData1.execute();
            }
        });

        tv_title5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData1 getData1 = new getData1();
                reset_title();

                tv_title5.setBackgroundResource(rd_et_background_line_subtitle);
                tv_title5.setTextColor(Color.parseColor("#5D02FF"));
                number = 4;
                getData1.execute();
            }
        });

        tv_title6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData1 getData1 = new getData1();
                reset_title();

                tv_title6.setBackgroundResource(rd_et_background_line_subtitle);
                tv_title6.setTextColor(Color.parseColor("#5D02FF"));
                number = 5;
                getData1.execute();
            }
        });

        // ????????? ?????????, ?????? ?????? ???????????? ???????????? ???????????????..
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!Thread.interrupted())
                    try {
                        html = getData();
                        Array = html.split("\n");
                        html_dust = getFineDust();
                        dust_list = html_dust.split("\n");
                        runOnUiThread(new Runnable() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void run() {
                                long now = System.currentTimeMillis();
                                Date date = new Date(now);
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
                                String get_time = dateFormat.format(date);
                                tv_time.setText(get_time.substring(0, 2) + " : " + get_time.substring(2, 4));
                                for (int i = 0; i < Array.length - 1; i++) {
                                    if (Array[i].contains("?????? :"))
                                        tv_date.setText(Array[i].substring(5, 9) + "." + Array[i].substring(9, 11) + "." + Array[i].substring(11, 13));
                                    else if (Array[i].contains("?????? :"))// ????????????
                                        switch (Array[i].substring(4, 7)) {
                                            case "POP":
                                                tv_waterPersent.setText("???????????? : " + Array[i + 3].substring(3) + "%");
                                                break;
                                            case "PTY":
                                                String num_PTY = Array[i + 3].substring(3);
                                                if (num_PTY.equals("0"))
                                                    tv_water.setText("???????????? : ??????");
                                                else if (num_PTY.equals("1")) {
                                                    tv_water.setText("???????????? : ???");
                                                    img_weather.setImageResource(R.drawable.rain);
                                                } else if (num_PTY.equals("2")) {
                                                    tv_water.setText("???????????? : ???/???");
                                                    img_weather.setImageResource(R.drawable.rain_snow);
                                                } else if (num_PTY.equals("3")) {
                                                    tv_water.setText("???????????? : ???");
                                                    img_weather.setImageResource(R.drawable.snow);
                                                } else if (num_PTY.equals("4")) {
                                                    tv_water.setText("???????????? : ?????????");
                                                    img_weather.setImageResource(R.drawable.rain);
                                                }
                                                break;
                                            case "REH":
                                                tv_humidity.setText("?????? : " + Array[i + 3].substring(3) + "%");
                                                break;
                                            case "SKY":
                                                String num_SKY = Array[i + 3].substring(3);
                                                if (num_SKY.equals("1")) {
                                                    tv_sky.setText("?????? : ??????");
                                                    img_weather.setImageResource(R.drawable.sun);
                                                } else if (num_SKY.equals("3")) {
                                                    tv_sky.setText("?????? : ????????????");
                                                    img_weather.setImageResource(R.drawable.cloud_m);
                                                } else if (num_SKY.equals("4")) {
                                                    tv_sky.setText("?????? : ??????");
                                                    img_weather.setImageResource(R.drawable.cloud);
                                                }
                                                break;
                                            case "TMP":
                                                tv_tmp.setText(Array[i + 3].substring(3) + "??C");
                                                break;
                                            case "VEC":
                                                if(Integer.parseInt(Array[i + 3].substring(3)) > 237 && Integer.parseInt(Array[i + 3].substring(3)) <= 360 ||
                                                        Integer.parseInt(Array[i + 3].substring(3)) >= 0 && Integer.parseInt(Array[i + 3].substring(3)) < 23){
                                                    tv_vec.setText("?????? : ???");
                                                }
                                                else if(Integer.parseInt(Array[i + 3].substring(3)) > 22 && Integer.parseInt(Array[i + 3].substring(3)) < 68){
                                                    tv_vec.setText("?????? : ??????");
                                                }
                                                else if(Integer.parseInt(Array[i + 3].substring(3)) > 67 && Integer.parseInt(Array[i + 3].substring(3)) < 113){
                                                    tv_vec.setText("?????? : ???");
                                                }
                                                else if(Integer.parseInt(Array[i + 3].substring(3)) > 112 && Integer.parseInt(Array[i + 3].substring(3)) < 158){
                                                    tv_vec.setText("?????? : ??????");
                                                }
                                                else if(Integer.parseInt(Array[i + 3].substring(3)) > 157 && Integer.parseInt(Array[i + 3].substring(3)) < 203){
                                                    tv_vec.setText("?????? : ???");
                                                }
                                                else if(Integer.parseInt(Array[i + 3].substring(3)) > 202 && Integer.parseInt(Array[i + 3].substring(3)) < 248){
                                                    tv_vec.setText("?????? : ??????");
                                                }
                                                else if(Integer.parseInt(Array[i + 3].substring(3)) > 247 && Integer.parseInt(Array[i + 3].substring(3)) < 293){
                                                    tv_vec.setText("?????? : ???");
                                                }
                                                else if(Integer.parseInt(Array[i + 3].substring(3)) > 292 && Integer.parseInt(Array[i + 3].substring(3)) < 238){
                                                    tv_vec.setText("?????? : ??????");
                                                }
                                                break;
                                            case "WSD":
                                                tv_wsd.setText("?????? : " + Array[i + 3].substring(3) + "m/s");
                                                break;
                                        }
                                }
                                for (int i = 0; i < dust_list.length - 1; i++) {
                                    if (dust_list[i].equals("?????? :" + address_dust_station)) {
                                        set_i = i;
                                        if (dust_list[set_i-2].contains("????????????25 :")) {
                                            tv_pm25Value.setText("????????????(25pm) : " + dust_list[set_i-2].substring(8) + "???/???");
                                        }if (dust_list[set_i-5].contains("??????????????? :")) {
                                            tv_coValue.setText("??????????????? : " + dust_list[set_i-5].substring(7) + "ppm");
                                        }if (dust_list[set_i-3].contains("????????????10 :")) {
                                            tv_pm10Value.setText("????????????(10pm) : " + dust_list[set_i-3].substring(8) + "???/???");
                                        }if (dust_list[set_i-4].contains("???????????? :")) {
                                            if(dust_list[set_i-4].substring(6).equals("1")){
                                                tv_o3Grade.setText("???????????? : ??????");
                                            }
                                            else if(dust_list[set_i-4].substring(6).equals("2")){
                                                tv_o3Grade.setText("???????????? : ??????");
                                            }
                                            else if(dust_list[set_i-4].substring(6).equals("3")){
                                                tv_o3Grade.setText("???????????? : ??????");
                                            }
                                            else if(dust_list[set_i-4].substring(6).equals("4")){
                                                tv_o3Grade.setText("???????????? : ????????????");
                                            }
                                        }if (dust_list[set_i-1].contains("????????????????????? :")) {
                                            if(dust_list[set_i-1].substring(9).equals("1")){
                                                tv_coGrade.setText("????????????????????? : ??????");
                                            }
                                            else if(dust_list[set_i-1].substring(9).equals("2")){
                                                tv_coGrade.setText("????????????????????? : ??????");
                                            }
                                            else if(dust_list[set_i-1].substring(9).equals("3")){
                                                tv_coGrade.setText("????????????????????? : ??????");
                                            }
                                            else if(dust_list[set_i-1].substring(9).equals("4")){
                                                tv_coGrade.setText("????????????????????? : ????????????");
                                            }
                                        }if (dust_list[set_i+1].contains("?????? :")) {
                                            tv_o3Value.setText("?????? : "+ dust_list[set_i+1].substring(4) + "ppm");
                                        }
                                    }
                                }
                                tv_tmp.setVisibility(View.VISIBLE);
                                tv_sky.setVisibility(View.VISIBLE);
                                tv_water.setVisibility(View.VISIBLE);
                                tv_waterPersent.setVisibility(View.VISIBLE);
                                tv_vec.setVisibility(View.VISIBLE);
                                tv_wsd.setVisibility(View.VISIBLE);
                                tv_humidity.setVisibility(View.VISIBLE);
                                img_weather.setVisibility(View.VISIBLE);

                                tv_pm25Value.setVisibility(View.VISIBLE);
                                tv_coValue.setVisibility(View.VISIBLE);
                                tv_o3Value.setVisibility(View.VISIBLE);
                                tv_o3Grade.setVisibility(View.VISIBLE);
                                tv_pm10Value.setVisibility(View.VISIBLE);
                                tv_coGrade.setVisibility(View.VISIBLE);

                                tv_slash1.setVisibility(View.VISIBLE);
                                tv_slash2.setVisibility(View.VISIBLE);
                                tv_slash3.setVisibility(View.VISIBLE);
                                tv_slash4.setVisibility(View.VISIBLE);
                                tv_slash5.setVisibility(View.VISIBLE);
                                tv_slash6.setVisibility(View.VISIBLE);
                            }
                        });
                        Thread.sleep(1000);
                    }catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    catch (Exception e) {
                        e.printStackTrace( );
                    }
            }
        }).start();

        new Thread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                while (!Thread.interrupted())
                    if(!address_search.equals("") && !Address_dust_sido_search.equals("")) {
                        try {
                            html_search = get_search_Data(url_xx, url_yy);
                            Array_search = html_search.split("\n");
                            html_dust_search = getFineDust_search();
                            dust_list_search = html_dust_search.split("\n");
                            tv_address_search.setText(Address_dust_sido_search +" " + address_search + " ????????? ?????? ??????");
                            runOnUiThread(new Runnable() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {
                                    for (int i = 0; i < Array_search.length - 1; i++) {
                                        if (Array_search[i].contains("?????? :"))// ????????????
                                            switch (Array_search[i].substring(4, 7)) {
                                                case "POP":
                                                    tv_waterPersent_search.setText("???????????? : " + Array_search[i + 3].substring(3) + "%");
                                                    break;
                                                case "PTY":
                                                    String num_PTY = Array_search[i + 3].substring(3);
                                                    if (num_PTY.equals("0"))
                                                        tv_water_search.setText("???????????? : ??????");
                                                    else if (num_PTY.equals("1")) {
                                                        tv_water_search.setText("???????????? : ???");
                                                        img_weather_search.setImageResource(R.drawable.rain);
                                                    } else if (num_PTY.equals("2")) {
                                                        tv_water_search.setText("???????????? : ???/???");
                                                        img_weather_search.setImageResource(R.drawable.rain_snow);
                                                    } else if (num_PTY.equals("3")) {
                                                        tv_water_search.setText("???????????? : ???");
                                                        img_weather_search.setImageResource(R.drawable.snow);
                                                    } else if (num_PTY.equals("4")) {
                                                        tv_water_search.setText("???????????? : ?????????");
                                                        img_weather_search.setImageResource(R.drawable.rain);
                                                    }
                                                    break;
                                                case "REH":
                                                    tv_humidity_search.setText("?????? : " + Array_search[i + 3].substring(3) + "%");
                                                    break;
                                                case "SKY":
                                                    String num_SKY = Array_search[i + 3].substring(3);
                                                    if (num_SKY.equals("1")) {
                                                        tv_sky_search.setText("?????? : ??????");
                                                        img_weather_search.setImageResource(R.drawable.sun);
                                                    } else if (num_SKY.equals("3")) {
                                                        tv_sky_search.setText("?????? : ????????????");
                                                        img_weather_search.setImageResource(R.drawable.cloud_m);
                                                    } else if (num_SKY.equals("4")) {
                                                        tv_sky_search.setText("?????? : ??????");
                                                        img_weather_search.setImageResource(R.drawable.cloud);
                                                    }
                                                    break;
                                                case "TMP":
                                                    tv_tmp_search.setText(Array_search[i + 3].substring(3) + "??C");
                                                    break;
                                                case "VEC":
                                                    if(Integer.parseInt(Array[i + 3].substring(3)) > 237 && Integer.parseInt(Array[i + 3].substring(3)) <= 360 ||
                                                            Integer.parseInt(Array[i + 3].substring(3)) >= 0 && Integer.parseInt(Array[i + 3].substring(3)) < 23){
                                                        tv_vec_search.setText("?????? : ???");
                                                    }
                                                    else if(Integer.parseInt(Array[i + 3].substring(3)) > 22 && Integer.parseInt(Array[i + 3].substring(3)) < 68){
                                                        tv_vec_search.setText("?????? : ??????");
                                                    }
                                                    else if(Integer.parseInt(Array[i + 3].substring(3)) > 67 && Integer.parseInt(Array[i + 3].substring(3)) < 113){
                                                        tv_vec_search.setText("?????? : ???");
                                                    }
                                                    else if(Integer.parseInt(Array[i + 3].substring(3)) > 112 && Integer.parseInt(Array[i + 3].substring(3)) < 158){
                                                        tv_vec_search.setText("?????? : ??????");
                                                    }
                                                    else if(Integer.parseInt(Array[i + 3].substring(3)) > 157 && Integer.parseInt(Array[i + 3].substring(3)) < 203){
                                                        tv_vec_search.setText("?????? : ???");
                                                    }
                                                    else if(Integer.parseInt(Array[i + 3].substring(3)) > 202 && Integer.parseInt(Array[i + 3].substring(3)) < 248){
                                                        tv_vec_search.setText("?????? : ??????");
                                                    }
                                                    else if(Integer.parseInt(Array[i + 3].substring(3)) > 247 && Integer.parseInt(Array[i + 3].substring(3)) < 293){
                                                        tv_vec_search.setText("?????? : ???");
                                                    }
                                                    else if(Integer.parseInt(Array[i + 3].substring(3)) > 292 && Integer.parseInt(Array[i + 3].substring(3)) < 238){
                                                        tv_vec_search.setText("?????? : ??????");
                                                    }
                                                    break;
                                                case "WSD":
                                                    tv_wsd_search.setText("?????? : " + Array_search[i + 3].substring(3) + "m/s");
                                                    break;
                                            }
                                    }
                                    for (int i = 0; i < dust_list_search.length - 1; i++) {
                                        if (dust_list_search[i].equals("?????? :" + address_search)) {
                                            set_i = i;
                                            if (dust_list_search[set_i-2].contains("????????????25 :")) {
                                                tv_pm25Value_search.setText("????????????(25pm) : " + dust_list_search[set_i-2].substring(8) + "???/???");
                                            }if (dust_list_search[set_i-5].contains("??????????????? :")) {
                                                tv_coValue_search.setText("??????????????? : " + dust_list_search[set_i-5].substring(7) + "ppm");
                                            }if (dust_list_search[set_i-3].contains("????????????10 :")) {
                                                tv_pm10Value_search.setText("????????????(10pm) : " + dust_list_search[set_i-3].substring(8) + "???/???");
                                            }if (dust_list_search[set_i-4].contains("???????????? :")) {
                                                if(dust_list_search[set_i-4].substring(6).equals("1")){
                                                    tv_o3Grade_search.setText("???????????? : ??????");
                                                }
                                                else if(dust_list_search[set_i-4].substring(6).equals("2")){
                                                    tv_o3Grade_search.setText("???????????? : ??????");
                                                }
                                                else if(dust_list_search[set_i-4].substring(6).equals("3")){
                                                    tv_o3Grade_search.setText("???????????? : ??????");
                                                }
                                                else if(dust_list_search[set_i-4].substring(6).equals("4")){
                                                    tv_o3Grade_search.setText("???????????? : ????????????");
                                                }
                                            }if (dust_list_search[set_i-1].contains("????????????????????? :")) {
                                                if(dust_list_search[set_i-1].substring(9).equals("1")){
                                                    tv_coGrade_search.setText("????????????????????? : ??????");
                                                }
                                                else if(dust_list_search[set_i-1].substring(9).equals("2")){
                                                    tv_coGrade_search.setText("????????????????????? : ??????");
                                                }
                                                else if(dust_list_search[set_i-1].substring(9).equals("3")){
                                                    tv_coGrade_search.setText("????????????????????? : ??????");
                                                }
                                                else if(dust_list_search[set_i-1].substring(9).equals("4")){
                                                    tv_coGrade_search.setText("????????????????????? : ????????????");
                                                }
                                            }if (dust_list_search[set_i+1].contains("?????? :")) {
                                                tv_o3Value_search.setText("?????? : "+ dust_list_search[set_i+1].substring(4) + "ppm");
                                            }
                                        }
                                    }
                                    tv_tmp_search.setVisibility(View.VISIBLE);
                                    tv_sky_search.setVisibility(View.VISIBLE);
                                    tv_water_search.setVisibility(View.VISIBLE);
                                    tv_waterPersent_search.setVisibility(View.VISIBLE);
                                    tv_vec_search.setVisibility(View.VISIBLE);
                                    tv_wsd_search.setVisibility(View.VISIBLE);
                                    tv_humidity_search.setVisibility(View.VISIBLE);
                                    img_weather_search.setVisibility(View.VISIBLE);

                                    tv_pm25Value_search.setVisibility(View.VISIBLE);
                                    tv_coValue_search.setVisibility(View.VISIBLE);
                                    tv_o3Value_search.setVisibility(View.VISIBLE);
                                    tv_o3Grade_search.setVisibility(View.VISIBLE);
                                    tv_pm10Value_search.setVisibility(View.VISIBLE);
                                    tv_coGrade_search.setVisibility(View.VISIBLE);

                                    tv_slash1_search.setVisibility(View.VISIBLE);
                                    tv_slash2_search.setVisibility(View.VISIBLE);
                                    tv_slash3_search.setVisibility(View.VISIBLE);
                                    tv_slash4_search.setVisibility(View.VISIBLE);
                                    tv_slash5_search.setVisibility(View.VISIBLE);
                                    tv_slash6_search.setVisibility(View.VISIBLE);
                                    tv_address_search.setVisibility(View.VISIBLE);

                                    v_center_line.setVisibility(View.VISIBLE);
                                    box3.setVisibility(View.VISIBLE);
                                    box4.setVisibility(View.VISIBLE);

                                }
                            });
                            Thread.sleep(1000);
                        }catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        catch (Exception e) {
                            e.printStackTrace( );
                        }
                    }
            }
        }).start();

        html = getData();
        Array = html.split("\n");
        html_dust = getFineDust();
        dust_list = html_dust.split("\n");
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
        String get_time = dateFormat.format(date);
        tv_time.setText(get_time.substring(0, 2) + " : " + get_time.substring(2, 4));
        for (int i = 0; i < Array.length - 1; i++) {
            if (Array[i].contains("?????? :"))
                tv_date.setText(Array[i].substring(5, 9) + "." + Array[i].substring(9, 11) + "." + Array[i].substring(11, 13));
            else if (Array[i].contains("?????? :"))// ????????????
                switch (Array[i].substring(4, 7)) {
                    case "POP":
                        tv_waterPersent.setText("???????????? : " + Array[i + 3].substring(3) + "%");
                        break;
                    case "PTY":
                        String num_PTY = Array[i + 3].substring(3);
                        if (num_PTY.equals("0"))
                            tv_water.setText("???????????? : ??????");
                        else if (num_PTY.equals("1")) {
                            tv_water.setText("???????????? : ???");
                            img_weather.setImageResource(R.drawable.rain);
                        } else if (num_PTY.equals("2")) {
                            tv_water.setText("???????????? : ???/???");
                            img_weather.setImageResource(R.drawable.rain_snow);
                        } else if (num_PTY.equals("3")) {
                            tv_water.setText("???????????? : ???");
                            img_weather.setImageResource(R.drawable.snow);
                        } else if (num_PTY.equals("4")) {
                            tv_water.setText("???????????? : ?????????");
                            img_weather.setImageResource(R.drawable.rain);
                        }
                        break;
                    case "REH":
                        tv_humidity.setText("?????? : " + Array[i + 3].substring(3) + "%");
                        break;
                    case "SKY":
                        String num_SKY = Array[i + 3].substring(3);
                        if (num_SKY.equals("1")) {
                            tv_sky.setText("?????? : ??????");
                            img_weather.setImageResource(R.drawable.sun);
                        } else if (num_SKY.equals("3")) {
                            tv_sky.setText("?????? : ????????????");
                            img_weather.setImageResource(R.drawable.cloud_m);
                        } else if (num_SKY.equals("4")) {
                            tv_sky.setText("?????? : ??????");
                            img_weather.setImageResource(R.drawable.cloud);
                        }
                        break;
                    case "TMP":
                        tv_tmp.setText(Array[i + 3].substring(3) + "??C");
                        break;
                    case "VEC":
                        tv_vec.setText("?????? : " + Array[i + 3].substring(3) + "m/s");
                        break;
                    case "WSD":
                        tv_wsd.setText("?????? : " + Array[i + 3].substring(3) + "m/s");
                        break;
                }
        }
        for (int i = 0; i < dust_list.length - 1; i++) {
            if (dust_list[i].equals("?????? :" + address_dust_station)) {
                set_i = i;
                if (dust_list[set_i-2].contains("????????????25 :")) {
                    tv_pm25Value.setText("????????????(25pm) : " + dust_list[set_i-2].substring(8) + "???/???");
                }if (dust_list[set_i-5].contains("??????????????? :")) {
                    tv_coValue.setText("??????????????? : " + dust_list[set_i-5].substring(7) + "ppm");
                }if (dust_list[set_i-3].contains("????????????10 :")) {
                    tv_pm10Value.setText("????????????(10pm) : " + dust_list[set_i-3].substring(8) + "???/???");
                }if (dust_list[set_i-4].contains("???????????? :")) {
                    if(dust_list[set_i-4].substring(6).equals("1")){
                        tv_o3Grade.setText("???????????? : ??????");
                    }
                    else if(dust_list[set_i-4].substring(6).equals("2")){
                        tv_o3Grade.setText("???????????? : ??????");
                    }
                    else if(dust_list[set_i-4].substring(6).equals("3")){
                        tv_o3Grade.setText("???????????? : ??????");
                    }
                    else if(dust_list[set_i-4].substring(6).equals("4")){
                        tv_o3Grade.setText("???????????? : ????????????");
                    }
                }if (dust_list[set_i-1].contains("????????????????????? :")) {
                    if(dust_list[set_i-1].substring(9).equals("1")){
                        tv_coGrade.setText("????????????????????? : ??????");
                    }
                    else if(dust_list[set_i-1].substring(9).equals("2")){
                        tv_coGrade.setText("????????????????????? : ??????");
                    }
                    else if(dust_list[set_i-1].substring(9).equals("3")){
                        tv_coGrade.setText("????????????????????? : ??????");
                    }
                    else if(dust_list[set_i-1].substring(9).equals("4")){
                        tv_coGrade.setText("????????????????????? : ????????????");
                    }
                }if (dust_list[set_i+1].contains("?????? :")) {
                    tv_o3Value.setText("?????? : "+ dust_list[set_i+1].substring(4) + "ppm");
                }
            }
        }
        tv_tmp.setVisibility(View.VISIBLE);
        tv_sky.setVisibility(View.VISIBLE);
        tv_water.setVisibility(View.VISIBLE);
        tv_waterPersent.setVisibility(View.VISIBLE);
        tv_vec.setVisibility(View.VISIBLE);
        tv_wsd.setVisibility(View.VISIBLE);
        tv_humidity.setVisibility(View.VISIBLE);
        img_weather.setVisibility(View.VISIBLE);

        tv_pm25Value.setVisibility(View.VISIBLE);
        tv_coValue.setVisibility(View.VISIBLE);
        tv_o3Value.setVisibility(View.VISIBLE);
        tv_o3Grade.setVisibility(View.VISIBLE);
        tv_pm10Value.setVisibility(View.VISIBLE);
        tv_coGrade.setVisibility(View.VISIBLE);

        tv_slash1.setVisibility(View.VISIBLE);
        tv_slash2.setVisibility(View.VISIBLE);
        tv_slash3.setVisibility(View.VISIBLE);
        tv_slash4.setVisibility(View.VISIBLE);
        tv_slash5.setVisibility(View.VISIBLE);
        tv_slash6.setVisibility(View.VISIBLE);
    }

    // sub_title
    private void reset_title(){
        tv_title1.setBackgroundResource(rd_et_background_line_subtitle_nocheck);
        tv_title2.setBackgroundResource(rd_et_background_line_subtitle_nocheck);
        tv_title3.setBackgroundResource(rd_et_background_line_subtitle_nocheck);
        tv_title4.setBackgroundResource(rd_et_background_line_subtitle_nocheck);
        tv_title5.setBackgroundResource(rd_et_background_line_subtitle_nocheck);
        tv_title6.setBackgroundResource(rd_et_background_line_subtitle_nocheck);

        tv_title1.setTextColor(Color.parseColor("#000000"));
        tv_title2.setTextColor(Color.parseColor("#000000"));
        tv_title3.setTextColor(Color.parseColor("#000000"));
        tv_title4.setTextColor(Color.parseColor("#000000"));
        tv_title5.setTextColor(Color.parseColor("#000000"));
        tv_title6.setTextColor(Color.parseColor("#000000"));
    }

    //?????? https://textbox.tistory.com/13 ????????????
    private String getDay() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String get_day = dateFormat.format(date);
        return get_day;
    }
    //??????
    private String getTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("HHmm");
        String get_time = dateFormat.format(date);
        int time_tmp = Integer.parseInt(get_time);
        if (time_tmp >= 210 && time_tmp < 510) {
            return "0200";
        } else if (time_tmp >= 510 && time_tmp < 810) {
            return "0500";
        } else if (time_tmp >= 810 && time_tmp < 1110) {
            return "0800";
        } else if (time_tmp >= 1110 && time_tmp < 1410) {
            return "1100";
        } else if (time_tmp >= 1410 && time_tmp < 1710) {
            return "1400";
        } else if (time_tmp >= 1710 && time_tmp < 2010) {
            return "1700";
        } else if (time_tmp >= 2010 && time_tmp < 2310) {
            return "2000";
        } else {
            return "2300";
        }
    }

    // ??????
    String getData() {
        StringBuffer buffer = new StringBuffer();
        url_m = url_weather + url_service_Key + "&numOfRows=10&pageNo=2&base_date=" + getDay() + "&base_time=" + getTime() + "&nx="+url_x+"&ny="+url_y;

        try {
            URL url = new URL(url_m); // ???????????? ??? ?????? url??? URL ????????? ??????.
            InputStream is = url.openStream(); // url ????????? ??????????????? ??????

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new InputStreamReader(is, "UTF-8"));
            String tag;
            xpp.next();

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // ?????? ?????? ????????????

                        if (tag.equals("item")) ;
                        else if (tag.equals("fcstDate")) {
                            buffer.append("?????? : ");
                            xpp.next();
                            // addr ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append(xpp.getText());
                            buffer.append("\n"); // ????????? ?????? ??????
                        } else if (tag.equals("fcstTime")) {
                            buffer.append("?????? : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("category")) {
                            buffer.append("?????? :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("fcstValue")) {
                            buffer.append("??? :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // ?????? ?????? ????????????
                        if (tag.equals("item")) buffer.append("\n"); // ????????? ?????????????????? ??? ?????????
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return buffer.toString(); // ?????? ??? ?????? ??? StringBuffer ????????? ?????? ??????
    }

    @SuppressLint("SetTextI18n")
    String get_search_Data(int xx, int yy) {
        StringBuffer buffer = new StringBuffer();
        url_m_search = url_weather + url_service_Key + "&numOfRows=10&pageNo=2&base_date=" + getDay() + "&base_time=" + getTime() + "&nx="+xx+"&ny="+yy;

        try {
            URL url = new URL(url_m_search); // ???????????? ??? ?????? url??? URL ????????? ??????.
            InputStream is = url.openStream(); // url ????????? ??????????????? ??????

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new InputStreamReader(is, "UTF-8"));
            String tag;
            xpp.next();

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // ?????? ?????? ????????????

                        if (tag.equals("item")) ;
                        else if (tag.equals("fcstDate")) {
                            buffer.append("?????? : ");
                            xpp.next();
                            // addr ????????? TEXT ???????????? ?????????????????? ??????
                            buffer.append(xpp.getText());
                            buffer.append("\n"); // ????????? ?????? ??????
                        } else if (tag.equals("fcstTime")) {
                            buffer.append("?????? : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("category")) {
                            buffer.append("?????? :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        } else if (tag.equals("fcstValue")) {
                            buffer.append("??? :");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // ?????? ?????? ????????????
                        if (tag.equals("item")) buffer.append("\n"); // ????????? ?????????????????? ??? ?????????
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return buffer.toString(); // ?????? ??? ?????? ??? StringBuffer ????????? ?????? ??????
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @SuppressLint("SetTextI18n")
        public void onLocationChanged(Location location) {
            cur_lon = location.getLongitude(); //??????
            cur_lat = location.getLatitude();   //??????
            MainActivity_login.LatXLngY tmp = convertGRID_GPS(TO_GRID, cur_lat, cur_lon);
            address = getCurrentAddress(cur_lat, cur_lon);

            tv_address_login = findViewById(R.id.tv_address_login);
            tv_address_login.setText(address);
            tv_address_login.setSingleLine(true);    // ????????? ????????????
            tv_address_login.setEllipsize(TextUtils.TruncateAt.MARQUEE); // ????????? ?????????
            tv_address_login.setSelected(true);

            tv_address = findViewById(R.id.tv_address);
            tv_address.setText(address); // ?????? ??????

            Array_dust = address.split(" ");
            address_dust_sido = Array_dust[1].substring(0,2);
            address_dust_station = Array_dust[4];

            url_x = (int) Math.round(tmp.x);
            url_y = (int) Math.round(tmp.y);
        }
        public void onProviderDisabled(String provider) {
            // Disabled???
        }

        public void onProviderEnabled(String provider) {
            // Enabled???
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // ?????????
        }
    };

    //????????? ??????
    private void checkDangerousPermissions() {
        String[] permissions = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "?????? ?????? ?????????.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }
    //????????? ??????
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, " ????????? ?????????.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, " ????????? ???????????? ??????.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    // ??????????????? ?????? (https://gist.github.com/fronteer-kr/14d7f779d52a21ac2f16 ????????????)
    private MainActivity_login.LatXLngY convertGRID_GPS(int mode, double lat_X, double lng_Y )
    {
        double RE = 6371.00877; // ?????? ??????(km)
        double GRID = 5.0; // ?????? ??????(km)
        double SLAT1 = 30.0; // ?????? ??????1(degree)
        double SLAT2 = 60.0; // ?????? ??????2(degree)
        double OLON = 126.0; // ????????? ??????(degree)
        double OLAT = 38.0; // ????????? ??????(degree)
        double XO = 43; // ????????? X??????(GRID)
        double YO = 136; // ???1?????? Y??????(GRID)


        double DEGRAD = Math.PI / 180.0;
        double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        MainActivity_login.LatXLngY rs = new MainActivity_login.LatXLngY();

        if (mode == TO_GRID) {
            rs.lat = lat_X;
            rs.lng = lng_Y;
            double ra = Math.tan(Math.PI * 0.25 + (lat_X) * DEGRAD * 0.5);
            ra = re * sf / Math.pow(ra, sn);
            double theta = lng_Y * DEGRAD - olon;
            if (theta > Math.PI) theta -= 2.0 * Math.PI;
            if (theta < -Math.PI) theta += 2.0 * Math.PI;
            theta *= sn;
            rs.x = Math.floor(ra * Math.sin(theta) + XO + 0.5);
            rs.y = Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
        }
        else {
            rs.x = lat_X;
            rs.y = lng_Y;
            double xn = lat_X - XO;
            double yn = ro - lng_Y + YO;
            double ra = Math.sqrt(xn * xn + yn * yn);
            if (sn < 0.0) {
                ra = -ra;
            }
            double alat = Math.pow((re * sf / ra), (1.0 / sn));
            alat = 2.0 * Math.atan(alat) - Math.PI * 0.5;

            double theta = 0.0;
            if (Math.abs(xn) <= 0.0) {
                theta = 0.0;
            }
            else {
                if (Math.abs(yn) <= 0.0) {
                    theta = Math.PI * 0.5;
                    if (xn < 0.0) {
                        theta = -theta;
                    }
                }
                else theta = Math.atan2(xn, yn);
            }
            double alon = theta / sn + olon;
            rs.lat = alat * RADDEG;
            rs.lng = alon * RADDEG;
        }
        return rs;
    }
    // ??????????????? ?????? (https://gist.github.com/fronteer-kr/14d7f779d52a21ac2f16 ????????????)
    class LatXLngY
    {
        public double lat;
        public double lng;

        public double x; // ????????? x -> ????????????
        public double y; // ????????? y -> ????????????

    }
    // ?????? ???????????? https://webnautes.tistory.com/1315 ????????????
    public String getCurrentAddress( double latitude, double longitude) {
        //????????????... GPS??? ????????? ??????
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    10);
        } catch (IOException ioException) {
            //???????????? ??????
            Toast.makeText(this, "???????????? ????????? ????????????", Toast.LENGTH_LONG).show();
            return "???????????? ????????? ????????????";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "????????? GPS ??????", Toast.LENGTH_LONG).show();
            return "????????? GPS ??????";

        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "?????? ?????????", Toast.LENGTH_LONG).show();
            return "?????? ?????????";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // nav_header ?????? ??????
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search_position).getActionView();
        searchView.setQueryHint("ex) ?????? ?????????");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                address_search = s.substring(3);
                Address_dust_sido_search = s.substring(0,2);
                Location location = getLocationFromAddress(getApplicationContext(), s);
                search_lat = location.getLatitude();
                search_lon = location.getLongitude();
                LatXLngY search_tmp = convertGRID_GPS(TO_GRID, search_lat, search_lon);
                url_xx = (int) Math.round(search_tmp.x);
                url_yy = (int) Math.round(search_tmp.y);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        tv_login_id = findViewById(R.id.tv_login_id);
        tv_login_name = findViewById(R.id.tv_login_name);
        tv_login_company = findViewById(R.id.tv_login_company);
        tv_login_phone = findViewById(R.id.tv_login_phone);
        tv_address_login = findViewById(R.id.tv_address_login);
        btn_tell = findViewById(R.id.btn_tell);

        Intent intent = getIntent(); // 1
        userID = intent.getStringExtra("userID"); // 2
        userPassword = intent.getStringExtra("userPassword");
        userCompany = intent.getStringExtra("userCompany");
        userName = intent.getStringExtra("userName");

        tv_login_id.setText(userID);
        tv_login_name.setText(userName);
        tv_login_company.setText(userCompany);
        tv_address_login.setSingleLine(true);    // ????????? ????????????
        tv_address_login.setEllipsize(TextUtils.TruncateAt.MARQUEE); // ????????? ?????????
        tv_address_login.setSelected(true);
        if(hashMap.containsKey(userCompany)){
            tv_login_phone.setText(hashMap.get(userCompany));
        }

        btn_tell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri
                        .parse("tel:/"+hashMap.get(userCompany)));
                startActivity(mIntent);
            }
        });


        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_login);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    String getFineDust() {
        StringBuffer buffer_dust = new StringBuffer();
        url_d = url_dust + address_dust_sido + "&pageNo=1&numOfRows=100&returnType=xml&serviceKey=" +  url_dust_service_Key + "&ver=1.0";

        try {
            URL url = new URL(url_d); // ???????????? ??? ?????? url??? URL ????????? ??????.
            InputStream is = url.openStream(); // url ????????? ??????????????? ??????

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new InputStreamReader(is, "UTF-8"));
            String tag;
            xpp.next();

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // ?????? ?????? ????????????

                        if (tag.equals("item")) ;
                        else if (tag.equals("pm25Value")) {
                            buffer_dust.append("????????????25 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n"); // ????????? ?????? ??????
                        } else if (tag.equals("coValue")) {
                            buffer_dust.append("??????????????? :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        } else if (tag.equals("pm10Value")) {
                            buffer_dust.append("????????????10 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("o3Grade")) {
                            buffer_dust.append("???????????? :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("coGrade")) {
                            buffer_dust.append("????????????????????? :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("o3Value")) {
                            buffer_dust.append("?????? :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("stationName")) {
                            buffer_dust.append("?????? :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }

                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // ?????? ?????? ????????????
                        if (tag.equals("item")) buffer_dust.append("\n"); // ????????? ?????????????????? ??? ?????????
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return buffer_dust.toString(); // ?????? ??? ?????? ??? StringBuffer ????????? ?????? ??????
    }

    String getFineDust_search() {
        StringBuffer buffer_dust = new StringBuffer();
        url_d_search = url_dust + Address_dust_sido_search + "&pageNo=1&numOfRows=100&returnType=xml&serviceKey=" +  url_dust_service_Key + "&ver=1.0";

        try {
            URL url = new URL(url_d_search); // ???????????? ??? ?????? url??? URL ????????? ??????.
            InputStream is = url.openStream(); // url ????????? ??????????????? ??????

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

            XmlPullParser xpp = factory.newPullParser();

            xpp.setInput(new InputStreamReader(is, "UTF-8"));
            String tag;
            xpp.next();

            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName(); // ?????? ?????? ????????????

                        if (tag.equals("item")) ;
                        else if (tag.equals("pm25Value")) {
                            buffer_dust.append("????????????25 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n"); // ????????? ?????? ??????
                        } else if (tag.equals("coValue")) {
                            buffer_dust.append("??????????????? :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        } else if (tag.equals("pm10Value")) {
                            buffer_dust.append("????????????10 :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("o3Grade")) {
                            buffer_dust.append("???????????? :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("coGrade")) {
                            buffer_dust.append("????????????????????? :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("o3Value")) {
                            buffer_dust.append("?????? :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }else if (tag.equals("stationName")) {
                            buffer_dust.append("?????? :");
                            xpp.next();
                            buffer_dust.append(xpp.getText());
                            buffer_dust.append("\n");
                        }

                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); // ?????? ?????? ????????????
                        if (tag.equals("item")) buffer_dust.append("\n"); // ????????? ?????????????????? ??? ?????????
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return buffer_dust.toString(); // ?????? ??? ?????? ??? StringBuffer ????????? ?????? ??????
    }

    // ?????? ??????
    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location resLocation = new Location("");
        try {
            addresses = geocoder.getFromLocationName(address, 10);
            Address addressLoc = addresses.get(0);

            resLocation.setLatitude(addressLoc.getLatitude());
            resLocation.setLongitude(addressLoc.getLongitude());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLocation;
    }

    // ????????? ?????? ?????? ??????
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        url_news = "";
        number = 0;
        getData1 getData1 = new getData1();
        switch (item.getItemId()){
            case R.id.action_1:
//                url_news = hashMap.get("??????");
                number = 0;
                getData1.execute();
                return true;
            case R.id.action_2:
//                url_news = hashMap.get("??????");
                number = 1;
                getData1.execute();
                return true;
            case R.id.action_3:
//                url_news = hashMap.get("??????");
                number = 2;
                getData1.execute();
                return true;
            case R.id.action_4:
//                url_news = hashMap.get("??????/??????");
                number = 3;
                getData1.execute();
                return true;
            case R.id.action_5:
//                url_news = hashMap.get("IT/??????");
                number = 4;
                getData1.execute();
                return true;
            case R.id.action_6:
//                url_news = hashMap.get("??????");
                number = 5;
                getData1.execute();
                return true;
        }
        return false;
    }

    private class getData1 extends AsyncTask<String, Void, String> {
        // String ?????? ?????? ???????????? ?????? ????????????, Boolean ?????? doInBackground ????????? ????????????.
        private String[] text = new String[6];
        @Override
        protected String doInBackground(String... params) {
            try {
                document[0] = Jsoup.connect(hashMap1.get("??????")).get();
                elements[0] = document[0].select(".cluster_text");

                document[1] = Jsoup.connect(hashMap1.get("??????")).get();
                elements[1] = document[1].select(".cluster_text");

                document[2] = Jsoup.connect(hashMap1.get("??????")).get();
                elements[2] = document[2].select(".cluster_text");

                document[3] = Jsoup.connect(hashMap1.get("??????/??????")).get();
                elements[3] = document[3].select(".cluster_text");

                document[4] = Jsoup.connect(hashMap1.get("IT/??????")).get();
                elements[4] = document[4].select(".cluster_text");

                document[5] = Jsoup.connect(hashMap1.get("??????")).get();
                elements[5] = document[5].select(".cluster_text");


                for(int i = 0; i < 6; i++) {
                    text[i] = elements[i].text();
                }

                return text[number];
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            tv_news = findViewById(R.id.tv_news);
            tv_news.setText(text[number]);
            tv_news.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            tv_news.setSelected(true);

        }
    }
}