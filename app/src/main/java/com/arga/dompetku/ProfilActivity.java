package com.arga.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfilActivity extends AppCompatActivity {
    ImageView btnHome, btnSetting, btnWallet;
    TextView fullname, username, city;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            btnHome = findViewById(R.id.imageViewHome7);
            btnSetting = findViewById(R.id.imageViewSetting7);
            btnWallet = findViewById(R.id.imageViewWallet7);
            fullname = findViewById(R.id.viewFullName);
            username = findViewById(R.id.viewUsername);
            city = findViewById(R.id.viewCity);
            User user = SharedPrefManager.getInstance(this).getUser();

            fullname.setText(String.valueOf(user.getFullname()));
            username.setText(String.valueOf(user.getUsername()));
            city.setText(String.valueOf(user.getCity()));

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfilActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            btnWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfilActivity.this, WalletActivity.class);
                    startActivity(intent);
                }
            });

            btnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProfilActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            Intent intent = new Intent(ProfilActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
