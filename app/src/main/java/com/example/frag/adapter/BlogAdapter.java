package com.example.frag.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.frag.R;
import com.example.frag.activity.BlogDetail;
import com.example.frag.model.Blog;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class BlogAdapter extends FirebaseRecyclerAdapter<Blog,BlogAdapter.BlogViewHolder>{


    public BlogAdapter(@NonNull FirebaseRecyclerOptions<Blog> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BlogViewHolder holder, int position, @NonNull final Blog model) {
        holder.tvTitulo.setText(model.getTitulo());
        holder.tvDescripcion.setText(model.getDescripcion());

        Glide.with(holder.imgItem.getContext()).load(model.getPurl()).into(holder.imgItem);

        holder.imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, BlogDetail.class);
                intent.putExtra("title", model.getTitulo());
                intent.putExtra("des", model.getDescripcion());
                intent.putExtra("image", model.getPurl());
                context.startActivity(intent);
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail().toString().trim();
        if ( email.equals("admin@gmail.com") ){
            holder.blog_remove.setVisibility(View.VISIBLE);
            holder.blog_edit.setVisibility(View.VISIBLE);
        }

        holder.blog_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("Bạn có chắc muốn xoá ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                Query applesQuery = ref.child("blog").orderByChild("titulo").equalTo(model.getTitulo());
                                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                            appleSnapshot.getRef().removeValue();
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

        holder.blog_edit.setOnClickListener(new View.OnClickListener() {

            private DatabaseReference ref;


            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_dialog_edit_blog);

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


                Button btn_edit_blog = dialog.findViewById(R.id.btn_edit_blog);
                EditText blog_edit_descripcion = dialog.findViewById(R.id.blog_edit_descripcion);
                EditText blog_edit_purl = dialog.findViewById(R.id.blog_edit_purl);
                EditText blog_edit_titulo = dialog.findViewById(R.id.blog_edit_titulo);

                blog_edit_descripcion.setText(model.getDescripcion());
                blog_edit_purl.setText(model.getPurl());
                blog_edit_titulo.setText(model.getTitulo());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                ref = database.getReference();

                btn_edit_blog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String descripcion = blog_edit_descripcion.getText().toString();
                        String purl =blog_edit_purl.getText().toString();
                        String titulo = blog_edit_titulo.getText().toString();

                        if(titulo.isEmpty() || titulo.equals(" ")) {
                            Toast.makeText(view.getContext(),"ID không được bỏ trống",Toast.LENGTH_LONG).show();
                        }else if (descripcion.isEmpty() || descripcion.equals(" ")){
                            Toast.makeText(view.getContext(), "Mô tả không được trống", Toast.LENGTH_SHORT).show();
                        }else if (purl.isEmpty() || purl.equals(" ")){
                            Toast.makeText(view.getContext(), "Link ảnh không được trống", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Blog blog = new Blog(descripcion, purl, titulo);
                            ref.child("blog").child(String.valueOf(blog.getTitulo())).setValue(blog);

                            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                            Query applesQuery = ref1.child("blog").orderByChild("titulo").equalTo(model.getTitulo());
                            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot appleSnapshot : dataSnapshot.getChildren()) {
                                        appleSnapshot.getRef().removeValue();
                                        Toast.makeText(view.getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
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

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_item_list_view,parent,false);
        return new BlogViewHolder(view);
    }

    public class BlogViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgItem;
        TextView tvTitulo,tvDescripcion;
        ImageButton blog_remove;
        ImageButton blog_edit;



        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);

            imgItem=itemView.findViewById(R.id.imgItem);
            tvTitulo=itemView.findViewById(R.id.tvTitulo);
            tvDescripcion=itemView.findViewById(R.id.tvDescripcion);
            blog_remove=itemView.findViewById(R.id.blog_remove);
            blog_edit = itemView.findViewById(R.id.blog_edit);

        }
    }

    private void openDialogEditBlog(int gravity){

    }

}
