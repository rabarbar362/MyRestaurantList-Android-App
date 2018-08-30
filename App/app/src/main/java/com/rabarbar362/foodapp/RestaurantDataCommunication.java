package com.rabarbar362.foodapp;

public interface RestaurantDataCommunication {

     String getResName();
     String getHowGood();
     String getCommentary();
     String getLatitude();
     String getLongitude();
     RestaurantListAdapter getRestaurantListAdapter();

     void setResName(String name);
     void setHowGood(String howGood);
     void setCommentary(String commentary);
     void setLatitude(String latitude);
     void setLongitude(String longitude);
     void setRestaurantListAdapter(RestaurantListAdapter restaurantListAdapter);

}
