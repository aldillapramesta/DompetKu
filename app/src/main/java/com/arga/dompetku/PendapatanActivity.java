package com.arga.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PendapatanActivity extends AppCompatActivity {
    ImageView btnAdd, btnList, btnHome, btnWallet, btnSetting, btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pendapatan);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            btnAdd = findViewById(R.id.imageViewAdd2);
            btnList = findViewById(R.id.imageViewList2);
            btnHome = findViewById(R.id.imageViewHome2);
            btnWallet = findViewById(R.id.imageViewWallet2);
            btnSetting = findViewById(R.id.imageViewSetting2);
            btnProfile = findViewById(R.id.imageViewProfile2);
            User user = SharedPrefManager.getInstance(this).getUser();

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PendapatanActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            btnWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PendapatanActivity.this, WalletActivity.class);
                    startActivity(intent);
                }
            });

            btnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PendapatanActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
            });

            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PendapatanActivity.this, ProfilActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            Intent intent = new Intent(PendapatanActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
