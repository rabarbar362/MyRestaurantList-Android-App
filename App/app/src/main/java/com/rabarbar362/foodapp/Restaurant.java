package com.rabarbar362.foodapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity (tableName = "restaurant_table")
public class Restaurant {

    @PrimaryKey (autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo (name="restaurant_name")
    private String restaurantName;
    @NonNull
    @ColumnInfo (name = "commentary")
    private String restaurantCommentary;
    @NonNull
    @ColumnInfo (name = "how_good")
    private String howGoodIsTheRestaurant;
    @NonNull
    @ColumnInfo (name = "latitude")
    private String locationLatitude;
    @NonNull
    @ColumnInfo (name = "longitude")
    private String locationLongitude;

    public Restaurant(String restaurantName, String howGoodIsTheRestaurant, String restaurantCommentary, String locationLatitude, String locationLongitude) {
        this.restaurantName = restaurantName;
        this.howGoodIsTheRestaurant = howGoodIsTheRestaurant;
        this.restaurantCommentary = restaurantCommentary;
        this.locationLatitude = locationLatitude;
        this.locationLongitude = locationLongitude;
    }

    @NonNull
    public int getId() {
        return id;
    }

    @NonNull
    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getRestaurantName() {
        return restaurantName;
    }

    @NonNull
    public String getHowGoodIsTheRestaurant() {
        return howGoodIsTheRestaurant;
    }

    @NonNull
    public String getRestaurantCommentary() {
        return restaurantCommentary;
    }

    @NonNull
    public String getLocationLatitude() {
        return locationLatitude;
    }

    @NonNull
    public String getLocationLongitude() {
        return locationLongitude;
    }

    public void setRestaurantName(@NonNull String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public void setRestaurantCommentary(@NonNull String restaurantCommentary) {
        this.restaurantCommentary = restaurantCommentary;
    }

    public void setHowGoodIsTheRestaurant(@NonNull String howGoodIsTheRestaurant) {
        this.howGoodIsTheRestaurant = howGoodIsTheRestaurant;
    }

    public void setLocationLatitude(@NonNull String locationLatitude) {
        this.locationLatitude = locationLatitude;
    }

    public void setLocationLongitude(@NonNull String locationLongitude) {
        this.locationLongitude = locationLongitude;
    }
}
