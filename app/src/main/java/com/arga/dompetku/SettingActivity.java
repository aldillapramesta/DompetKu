package com.arga.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {
    ImageView btnInformation, btnLogout, btnHome, btnWallet, btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            btnInformation = findViewById(R.id.imageViewInformation);
            btnLogout = findViewById(R.id.imageViewLogout);
            btnHome = findViewById(R.id.imageViewHome6);
            btnWallet = findViewById(R.id.imageViewWallet6);
            btnProfile = findViewById(R.id.imageViewProfile6);
            User user = SharedPrefManager.getInstance(this).getUser();

            //btnLogout.setOnClickListener(this);
            //btnInformation.setOnClickListener(this);

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            btnWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SettingActivity.this, WalletActivity.class);
                    startActivity(intent);
                }
            });

            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SettingActivity.this, ProfilActivity.class);
                    startActivity(intent);
                }
            });

            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPrefManager.getInstance(getApplicationContext()).logout();
                }
            });

            btnInformation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SettingActivity.this, InformationActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

//    public void onClick(View view){
//        if(view.equals(btnLogout)){
//            SharedPrefManager.getInstance(getApplicationContext()).logout();
//        }
//        else if(view.equals(btnInformation)){
//            SettingActivity.this.finish();
//            SettingActivity.this.startActivity(new Intent(SettingActivity.this.getApplicationContext(), InformationActivity.class));
//        }
//    }
}
