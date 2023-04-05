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
import com.example.frag.fragment.BlogFragment;
import com.example.frag.model.Blog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Blog_add extends AppCompatActivity {

    EditText edit_descripcion;
    EditText edit_purl;
    EditText edit_titulo;

    private DatabaseReference ref;
    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_add);

        edit_descripcion = findViewById(R.id.edit_descripcion);
        edit_purl = findViewById(R.id.edit_purl);
        edit_titulo = findViewById(R.id.edit_titulo);

        Button btnPushBlog = findViewById(R.id.btn_push_blog);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        ref = database.getReference();

        btnPushBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String descripcion = edit_descripcion.getText().toString();
                String purl =edit_purl.getText().toString();
                String titulo = edit_titulo.getText().toString();


                if(titulo.isEmpty() || titulo.equals(" ")){
                    Toast.makeText(view.getContext(), "Tiêu đề không được trống", Toast.LENGTH_SHORT).show();
                }else if (descripcion.isEmpty() || descripcion.equals(" ")){
                    Toast.makeText(view.getContext(), "Mô tả không được trống", Toast.LENGTH_SHORT).show();
                }else if (purl.isEmpty() || purl.equals(" ")){
                    Toast.makeText(view.getContext(), "Link ảnh không được trống", Toast.LENGTH_SHORT).show();
                }
                else {
                    Blog blog = new Blog(descripcion, purl, titulo);
                    ref.child("blog").child(String.valueOf(blog.getTitulo())).setValue(blog);
                    Toast.makeText(view.getContext(), "Thêm blog thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Blog_add.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
    public void onBackPressed() {
        Intent intent = new Intent(Blog_add.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}