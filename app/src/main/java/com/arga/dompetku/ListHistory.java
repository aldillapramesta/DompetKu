package com.arga.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
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

public class ListHistory extends AppCompatActivity {
    ListView listView;
    List<History> historyList;
    Integer id_user;
    ImageView btnHome, btnWallet, btnSetting, btnProfile;
    TextView wallet;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        listView = (ListView) findViewById(R.id.imageListHistory);
        btnHome = findViewById(R.id.imageViewHome4);
        btnWallet = findViewById(R.id.imageViewWallet4);
        btnSetting = findViewById(R.id.imageViewSetting4);
        btnProfile = findViewById(R.id.imageViewProfile4);
        wallet = findViewById(R.id.textViewWallet1);
        historyList = new ArrayList<>();
        showHistory();
        User user = SharedPrefManager.getInstance(this).getUser();
        id_user = user.getId();
        wallet.setText("Rp. " + String.valueOf(user.getWallet()));

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListHistory.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListHistory.this, WalletActivity.class);
                startActivity(intent);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListHistory.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListHistory.this, ProfilActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showHistory(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LIST_HISTORY,
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
                                History pend = new History(pendObj.getString("jenis"), pendObj.getInt("nominal"), pendObj.getString("tanggal"), pendObj.getString("kegiatan"));
                                historyList.add(pend);
                            }
                            HistoryAdapter adapter = new HistoryAdapter(historyList, getApplicationContext());
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
