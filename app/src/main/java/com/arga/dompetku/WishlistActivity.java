package com.arga.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class WishlistActivity extends AppCompatActivity {
    ImageView btnAdd, btnList, btnHome, btnWallet, btnSetting, btnProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            btnAdd = findViewById(R.id.imageViewAdd1);
            btnList = findViewById(R.id.imageViewList1);
            btnHome = findViewById(R.id.imageViewHome1);
            btnWallet = findViewById(R.id.imageViewWallet1);
            btnSetting = findViewById(R.id.imageViewSetting1);
            btnProfile = findViewById(R.id.imageViewProfile1);
            User user = SharedPrefManager.getInstance(this).getUser();

            btnList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WishlistActivity.this, ListWishlistActivity.class);
                    startActivity(intent);
                }
            });

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WishlistActivity.this, AddWishlistActivity.class);
                    startActivity(intent);
                }
            });

            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WishlistActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });

            btnWallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WishlistActivity.this, WalletActivity.class);
                    startActivity(intent);
                }
            });

            btnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WishlistActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
            });

            btnProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(WishlistActivity.this, ProfilActivity.class);
                    startActivity(intent);
                }
            });
        }
        else{
            Intent intent = new Intent(WishlistActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
