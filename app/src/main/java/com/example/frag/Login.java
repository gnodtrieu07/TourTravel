package com.example.frag;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            if(bundle.getString("some")!=null){
                Toast.makeText(getApplicationContext(),"data"+bundle.getString("some"),Toast.LENGTH_SHORT).show();
            }
        }
    }
}