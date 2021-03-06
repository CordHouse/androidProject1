package com.example.capstone_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone_1.databinding.AppBarLoginBinding;
import com.example.capstone_1.databinding.AppBarSearchIdPwBinding;

import org.json.JSONException;
import org.json.JSONObject;

public class Search_ID_Pw_Activity extends AppCompatActivity {
    private EditText et_search_ID_Pw, et_search_Name, et_search_Phone, et_search_Name_Pw, et_search_Phone_Pw;
    private Button btn_search_ID, btn_search_Pw;
    private ImageButton imageButton_search_back;
    private AppBarSearchIdPwBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach_id_pw);

        binding = AppBarSearchIdPwBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbarNewSearch);

        et_search_Name = findViewById(R.id.et_search_Name);
        et_search_Phone = findViewById(R.id.et_search_Phone);
        et_search_ID_Pw = findViewById(R.id.et_search_ID_Pw);
        et_search_Name_Pw = findViewById(R.id.et_search_Name_Pw);
        et_search_Phone_Pw = findViewById(R.id.et_search_Phone_Pw);

        btn_search_ID = findViewById(R.id.btn_search_ID);
        btn_search_Pw = findViewById(R.id.btn_search_Pw);

        btn_search_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search_Name = et_search_Name.getText().toString();
                String search_Phone = et_search_Phone.getText().toString();

                if(search_Name.equals("") || search_Phone.equals("")){
                    Toast.makeText(getApplicationContext(), "????????? ???????????????. ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // ????????? ????????? ??????
                                    String search_ID = jsonObject.getString("userID");
                                    Toast.makeText(getApplicationContext(), "????????? : " + search_ID, Toast.LENGTH_SHORT).show();
                                } else { // ????????? ????????? ??????
                                    Toast.makeText(getApplicationContext(), "???????????? ????????? ????????????.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    SearchRequest searchRequest = new SearchRequest("", search_Name, search_Phone, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Search_ID_Pw_Activity.this);
                    queue.add(searchRequest);
                }
            }
        });

        btn_search_Pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search_ID_Pw = et_search_ID_Pw.getText().toString();
                String search_Name_Pw = et_search_Name_Pw.getText().toString();
                String search_Phone_Pw = et_search_Phone_Pw.getText().toString();

                if(search_ID_Pw.equals("") || search_Name_Pw.equals("") || search_Phone_Pw.equals("")){
                    Toast.makeText(getApplicationContext(), "????????? ???????????????. ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean success = jsonObject.getBoolean("success");
                                if (success) { // ????????? ????????? ??????
                                    String search_Password = jsonObject.getString("userPassword");
                                    Toast.makeText(getApplicationContext(), "???????????? : " + search_Password, Toast.LENGTH_SHORT).show();
                                } else { // ????????? ????????? ??????
                                    Toast.makeText(getApplicationContext(), "????????? ????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    SearchRequest searchRequest = new SearchRequest(search_ID_Pw, search_Name_Pw, search_Phone_Pw, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Search_ID_Pw_Activity.this);
                    queue.add(searchRequest);
                }
            }
        });

        //????????????
        imageButton_search_back = findViewById(R.id.image_search_back);
        imageButton_search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Search_ID_Pw_Activity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // nav_header ?????? ??????
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
