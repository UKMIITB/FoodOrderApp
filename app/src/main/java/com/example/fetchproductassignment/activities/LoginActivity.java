package com.example.fetchproductassignment.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.app.EndPoints;
import com.example.fetchproductassignment.app.SharedPrefConstants;
import com.example.fetchproductassignment.models.LoginModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPassword;
    Button buttonLogin, buttonRegister;
    ProgressBar progressBar;

    Toolbar toolbar;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

    }

    private void init() {
        editTextEmail = findViewById(R.id.edit_text_email);
        editTextPassword = findViewById(R.id.edit_text_password);
        buttonLogin = findViewById(R.id.button_login);
        buttonRegister = findViewById(R.id.button_register);
        progressBar = findViewById(R.id.progressbar);

        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

        toolbar = findViewById(R.id.custom_app_bar);
        toolbar.setTitle("Login Activity");
        setSupportActionBar(toolbar);

        sharedPreferences = getSharedPreferences(SharedPrefConstants.SHARED_PREFERENCE, MODE_PRIVATE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                if (!isDetailsFilled()) {
                    Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
                    break;
                }
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                LoginModel loginModel = new LoginModel(email, password);

                Gson gson = new GsonBuilder().create();
                JSONObject JSONLoginData = null;
                try {
                    JSONLoginData = new JSONObject(gson.toJson(loginModel, LoginModel.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressBar.setVisibility(View.VISIBLE);
                executeLogin(JSONLoginData);
                break;

            case R.id.button_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                break;
        }
    }

    private void executeLogin(JSONObject jsonLoginData) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, EndPoints.getLogin(), jsonLoginData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                try {
                    editor.putString(SharedPrefConstants.USER_NAME, response.getJSONObject("user").getString(SharedPrefConstants.USER_NAME));
                    editor.putString(SharedPrefConstants.USER_EMAIL, response.getJSONObject("user").getString(SharedPrefConstants.USER_EMAIL));
                    editor.putString(SharedPrefConstants.USER_MOBILE, response.getJSONObject("user").getString(SharedPrefConstants.USER_MOBILE));
                    editor.putString(SharedPrefConstants.USER_ID, response.getJSONObject("user").getString(SharedPrefConstants.USER_ID));
                    editor.putBoolean(SharedPrefConstants.IS_LOGGED_IN, true);
                    editor.apply();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.GONE);

                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                finishAffinity();
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                showErrorDialogue();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

    private void showErrorDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Invalid UserName or Password");
        builder.setTitle("Login Failed");
        builder.setPositiveButton("OK", null);
        builder.setCancelable(true);
        builder.create().show();
    }


    private boolean isDetailsFilled() {
        if (editTextEmail.getText().toString().equals(""))
            return false;
        if (editTextPassword.getText().toString().equals(""))
            return false;
        return true;
    }
}