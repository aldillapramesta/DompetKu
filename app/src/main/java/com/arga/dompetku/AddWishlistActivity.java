package com.arga.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

public class AddWishlistActivity extends AppCompatActivity {
    EditText etWishlist, etTahun, etNominal;
    ImageView btnHome, btnWallet, btnSetting, btnProfile;
    Button btnSubmit;
    Integer id_user;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist_baru);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            etNominal = findViewById(R.id.imageView23);
            etWishlist = findViewById(R.id.imageView20);
            etTahun = findViewById(R.id.imageView21);
            btnHome = findViewById(R.id.imageViewHome11);
            btnWallet = findViewById(R.id.imageViewWallet11);
            btnSetting = findViewById(R.id.imageViewSetting11);
            btnProfile = findViewById(R.id.imageViewProfile11);
            btnSubmit = findViewById(R.id.button3);
            User user = SharedPrefManager.getInstance(this).getUser();
            id_user = user.getId();

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        addWishlist();
                        AddWishlistActivity.this.finish();
                        AddWishlistActivity.this.startActivity(new Intent(AddWishlistActivity.this.getApplicationContext(), MainActivity.class));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddWishlistActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            btnWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddWishlistActivity.this, WalletActivity.class);
                    startActivity(intent);
                }
            });

            btnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddWishlistActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
            });

            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddWishlistActivity.this, ProfilActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            Intent intent = new Intent(AddWishlistActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void addWishlist() throws ParseException {
        final String wish = etWishlist.getText().toString().trim();
        if(TextUtils.isEmpty(wish)){
            etWishlist.setError("Silahkan masukkan wishlist Anda");
            etWishlist.requestFocus();
            return;
        }
        String stringTahun = etTahun.getText().toString().trim();
        if(TextUtils.isEmpty(stringTahun)){
            etTahun.setError("Silahkan masukkan tahun");
            etTahun.requestFocus();
            return;
        }
        final Integer tahun = Integer.parseInt(stringTahun);
        String stringHarga = etNominal.getText().toString().trim();
        if(TextUtils.isEmpty(stringHarga)){
            etNominal.setError("Silahkan masukkan nominal wishlist Anda");
            etNominal.requestFocus();
            return;
        }
        final Integer harga = Integer.parseInt(stringHarga);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_ADD_WISHLIST,
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
                                Wishlist list = new Wishlist(
                                        listJson.getInt("id_wishlist"),
                                        listJson.getInt("id_user"),
                                        listJson.getString("wish"),
                                        listJson.getInt("tahun"),
                                        listJson.getInt("harga")
                                );
                                SharedPrefManager.getInstance(getApplicationContext()).addWishlist(list);
                            }
                            else{
                                Toast.makeText(AddWishlistActivity.this.getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("wish", wish);
                params.put("tahun", tahun.toString());
                params.put("harga", harga.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
