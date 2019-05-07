package com.nainal.agrimovies.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nainal.agrimovies.R;

public class LazyLoadHolder extends RecyclerView.ViewHolder {

    LinearLayout lazyProgress;

    public LazyLoadHolder(View view) {
        super(view);
        lazyProgress = view.findViewById(R.id.lazyProgress);
    }
}
