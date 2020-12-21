package com.example.fetchproductassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fetchproductassignment.R;
import com.example.fetchproductassignment.app.ItemClickListener;
import com.example.fetchproductassignment.models.AddressModel;

import java.util.ArrayList;

public class AddressActivityRecyclerViewAdapter extends RecyclerView.Adapter<AddressActivityRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<AddressModel> arrayList;
    ItemClickListener itemClickListener;

    public AddressActivityRecyclerViewAdapter(Context context, ArrayList<AddressModel> arrayList, ItemClickListener itemClickListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_address, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewHouseNumber.setText(arrayList.get(position).getHouseNo());
        holder.textViewStreetName.setText(arrayList.get(position).getStreetName());
        holder.textViewCity.setText(arrayList.get(position).getCity());
        holder.textViewPincode.setText(String.valueOf(arrayList.get(position).getPincode()));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setData(ArrayList<AddressModel> arrayList) {
        this.arrayList = arrayList;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewHouseNumber, textViewStreetName, textViewCity, textViewPincode;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewHouseNumber = itemView.findViewById(R.id.textview_house_number);
            textViewStreetName = itemView.findViewById(R.id.textview_street_name);
            textViewCity = itemView.findViewById(R.id.textview_city);
            textViewPincode = itemView.findViewById(R.id.textview_pincode);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (itemClickListener != null)
                itemClickListener.onItemClicked(view, getAdapterPosition());
        }
    }
}
