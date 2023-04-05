package com.example.frag.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frag.MainActivity;
import com.example.frag.R;
import com.example.frag.fragment.AccountFragment;
import com.example.frag.fragment.HomeFragment;
import com.example.frag.model.Blog;
import com.example.frag.model.Tour;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class TourDetails extends AppCompatActivity {
    private ViewPager2 mviewPager2;
    private CircleIndicator3 mcircleIndicator3;
    private List<Tour> mlistPhoto;



    private ImageView viewPagerdetails;
    private TextView tvTourName;
    private TextView placeTour;
    private TextView tvTimeTour;
    private TextView placeStart;
    private TextView tourtrend, tvPrice;
    private TextView sdt;
    private Button btnTimTour;
    private Tour itemDetail;

    Button btn_edit_tour;
    Button btn_remove_tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_details);

        initViews();

        Bundle bundle = getIntent().getExtras();
        String image = bundle.getString("image");
        String name = bundle.getString("name");
        String timeTour = bundle.getString("timeTour");
        String place = bundle.getString("placeTour");
        String startPlace = bundle.getString("placeStart");
        String pricePeople = bundle.getString("pricePeople");
        String priceChild = bundle.getString("priceChild");
        String about = bundle.getString("about");
        String sdt = bundle.getString("sdt");


        Picasso.get().load(image).into(viewPagerdetails);

        int price = Integer.parseInt(pricePeople);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);
        //viewPagerdetails.setImageResource(image);
        tvTourName.setText(name);

        placeTour.setText(place);
        placeStart.setText(startPlace);
        tvPrice.setText(decimalFormat.format(price) + " VND");
        tourtrend.setText(about);
        tvTimeTour.setText(timeTour);

        btnTimTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TourDetails.this, Tour_FindTour.class);
                intent.putExtra("name", name);
                intent.putExtra("image", image);
                intent.putExtra("timeTour", timeTour);
                intent.putExtra("image", image);

                intent.putExtra("pricePeople", pricePeople);
                intent.putExtra("priceChild", priceChild);
                intent.putExtra("sdt",sdt);

                intent.putExtra("placeStart",startPlace);
                intent.putExtra("placeTour",place);


                startActivity(intent);
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail().toString().trim();
        if ( email.equals("admin@gmail.com") ){
            btn_edit_tour.setVisibility(View.VISIBLE);
            btn_remove_tour.setVisibility(View.VISIBLE);
        }

        btn_remove_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Bạn muốn xoá tour này ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query applesQuery = ref.child("tour").orderByChild("name").equalTo(name);
                                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
                                            Intent intent = new Intent(TourDetails.this, MainActivity.class);
                                            startActivity(intent);
                                            Toast.makeText(view.getContext(),"Xoá thành công", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        btn_edit_tour.setOnClickListener(new View.OnClickListener() {
            private DatabaseReference ref;
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_dialog_edit_tour);

                Window window = dialog.getWindow();
                if(window==null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button btn_cancle_tour = dialog.findViewById(R.id.btn_cancle_tour);
                btn_cancle_tour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                Button btn_update_tour = dialog.findViewById(R.id.btn_update_tour);
                EditText tour_edit_about = dialog.findViewById(R.id.tour_edit_about);
                EditText tour_edit_name = dialog.findViewById(R.id.tour_edit_name);
                EditText tour_edit_placeStart = dialog.findViewById(R.id.tour_edit_placeStart);
                EditText tour_edit_placeTour = dialog.findViewById(R.id.tour_edit_placeTour);
                EditText tour_edit_priceChild = dialog.findViewById(R.id.tour_edit_priceChild);
                EditText tour_edit_pricePeople = dialog.findViewById(R.id.tour_edit_pricePeople);
                EditText tour_edit_resourceId = dialog.findViewById(R.id.tour_edit_resourceId);
                EditText tour_edit_timeTour = dialog.findViewById(R.id.tour_edit_timeTour);
                EditText tour_edit_sdt = dialog.findViewById(R.id.tour_edit_sdt);

                tour_edit_about.setText(about);
                tour_edit_name.setText(name);
                tour_edit_placeStart.setText(startPlace);
                tour_edit_placeTour.setText(place);
                tour_edit_priceChild.setText(priceChild);
                tour_edit_pricePeople.setText(pricePeople);
                tour_edit_resourceId.setText(image);
                tour_edit_timeTour.setText(timeTour);
                tour_edit_sdt.setText(sdt);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                ref = database.getReference();

                btn_update_tour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String ed_about = tour_edit_about.getText().toString();
                        String ed_name = tour_edit_name.getText().toString();
                        String ed_placeStart = tour_edit_placeStart.getText().toString();
                        String ed_placeTour = tour_edit_placeTour.getText().toString();
                        String ed_priceChild = tour_edit_priceChild.getText().toString();
                        String ed_pricePeople = tour_edit_pricePeople.getText().toString();
                        String ed_resourceId = tour_edit_resourceId.getText().toString();
                        String ed_timeTour = tour_edit_timeTour.getText().toString();
                        String ed_sdt = tour_edit_sdt.getText().toString();

                        if(ed_about.isEmpty() || ed_about.equals(" ")) {
                            Toast.makeText(view.getContext(), "Giới thiệu tour không được bỏ trống", Toast.LENGTH_LONG).show();
                        }else if (ed_name.isEmpty() || ed_name.equals(" ")) {
                            Toast.makeText(view.getContext(), "Tên không được bỏ trống", Toast.LENGTH_LONG).show();
                        }else if (ed_placeStart.isEmpty() || ed_placeStart.equals(" ")) {
                            Toast.makeText(view.getContext(), "Nơi khởi hành không được bỏ trống", Toast.LENGTH_LONG).show();
                        }else if (ed_placeTour.isEmpty() || ed_placeTour.equals(" ")) {
                            Toast.makeText(view.getContext(), "Điểm dến không được bỏ trống", Toast.LENGTH_LONG).show();
                        }
                        else if (priceChild.isEmpty() || priceChild.equals(" ") || !priceChild.matches("-?\\d+(\\.\\d+)?")) {
                            Toast.makeText(view.getContext(), "Giá trẻ em bỏ trống hoặc sai định dạng", Toast.LENGTH_LONG).show();
                        }
                        else if (pricePeople.isEmpty() || pricePeople.equals(" ") || !pricePeople.matches("-?\\d+(\\.\\d+)?")) {
                            Toast.makeText(view.getContext(), "Giá người lớn bỏ trống hoặc sai định dạng", Toast.LENGTH_LONG).show();
                        }
                        else if (ed_resourceId.isEmpty() || ed_resourceId.equals(" ")) {
                            Toast.makeText(view.getContext(), "Link ảnh không được bỏ trống", Toast.LENGTH_LONG).show();
                        }
                        else if (ed_timeTour.isEmpty() || ed_timeTour.equals(" ")) {
                            Toast.makeText(view.getContext(), "Thời gian đi không được bỏ trống", Toast.LENGTH_LONG).show();
                        }
                        else if (ed_sdt.isEmpty() || ed_sdt.equals(" ")) {
                            Toast.makeText(view.getContext(), "Liên hệ không được bỏ trống", Toast.LENGTH_LONG).show();
                        }
                        else {

                            Tour tour = new Tour(ed_about, ed_name, ed_placeStart, ed_placeTour, ed_priceChild, ed_pricePeople, ed_resourceId, ed_timeTour, ed_sdt);
                            ref.child("tour").child(String.valueOf(tour.getName())).setValue(tour);

                            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                            Query applesQuery = ref1.child("tour").orderByChild("name").equalTo(bundle.getString("name"));
                            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                        appleSnapshot.getRef().removeValue();
                                        Toast.makeText(TourDetails.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                                        //Update lại tour
                                        tvTourName.setText(ed_name);
                                        placeTour.setText(ed_placeTour);
                                        placeStart.setText(ed_placeStart);
                                        tvPrice.setText(ed_pricePeople);
                                        tourtrend.setText(ed_about);
                                        tvTimeTour.setText(ed_timeTour);

                                        String about = ed_about;
                                        String name = ed_name;
                                        String  startPlace = ed_placeStart;
                                        String  place= ed_placeTour;
                                        String priceChild = ed_priceChild;
                                        String pricePeople = ed_pricePeople;
                                        String image = ed_resourceId;
                                        String timeTour = ed_timeTour;
                                        String sdt = ed_sdt;
                                        dialog.dismiss();


                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }

                    }
                });

                dialog.show();
            }
        });


    }
    public void onBackPressed() {
        Intent intent = new Intent(TourDetails.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void initViews() {
        viewPagerdetails = findViewById(R.id.viewPagerdetails);
        tvTourName = findViewById(R.id.tvTourName);
        tvPrice = findViewById(R.id.tvPrice);
        tourtrend = findViewById(R.id.tourtrend);
        tvTimeTour = findViewById(R.id.timeTour);
        placeTour = findViewById(R.id.placeTour);
        placeStart = findViewById(R.id.placeStart);
        btnTimTour = findViewById(R.id.btnTimTour);
        sdt = findViewById(R.id.sdt);


        btn_edit_tour = findViewById(R.id.btn_edit_tour);
        btn_remove_tour = findViewById(R.id.btn_remove_tour);
    }


}