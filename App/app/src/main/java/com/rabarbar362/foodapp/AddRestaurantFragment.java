package com.rabarbar362.foodapp;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddRestaurantFragment extends Fragment implements View.OnClickListener {

    RestaurantDataCommunication mCallback;
    private Button nextStepButton;
    private EditText restaurantName;
    private RadioGroup howGoodForm;
    private RadioButton selectedRadioButton;
    private String howGood;
    private EditText restaurantCommentary;
    private String resName;
    private String resComment;
    private String resEvaluation;

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

        View addRestaurantView = inflater.inflate(R.layout.fragment_add_restaurant, null);
        nextStepButton = addRestaurantView.findViewById(R.id.button_go_to_choose_location);
        restaurantName = addRestaurantView.findViewById(R.id.restaurant_name_form);
        howGoodForm = addRestaurantView.findViewById(R.id.how_good_radio_group);
        selectedRadioButton = howGoodForm.findViewById(howGoodForm.getCheckedRadioButtonId());
        howGoodForm.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checked) {

                switch (checked) {
                    case R.id.radioButtonVeryGood:
                        selectedRadioButton = howGoodForm.findViewById(checked);
                        howGood = HowGoodWasTheRestaurantDescriptor.GOOD;
                        break;
                    case R.id.radioButtonOK:
                        selectedRadioButton = howGoodForm.findViewById(checked);
                        howGood = HowGoodWasTheRestaurantDescriptor.OK;
                        break;
                    case R.id.radioButtonBad:
                        selectedRadioButton = howGoodForm.findViewById(checked);
                        howGood = HowGoodWasTheRestaurantDescriptor.BAD;
                        break;
                }
            }
        });
        restaurantCommentary = addRestaurantView.findViewById(R.id.commentary_text_form);
        nextStepButton.setOnClickListener(this);
        return addRestaurantView;
    }

    @Override
    public void onClick(View view) {

        resName = restaurantName.getText().toString().trim();
        resComment = restaurantCommentary.getText().toString().trim();
        resEvaluation = howGood;

        if(resName.equals("") || resComment.equals("") || resEvaluation.equals("")) {
           showAlert();
        } else {
            mCallback.setResName(resName);
            mCallback.setHowGood(resEvaluation);
            mCallback.setCommentary(resComment);

            AddRestaurantMapFragment addRestaurantMapFragment = new AddRestaurantMapFragment();
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_home, addRestaurantMapFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    public void showAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Please fill in all fields");

        builder.setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


