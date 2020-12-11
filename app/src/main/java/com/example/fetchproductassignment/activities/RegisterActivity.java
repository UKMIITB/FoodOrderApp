package com.example.fetchproductassignment.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.app.EndPoints;
import com.example.fetchproductassignment.models.RegistrationModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextName, editTextEmail, editTextPhone, editTextPassword;
    Button buttonRegister, buttonSkipLogin;
    TextView textViewLogin;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.custom_app_bar);
        toolbar.setTitle("Registration Section");
        setSupportActionBar(toolbar);
    }

    private void init() {
        editTextName = findViewById(R.id.edit_text_name);
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPhone = findViewById(R.id.edit_text_phone);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonRegister = findViewById(R.id.button_register);
        buttonSkipLogin = findViewById(R.id.button_skip_login);
        textViewLogin = findViewById(R.id.textview_login);

        buttonRegister.setOnClickListener(this);
        buttonSkipLogin.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_register:

                if (!checkEnteredDetails()) {
                    Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                    break;
                }
                String name = editTextName.getText().toString();
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();
                String password = editTextPassword.getText().toString();

                if (password.length() < 6) {
                    Toast.makeText(this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show();
                    break;
                }

                RegistrationModel data = new RegistrationModel(email, name, password, phone);

                Gson gson = new GsonBuilder().create();

                JSONObject jsonPostData = null;
                try {
                    jsonPostData = new JSONObject(gson.toJson(data, RegistrationModel.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                postRegistrationRequest(jsonPostData);
                break;

            case R.id.textview_login:
                Intent intentLoginActivity = new Intent(RegisterActivity.this, LoginActivity.class);
                intentLoginActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intentLoginActivity);
                break;

            case R.id.button_skip_login:
                Intent intentMainActivity = new Intent(this, MainActivity.class);
                finishAffinity();
                startActivity(intentMainActivity);
                break;
        }
    }

    private void postRegistrationRequest(JSONObject jsonPostData) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, EndPoints.getRegistration(), jsonPostData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        finishAffinity();
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showErrorDialogue();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    private void showErrorDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Email already exist");
        builder.setTitle("Registration Failed");
        builder.setPositiveButton("OK", null);
        builder.setCancelable(true);
        builder.create().show();
    }

    private boolean checkEnteredDetails() {
        if (editTextName.getText().toString().equals(""))
            return false;
        if (editTextEmail.getText().toString().equals(""))
            return false;
        if (editTextPhone.getText().toString().equals(""))
            return false;
        if (editTextPassword.getText().toString().equals(""))
            return false;
        return true;
    }
}