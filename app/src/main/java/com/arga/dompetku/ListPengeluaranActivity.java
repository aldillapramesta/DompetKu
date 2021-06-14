package com.arga.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListPengeluaranActivity extends AppCompatActivity {
    ListView listView;
    List<History> pengeluaranList;
    Integer id_user;
    Button btnBack;
    ImageView btnHome, btnWallet, btnSetting, btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pengeluaran);
        listView = (ListView) findViewById(R.id.listPengeluaran);
        btnBack = findViewById(R.id.button5);
        btnHome = findViewById(R.id.imageViewHome13);
        btnWallet = findViewById(R.id.imageViewWallet13);
        btnSetting = findViewById(R.id.imageViewSetting13);
        btnProfile = findViewById(R.id.imageViewProfile13);
        pengeluaranList = new ArrayList<>();
        showPengeluaran();
        User user = SharedPrefManager.getInstance(this).getUser();
        id_user = user.getId();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPengeluaranActivity.this, PengeluaranActivity.class);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPengeluaranActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPengeluaranActivity.this, WalletActivity.class);
                startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPengeluaranActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListPengeluaranActivity.this, ProfilActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showPengeluaran(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LIST_PENGELUARAN,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(LoginActivity.this.getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();
                        //System.out.print(response.toString());
                        try{
                            JSONArray array = new JSONArray(response);
                            //Toast.makeText(ListPendapatanActivity.this.getApplicationContext(), array.getString("message"), Toast.LENGTH_SHORT).show();
                            //Log.i("tagconvertstr", "["+response+"]");
                            //JSONArray array = obj.getJSONArray("");

                            for(int i = 0; i < array.length(); i++){
                                JSONObject pendObj = array.getJSONObject(i);
                                History peng = new History(pendObj.getInt("nominal"), pendObj.getString("kegiatan"));
                                pengeluaranList.add(peng);
                            }
                            PendapatanAdapter adapter = new PendapatanAdapter(pengeluaranList, getApplicationContext());
                            listView.setAdapter(adapter);


                        } catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_user", id_user.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
