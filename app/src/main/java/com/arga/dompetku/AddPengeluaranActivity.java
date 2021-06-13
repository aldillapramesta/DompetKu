package com.arga.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddPengeluaranActivity extends AppCompatActivity {
    EditText etNominal, etKegiatan;
    ImageView btnHome, btnWallet, btnSetting, btnProfile;
    Button btnSubmit;
    Integer id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran_baru);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            etNominal = findViewById(R.id.imageView20);
            etKegiatan = findViewById(R.id.imageView21);
            btnHome = findViewById(R.id.imageViewHome10);
            btnWallet = findViewById(R.id.imageViewWallet10);
            btnSetting = findViewById(R.id.imageViewSetting10);
            btnProfile = findViewById(R.id.imageViewProfile10);
            btnSubmit = findViewById(R.id.button1);
            User user = SharedPrefManager.getInstance(this).getUser();
            id_user = user.getId();

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        addPengeluaran();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddPengeluaranActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            btnWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddPengeluaranActivity.this, WalletActivity.class);
                    startActivity(intent);
                }
            });

            btnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddPengeluaranActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
            });

            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddPengeluaranActivity.this, ProfilActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            Intent intent = new Intent(AddPengeluaranActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void addPengeluaran() throws ParseException {
        String stringNominal = etNominal.getText().toString().trim();
        if(TextUtils.isEmpty(stringNominal)){
            etNominal.setError("Silahkan masukkan pendapatan anda");
            etNominal.requestFocus();
            return;
        }
        final Integer nominal = Integer.parseInt(stringNominal);
        final String kegiatan = etKegiatan.getText().toString().trim();
        if(TextUtils.isEmpty(kegiatan)){
            etKegiatan.setError("Silahkan masukkan kegiatan anda");
            etKegiatan.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ADD_PENGELUARAN,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(AddPengeluaranActivity.this.getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();
                        //System.out.print(response.toString());
                        try{
                            //Log.i("tagconvertstr", "["+response+"]");
                            JSONObject obj = new JSONObject(response);
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject listJson = obj.getJSONObject("list");

                                //creating a new user object
                                History list = new History(
                                        listJson.getInt("id_history"),
                                        listJson.getInt("id_user"),
                                        listJson.getString("jenis"),
                                        listJson.getInt("nominal"),
                                        null,
                                        listJson.getString("kegiatan")
                                );
                                SharedPrefManager.getInstance(getApplicationContext()).addHistory(list);
                            }
                            else{
                                Toast.makeText(AddPengeluaranActivity.this.getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
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
                params.put("nominal", nominal.toString());
                params.put("kegiatan", kegiatan);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
