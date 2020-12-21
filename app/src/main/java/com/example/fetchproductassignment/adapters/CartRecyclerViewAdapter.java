package com.example.fetchproductassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.app.Config;
import com.example.fetchproductassignment.app.ItemClickListener;
import com.example.fetchproductassignment.models.CartDataModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<CartDataModel> arrayList;
    ItemClickListener itemClickListener;

    public CartRecyclerViewAdapter(Context context, ArrayList<CartDataModel> arrayList, ItemClickListener itemClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cart_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartDataModel data = arrayList.get(position);
        String imageUrl = Config.IMAGE_URL + data.getImage();

        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerCrop()
                .error(R.drawable.ic_baseline_shopping_cart_24)
                .into(holder.imageViewPic);

        holder.textViewProductName.setText(data.getProductName());
        holder.textViewQuantity.setText(String.valueOf(data.getQuantity()));

        double price = data.getPrice() * data.getQuantity();
        price = Math.round(price * 100.0) / 100.0;

        holder.buttonPrice.setText("Price: " + String.valueOf(price));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setData(ArrayList<CartDataModel> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<CartDataModel> arrayList, int position) {
        this.arrayList = arrayList;
        notifyItemChanged(position, arrayList);
    }

    public void removeData(ArrayList<CartDataModel> arrayList, int position) {
        this.arrayList = arrayList;
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageViewPic;
        TextView textViewProductName, textViewQuantity;
        Button buttonSubtract, buttonAdd, buttonPrice, buttonRemoveFromCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPic = itemView.findViewById(R.id.image_view_pic);
            textViewProductName = itemView.findViewById(R.id.textview_product_name);
            textViewQuantity = itemView.findViewById(R.id.textview_quantity);
            buttonSubtract = itemView.findViewById(R.id.button_subtract);
            buttonAdd = itemView.findViewById(R.id.button_add);
            buttonPrice = itemView.findViewById(R.id.button_price);
            buttonRemoveFromCart = itemView.findViewById(R.id.button_remove_from_cart);

            buttonAdd.setOnClickListener(this);
            buttonSubtract.setOnClickListener(this);
            buttonRemoveFromCart.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemClicked(view, getAdapterPosition());
        }
    }
}
