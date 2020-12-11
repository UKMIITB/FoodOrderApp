package com.example.fetchproductassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.adapters.CategoryRecyclerViewAdapter;
import com.example.fetchproductassignment.adapters.MainActivityViewPagerAdapter;
import com.example.fetchproductassignment.app.EndPoints;
import com.example.fetchproductassignment.app.ItemClickListener;
import com.example.fetchproductassignment.models.AllCategoryModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.pzienowicz.autoscrollviewpager.AutoScrollViewPager;

public class MainActivity extends BaseActivity implements ItemClickListener {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CategoryRecyclerViewAdapter adapter;

    ArrayList<AllCategoryModel> arrayList = new ArrayList<>();

    AutoScrollViewPager viewPager;
    MainActivityViewPagerAdapter mainActivityViewPagerAdapter;

    Toolbar toolbar;

    public static final String CATEGORYID = "catId";
    public static final String CATEGORY_NAME = "catName";

    private final String[] imageUrls = new String[]{
            "https://i.gadgets360cdn.com/large/flipkart_big_billion_days_sale_2020_1601713929030.jpg",
            "https://images.indianexpress.com/2020/10/Untitled-design-2020-10-15T171350.830.jpg",
            "https://cdn.grabon.in/gograbon/images/merchant/1545547346420.png",
            "https://cdn.static-zoutons.com/images/originals/blog/zomatoexistinguseroffers_1530610267.jpg",
            "https://www.dineout.co.in/blog/wp-content/uploads/2018/10/WhatsApp-Image-2018-10-18-at-8.06.23-PM.jpeg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup ToolBar
        toolbar = findViewById(R.id.custom_app_bar);
        toolbar.setTitle("Home Page");
        setSupportActionBar(toolbar);

        init();
    }

    private void generateData() {
        StringRequest request = new StringRequest(Request.Method.GET, EndPoints.getCategory(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int count = jsonObject.getInt("count");

                    JSONArray data = jsonObject.getJSONArray("data");
                    Gson gson = new GsonBuilder().create();

                    for (int i = 0; i < count; i++) {
                        AllCategoryModel allCategoryModel = gson.fromJson(String.valueOf(data.get(i)), AllCategoryModel.class);
                        arrayList.add(allCategoryModel);
                    }

                    ProgressBar progressBar = findViewById(R.id.progressbar);
                    progressBar.setVisibility(View.GONE);

                    adapter.setData(arrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", "" + error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    private void init() {
        generateData();
        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CategoryRecyclerViewAdapter(this, arrayList, this);
        recyclerView.setAdapter(adapter);

        viewPager = findViewById(R.id.viewpager);
        mainActivityViewPagerAdapter = new MainActivityViewPagerAdapter(this, imageUrls);
        viewPager.setAdapter(mainActivityViewPagerAdapter);

        //Properties of Auto Scroll ViewPager
        viewPager.setDirection(AutoScrollViewPager.Direction.RIGHT);
        viewPager.setInterval(2000);
        viewPager.setCycle(true);
        viewPager.setBorderAnimation(true);
        viewPager.setSlideBorderMode(AutoScrollViewPager.SlideBorderMode.TO_PARENT);
        viewPager.startAutoScroll();

        //Update options menu
        invalidateOptionsMenu();
    }

    @Override
    public void onItemClicked(View view, int position) {
        Intent intent = new Intent(this, SubCategoryActivity.class);
        intent.putExtra(CATEGORYID, arrayList.get(position).getCatId());
        intent.putExtra(CATEGORY_NAME, arrayList.get(position).getCatName());
        startActivity(intent);
    }
}