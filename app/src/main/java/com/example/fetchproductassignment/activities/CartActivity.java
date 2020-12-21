package com.example.fetchproductassignment.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.adapters.CartRecyclerViewAdapter;
import com.example.fetchproductassignment.app.CartDataBase;
import com.example.fetchproductassignment.app.ItemClickListener;
import com.example.fetchproductassignment.models.CartDataModel;
import com.example.fetchproductassignment.models.SubCategoryProductModel;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements ItemClickListener, View.OnClickListener {

    SubCategoryProductModel data;
    CartDataBase cartDataBase;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    CartRecyclerViewAdapter adapter;

    TextView textViewTotal, textViewDiscount, textViewPayable;
    Button buttonCheckOut;

    ArrayList<CartDataModel> arrayList;
    double totalCost, totalDiscount, totalPayable;
    final double discountPercent = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        Intent intent = getIntent();
        data = (SubCategoryProductModel) intent.getSerializableExtra("ProductData");
        cartDataBase = CartDataBase.getInstance(this);

        if (data != null)
            addToDB(data);

        init();
    }

    private void addToDB(SubCategoryProductModel data) {
        String id = data.get_id();
        int productExist = cartDataBase.cartDataDAO().productExist(id);

        if (productExist == 0)
            cartDataBase.cartDataDAO().addProduct(new CartDataModel(data.get_id(), data.getProductName(), data.getImage(), data.getPrice()));
        else {
            int productCount = cartDataBase.cartDataDAO().productCount(id);
            cartDataBase.cartDataDAO().updateProductCount(data.get_id(), productCount + 1);
        }
    }

    private void init() {
        arrayList = (ArrayList<CartDataModel>) cartDataBase.cartDataDAO().getAllData();

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        adapter = new CartRecyclerViewAdapter(this, arrayList, this);

        textViewTotal = findViewById(R.id.textview_total);
        textViewDiscount = findViewById(R.id.textview_discount);
        textViewPayable = findViewById(R.id.textview_payable);
        buttonCheckOut = findViewById(R.id.button_checkout);
        buttonCheckOut.setOnClickListener(this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setData(arrayList);

        updateCostUI();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(View view, int position) {
        CartDataModel clickedData = arrayList.get(position);
        String id = clickedData.getId();
        switch (view.getId()) {
            case R.id.button_add:
            case R.id.button_subtract:
                int originalCount = clickedData.getQuantity();

                //Update quantity info in database
                cartDataBase.cartDataDAO().updateProductCount(id, originalCount + 1);

                //Update quantity in arrayList
                if (view.getId() == R.id.button_add)
                    clickedData.setQuantity(originalCount + 1);
                else {
                    if (originalCount > 1)
                        clickedData.setQuantity(originalCount - 1);
                    else
                        Toast.makeText(this, "Quantity cannot be less than 1", Toast.LENGTH_SHORT).show();
                }

                arrayList.set(position, clickedData);

                //Updating recyclerView
                adapter.updateData(arrayList, position);

                //Update cost UI
                updateCostUI();


                break;

            case R.id.button_remove_from_cart:
                cartDataBase.cartDataDAO().deleteProduct(id);
                arrayList.remove(position);
                adapter.removeData(arrayList, position);

                //Update cost UI
                updateCostUI();

                break;
        }
    }

    public void updateCostUI() {
        totalCost = 0;
        for (int i = 0; i < arrayList.size(); i++)
            totalCost += (arrayList.get(i).getPrice() * arrayList.get(i).getQuantity());

        totalDiscount = (discountPercent / 100) * totalCost;
        totalPayable = totalCost - totalDiscount;

        totalCost = Math.round(totalCost * 100.0) / 100.0;
        totalDiscount = Math.round(totalDiscount * 100.0) / 100.0;
        totalPayable = Math.round(totalPayable * 100.0) / 100.0;

        textViewDiscount.setText("Discount: " + String.valueOf(totalDiscount));
        textViewTotal.setText("Total: " + String.valueOf(totalCost));
        textViewPayable.setText("Payable: " + String.valueOf(totalPayable));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_checkout) {
            if (arrayList.size() > 0) {
                Intent intentAddressActivity = new Intent(CartActivity.this, AddressActivity.class);
                intentAddressActivity.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intentAddressActivity);
            } else
                showCheckoutDialogueError();
        }
    }

    private void showCheckoutDialogueError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Order Checkout Error");
        builder.setMessage("Please add some product to place order");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }
}