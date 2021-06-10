package com.arga.dompetku;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText etNamaDepan, etNamaBelakang, etUsername, etPassword, etConfPass, etCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        changeStatusBarColor();
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            return;
        }
        //String fullname = R.id.namadepan + " " + R.id.namabelakang;
        etNamaDepan = findViewById(R.id.namadepan);
        etNamaBelakang = findViewById(R.id.namabelakang);
        etUsername = findViewById(R.id.username);
        etPassword = findViewById(R.id.password);
        etConfPass = findViewById(R.id.confpassword);
        etCity = findViewById(R.id.city);

        findViewById(R.id.btnCreateAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                registerUser();
            }
        });

        findViewById(R.id.txtSignin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    private void registerUser() {
        final String namaDepan = etNamaDepan.getText().toString().trim();
        final String namaBelakang = etNamaBelakang.getText().toString().trim();
        final String fullname = namaDepan + " " + namaBelakang;
        final String username = etUsername.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();
        final String confPass = etConfPass.getText().toString().trim();
        final String city = etCity.getText().toString().trim();
        final Integer wallet = 0;

        if(TextUtils.isEmpty(namaDepan)){
            etNamaDepan.setError("Please enter your first name");
            etNamaDepan.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(namaBelakang)){
            etNamaBelakang.setError("Please enter your last name");
            etNamaBelakang.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(username)){
            etUsername.setError("Please enter your username");
            etUsername.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(confPass)){
            etConfPass.setError("Please enter your confirm password");
            etConfPass.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(city)){
            etCity.setError("Please enter your city name");
            etCity.requestFocus();
            return;
        }

        if(!TextUtils.equals(password, confPass)){
            etConfPass.setError("Your confirm password not match with your password");
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            Log.i("tagconvertstr", "["+response+"]");
                            JSONObject obj = new JSONObject(response);
                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                User user = new User(
                                        userJson.getInt("id_user"),
                                        userJson.getString("username"),
                                        userJson.getString("fullname"),
                                        userJson.getString("city"),
                                        userJson.getInt("wallet")
                                );

                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                params.put("fullname", fullname);
                params.put("city", city);
                params.put("wallet", wallet.toString());
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
//    public void onLoginClick(View view){
//        startActivity(new Intent(this,LoginActivity.class));
//        overridePendingTransition(R.anim.slide_in_left,android.R.anim.slide_out_right);
//    }
//    public void openActivity(View View){
//        startActivity(new Intent(this,RegisterActivity.class));
//        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
//    }
//    public void closeActivity(View View){
//        finish();
//        overridePendingTransition(R.anim.stay, R.anim.slide_down);
//    }
}
