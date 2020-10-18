package com.hilde.schnitze;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;



public class SettingsDataActivity extends AppCompatActivity {

    private TextView mTextView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_data); //activity_settings_data

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragmentAdd(context))
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
       // mTextView = (TextView) findViewById(R.id.text);

    }

    @Override
    public boolean onSupportNavigateUp() {
        this.finish();
        return true;
        //return super.onSupportNavigateUp();
    }


}