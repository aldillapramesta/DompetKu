package com.arga.dompetku;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView btnInformation, btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            btnInformation = findViewById(R.id.imageViewInformation);
            btnLogout = findViewById(R.id.imageViewLogout);
            User user = SharedPrefManager.getInstance(this).getUser();

            btnLogout.setOnClickListener(this);
            btnInformation.setOnClickListener(this);
        }
        else{
            Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void onClick(View view){
        if(view.equals(btnLogout)){
            SharedPrefManager.getInstance(getApplicationContext()).logout();
        }
        else if(view.equals(btnInformation)){
            SettingActivity.this.finish();
            SettingActivity.this.startActivity(new Intent(SettingActivity.this.getApplicationContext(), InformationActivity.class));
        }
    }
}
