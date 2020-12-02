package com.example.fetchproductassignment.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fetchproductassignment.adapters.MainActivityViewPagerAdapter;
import com.example.fetchproductassignment.app.ItemClickListener;
import com.example.fetchproductassignment.models.AllCategoryModel;
import com.example.fetchproductassignment.adapters.CategoryRecyclerViewAdapter;
import com.example.fetchproductassignment.app.EndPoints;
import com.example.fetchproductassignment.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ItemClickListener {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CategoryRecyclerViewAdapter adapter;

    ArrayList<AllCategoryModel> arrayList = new ArrayList<>();

    ViewPager viewPager;
    MainActivityViewPagerAdapter mainActivityViewPagerAdapter;

    public static final String CATEGORYID = "catId";

    private final String[] imageUrls = new String[]{
            "https://cdn.pixabay.com/photo/2016/11/11/23/34/cat-1817970_960_720.jpg",
            "https://cdn.pixabay.com/photo/2017/12/21/12/26/glowworm-3031704_960_720.jpg",
            "https://cdn.pixabay.com/photo/2017/12/24/09/09/road-3036620_960_720.jpg",
            "https://cdn.pixabay.com/photo/2017/11/07/00/07/fantasy-2925250_960_720.jpg",
            "https://cdn.pixabay.com/photo/2017/10/10/15/28/butterfly-2837589_960_720.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                    adapter.setData(arrayList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage());
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
    }

    @Override
    public void onItemClicked(View view, int position) {
        Intent intent = new Intent(this, SubCategoryActivity.class);
        intent.putExtra(CATEGORYID, arrayList.get(position).getCatId());
        startActivity(intent);
    }
}