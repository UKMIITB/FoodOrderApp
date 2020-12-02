package com.example.fetchproductassignment.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.app.Config;
import com.example.fetchproductassignment.models.SubCategoryProductModel;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {

    ImageView imageViewPic;
    TextView textViewProductName, textViewDescription, textViewMrp, textViewPrice;
    SubCategoryProductModel data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Intent intent = getIntent();
        data = (SubCategoryProductModel) intent.getSerializableExtra("ProductModel");
        init();
    }

    private void init() {
        imageViewPic = findViewById(R.id.image_view_pic);
        textViewProductName = findViewById(R.id.textview_product_name);
        textViewDescription = findViewById(R.id.textview_description);
        textViewMrp = findViewById(R.id.textview_mrp);
        textViewPrice = findViewById(R.id.textview_price);

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