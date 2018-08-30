package com.rabarbar362.foodapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class HomeFragment extends Fragment {

    RestaurantDataCommunication mCallback;
    private RestaurantViewModel mRestaurantViewModel;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (RestaurantDataCommunication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement RestaurantDataCommunication");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View browseRestaurantList = inflater.inflate(R.layout.fragment_home, null);

        RecyclerView recyclerView = browseRestaurantList.findViewById(R.id.recyclerview);
        final RestaurantListAdapter adapter = new RestaurantListAdapter(this.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mRestaurantViewModel = ViewModelProviders.of(this).get(RestaurantViewModel.class);

        mRestaurantViewModel.getmAllRes().observe(this, new Observer<List<Restaurant>>() {
            @Override
            public void onChanged(@Nullable final List<Restaurant> restaurants) {
                adapter.setRestaurants(restaurants);
            }
        });
        mCallback.setRestaurantListAdapter(adapter);

        return browseRestaurantList;

    }
}