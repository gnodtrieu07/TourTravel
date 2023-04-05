package com.example.frag.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.frag.R;
import com.example.frag.activity.TourDetails;
import com.example.frag.model.Tour;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DecimalFormat;

public class BestSaleAdapter extends FirebaseRecyclerAdapter<Tour,BestSaleAdapter.TourViewHolder> {

    public BestSaleAdapter(@NonNull FirebaseRecyclerOptions<Tour> options) {
        super(options);
    }


    @NonNull
    @Override
    public BestSaleAdapter.TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_photo, parent, false);
        return new BestSaleAdapter.TourViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull BestSaleAdapter.TourViewHolder holder, int position, @NonNull Tour model) {
        int price = Integer.parseInt(model.getPricePeople());
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);

        holder.about.setText(model.getAbout());
        holder.name.setText(model.getName());
        holder.placeStart.setText(model.getPlaceStart());
        holder.placeTour.setText(model.getPlaceTour());
        holder.priceChild.setText(model.getPriceChild());
        holder.pricePeople.setText(decimalFormat.format(price) + " VND");
        holder.timeTour.setText(model.getTimeTour());
        holder.sdt.setText(model.getSdt());

        Glide.with(holder.resourceId.getContext()).load(model.getResourceId()).into(holder.resourceId);

        holder.resourceId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, TourDetails.class);
                intent.putExtra("name", model.getName());
                intent.putExtra("about", model.getAbout());
                intent.putExtra("image", model.getResourceId());
                intent.putExtra("placeStart", model.getPlaceStart());
                intent.putExtra("placeTour", model.getPlaceTour());
                intent.putExtra("timeTour", model.getTimeTour());
                intent.putExtra("pricePeople", model.getPricePeople());
                intent.putExtra("priceChild", model.getPriceChild());
                intent.putExtra("sdt", model.getSdt());
                //Toast.makeText(context, model.getName(), Toast.LENGTH_SHORT).show();

                context.startActivity(intent);
            }
        });

    }

    public class TourViewHolder extends RecyclerView.ViewHolder {
        ImageView resourceId;
        TextView about, name, placeStart, placeTour, priceChild, pricePeople, timeTour,sdt;

        Button btn_edit_tour;
        Button btn_remove_tour;

        public TourViewHolder(@NonNull View itemView) {
            super(itemView);

            about = itemView.findViewById(R.id.about);
            name = itemView.findViewById(R.id.name);
            placeStart = itemView.findViewById(R.id.placeStart);
            placeTour = itemView.findViewById(R.id.placeTour);
            priceChild = itemView.findViewById(R.id.priceChild);
            pricePeople = itemView.findViewById(R.id.pricePeople);
            resourceId = itemView.findViewById(R.id.img_slider);
            timeTour = itemView.findViewById(R.id.timeTour);
            sdt = itemView.findViewById(R.id.sdt);

            btn_edit_tour = itemView.findViewById(R.id.btn_edit_tour);
            btn_remove_tour = itemView.findViewById(R.id.btn_remove_tour);

        }
    }
}