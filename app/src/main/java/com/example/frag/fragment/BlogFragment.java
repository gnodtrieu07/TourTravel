package com.example.frag.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.frag.activity.Blog_add;
import com.example.frag.adapter.BlogAdapter;
import com.example.frag.R;
import com.example.frag.model.Blog;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlogFragment extends Fragment {

    private String mParam1;
    private String mParam2;
    RecyclerView recview;
    BlogAdapter adapter;
    ImageButton blog_remove;
    public BlogFragment() {

    }

    public static BlogFragment newInstance(String param1, String param2) {
        BlogFragment fragment = new BlogFragment();
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
        View view=inflater.inflate(R.layout.fragment_blog, container, false);

        recview=(RecyclerView)view.findViewById(R.id.recview);
        recview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Blog> options =
                new FirebaseRecyclerOptions.Builder<Blog>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("blog"), Blog.class)
                        .build();

        adapter=new BlogAdapter(options);
        recview.setAdapter(adapter);



        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

//    private void XacNhanXoa(){
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//        alertDialog.setTitle("Cảnh báo");
//        alertDialog.show();
//    }

    /*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initValue();

        rvLista = view.findViewById(R.id.rvLista);
        rvLista.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLista.hasFixedSize();
        BlogAdapter adapter = new BlogAdapter(getContext(),arrayList);
        rvLista.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initValue() {
        arrayList = new ArrayList<>();

        arrayList.add(new Blog("Vịnh Hạ Long", "Vịnh Hạ Long nằm thuộc bộ phận của vịnh Bắc Bộ, phía Đông Bắc giáp với vịnh Bái Tử Long, phía Tây Nam giáp với quần đảo Cát Bà, phía Tây và Tây Bắc giáp với đất liền, phía Đông Nam và phía Nam hướng ra vịnh Bắc Bộ. Cảnh quan non nước ngoạn mục trên Vịnh được kiến tạo bởi hơn 1600 hòn đảo đá vôi lớn nhỏ trên làn nước xanh ngọc lục bảo đặc trưng của Vịnh Hạ Long. Đây cũng là nơi chứng kiến những thay đổi trong lịch sử phát triển của Trái đất. Các cột đá vôi được bao phủ bởi các hàng cây nhiệt đới xanh thẳm, cùng hệ thống hang, động đá vôi kỳ vĩ. Vịnh Hạ Long được UNESCO công nhận là Di sản Thiên nhiên Thế giới lần đầu tiên vào năm 1994 nhờ những giá trị cảnh quan tự nhiên độc đáo và quan trọng về mặt thẩm mỹ. Năm 2000, Vịnh Hạ Long vinh dự lần thứ hai được công nhận bởi những giá trị địa chất địa mạo đặc trưng, qua quá trình Trái đất kiến tạo trong hàng tỉ năm. Nhiều du khách không ngờ rằng Hạ Long lại là một Di sản mang giá trị lớn với nhân loại đến vậy bởi sự đa dạng sinh học, văn hóa và lịch sử hiếm có. Nhờ những giá trị độc đáo đó, Vịnh Hạ Long đã trở thành điểm đến hấp dẫn cho du khách, nơi họ có thể đến gần hơn với đất nước, con người Việt Nam sôi động, thú vị.", R.drawable.img));
        arrayList.add(new Blog("Ninh Bình", "Cách thủ đô Hà Nội náo nhiệt không xa nhưng Ninh Bình luôn mang đến cho du khách một cảm giác bình yên và nhẹ nhàng đến kỳ lạ. Một số địa điểm du lịch nổi tiếng tại Ninh Bình khiến khách du lịch xiêu lòng chính là Tràng An, chùa Bái Đính, cố đô Hoa Lư hay Tam Cốc, Bích Động…\n" +
                "\n" +
                "Khu du lịch Tràng An chính là địa điểm du lịch nổi tiếng nhất của Ninh Bình hiện nay. Với phong cảnh thiên nhiên hoang sơ tuyệt đẹp, Tràng An Ninh Bình đã được Unesco công nhận là di sản thiên nhiên và di sản văn hóa thế giới.", R.drawable.img_10));
        arrayList.add(new Blog("Phong Nha - Kẻ Bàng", "Động Phong Nha là danh thắng tiêu biểu nhất của hệ thống hang động thuộc quần thể danh thắng Phong Nha – Kẻ Bàng. Phong Nha được bình chọn là một trong những hang động đẹp nhất thế giới với các tiêu chí: Sông ngầm dài nhất, Hồ nước ngầm đẹp nhất. Cửa hang cao và rộng nhất, Các bãi cát, bãi đá ngầm đẹp nhất, Hang khô rộng và đẹp nhất, Hệ thống thạch nhũ kỳ ảo và tráng lệ nhất, Hang động nước dài nhất. Động Phong Nha là một điểm đến được nhiều du khách lựa chọn trong chuyến du lịch Quảng Bình.", R.drawable.img_1));
        arrayList.add(new Blog("Hội An", "Nhắc đến du lịch Việt Nam thì Hội An là một cái tên không thể không nhắc đến. Hội An mang dáng dấp xưa cũ với nếp sống nhẹ nhàng được nhiều khách du lịch chọn lựa. Hiện tại, du lịch tại Hội An rất phát triển với đông đảo khách du lịch trong và ngoài nước đến hàng năm. Ở đây cũng có rất nhiều địa điểm để tham quan và chụp ảnh.\n" +
                "\n" +
                "Phố cổ Hội An là một phố cổ nằm ven biển của tỉnh miền Trung cách thành phố Đà Nẵng 30km về phía Nam, thuộc tỉnh Quảng nam Việt Nam. Phố cổ Hội An điển hình cho một thương cảng thị trường sầm uất, nơi này từng có dấu tích của thương cảng người Chăm, được biết đến với con đường tơ lụa nổi tiếng. trong hai cuộc chiến tranh lớn của việt Nam không bị tàn phá nơi đây chính thức trở thành nơi du lịch hấp dẫn được nhiều du khách bốn phương biết tới.", R.drawable.img_4));
        arrayList.add(new Blog("Cầu Vàng - Kiệt tác giữa núi rừng", "Là một trong những địa điểm du lịch mới vô cùng nổi tiếng, Cầu Vàng Đà Nẵng trên Bà Nà Hills đã khiến dân tình phải trầm trồ, xốn xang bởi vẻ đẹp kỳ vĩ. Từ lần đầu xuất hiện Cầu Vàng không chỉ tạo nên cơn sốt trong nước mà còn gây \"chấn động\" khắp thế giới\n" + "\n" + "Cầu Vàng thuộc khu du lịch Sun World Ba Na Hills, sau 1 năm thiết kế và 1 năm thi công, cây cầu chính thức được khánh thành vào tháng 6/2018. Giống như tên gọi của mình, Cầu Vàng có một màu vàng đặc trưng lấp lánh và nổi bật giữa thiên nhiên xanh mát.", R.drawable.img_7));
        arrayList.add(new Blog("Chợ nổi Cái Răng Cần Thơ", "Chợ nổi Cái Răng là loại hình chợ độc đáo và đặc trưng của vùng đồng bằng sông Cửu Long mà không nơi nào trên đất nước Việt Nam có được. Và đây cũng là khu chợ tiêu biểu, sầm uất, nổi tiếng nhất cho nét văn hóa sông nước miền Tây. Chợ nổi Cái Răng Cần Thơ sẽ mang đến những trải nghiệm có 1-0-2 cho bạn như chiêm ngưỡng khung cảnh nên thơ của chợ nổi lúc bình minh, ngồi lênh đênh trên thuyền hòa mình vào không khí buôn bán tấp nập sáng sớm và thưởng thức tô bún riêu ngay trên ghe thuyền… vô cùng tuyệt vời. ", R.drawable.img_8));
    }*/

}