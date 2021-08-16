package com.kali_corporation.freeonlinetranslation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.kali_corporation.freeonlinetranslation.nt.tool.NtListener;
import com.kali_corporation.freeonlinetranslation.nt.tool.NtManager;
import com.kali_corporation.freeonlinetranslation.R;

public class SplashScreen extends AppCompatActivity {

    public static boolean isN=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_splash_screen);
        loadD();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//
//            }
//        },1*1000);


    }

    private void loadD(){
        NtManager.initPost(this, new NtListener() {
            @Override
            public void ntstate(int st) {
                isN = true;
                Intent i = new Intent(SplashScreen.this, HomeActivity.class);
                startActivity(i);
                finish();
            }

        });
    }

}