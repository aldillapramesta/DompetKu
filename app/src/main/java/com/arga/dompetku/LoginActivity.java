package com.arga.dompetku;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
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

public class LoginActivity extends AppCompatActivity {
    EditText etName, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this, MainActivity.class));
            overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        }

        etName = findViewById(R.id.etUserName);
        etPassword = findViewById(R.id.etUserPassword);

        findViewById(R.id.btnLogin).setOnClickListener(view -> userLogin());

        findViewById(R.id.txtSignUp).setOnClickListener(view -> {
            finish();
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        });
    }

    private void userLogin(){
        final String username = etName.getText().toString();
        final String password = etPassword.getText().toString();

        if(TextUtils.isEmpty(username)){
            etName.setError("Please enter your username");
            etName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password)){
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(LoginActivity.this.getApplicationContext(), "Response :" + response.toString(), Toast.LENGTH_LONG).show();
                        System.out.print(response.toString());
                        try{
                            JSONObject obj = new JSONObject(response);

                            if(!obj.getBoolean("error")){
                                Toast.makeText(LoginActivity.this.getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                JSONObject userJson = obj.getJSONObject("user");

                                User user = new User(
                                        userJson.getInt("id_user"),
                                        userJson.getString("username"),
                                        userJson.getString("fullname"),
                                        userJson.getString("city"),
                                        userJson.getInt("wallet")
                                );

                                SharedPrefManager.getInstance(LoginActivity.this.getApplicationContext()).userLogin(user);
                                LoginActivity.this.finish();
                                LoginActivity.this.startActivity(new Intent(LoginActivity.this.getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(LoginActivity.this.getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
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
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
//    public void openActivity(View View){
//        startActivity(new Intent(this,RegisterActivity.class));
//        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
//    }
//    public void closeActivity(View View){
//        finish();
//        overridePendingTransition(R.anim.stay, R.anim.slide_down);
//    }
}