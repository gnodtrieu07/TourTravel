package com.example.frag.adapter;

import static java.lang.reflect.Array.getInt;

import android.content.Context;
import android.content.Intent;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.frag.R;
import com.example.frag.activity.MyTourDetail;
import com.example.frag.activity.TourDetails;
import com.example.frag.model.Ticket;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MyTourAdapter extends FirebaseRecyclerAdapter<Ticket,MyTourAdapter.TourViewHolder> {

    private static int currentPosition = 0;
    private Context context;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyTourAdapter(@NonNull FirebaseRecyclerOptions<Ticket> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyTourAdapter.TourViewHolder holder, int position, @NonNull Ticket model) {

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);

        Glide.with(holder.imgItem.getContext()).load(model.getImage()).into(holder.imgItem);
        holder.tvTourName.setText(model.getNameTour());
        holder.tvtimeTour.setText(model.getTimeTour());
        holder.placeTour.setText(model.getPlaceTour());
        holder.tvPrice.setText(decimalFormat.format(Integer.parseInt(model.getPriceTotal())) + "VND");
        holder.phoneCustom.setText(model.getPhoneCustom());
        holder.emailCustom.setText(model.getEmailCustom());
        holder.placeStart.setText(model.getPlaceStart());

        holder.arrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.llExpandArea.getVisibility() == View.VISIBLE) {
                    // The transition of the hiddenView is carried out by the TransitionManager class.
                    // Here we use an object of the AutoTransition Class to create a default transition
                    TransitionManager.beginDelayedTransition(holder.base_cardview, new AutoTransition());
                    holder.llExpandArea.setVisibility(View.GONE);
                    holder.arrow_button.setImageResource(R.drawable.ic_baseline_expand_more_24);
                }

                // If the CardView is not expanded, set its visibility to
                // visible and change the expand more icon to expand less.
                else {
                    TransitionManager.beginDelayedTransition(holder.base_cardview, new AutoTransition());
                    holder.llExpandArea.setVisibility(View.VISIBLE);
                    holder.arrow_button.setImageResource(R.drawable.ic_baseline_expand_less_24);
                }
            }
        });



        /*holder.imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, MyTourDetail.class);
                intent.putExtra("tourName", model.getNameTour());
                intent.putExtra("tvtimeTour", model.getTimeTour());
                intent.putExtra("placeTour", model.getPlaceTour());
                intent.putExtra("tvPrice", model.getPriceTotal());
                intent.putExtra("phoneCustom", model.getPhoneCustom());
                intent.putExtra("emailCustom", model.getEmailCustom());

                intent.putExtra("people_amount", model.getPeople_amount());
                intent.putExtra("child_amount", model.getChild_amount());





                context.startActivity(intent);
            }
        });*/

    }

    @NonNull
    @Override
    public MyTourAdapter.TourViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.savetour, parent, false);
        return new MyTourAdapter.TourViewHolder(view);
    }

    public class TourViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llExpandArea;
        ImageView imgItem;
        ImageButton arrow_button;
        TextView tvTourName, tvtimeTour, placeTour, tvPrice, pricePeople, priceChild, time;
        TextView nameCustom, phoneCustom, emailCustom, people_amount, child_amount, placeStart;
        CardView base_cardview;
        public TourViewHolder(@NonNull View itemView) {
            super(itemView);
            llExpandArea = (LinearLayout) itemView.findViewById(R.id.llExpandArea);
            imgItem = itemView.findViewById(R.id.imgItem);
            tvTourName = itemView.findViewById(R.id.tvTourName);
            tvtimeTour = itemView.findViewById(R.id.tvtimeTour);
            placeTour = itemView.findViewById(R.id.placeTour);
            tvPrice = itemView.findViewById(R.id.tvPrice);

            placeStart = itemView.findViewById(R.id.placeStart);

            phoneCustom = itemView.findViewById(R.id.phoneCustom);
            emailCustom = itemView.findViewById(R.id.emailCustom);
            arrow_button = itemView.findViewById(R.id.arrow_button);
            base_cardview = itemView.findViewById(R.id.base_cardview);




        }
    }




    /*

    @NonNull
    @Override
    public MyTourAdapter.MyTourRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.savetour,parent,false);

        return new MyTourRecyclerHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MyTourAdapter.MyTourRecyclerHolder holder, int position) {
        Ticket item1 = mListItem.get(position);
        if(item1 == null){
            return;
        }
        holder.imgtab1.setImageResource(item1.getResourceId());
        holder.nametab1.setText(item1.getNametab1());
        holder.infortab1.setText(item1.getInfortab1());
    }

    @Override
    public int getItemCount() {
        return mListItem.size();
    }

    public class MyTourRecyclerHolder extends RecyclerView.ViewHolder {
        private ImageView imgtab1;
        private TextView nametab1;
        private TextView infortab1;

        public MyTourRecyclerHolder(@NonNull View itemView) {
            super(itemView);

            imgtab1 = itemView.findViewById(R.id.imgItem);
            nametab1 = itemView.findViewById(R.id.tvTourName);
            infortab1 = itemView.findViewById(R.id.tvLocation);
        }
    }
    */

}

