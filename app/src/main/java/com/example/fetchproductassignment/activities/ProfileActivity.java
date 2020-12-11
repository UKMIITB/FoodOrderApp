package com.example.fetchproductassignment.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.app.SharedPrefConstants;

public class ProfileActivity extends BaseActivity {

    TextView textViewName, textViewEmail, textViewMobile;
    Button buttonLogout;

    SharedPreferences sharedPreferences;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbar = findViewById(R.id.custom_app_bar);
        toolbar.setTitle("Profile Activity");
        setSupportActionBar(toolbar);

        init();
        setData();
    }

    private void setData() {
        textViewName.setText("Name: " + sharedPreferences.getString(SharedPrefConstants.USER_NAME, "Guest"));
        textViewEmail.setText("Email: " + sharedPreferences.getString(SharedPrefConstants.USER_EMAIL, "Guest@guest.com"));
        textViewMobile.setText("Contact No: " + sharedPreferences.getString(SharedPrefConstants.USER_MOBILE, "1234567890"));
    }

    private void init() {
        textViewName = findViewById(R.id.textview_name);
        textViewEmail = findViewById(R.id.textview_email);
        textViewMobile = findViewById(R.id.textview_mobile);
        buttonLogout = findViewById(R.id.button_logout);

        sharedPreferences = getSharedPreferences(SharedPrefConstants.SHARED_PREFERENCE, MODE_PRIVATE);

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                finishAffinity();
                startActivity(intent);
            }
        });
    }
}