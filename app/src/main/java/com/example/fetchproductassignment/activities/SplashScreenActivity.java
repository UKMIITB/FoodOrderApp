package com.example.fetchproductassignment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.app.SharedPrefConstants;

public class SplashScreenActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        init();

        Handler handler = new Handler();
        int delayedTime = 2000;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadNextActivity();
            }
        }, delayedTime);
    }

    private void init() {
        sharedPreferences = getSharedPreferences(SharedPrefConstants.SHARED_PREFERENCE, MODE_PRIVATE);
    }

    private void loadNextActivity() {
        if (!sharedPreferences.getBoolean(SharedPrefConstants.IS_LOGGED_IN, false)) {
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
        }
        finish();
    }
}