package com.rabarbar362.foodapp;

public class HowGoodWasTheRestaurantDescriptor {

    public static final String GOOD = "awesome!";
    public static final String OK = "ok, but not the best";
    public static final String BAD = "won't go there anymore";

    public final String howGoodWasTheRestaurant;

    public HowGoodWasTheRestaurantDescriptor(String howGoodWasTheRestaurant) {
        this.howGoodWasTheRestaurant = howGoodWasTheRestaurant;
    }
}