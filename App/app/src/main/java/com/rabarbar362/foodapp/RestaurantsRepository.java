package com.rabarbar362.foodapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;
public class RestaurantsRepository {

    private RestaurantDao mRestaurantDao;
    private LiveData<List<Restaurant>> mAllRestaurants;

    RestaurantsRepository(Application application) {
        RestaurantRoomDatabase db = RestaurantRoomDatabase.getDatabase(application);
        mRestaurantDao = db.restaurantDao();
        mAllRestaurants = mRestaurantDao.getAllRestaurants();
    }

    LiveData<List<Restaurant>> getAllRestaurants() {
        return mAllRestaurants;
    }


    public void insert (Restaurant restaurant) {
        new insertAsyncTask(mRestaurantDao).execute(restaurant);
    }

    private static class insertAsyncTask extends AsyncTask<Restaurant, Void, Void> {

        private RestaurantDao mAsyncTaskDao;

        insertAsyncTask(RestaurantDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Restaurant... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
