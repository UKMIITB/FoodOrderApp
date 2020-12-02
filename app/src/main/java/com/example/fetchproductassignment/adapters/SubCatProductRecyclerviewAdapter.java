package com.example.fetchproductassignment.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.app.Config;
import com.example.fetchproductassignment.app.ItemClickListener;
import com.example.fetchproductassignment.models.SubCategoryProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SubCatProductRecyclerviewAdapter extends RecyclerView.Adapter<SubCatProductRecyclerviewAdapter.MyViewHolder> {

    Context context;
    ArrayList<SubCategoryProductModel> arrayList;
    ItemClickListener itemClickListener;

    public SubCatProductRecyclerviewAdapter(Context context, ArrayList<SubCategoryProductModel> arrayList, ItemClickListener itemClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_subcat_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SubCategoryProductModel data = arrayList.get(position);
        String imageUrl = Config.IMAGE_URL + data.getImage();
        Picasso.get()
                .load(imageUrl)
                .fit()
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(holder.imageViewPic);
        holder.textViewProductName.setText(data.getProductName());
        holder.textViewQuantity.setText("Quantity: " + String.valueOf(data.getQuantity()));
        holder.textViewPrice.setText("Price: " + String.valueOf(data.getPrice()));
        holder.textViewMRP.setText("MRP :" + String.valueOf(data.getMrp()));
        holder.textViewMRP.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
    }

    public void setData(ArrayList<SubCategoryProductModel> arrayList) {
        Log.d("TAG", "notify data set called");
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewPic;
        TextView textViewProductName, textViewQuantity, textViewPrice, textViewMRP;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewPic = itemView.findViewById(R.id.image_view_pic);
            textViewProductName = itemView.findViewById(R.id.textview_product_name);
            textViewQuantity = itemView.findViewById(R.id.textview_quantity);
            textViewPrice = itemView.findViewById(R.id.textview_price);
            textViewMRP = itemView.findViewById(R.id.textview_mrp);

            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemClicked(view, getAdapterPosition());
        }
    }
}
