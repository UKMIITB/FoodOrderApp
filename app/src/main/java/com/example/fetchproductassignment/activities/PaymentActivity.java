package com.example.fetchproductassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.fetchproductassignment.R;

public class PaymentActivity extends AppCompatActivity {

    Toolbar toolbar;
    RadioGroup radioGroupPaymentMode;
    RadioButton radioButtonCash, radioButtonOnline;
    Button buttonConfirmPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        init();
    }

    private void init() {
        toolbar = findViewById(R.id.custom_app_bar);
        toolbar.setTitle("Payment Activity");
        setSupportActionBar(toolbar);

        radioGroupPaymentMode = findViewById(R.id.radio_group_payment_mode);
        radioButtonCash = findViewById(R.id.radio_button_cash);
        radioButtonOnline = findViewById(R.id.radio_button_online);

        buttonConfirmPayment = findViewById(R.id.button_confirm_payment);

        buttonConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (radioGroupPaymentMode.getCheckedRadioButtonId() == R.id.radio_button_online)
                    showErrorDialogue();
                else {
                    finishAffinity();
                    startActivity(new Intent(PaymentActivity.this, OrderConfirmationActivity.class));
                }
            }
        });
    }

    private void showErrorDialogue() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Payment Mode Error");
        builder.setMessage("Online Payment Mode not available at the moment.\nPlease select Cash option");
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }
}