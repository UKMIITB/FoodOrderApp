package com.example.fetchproductassignment.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.adapters.SubCatFragmentViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class SubCategoryActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    SubCatFragmentViewPagerAdapter adapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        Intent intent = getIntent();
        int catId = intent.getIntExtra(MainActivity.CATEGORYID, 0);
        init(catId);
    }

    private void init(int catId) {
        context = getApplicationContext();
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tab_layout);
        adapter = new SubCatFragmentViewPagerAdapter(getSupportFragmentManager(), catId, context);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}