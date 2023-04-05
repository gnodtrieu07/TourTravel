package com.example.frag.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frag.R;
import com.example.frag.model.Ticket;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class Tour_FillInfo extends AppCompatActivity {
    private Button btnPay;
    private ImageView img;
    private TextView tvTitle, tvTimeTour, tvPlaceStart;
    private TextView tvTourName, tvPeople_amount, tvChild_amount, tvPriceTotal;

    private EditText edNameCustomer, edPhoneCustom, edEmailCustom;
    private TextView tvAmountTicketChild, tvAmountTicketPeople, tvPricePeople, tvPriceChild;
    private TextInputLayout floatingPhoneLabel ;

    private DatabaseReference ref;
    private Boolean checkErro = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_fillinfo);

        initViews();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail().toString().trim();

        Bundle bundle = getIntent().getExtras();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        String image = bundle.getString("image");
        String name = bundle.getString("name");
        String pricePeople = bundle.getString("pricePeople");
        String priceChild = bundle.getString("priceChild");
        String timeTour = bundle.getString("timeTour");
        String placeTour = bundle.getString("placeTour");
        String placeStart = bundle.getString("placeStart");

        int people=Integer.parseInt(pricePeople);
        int child=Integer.parseInt(priceChild);

        int people_amount = bundle.getInt("_counter_people");
        int child_amount = bundle.getInt("_counter_child");
        //Toast.makeText(Tour_FillInfo.this, "_counter_people"+people_amount + "_counter_child"+child_amount ,Toast.LENGTH_LONG).show();
        String priceTotal = String.valueOf(people_amount*people+child_amount*child);

        int price = Integer.parseInt(priceTotal);
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        decimalFormat.setGroupingUsed(true);
        decimalFormat.setGroupingSize(3);



        Picasso.get().load(image).into(img);
        tvTourName.setText(name);
        tvTimeTour.setText(timeTour);
        tvPlaceStart.setText(placeStart);
        tvPriceTotal.setText(decimalFormat.format(price) + "VND");
        tvPeople_amount.setText(String.valueOf(people_amount));
        tvChild_amount.setText(String.valueOf(child_amount));

        tvAmountTicketPeople.setText("("+String.valueOf(people_amount)+"x)");
        tvAmountTicketChild.setText("("+String.valueOf(child_amount)+"x)");

        tvPricePeople.setText(decimalFormat.format(Integer.parseInt(String.valueOf(people_amount*people))));
        tvPriceChild.setText(decimalFormat.format(Integer.parseInt(String.valueOf(child_amount*child))));


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String edName = edNameCustomer.getText().toString();
                    String edphone = edPhoneCustom.getText().toString();
                    String edemail = edEmailCustom.getText().toString();

                    boolean emailchk= checkEmail(edemail);
                    boolean phonechk = checkPhone(edphone);

                    if( edName.isEmpty() || emailchk == false || phonechk == false){
                        Toast.makeText(view.getContext(),"Điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                        //Toast.makeText(view.getContext(), emailchk + "sgsd", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Intent intent = new Intent(Tour_FillInfo.this, Tour_PaySuccess.class);
                        //String nameCustom = edNameCustomer.getText().toString();
                        String phoneCustom =edPhoneCustom.getText().toString();
                        String emailCustom = edEmailCustom.getText().toString();

                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat  format = new SimpleDateFormat("dd-MM-yyyyHH:mm:ss");
                        String time = format.format(c.getTime());

                        int a =email.indexOf("@");
                        String email1 = email.substring(0, a);

                        Ticket ticket = new Ticket(image,name,placeTour,priceTotal,timeTour, phoneCustom,emailCustom,  placeStart, time, people_amount, child_amount );
                        ref.child("ticket").child(email1.toString()).child(String.valueOf(ticket.getTime())).setValue(ticket);

                        ref.child("ticket").child("admin").child(String.valueOf(ticket.getTime())).setValue(ticket);

                        startActivity(intent);
                    }


            }
        });
        final TextInputLayout floatingUsernameLabel = (TextInputLayout) findViewById(R.id.name_text_input_layout);
        floatingUsernameLabel.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                if (text.length() > 0 && text.length() <= 4) {
                    floatingUsernameLabel.setError("valid name");
                    floatingUsernameLabel.setErrorEnabled(true);

                } else {
                    floatingUsernameLabel.setErrorEnabled(false);
                    checkErro = false;
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        final TextInputLayout floatingEmailLabel = (TextInputLayout) findViewById(R.id.email_text_input_layout);
        floatingEmailLabel.getEditText().addTextChangedListener(new TextWatcher() {
            // ...
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                String e = text.toString();
                boolean c = checkEmail(e);
                if (!c) {
                    floatingEmailLabel.setError("valid email id is required");
                    floatingEmailLabel.setErrorEnabled(true);

                } else {
                    floatingEmailLabel.setErrorEnabled(false);
                    checkErro = false;
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public static boolean checkPhone(String phone) {

        Pattern PHONE_PATTERN = Pattern
                .compile("^(0|\\+84)(\\s|\\.)?((3[2-9])|(5[689])|(7[06-9])|(8[1-689])|(9[0-46-9]))(\\d)(\\s|\\.)?(\\d{3})(\\s|\\.)?(\\d{3})$");
        return PHONE_PATTERN.matcher(phone).matches();
    }

    public static boolean checkEmail(String email) {

        Pattern EMAIL_ADDRESS_PATTERN = Pattern
                .compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    private void initViews() {
        img = findViewById(R.id.img);
        tvTitle = findViewById(R.id.tvTitle);
        tvTourName = findViewById(R.id.tvTourName);

        edNameCustomer = findViewById(R.id.edNameCustomer);
        edPhoneCustom = findViewById(R.id.edPhoneCustom);
        edEmailCustom = findViewById(R.id.edEmailCustom);
        btnPay = findViewById(R.id.btnPay);

        tvPeople_amount = findViewById(R.id.tvPeople_amount);
        tvChild_amount = findViewById(R.id.tvChild_amount);
        tvPriceTotal = findViewById(R.id.tvPriceTotal);

        tvAmountTicketChild = findViewById(R.id.tvAmountTicketChild);
        tvAmountTicketPeople = findViewById(R.id.tvAmountTicketPeople);
        tvPricePeople = findViewById(R.id.tvPricePeople);
        tvPriceChild = findViewById(R.id.tvPriceChild);

        tvTimeTour = findViewById(R.id.tvTimeTour);
        tvPlaceStart = findViewById(R.id.tvPlaceStart);

    }
}