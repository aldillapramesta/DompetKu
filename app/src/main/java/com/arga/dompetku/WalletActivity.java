package com.arga.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WalletActivity extends AppCompatActivity {
    ImageView btnHome, btnSetting, btnProfile;
    TextView wallet;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            wallet = findViewById(R.id.textViewWallet2);
            btnHome = findViewById(R.id.imageViewHome5);
            btnSetting = findViewById(R.id.imageViewSetting5);
            btnProfile = findViewById(R.id.imageViewProfile5);
            User user = SharedPrefManager.getInstance(this).getUser();

            wallet.setText("Rp." + String.valueOf(user.getWallet()));

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WalletActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            btnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WalletActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
            });

            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WalletActivity.this, ProfilActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            Intent intent = new Intent(WalletActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
