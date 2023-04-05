package com.example.frag.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.frag.MainActivity;
import com.example.frag.R;
import com.example.frag.fragment.AccountFragment;
import com.example.frag.model.Tour;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;

public class Tour_add extends AppCompatActivity {

    EditText edit_about;
    EditText edit_name;
    EditText edit_placeStart;
    EditText edit_placeTour;
    EditText edit_priceChild;
    EditText edit_pricePeople;
    EditText edit_resourceId;
    EditText edit_timeTour;
    EditText edit_sdt;

    private DatabaseReference ref;
    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_add);

        edit_about = findViewById(R.id.edit_about);
        edit_name = findViewById(R.id.edit_name);
        edit_placeStart = findViewById(R.id.edit_placeStart);
        edit_placeTour = findViewById(R.id.edit_placeTour);
        edit_priceChild = findViewById(R.id.edit_priceChild);
        edit_pricePeople = findViewById(R.id.edit_pricePeople);
        edit_resourceId = findViewById(R.id.edit_resourceId);
        edit_timeTour = findViewById(R.id.edit_timeTour);
        edit_sdt = findViewById(R.id.edit_sdt);

        Button btnPushTour = findViewById(R.id.btn_push_tour);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        btnPushTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String about = edit_about.getText().toString();
                String name = edit_name.getText().toString();
                String placeStart = edit_placeStart.getText().toString();
                String placeTour = edit_placeTour.getText().toString();
                String priceChild = edit_priceChild.getText().toString();
                String pricePeople = edit_pricePeople.getText().toString();
                String resourceId = edit_resourceId.getText().toString();
                String timeTour = edit_timeTour.getText().toString();
                String sdt  = edit_sdt.getText().toString();


                if(about.isEmpty() || about.equals(" ")) {
                    Toast.makeText(view.getContext(), "Giới thiệu tour không được bỏ trống", Toast.LENGTH_LONG).show();
                }else if (name.isEmpty() || name.equals(" ")) {
                    Toast.makeText(view.getContext(), "Tên tour không được bỏ trống", Toast.LENGTH_LONG).show();
                }else if (placeStart.isEmpty() || placeStart.equals(" ")) {
                    Toast.makeText(view.getContext(), "Nơi khởi hành không được bỏ trống", Toast.LENGTH_LONG).show();
                }else if (placeTour.isEmpty() || placeTour.equals(" ")) {
                    Toast.makeText(view.getContext(), "Điểm dến không được bỏ trống", Toast.LENGTH_LONG).show();
                }
                else if (priceChild.isEmpty() || priceChild.equals(" ") || !priceChild.matches("-?\\d+(\\.\\d+)?")) {
                    Toast.makeText(view.getContext(), "Giá trẻ em bỏ trống hoặc sai định dạng", Toast.LENGTH_LONG).show();
                }
                else if (pricePeople.isEmpty() || pricePeople.equals(" ") || !pricePeople.matches("-?\\d+(\\.\\d+)?")) {
                    Toast.makeText(view.getContext(), "Giá người lớn bỏ trống hoặc sai định dạng", Toast.LENGTH_LONG).show();
                }
                else if (resourceId.isEmpty() || resourceId.equals(" ")) {
                    Toast.makeText(view.getContext(), "Link ảnh không được bỏ trống", Toast.LENGTH_LONG).show();
                }
                else if (timeTour.isEmpty() || timeTour.equals(" ")) {
                    Toast.makeText(view.getContext(), "Thời gian đi không được bỏ trống", Toast.LENGTH_LONG).show();
                }
                else if (sdt.isEmpty() || sdt.equals(" ")) {
                    Toast.makeText(view.getContext(), "Liên hệ không được bỏ trống", Toast.LENGTH_LONG).show();
                }
                else {
                    Tour tour = new Tour(about, name, placeStart, placeTour, priceChild, pricePeople, resourceId, timeTour, sdt);
                    ref.child("tour").child(String.valueOf(tour.getName())).setValue(tour);
                    Intent intent = new Intent(Tour_add.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(view.getContext(), "Thêm tour thành công", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(Tour_add.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}