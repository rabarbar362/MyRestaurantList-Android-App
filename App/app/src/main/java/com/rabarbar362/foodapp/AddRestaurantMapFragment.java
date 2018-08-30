package com.rabarbar362.foodapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class AddRestaurantMapFragment extends Fragment implements OnMapReadyCallback {

    RestaurantDataCommunication mCallback;
    private SupportMapFragment mapSupportFragment;
    private Button addRestaurantButton;

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
        View addRestaurantOnMapView = inflater.inflate(R.layout.fragment_add_restaurant_map, null);
        addRestaurantButton = addRestaurantOnMapView.findViewById(R.id.addRestaurantButton);
        addRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SaveToDatabaseAsync().execute();
                changeFragmentToHome();
                Toast.makeText(getContext(),"Added successfully", Toast.LENGTH_SHORT).show();
            }
        });
        addRestaurantButton.setEnabled(false);
        return addRestaurantOnMapView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mapSupportFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapAddRestaurant);
        mapSupportFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        LatLng Cracow = new LatLng(50.049683, 19.944544);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Cracow, 12));

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                googleMap.clear();
                Marker restaurantMarker = googleMap.addMarker(new MarkerOptions().position(point));
                LatLng restaurantLatLng = restaurantMarker.getPosition();
                Double restaurantLat = restaurantLatLng.latitude;
                Double restaurantLng = restaurantLatLng.longitude;
                mCallback.setLatitude(restaurantLat.toString());
                mCallback.setLongitude(restaurantLng.toString());
                addRestaurantButton.setEnabled(true);
            }
        });
    }

    public void changeFragmentToHome() {
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_home, homeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private class SaveToDatabaseAsync extends AsyncTask<Object, Void, List<Restaurant>> {

        private String resName = mCallback.getResName();
        private String howGood = mCallback.getHowGood();
        private String commentary = mCallback.getCommentary();
        private String latitude = mCallback.getLatitude();
        private String longitude = mCallback.getLongitude();

        @Override
        protected List<Restaurant> doInBackground(Object... params) {
            Restaurant restaurant = new Restaurant(resName, howGood, commentary, latitude, longitude);
            RestaurantRoomDatabase.getDatabase(getContext()).restaurantDao().insert(restaurant);
            return null;
        }
    }
}

