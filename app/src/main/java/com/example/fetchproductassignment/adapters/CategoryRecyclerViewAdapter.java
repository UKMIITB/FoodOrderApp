package com.example.fetchproductassignment.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchproductassignment.models.AllCategoryModel;
import com.example.fetchproductassignment.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<AllCategoryModel> arrayList;

    public CategoryRecyclerViewAdapter(Context context, ArrayList<AllCategoryModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AllCategoryModel data = arrayList.get(position);
        holder.textView.setText(data.getCategoryName());
        Log.d("TAG1", "NEw: "+data.getImageUrl());
        Picasso.get()
                .load(data.getImageUrl())
                .resize(100,100)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(holder.imageView);
    }

    public void setData(ArrayList<AllCategoryModel> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view_pic);
            textView = itemView.findViewById(R.id.text_view_category);
        }
    }
}
