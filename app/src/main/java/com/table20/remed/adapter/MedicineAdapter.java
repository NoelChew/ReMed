package com.table20.remed.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.table20.remed.R;
import com.table20.remed.customclass.Medicine;
import com.table20.remed.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by noelchew on 22/09/2016.
 */

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {
    public Context context;
    private ArrayList<Medicine> medicines;
    private OnItemClickListener listener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView ivImage, ivBanner;
        public TextView tvExpiry;
        public TextView tvName;
        public CardView cardView;

        public ViewHolder(View v) {
            super(v);
            ivImage = (ImageView) v.findViewById(R.id.image_view);
            ivBanner = (ImageView) v.findViewById(R.id.image_view_banner);
//            tvPurchase = (TextView) v.findViewById(R.id.text_view_purchase);
            tvExpiry = (TextView) v.findViewById(R.id.text_view_expiry);
            tvName = (TextView) v.findViewById(R.id.text_view_name);
            cardView = (CardView) v.findViewById(R.id.card_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MedicineAdapter(ArrayList<Medicine> medicines, Context context, OnItemClickListener listener) {
        this.medicines = medicines;
        this.context = context;
        this.listener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MedicineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_medicine, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Medicine medicine = medicines.get(position);
//        Log.d(TAG, "ImageURL: " + medicineWithExpiry.getImageUrl());
        Glide.with(context).load(medicine.getImageUrl()).into(holder.ivImage);
//        Glide.with(context).load("http://plusquotes.com/images/quotes-img/flower-25.jpg").into(holder.ivImage);
//        Glide.with(context).load(R.mipmap.ic_launcher).into(holder.ivImage);
        holder.tvExpiry.setText(medicine.getExpiryDateInString());
//        holder.tvPurchase.setText("Purchase Date: " + medicine.getPurchaseDateInString());
        holder.tvName.setText(medicine.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onClick(medicine);
                }
            }
        });

        if (medicine.getExpiryDate() <= new Date().getTime()) {
            // expired
            holder.ivBanner.setVisibility(View.VISIBLE);
        } else {
            holder.ivBanner.setVisibility(View.GONE);
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if (medicines == null || medicines.isEmpty()) {
            return 0;
        }
        return medicines.size();
    }
}



