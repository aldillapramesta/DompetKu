package com.arga.dompetku;

import android.content.Intent;
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
            User user = SharedPrefManager.getInstance(this).getUser();

            fullname.setText(String.valueOf(user.getFullname()));
            wallet.setText(user.getWallet());

            btnSetting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, SettingActivity.class);
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
