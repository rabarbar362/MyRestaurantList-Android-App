package com.rabarbar362.foodapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener, RestaurantDataCommunication {


    private String resName, howGood, commentary, latitude, longitude;
    private RestaurantListAdapter restaurantListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new HomeFragment());
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home, fragment).commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        Fragment fragment = null;

        switch (menuItem.getItemId()) {

            case R.id.navigation_home:
                fragment = new HomeFragment();
                break;

            case R.id.navigation_map:
                fragment = new MapsFragment();
                break;

            case R.id.navigation_add:
                fragment = new AddRestaurantFragment();
                break;
        }

        return loadFragment(fragment);
    }

    @Override
    public String getResName() {
        return resName;
    }

    @Override
    public String getHowGood() {
        return howGood;
    }

    @Override
    public String getCommentary() {
        return commentary;
    }

    @Override
    public String getLatitude() {
        return latitude;
    }

    @Override
    public String getLongitude() {
        return longitude;
    }

    @Override
    public RestaurantListAdapter getRestaurantListAdapter() {
        return restaurantListAdapter;
    }

    @Override
    public void setResName(String name) {
        this.resName = name;
    }

    @Override
    public void setHowGood(String howGood) {
        this.howGood = howGood;
    }

    @Override
    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    @Override
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public void setRestaurantListAdapter(RestaurantListAdapter restaurantListAdapter) {
        this.restaurantListAdapter = restaurantListAdapter;
    }
}
