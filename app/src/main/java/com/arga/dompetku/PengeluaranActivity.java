package com.arga.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PengeluaranActivity extends AppCompatActivity {
    ImageView btnAdd, btnList, btnHome, btnWallet, btnSetting, btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            btnAdd = findViewById(R.id.imageViewAdd3);
            btnList = findViewById(R.id.imageViewList3);
            btnHome = findViewById(R.id.imageViewHome3);
            btnWallet = findViewById(R.id.imageViewWallet3);
            btnSetting = findViewById(R.id.imageViewSetting3);
            btnProfile = findViewById(R.id.imageViewProfile3);
            User user = SharedPrefManager.getInstance(this).getUser();

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PengeluaranActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            btnWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PengeluaranActivity.this, WalletActivity.class);
                    startActivity(intent);
                }
            });

            btnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PengeluaranActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
            });

            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PengeluaranActivity.this, ProfilActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            Intent intent = new Intent(PengeluaranActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
