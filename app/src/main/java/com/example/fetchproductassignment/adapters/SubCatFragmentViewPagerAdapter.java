package com.example.fetchproductassignment.adapters;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fetchproductassignment.app.EndPoints;
import com.example.fetchproductassignment.fragments.SubCategoryFragment;
import com.example.fetchproductassignment.models.SubCategoryModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SubCatFragmentViewPagerAdapter extends FragmentPagerAdapter {
    int catId;
    Context context;
    int subCatCount = 0;
    ArrayList<SubCategoryModel> arrayList = new ArrayList<>();

    public SubCatFragmentViewPagerAdapter(@NonNull FragmentManager fm, int catId, Context context) {
        super(fm);
        this.catId = catId;
        this.context = context;
        generateData();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("subId", arrayList.get(position).getSubId());
        Fragment fragment = new SubCategoryFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return subCatCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return arrayList.get(position).getSubName();
    }

    private void generateData() {
        StringRequest request = new StringRequest(Request.Method.GET, EndPoints.getSubCategory(catId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    subCatCount = jsonObject.getInt("count");

                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Gson gson = new GsonBuilder().create();

                    for (int i = 0; i < subCatCount; i++) {
                        SubCategoryModel subCategoryModel = gson.fromJson(String.valueOf(jsonArray.get(i)), SubCategoryModel.class);
                        arrayList.add(subCategoryModel);
                    }
                    notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(context).add(request);
    }
}
