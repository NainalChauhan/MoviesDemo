package com.nainal.agrimovies.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nainal.agrimovies.R;

public class MoviesHolder extends RecyclerView.ViewHolder {

    TextView text_view,text_view_vote;
    CardView card_view;
    ImageView image_view;

    public MoviesHolder(View view) {
        super(view);
        text_view = view.findViewById(R.id.text_view);
        text_view_vote = view.findViewById(R.id.text_view_vote);
        image_view = view.findViewById(R.id.image_view);
        card_view = view.findViewById(R.id.card_view);
    }
}
