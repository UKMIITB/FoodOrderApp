package com.example.fetchproductassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.app.EndPoints;
import com.example.fetchproductassignment.app.SharedPrefConstants;
import com.example.fetchproductassignment.models.AddressModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class AddAddressActivity extends AppCompatActivity {

    Toolbar toolbar;

    EditText editTextUserID, editTextHouseNumber, editTextStreetName, editTextCity, editTextPincode;
    RadioGroup radioGroupAddressType;
    RadioButton radioButtonHome, radioButtonOffice;
    Button buttonSaveAddress;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        Intent intent = getIntent();
        String userID = intent.getStringExtra(SharedPrefConstants.USER_ID);

        setupToolbar();
        init(userID);
    }

    private void init(String userID) {
        editTextUserID = findViewById(R.id.edit_text_user_id);
        editTextUserID.setText(userID);
        editTextUserID.setFocusable(false);
        editTextUserID.setFocusableInTouchMode(false);
        editTextUserID.setEnabled(false);
        editTextUserID.setCursorVisible(false);

        editTextHouseNumber = findViewById(R.id.edit_text_house_number);
        editTextStreetName = findViewById(R.id.edit_text_street_name);
        editTextCity = findViewById(R.id.edit_text_city);
        editTextPincode = findViewById(R.id.edit_text_pincode);
        radioGroupAddressType = findViewById(R.id.radio_group_address_type);
        radioButtonHome = findViewById(R.id.radio_button_home);
        radioButtonOffice = findViewById(R.id.radio_button_office);
        buttonSaveAddress = findViewById(R.id.button_save_address);

        progressBar = findViewById(R.id.progressbar);

        buttonSaveAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkInputDetails())
                    showErrorDialogue("Please enter all the details");
                else {
                    String userId = editTextUserID.getText().toString();
                    long pincode = Long.parseLong(editTextPincode.getText().toString());
                    String city = editTextCity.getText().toString();
                    String streetName = editTextStreetName.getText().toString();
                    String houseNo = editTextHouseNumber.getText().toString();

                    String type = "";
                    if (radioGroupAddressType.getCheckedRadioButtonId() == R.id.radio_button_home)
                        type = "home";
                    else
                        type = "office";

                    AddressModel addressModel = new AddressModel(userId, pincode, city, streetName, houseNo, type);
                    Gson gson = new GsonBuilder().create();

                    JSONObject addAddressObject = null;
                    try {
                        addAddressObject = new JSONObject(gson.toJson(addressModel, AddressModel.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    progressBar.setVisibility(View.VISIBLE);
                    postAddAddress(addAddressObject);
                }
            }
        });
    }

    private void postAddAddress(JSONObject addAddressObject) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, EndPoints.postAddress(), addAddressObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(AddAddressActivity.this, "Address saved successfully", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(AddAddressActivity.this, AddressActivity.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                showErrorDialogue("Some error occurred while trying to save address.\nPlease try again");
            }
        });
        Volley.newRequestQueue(this).add(request);
    }


    private void showErrorDialogue(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle("Address save failed");
        builder.setPositiveButton("OK", null);
        builder.setCancelable(true);
        builder.create().show();
    }

    private boolean checkInputDetails() {
        if (editTextUserID.getText().toString().equals(""))
            return false;
        if (editTextHouseNumber.getText().toString().equals(""))
            return false;
        if (editTextStreetName.getText().toString().equals(""))
            return false;
        if (editTextCity.getText().toString().equals(""))
            return false;
        if (editTextPincode.getText().toString().equals(""))
            return false;
        return true;
    }


    private void setupToolbar() {
        toolbar = findViewById(R.id.custom_app_bar);
        toolbar.setTitle("Select Address");
        setSupportActionBar(toolbar);
    }
}