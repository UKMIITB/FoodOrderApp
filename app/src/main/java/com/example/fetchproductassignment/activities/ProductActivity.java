package com.example.fetchproductassignment.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.app.Config;
import com.example.fetchproductassignment.models.SubCategoryProductModel;
import com.squareup.picasso.Picasso;

public class ProductActivity extends BaseActivity {

    ImageView imageViewPic;
    TextView textViewProductName, textViewDescription, textViewMrp, textViewPrice;
    Button buttonAddToCart;
    SubCategoryProductModel data;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        data = (SubCategoryProductModel) intent.getSerializableExtra("ProductModel");

        //Setup ToolBar
        toolbar = findViewById(R.id.custom_app_bar);
        toolbar.setTitle(data.getProductName());
        setSupportActionBar(toolbar);

        init();
        cartClickListener();
    }

    private void cartClickListener() {
        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCartActivity = new Intent(ProductActivity.this, CartActivity.class);
                intentCartActivity.putExtra("ProductData", data);
                Log.d("TAG", "Sent an intent to CartActivity");
                startActivity(intentCartActivity);
                finish();
            }
        });
    }

    private void init() {
        imageViewPic = findViewById(R.id.image_view_pic);
        textViewProductName = findViewById(R.id.textview_product_name);
        textViewDescription = findViewById(R.id.textview_description);
        textViewMrp = findViewById(R.id.textview_mrp);
        textViewPrice = findViewById(R.id.textview_price);
        buttonAddToCart = findViewById(R.id.button_add_to_cart);

        String imageUrl = Config.IMAGE_URL + data.getImage();
        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerCrop()
                .error(R.drawable.ic_launcher_background)
                .into(imageViewPic);

        textViewProductName.setText(data.getProductName());
        textViewDescription.setText(data.getDescription());
        textViewMrp.setText("MRP :" + String.valueOf(data.getMrp()));
        textViewMrp.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        textViewPrice.setText("Offer Price :" + String.valueOf(data.getPrice()));
    }
}