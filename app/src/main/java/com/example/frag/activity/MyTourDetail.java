package com.example.frag.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frag.R;

public class MyTourDetail extends AppCompatActivity {

    private ImageView imgItem;
    private TextView tvTourName, tvtimeTour, tvPrice, tvphoneCustom, tvemailCustom, tvplaceTour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tour_detail);

        initViews();

        Bundle bundle = getIntent().getExtras();

        String tourName = bundle.getString("tourName");
        String timeTour = bundle.getString("tvtimeTour");
        String placeTour = bundle.getString("placeTour");
        String price = bundle.getString("tvPrice");
        String phoneCustom = bundle.getString("phoneCustom");
        String emailCustom = bundle.getString("emailCustom");
        int tvPeople_amount = bundle.getInt("people_amount");
        int tvChild_amount = bundle.getInt("child_amount");
        String tvPricePeople = bundle.getString("pricePeople");




        tvTourName.setText(tourName);
        tvtimeTour.setText(timeTour);
        tvplaceTour.setText(placeTour);
        tvPrice.setText(price);
        tvphoneCustom.setText(phoneCustom);
        tvemailCustom.setText(emailCustom);

    }

    private void initViews() {
        imgItem = findViewById(R.id.imgItem);
        tvTourName = findViewById(R.id.tvTourName);
        tvtimeTour = findViewById(R.id.tvtimeTour);
        tvplaceTour = findViewById(R.id.placeTour);
        tvPrice = findViewById(R.id.tvPrice);
        tvphoneCustom = findViewById(R.id.phoneCustom);
        tvemailCustom = findViewById(R.id.emailCustom);


    }
}