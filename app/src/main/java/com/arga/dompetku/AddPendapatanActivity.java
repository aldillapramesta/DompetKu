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

public class AddPendapatanActivity extends AppCompatActivity {
    EditText etNominal, etTanggal;
    ImageView btnHome, btnWallet, btnSetting, btnProfile;
    Button btnSubmit;
    Integer id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendapatan_baru);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            etNominal = findViewById(R.id.nominal);
            etTanggal = findViewById(R.id.date);
            btnHome = findViewById(R.id.imageViewHome9);
            btnWallet = findViewById(R.id.imageViewWallet9);
            btnSetting = findViewById(R.id.imageViewSetting9);
            btnProfile = findViewById(R.id.imageViewProfile9);
            btnSubmit = findViewById(R.id.btnSubmit);
            User user = SharedPrefManager.getInstance(this).getUser();
            id_user = user.getId();

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        addPendapatan();
                        AddPendapatanActivity.this.finish();
                        AddPendapatanActivity.this.startActivity(new Intent(AddPendapatanActivity.this.getApplicationContext(), MainActivity.class));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddPendapatanActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            btnWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddPendapatanActivity.this, WalletActivity.class);
                    startActivity(intent);
                }
            });

            btnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddPendapatanActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
            });

            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddPendapatanActivity.this, ProfilActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            Intent intent = new Intent(AddPendapatanActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void addPendapatan() throws ParseException {
        String stringNominal = etNominal.getText().toString().trim();
        if(TextUtils.isEmpty(stringNominal)){
            etNominal.setError("Silahkan masukkan pendapatan anda");
            etNominal.requestFocus();
            return;
        }
        final Integer nominal = Integer.parseInt(stringNominal);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date tanggal;
        String dob_var = etTanggal.getText().toString().trim();
        if(TextUtils.isEmpty(dob_var)){
            etTanggal.setError("Silahkan masukkan tanggal");
            etTanggal.requestFocus();
            return;
        }
        tanggal = formatter.parse(dob_var);
        final String tanggalFix = formatter.format(tanggal);



        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ADD_PENDAPATAN,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(AddPendapatanActivity.this.getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();
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
                                        listJson.getString("tanggal"),
                                        null
                                );
                                SharedPrefManager.getInstance(getApplicationContext()).addHistory(list);
                            }
                            else{
                                Toast.makeText(AddPendapatanActivity.this.getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                //assert tanggal != null;
                params.put("tanggal", tanggalFix);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
