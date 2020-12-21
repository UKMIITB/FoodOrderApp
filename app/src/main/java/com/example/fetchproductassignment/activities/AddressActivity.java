package com.example.fetchproductassignment.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.adapters.AddressActivityRecyclerViewAdapter;
import com.example.fetchproductassignment.app.EndPoints;
import com.example.fetchproductassignment.app.ItemClickListener;
import com.example.fetchproductassignment.app.SharedPrefConstants;
import com.example.fetchproductassignment.models.AddressModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddressActivity extends AppCompatActivity implements ItemClickListener {

    Button buttonAddAddress;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    AddressActivityRecyclerViewAdapter adapter;

    Toolbar toolbar;

    ArrayList<AddressModel> arrayList = new ArrayList<>();

    SharedPreferences sharedPreferences;
    String userID;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        toolbar = findViewById(R.id.custom_app_bar);
        toolbar.setTitle("Select Address");
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(SharedPrefConstants.SHARED_PREFERENCE, MODE_PRIVATE);
        userID = sharedPreferences.getString(SharedPrefConstants.USER_ID, null);

        init();
    }

    private void init() {
        progressBar = findViewById(R.id.progressbar);

        generateData(userID);

        buttonAddAddress = findViewById(R.id.button_add_address);
        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        adapter = new AddressActivityRecyclerViewAdapter(this, arrayList, this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        buttonAddAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddAddressActivity = new Intent(AddressActivity.this, AddAddressActivity.class);
                intentAddAddressActivity.putExtra(SharedPrefConstants.USER_ID, userID);
                startActivity(intentAddAddressActivity);
            }
        });
    }

    private void generateData(String userID) {
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, EndPoints.getAddress(userID), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int count = response.getInt("count");
                    JSONArray jsonArray = response.getJSONArray("data");
                    Gson gson = new GsonBuilder().create();

                    AddressModel addressModel = null;
                    for (int i = 0; i < count; i++) {
                        addressModel = gson.fromJson(String.valueOf(jsonArray.get(i)), AddressModel.class);
                        arrayList.add(addressModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressBar.setVisibility(View.GONE);
                adapter.setData(arrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "Error occurred: " + error.getMessage());
                Toast.makeText(AddressActivity.this, "Some error occurred in loading saved addresses", Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onItemClicked(View view, int position) {
        Intent intentPaymentActivity = new Intent(AddressActivity.this, PaymentActivity.class);
        intentPaymentActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intentPaymentActivity);
    }
}