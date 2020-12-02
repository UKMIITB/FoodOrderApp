package com.example.fetchproductassignment.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.fetchproductassignment.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MainActivityViewPagerAdapter extends PagerAdapter {
    Context context;
    String[] imageUrls;

    public MainActivityViewPagerAdapter(Context context, String[] imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public int getCount() {
        return imageUrls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.row_main_activity_view_pager, container, false);
        ImageView imageViewPic = view.findViewById(R.id.image_view_pic);
        Picasso.get()
                .load(imageUrls[position])
                .fit()
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(imageViewPic);
        Objects.requireNonNull(container).addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
