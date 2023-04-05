package com.example.frag.fragment;

import static com.example.frag.activity.Tour_FillInfo.checkEmail;
import static com.example.frag.activity.Tour_FillInfo.checkPhone;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frag.LoginActivity;
import com.example.frag.R;
import com.example.frag.activity.Blog_add;
import com.example.frag.activity.Tour_add;
import com.example.frag.model.Blog;
import com.example.frag.model.Ticket;
import com.example.frag.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment  {
    TextView tvName;
    Button btnLogin,blog_add,btnLogout;
    LinearLayout acti_support, acti_infor, infor;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;




    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        Button btnLogout = view.findViewById(R.id.btnLogout);
        TextView tvName =view.findViewById(R.id.tvName);


        //getProfile();
        /*btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                //tvName.setVisibility(View.VISIBLE);

                startActivity(intent);
            }
        });*/

        LinearLayout infor = view.findViewById(R.id.tvInf);
        infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.information);
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String email = user.getEmail().toString().trim();
                int a =email.indexOf("@");
                String email1 = email.substring(0, a);

                Window window = dialog.getWindow();
                if(window==null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button btn_cancle_blog = dialog.findViewById(R.id.btn_cancle_blog);
                btn_cancle_blog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });



                String[] items = new String[] {"Chưa đặt", "Nam", "Nữ"};

                EditText ed_name = dialog.findViewById(R.id.name);
                EditText ed_phone = dialog.findViewById(R.id.phone);
                EditText ed_email = dialog.findViewById(R.id.email);
                ed_email.setText(email);





                Spinner spinner = dialog.findViewById(R.id.spinnerSex);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                spinner.setAdapter(adapter);

                EditText uDateOfBirth = dialog.findViewById(R.id.txtDate);
                uDateOfBirth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showDateDialog();
                    }

                    private void showDateDialog() {
                        Calendar cal = Calendar.getInstance();
                        final int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH) ;
                        int year = cal.get(Calendar.YEAR);

                        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                if (day < 10 && monthOfYear < 10)
                                    uDateOfBirth.setText("0" + dayOfMonth + "/0" + monthOfYear + "/" + year);

                                else if (day < 10 && monthOfYear > 10)
                                    uDateOfBirth.setText("0" + dayOfMonth + "/" + monthOfYear + "/" + year);

                                else if (day > 10 && monthOfYear < 10)
                                    uDateOfBirth.setText(dayOfMonth + "/0" + monthOfYear + "/" + year);

                                else
                                    uDateOfBirth.setText(dayOfMonth + "/" + monthOfYear + "/" + year);


                            }
                        };

                        DatePickerDialog dpDialog = new DatePickerDialog(getContext(), listener, year, month, day);
                        dpDialog.show();
                    }
                });
                DatabaseReference  ref = FirebaseDatabase.getInstance().getReference().child("user").child(email1);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists())
                        {

                            String  name_vl=snapshot.child("name").getValue().toString();
                            String  birthDay_vl=snapshot.child("birthDay").getValue().toString();

                            String  phone_vl=snapshot.child("phone").getValue().toString();
                            String  gender_vl=snapshot.child("gender").getValue().toString();

                            ed_name.setText(name_vl);
                            ed_phone.setText(phone_vl);
                            uDateOfBirth.setText(birthDay_vl);



                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



                Button updateProfileDetails = dialog.findViewById(R.id.updateProfileDetails);
                updateProfileDetails.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String email = user.getEmail().toString().trim();

                                String fullName = ed_name.getText().toString().trim();
                                String genderselection = spinner.getSelectedItem().toString();
                                String dateOfBirthSelected = uDateOfBirth.getText().toString();
                                String edphone = ed_phone.getText().toString();
                                String edemail = ed_email.getText().toString();

                                boolean emailchk= checkEmail(edemail);
                                boolean phonechk = checkPhone(edphone);


                                if (fullName.isEmpty()){
                                    Toast.makeText(getContext(), "Please enter your Name", Toast.LENGTH_SHORT).show();
                                    return;
                                }


                                if (genderselection.isEmpty()){
                                    Toast.makeText(getContext(), "Please Select the Gender", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if (dateOfBirthSelected.isEmpty()){
                                    Toast.makeText(getContext(), "Please Enter the Date of Birth", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if( emailchk == false){
                                    Toast.makeText(getContext(),"Please Enter Email", Toast.LENGTH_LONG).show();

                                }
                                if(  phonechk == false){
                                    Toast.makeText(getContext(),"Please Enter Phone", Toast.LENGTH_LONG).show();

                                }

                                else{
                                    int a =email.indexOf("@");
                                    String email1 = email.substring(0, a);


                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

                                    User  user1 = new User(fullName,dateOfBirthSelected,edphone,genderselection,edemail);
                                    ref.child("user").child(email1.toString()).setValue(user1);

                                    Toast.makeText(view.getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    //ref.child("user").child(String.valueOf(email1)).setValue(u);

                                }
                            }

                        });


                dialog.show();
            }
        });



        LinearLayout acti_support = view.findViewById(R.id.acti_support);
        acti_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_support);

                Window window = dialog.getWindow();
                if(window==null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ImageButton btn_back = dialog.findViewById(R.id.btn_back);
                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        LinearLayout acti_infor = view.findViewById(R.id.acti_infor);
        acti_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_infor);

                Window window = dialog.getWindow();
                if(window==null){
                    return;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ImageButton btn_back = dialog.findViewById(R.id.btn_back);
                btn_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail().toString().trim();
        tvName.setText(email);
        //blog_add.setVisibility(View.VISIBLE);
        LinearLayout blog_add = view.findViewById(R.id.blog_add);
        LinearLayout tour_add = view.findViewById(R.id.tour_add);

        if ( email.equals("admin@gmail.com") )
        {
            blog_add.setVisibility(View.VISIBLE);
            tour_add.setVisibility(View.VISIBLE);
            blog_add.setOnClickListener(new Button.OnClickListener()
            {
                public void onClick(View v)
                {
                    Intent intent = new Intent(getActivity(), Blog_add.class);
                    startActivity(intent);
                    //Toast.makeText(getContext(), "Done", Toast.LENGTH_LONG).show();
                }
            });
            tour_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), Tour_add.class);
                    startActivity(intent);
                    //Toast.makeText(getContext(), "Done", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            blog_add.setVisibility(View.INVISIBLE);
        }

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                tvName.setText("Bạn chưa đăng nhập");

                //btnLogout.setVisibility(View.VISIBLE);
                //imageView.setVisibility(View.GONE);
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
            }
        });


        return view;
    }

}