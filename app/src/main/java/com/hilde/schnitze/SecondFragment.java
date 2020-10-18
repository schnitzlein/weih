package com.hilde.schnitze;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

public class SecondFragment extends Fragment {

    DataManager mydata;
    TextView tv;
    private Button nextButton;
    String ui_food_name;

    private View.OnClickListener nextButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            nextButtonClicked();
        }
    };

    private void nextButtonClicked() {
        ui_food_name = mydata.getRandomFoodName();
        tv.setText(ui_food_name);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //savedInstanceState.getBundle("transport_data");

        nextButton = view.findViewById(R.id.button_second);
        nextButton.setOnClickListener(nextButtonOnClickListener);

        tv = (TextView)view.findViewById(R.id.textview_second);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        mydata = new DataManager(this.getContext());



        /*
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
                //mytext = mydata.getRandomFoodName();
                //tv.setText(mytext);
            }
        });
        */

        ui_food_name = mydata.loadCurrentFood();
        tv.setText(ui_food_name);
        //tv.setText("nix");

    }

    @Override
    public void onStop() {
        super.onStop();

        Log.i("SYSTEM", "Fragment onStop called");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("SYSTEM", "Fragment onStart called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("SYSTEM", "Fragment onResume called");
        tv.setText(mydata.loadCurrentFood());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("SYSTEM", "Fragment onPause called");
        mydata.saveCurrentFood(tv.getText().toString());
    }

}