package com.example.frag.fragment;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frag.R;
import com.example.frag.adapter.BestSaleAdapter;

import com.example.frag.model.Tour;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    private View mView;
    private ArrayList<Tour> listitem;
    private BestSaleAdapter photo1Adapter;


    private ArrayList<Tour> arrayList;
    private RecyclerView home1_viewpager1;

    private MenuItem menuItem;
    private SearchView searchView;


    FirebaseRecyclerAdapter<Tour, BestSaleAdapter.TourViewHolder> adapter;
    private DatabaseReference ref;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        menuItem = menu.findItem(R.id.searchId);
        inflater.inflate(R.menu.menu_item,menu);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.searchId));
        searchView.setIconified(true);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mysearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                mysearch(query);

                return true;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }


    private void mysearch(String query) {

        String a =query.toLowerCase().toString();
        FirebaseRecyclerOptions<Tour> optionBestSale =
                new FirebaseRecyclerOptions.Builder<Tour>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("tour").orderByChild("name").startAt(query).endAt(query + "\uf8ff"), Tour.class)
                        .build();


        photo1Adapter = new BestSaleAdapter(optionBestSale);
        photo1Adapter.startListening();
        home1_viewpager1.setAdapter(photo1Adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        home1_viewpager1=(RecyclerView)view.findViewById(R.id.home1_viewpager1);
        home1_viewpager1.setLayoutManager(new GridLayoutManager(this.getContext(),2));

        FirebaseRecyclerOptions<Tour> optionBestSale =
                new FirebaseRecyclerOptions.Builder<Tour>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("tour"), Tour.class)
                        .build();

        adapter=new BestSaleAdapter(optionBestSale);
        home1_viewpager1.setAdapter(adapter);



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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ref = FirebaseDatabase.getInstance().getReference();
        listitem=new ArrayList<>();

    }


}
