package com.example.fetchproductassignment.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.adapters.SubCatFragmentViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class SubCategoryActivity extends BaseActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    SubCatFragmentViewPagerAdapter adapter;
    Context context;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        Intent intent = getIntent();
        int catId = intent.getIntExtra(MainActivity.CATEGORYID, 0);
        String catName = intent.getStringExtra(MainActivity.CATEGORY_NAME);

        //Setup ToolBar
        toolbar = findViewById(R.id.custom_app_bar);
        toolbar.setTitle(catName);
        setSupportActionBar(toolbar);

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