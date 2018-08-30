package com.rabarbar362.foodapp;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Objects;

public class SelectedRestaurantViewFragment extends Fragment implements OnMapReadyCallback {

    RestaurantDataCommunication mCallback;
    private SupportMapFragment mapSupportFragment;
    private TextView restaurantName,restaurantEvaluation,restaurantComment;
    private double rLatitude, rLongitude;
    private ImageButton deleteButton;
    private int position;
    RestaurantListAdapter resListAdapter;
    List<Restaurant> listOfRestaurants;
    Restaurant selectedRestaurant;

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
        View selectedRestaurantView = inflater.inflate(R.layout.fragment_selected_restaurant_view, null);
        restaurantName = selectedRestaurantView.findViewById(R.id.restaurant_name);
        restaurantEvaluation = selectedRestaurantView.findViewById(R.id.restaurant_evaluation);
        restaurantComment = selectedRestaurantView.findViewById(R.id.restaurant_commentary);
        deleteButton = selectedRestaurantView.findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteFromDBAsync().execute();
                changeFragmentToHome();
                Toast.makeText(getContext(),"Deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });

        Bundle selectedResPositionBundle = getArguments();
        position = selectedResPositionBundle.getInt("positionClicked");
        resListAdapter = mCallback.getRestaurantListAdapter();
        listOfRestaurants = resListAdapter.getmRestaurants();

        selectedRestaurant = listOfRestaurants.get(position);
        restaurantName.setText(selectedRestaurant.getRestaurantName());
        restaurantEvaluation.setText(selectedRestaurant.getHowGoodIsTheRestaurant());
        restaurantComment.setText(selectedRestaurant.getRestaurantCommentary());

        switch (selectedRestaurant.getHowGoodIsTheRestaurant()) {
            case HowGoodWasTheRestaurantDescriptor.GOOD:
                restaurantEvaluation.setTextColor(Color.parseColor("#00b015"));
                break;
            case HowGoodWasTheRestaurantDescriptor.OK:
                restaurantEvaluation.setTextColor(Color.parseColor("#ff9e00"));
                break;
            case HowGoodWasTheRestaurantDescriptor.BAD:
                restaurantEvaluation.setTextColor(Color.parseColor("#ee1010"));
                break;
        }

        return selectedRestaurantView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mapSupportFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.selectedRestaurantMap);
        mapSupportFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        rLatitude = Double.parseDouble(selectedRestaurant.getLocationLatitude());
        rLongitude = Double.parseDouble(selectedRestaurant.getLocationLongitude());
        LatLng restaurantLocalization = new LatLng(rLatitude, rLongitude);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocalization, 14));
        Marker restaurantMarker = googleMap.addMarker(new MarkerOptions().position(restaurantLocalization));
    }

    public void changeFragmentToHome() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_home, homeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private class DeleteFromDBAsync extends AsyncTask<Object, Void, List<Restaurant>> {

        @Override
        protected List<Restaurant> doInBackground(Object... params) {
            RestaurantRoomDatabase.getDatabase(getContext()).restaurantDao().deleteRestaurant(selectedRestaurant);
            return null;
        }
    }
}
