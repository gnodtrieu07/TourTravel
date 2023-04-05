package com.example.frag.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.frag.MainActivity;
import com.example.frag.R;
import com.example.frag.fragment.BlogFragment;
import com.example.frag.model.Blog;
import com.squareup.picasso.Picasso;

public class BlogDetail extends AppCompatActivity {
    private ImageView imgItemDetail;
    private TextView tvTitleDetail;
    private TextView tvDescripcionDetail;
    private Blog itemDetail;


    //public static final String EXTRA_POST_KEY = "post_key";

    private String mPostKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_detail);

        //mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);

        initViews();

        Bundle bundle = getIntent().getExtras();
        String image = bundle.getString("image");
        String title = bundle.getString("title");
        String des = bundle.getString("des");

        //Bitmap bitmap = (Bitmap) intent. getParcelableExtra("image");
        Picasso.get().load(image).into(imgItemDetail);

        //imgItemDetail.setImageBitmap(bitmap);
        tvTitleDetail.setText(title);
        tvDescripcionDetail.setText(des);

        /*Toast.makeText(this, "image : " +image , Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "title : " +title , Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "des : " +des , Toast.LENGTH_SHORT).show();*/
    }
    private void initViews() {
        imgItemDetail = findViewById(R.id.imgItemDetail);
        tvTitleDetail = findViewById(R.id.tvTitleDetail);
        tvDescripcionDetail = findViewById(R.id.tvDescripcionDetail);
    }
    public void onBackPressed() {
        Intent intent = new Intent(BlogDetail.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}