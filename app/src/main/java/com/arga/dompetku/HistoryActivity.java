package com.arga.dompetku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {
    ImageView btnHome, btnWallet, btnSetting, btnProfile;
    TextView wallet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            btnHome = findViewById(R.id.imageViewHome4);
            btnWallet = findViewById(R.id.imageViewWallet4);
            btnSetting = findViewById(R.id.imageViewSetting4);
            btnProfile = findViewById(R.id.imageViewProfile4);
            wallet = findViewById(R.id.textViewWallet1);
            User user = SharedPrefManager.getInstance(this).getUser();

            wallet.setText("Rp." + String.valueOf(user.getWallet()));

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HistoryActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            btnWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HistoryActivity.this, WalletActivity.class);
                    startActivity(intent);
                }
            });

            btnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HistoryActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
            });

            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HistoryActivity.this, ProfilActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            Intent intent = new Intent(HistoryActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
