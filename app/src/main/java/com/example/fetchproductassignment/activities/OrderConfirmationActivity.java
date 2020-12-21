package com.example.fetchproductassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.app.CartDataBase;

public class OrderConfirmationActivity extends AppCompatActivity {

    TextView textViewProcessingMessage, textViewConfirmationMessage;
    ProgressBar progressBar;
    ImageView imageViewTick;

    CartDataBase cartDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirmation);

        init();
        displayOrderConfirmationMessage();
    }

    private void displayOrderConfirmationMessage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textViewProcessingMessage.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                imageViewTick.setVisibility(View.VISIBLE);
                textViewConfirmationMessage.setVisibility(View.VISIBLE);

                redirectToHomePage();
            }
        }, 3000);
    }

    private void redirectToHomePage() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finishAffinity();
                cartDataBase.cartDataDAO().deleteTable();
                startActivity(new Intent(OrderConfirmationActivity.this, MainActivity.class));
            }
        }, 3000);
    }

    private void init() {
        textViewProcessingMessage = findViewById(R.id.textview_processing_message);
        textViewConfirmationMessage = findViewById(R.id.textview_confirmation_message);
        progressBar = findViewById(R.id.progressbar);
        imageViewTick = findViewById(R.id.image_view_tick);

        cartDataBase = CartDataBase.getInstance(this);
    }
}