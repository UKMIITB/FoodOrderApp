package com.example.fetchproductassignment.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.activities.ProductActivity;
import com.example.fetchproductassignment.adapters.SubCatProductRecyclerviewAdapter;
import com.example.fetchproductassignment.app.EndPoints;
import com.example.fetchproductassignment.app.ItemClickListener;
import com.example.fetchproductassignment.models.SubCategoryProductModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductFragment extends Fragment implements ItemClickListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    SubCatProductRecyclerviewAdapter adapter;
    ArrayList<SubCategoryProductModel> arrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int subId = getArguments().getInt("subId", 1);
        generateData(subId);
        Log.d("TAG", "Oncreate :" + subId);
    }

    private void generateData(int subId) {
        Log.d("TAG", "In generate Data");
        Log.d("TAG", EndPoints.getSubCategoryProducts(subId));
        StringRequest request = new StringRequest(Request.Method.GET, EndPoints.getSubCategoryProducts(subId), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("TAG", "Inside try");
                    JSONObject jsonObject = new JSONObject(response);
                    int count = jsonObject.getInt("count");
                    Log.d("TAG", "count is :" + count);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    Gson gson = new GsonBuilder().create();

                    for (int i = 0; i < count; i++) {
                        SubCategoryProductModel subCategoryProductModel = gson.fromJson(String.valueOf(jsonArray.get(i)), SubCategoryProductModel.class);
                        arrayList.add(subCategoryProductModel);
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
        Volley.newRequestQueue(getActivity()).add(request);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        init(view);
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SubCatProductRecyclerviewAdapter(getContext(), arrayList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(View view, int position) {
        Intent intent = new Intent(getContext(), ProductActivity.class);
        intent.putExtra("ProductModel", arrayList.get(position));
        startActivity(intent);
    }
}