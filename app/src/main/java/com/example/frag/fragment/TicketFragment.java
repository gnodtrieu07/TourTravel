package com.example.frag.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.frag.adapter.BestSaleAdapter;
import com.example.frag.adapter.MyTourAdapter;
import com.example.frag.R;
import com.example.frag.model.Ticket;
import com.example.frag.model.Tour;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TicketFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TicketFragment extends Fragment {

    private RecyclerView rcvmytour;
    FirebaseRecyclerAdapter<Ticket, MyTourAdapter.TourViewHolder> adapter;
    private MyTourAdapter tourAdapter;
    private DatabaseReference ref;

    public TicketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TicketFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TicketFragment newInstance(String param1, String param2) {
        TicketFragment fragment = new TicketFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_ticket, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String email = user.getEmail().toString().trim();

        int a =email.indexOf("@");
        String email1 = email.substring(0, a);


        rcvmytour=(RecyclerView)view.findViewById(R.id.rcvmytour);
        rcvmytour.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<Ticket> options =
                new FirebaseRecyclerOptions.Builder<Ticket>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("ticket").child(email1.toString()), Ticket.class)
                        .build();

        adapter=new MyTourAdapter(options);
        rcvmytour.setAdapter(adapter);

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
/*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initValue();

        rvLista = view.findViewById(R.id.rcvmytour);
        rvLista.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLista.hasFixedSize();

        rvLista.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /*private void initValue() {
        arrayList = new ArrayList<>();

        arrayList.add(new item(R.drawable.a2,"Vịnh Hạ Long", "Vịnh Hạ Long nằm thuộc bộ phận của vịnh Bắc Bộ, phía Đông Bắc giáp với vịnh Bái Tử Long, phía Tây Nam giáp với quần đảo Cát Bà, phía Tây và Tây Bắc giáp với đất liền, phía Đông Nam và phía Nam hướng ra vịnh Bắc Bộ. Cảnh quan non nước ngoạn mục trên Vịnh được kiến tạo bởi hơn 1600 hòn đảo đá vôi lớn nhỏ trên làn nước xanh ngọc lục bảo đặc trưng của Vịnh Hạ Long. Đây cũng là nơi chứng kiến những thay đổi trong lịch sử phát triển của Trái đất. Các cột đá vôi được bao phủ bởi các hàng cây nhiệt đới xanh thẳm, cùng hệ thống hang, động đá vôi kỳ vĩ. Vịnh Hạ Long được UNESCO công nhận là Di sản Thiên nhiên Thế giới lần đầu tiên vào năm 1994 nhờ những giá trị cảnh quan tự nhiên độc đáo và quan trọng về mặt thẩm mỹ. Năm 2000, Vịnh Hạ Long vinh dự lần thứ hai được công nhận bởi những giá trị địa chất địa mạo đặc trưng, qua quá trình Trái đất kiến tạo trong hàng tỉ năm. Nhiều du khách không ngờ rằng Hạ Long lại là một Di sản mang giá trị lớn với nhân loại đến vậy bởi sự đa dạng sinh học, văn hóa và lịch sử hiếm có. Nhờ những giá trị độc đáo đó, Vịnh Hạ Long đã trở thành điểm đến hấp dẫn cho du khách, nơi họ có thể đến gần hơn với đất nước, con người Việt Nam sôi động, thú vị."));
        arrayList.add(new item(R.drawable.a3,"Ninh Bình", "Cách thủ đô Hà Nội náo nhiệt không xa nhưng Ninh Bình luôn mang đến cho du khách một cảm giác bình yên và nhẹ nhàng đến kỳ lạ. Một số địa điểm du lịch nổi tiếng tại Ninh Bình khiến khách du lịch xiêu lòng chính là Tràng An, chùa Bái Đính, cố đô Hoa Lư hay Tam Cốc, Bích Động…\n" +
                "\n" +
                "Khu du lịch Tràng An chính là địa điểm du lịch nổi tiếng nhất của Ninh Bình hiện nay. Với phong cảnh thiên nhiên hoang sơ tuyệt đẹp, Tràng An Ninh Bình đã được Unesco công nhận là di sản thiên nhiên và di sản văn hóa thế giới."));
        arrayList.add(new item(R.drawable.a4,"Nghe An", "Cách thủ đô Hà Nội náo nhiệt không xa nhưng Ninh Bình luôn mang đến cho du khách một cảm giác bình yên và nhẹ nhàng đến kỳ lạ. Một số địa điểm du lịch nổi tiếng tại Ninh Bình khiến khách du lịch xiêu lòng chính là Tràng An, chùa Bái Đính, cố đô Hoa Lư hay Tam Cốc, Bích Động…\n" +
                "\n" +
                "Khu du lịch Tràng An chính là địa điểm du lịch nổi tiếng nhất của Ninh Bình hiện nay. Với phong cảnh thiên nhiên hoang sơ tuyệt đẹp, Tràng An Ninh Bình đã được Unesco công nhận là di sản thiên nhiên và di sản văn hóa thế giới."));
        arrayList.add(new item(R.drawable.a5,"Khanh hoa", "Cách thủ đô Hà Nội náo nhiệt không xa nhưng Ninh Bình luôn mang đến cho du khách một cảm giác bình yên và nhẹ nhàng đến kỳ lạ. Một số địa điểm du lịch nổi tiếng tại Ninh Bình khiến khách du lịch xiêu lòng chính là Tràng An, chùa Bái Đính, cố đô Hoa Lư hay Tam Cốc, Bích Động…\n" +
                "\n" +
                "Khu du lịch Tràng An chính là địa điểm du lịch nổi tiếng nhất của Ninh Bình hiện nay. Với phong cảnh thiên nhiên hoang sơ tuyệt đẹp, Tràng An Ninh Bình đã được Unesco công nhận là di sản thiên nhiên và di sản văn hóa thế giới."));
        arrayList.add(new item(R.drawable.a1,"Ha Noi", "Cách thủ đô Hà Nội náo nhiệt không xa nhưng Ninh Bình luôn mang đến cho du khách một cảm giác bình yên và nhẹ nhàng đến kỳ lạ. Một số địa điểm du lịch nổi tiếng tại Ninh Bình khiến khách du lịch xiêu lòng chính là Tràng An, chùa Bái Đính, cố đô Hoa Lư hay Tam Cốc, Bích Động…\n" +
                "\n" +
                "Khu du lịch Tràng An chính là địa điểm du lịch nổi tiếng nhất của Ninh Bình hiện nay. Với phong cảnh thiên nhiên hoang sơ tuyệt đẹp, Tràng An Ninh Bình đã được Unesco công nhận là di sản thiên nhiên và di sản văn hóa thế giới."));

    }*/
}