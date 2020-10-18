package com.hilde.schnitze;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // https://www.accentsconagua.com/articles/code/android-sdk-implement-a-share-intent.html
        /*
        ImageButton sharingButton = new ImageButton (view.getContext());
        ViewGroup.LayoutParams neue = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sharingButton.setLayoutParams (neue);
        sharingButton.setImageResource (R.drawable.sharing);
        //sharingButton.setOnClickListener (new View.OnClickListener () public void onClick (Ansicht v) shareIt (););

         */
    }
}