package com.example.frag.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frag.MainActivity;
import com.example.frag.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tour_FindTour extends AppCompatActivity {
    private TextView tvTitle, txtDate, txtDate2, txtSDT, tvPricePeople, tvPriceChild;
    private TextView tvTourName, people_amount, child_amount;
    private  ImageButton btnDate;
    private Button  btnFillInfo;
    private static int _counter_people = 0 ;
    private static int _counter_child = 0 ;
    private ImageButton people_minus, people_add, child_minus, child_add;
    DatePickerDialog picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_findtour);

        initViews();

        Bundle bundle = getIntent().getExtras();
        String image = bundle.getString("image");
        String name = bundle.getString("name");
        String pricePeople = bundle.getString("pricePeople");
        String priceChild = bundle.getString("priceChild");
        String sdt = bundle.getString("sdt");

        String placeTour = bundle.getString("placeTour");
        String placeStart = bundle.getString("placeStart");

        int priceP = Integer.parseInt(pricePeople);
        int priceC = Integer.parseInt(priceChild);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);

        tvTitle.setText(name);
        tvTourName.setText(name);
        txtSDT.setText(sdt);
        tvPricePeople.setText("(" + decimalFormat.format(priceP) + " VND )");
        tvPriceChild.setText("(" +decimalFormat.format(priceC)+ " VND )");

        people_amount = (TextView)findViewById(R.id.people_amount);
        _counter_people = Integer.valueOf(people_amount.getText().toString());


        people_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _counter_people++;

                people_amount.setText("" + _counter_people);

            }
        });

        people_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_counter_people < 0){
                    _counter_people =  0;
                    people_amount.setText(_counter_people+ "");
                }
                if (_counter_people > 0) {
                    _counter_people = _counter_people - 1;
                    people_amount.setText(_counter_people+ "");
                }

            }
        });

        child_amount = (TextView)findViewById(R.id.child_amount);
        _counter_child = Integer.valueOf(people_amount.getText().toString());


        child_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _counter_child++;

                child_amount.setText(_counter_child+ "");

            }
        });

        child_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (_counter_child < 0){
                    _counter_child =  0;
                    child_amount.setText(_counter_child+ "");
                }
                if (_counter_child > 0) {
                    _counter_child = _counter_child - 1;
                    child_amount.setText(_counter_child+ "");
                }

            }
        });

        Calendar c = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(c.getTime());


        txtDate.setText(formattedDate.toString());
        txtDate2.setText(formattedDate.toString());

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                // date picker dialog
                picker = new DatePickerDialog(Tour_FindTour.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                txtDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                txtDate2.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        btnFillInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (_counter_people==0){
                    Toast.makeText(view.getContext(),"Vui lòng chọn ít nhất 1 vé người lớn",Toast.LENGTH_LONG).show();
                }
                else {
                    Intent intent = new Intent(Tour_FindTour.this, Tour_FillInfo.class);
                    intent.putExtra("name", name);
                    intent.putExtra("image", image);
                    intent.putExtra("pricePeople", pricePeople);
                    intent.putExtra("priceChild", priceChild);
                    intent.putExtra("_counter_people", _counter_people);
                    intent.putExtra("_counter_child", _counter_child);
                    intent.putExtra("timeTour", txtDate.getText().toString());

                    intent.putExtra("placeStart",placeStart);
                    intent.putExtra("placeTour",placeTour);
                    startActivity(intent);
                }

            }
        });
    }

    private void initViews() {
        tvTitle = findViewById(R.id.tvTitle);
        tvTourName = findViewById(R.id.tvTourName);
        txtDate = findViewById(R.id.txtDate);
        txtDate2 = findViewById(R.id.txtDate2);
        txtSDT = findViewById(R.id.txtSDT);

        btnDate = findViewById(R.id.btnDate);
        btnFillInfo = findViewById(R.id.btnFillInfo);

        people_minus = findViewById(R.id.people_minus);
        people_add = findViewById(R.id.people_add);

        child_minus = findViewById(R.id.child_minus);
        child_add = findViewById(R.id.child_add);

        tvPricePeople = findViewById(R.id.tvPricePeople);
        tvPriceChild = findViewById(R.id.tvPriceChild);
    }
}