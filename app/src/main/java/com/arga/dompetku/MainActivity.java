package com.arga.dompetku;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView fullname, wallet;
    ImageView btnWishlist, btnPendapatan, btnPengeluaran, btnHistory, btnWallet, btnSetting, btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        User user = SharedPrefManager.getInstance(this).getUser();
        SharedPrefManager.getInstance(this).userLogin(user);
        setContentView(R.layout.activity_home);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            fullname = findViewById(R.id.textViewFullname);
            wallet = findViewById(R.id.textViewWallet);
            btnWishlist = findViewById(R.id.imageViewWishlist);
            btnPendapatan = findViewById(R.id.imageViewPendapatan);
            btnPengeluaran = findViewById(R.id.imageViewPengeluaran);
            btnHistory = findViewById(R.id.imageViewHistory);
            btnWallet = findViewById(R.id.imageViewWallet);
            btnSetting = findViewById(R.id.imageViewSetting);
            btnProfile = findViewById(R.id.imageViewProfile);


            fullname.setText(String.valueOf(user.getFullname()));
            wallet.setText("Rp." + String.valueOf(user.getWallet()));

            btnWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, WishlistActivity.class);
                    startActivity(intent);
                }
            });

            btnPendapatan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, PendapatanActivity.class);
                    startActivity(intent);
                }
            });

            btnPengeluaran.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, PengeluaranActivity.class);
                    startActivity(intent);
                }
            });

            btnHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(intent);
                }
            });

            btnWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, WalletActivity.class);
                    startActivity(intent);
                }
            });

            btnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
            });

            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
