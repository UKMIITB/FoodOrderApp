package com.example.fetchproductassignment.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.app.CartDataBase;
import com.example.fetchproductassignment.app.SharedPrefConstants;

public class BaseActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    CartDataBase cartDataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(SharedPrefConstants.SHARED_PREFERENCE, MODE_PRIVATE);
        cartDataBase = CartDataBase.getInstance(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem itemMenuUser = menu.findItem(R.id.menu_user);
        MenuItem itemMenuLoginLogout = menu.findItem(R.id.menu_login_logout);

        if (sharedPreferences.getBoolean(SharedPrefConstants.IS_LOGGED_IN, false)) {
            String userName = sharedPreferences.getString(SharedPrefConstants.USER_NAME, "Guest!");
            itemMenuUser.setTitle("Hello, " + userName);
            itemMenuLoginLogout.setTitle("Logout");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_user:
                if (item.getTitle().toString().toLowerCase().contains("guest")) {
                    showErrorDialogue();
                    return true;
                }
                Intent intentProfileActivity = new Intent(this, ProfileActivity.class);
                intentProfileActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intentProfileActivity);
                return true;

            case R.id.menu_cart:
                Intent intentCartActivity = new Intent(this, CartActivity.class);
                intentCartActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intentCartActivity);
                return true;

            case R.id.menu_login_logout:

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                cartDataBase.cartDataDAO().deleteTable();

                Intent intentLoginActivity = new Intent(this, LoginActivity.class);
                finishAffinity();
                startActivity(intentLoginActivity);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showErrorDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please login to view your profile");
        builder.setTitle("View Profile Failed");
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intentLoginActivity = new Intent(BaseActivity.this, LoginActivity.class);
                intentLoginActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intentLoginActivity);
            }
        });
        builder.setCancelable(true);
        builder.create().show();
    }
}
