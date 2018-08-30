package com.rabarbar362.foodapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder> {

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameItemView;
        private final TextView howGoodItemView;
        private final TextView commentaryItemView;
        private ImageButton selected_item;

        private RestaurantViewHolder(View itemView) {
            super(itemView);
            nameItemView = itemView.findViewById(R.id.txtRestaurantName);
            howGoodItemView = itemView.findViewById(R.id.txtRestaurantEvaluation);
            commentaryItemView = itemView.findViewById(R.id.txtRestaurantCommentary);
            selected_item = itemView.findViewById(R.id.select_restaurant);
        }
    }

    private final LayoutInflater mInflater;
    private List<Restaurant> mRestaurants; // Cached copy of words
    private Context mContext;
    private int adapterPosition;

    RestaurantListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_item, parent, false);
        final RestaurantViewHolder rHolder = new RestaurantViewHolder(itemView);

        rHolder.selected_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterPosition = rHolder.getAdapterPosition();
                Bundle adapterPositionBundle = new Bundle();
                adapterPositionBundle.putInt("positionClicked", adapterPosition);
                SelectedRestaurantViewFragment selectedRestaurantViewFragment = new SelectedRestaurantViewFragment();
                MainActivity myActivity = (MainActivity)mContext;
                FragmentTransaction fragmentTransaction = myActivity.getSupportFragmentManager().beginTransaction();
                selectedRestaurantViewFragment.setArguments(adapterPositionBundle);
                fragmentTransaction.replace(R.id.fragment_container_home, selectedRestaurantViewFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return rHolder;
    }


    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, final int position) {
        if (mRestaurants != null) {
            Restaurant current = mRestaurants.get(position);
            holder.nameItemView.setText(current.getRestaurantName());
            holder.howGoodItemView.setText(current.getHowGoodIsTheRestaurant());
            holder.commentaryItemView.setText(current.getRestaurantCommentary());

            switch (current.getHowGoodIsTheRestaurant()) {
                case HowGoodWasTheRestaurantDescriptor.GOOD:
                    holder.howGoodItemView.setTextColor(Color.parseColor("#00b015"));
                    break;
                case HowGoodWasTheRestaurantDescriptor.OK:
                    holder.howGoodItemView.setTextColor(Color.parseColor("#ff9e00"));
                    break;
                case HowGoodWasTheRestaurantDescriptor.BAD:
                    holder.howGoodItemView.setTextColor(Color.parseColor("#ee1010"));
                    break;
            }
        } else {
            holder.nameItemView.setText("");
        }
    }

    void setRestaurants(List<Restaurant> restaurants){
        mRestaurants = restaurants;
        notifyDataSetChanged();
    }

    public List<Restaurant> getmRestaurants() {
        return mRestaurants;
    }

    @Override
    public int getItemCount() {
        if (mRestaurants != null)
            return mRestaurants.size();
        else return 0;
    }
}
