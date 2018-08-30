package com.rabarbar362.foodapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class RestaurantViewModel extends AndroidViewModel {

    private RestaurantsRepository mRepository;
    private LiveData<List<Restaurant>> mAllRes;

    public RestaurantViewModel(@NonNull Application application) {
        super(application);
        mRepository = new RestaurantsRepository(application);
        mAllRes = mRepository.getAllRestaurants();
    }

    public LiveData<List<Restaurant>> getmAllRes() {
        return mAllRes;
    }
}
